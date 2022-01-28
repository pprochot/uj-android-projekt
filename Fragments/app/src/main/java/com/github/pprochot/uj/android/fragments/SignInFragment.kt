package com.github.pprochot.uj.android.fragments

import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.domain.request.UserRequest
import com.github.pprochot.uj.android.domain.response.UserResponse
import com.github.pprochot.uj.android.mappers.UserMapper
import com.github.pprochot.uj.android.realmmodels.User
import com.github.pprochot.uj.android.services.UserService
import com.github.pprochot.uj.android.valdiators.SignInValidator
import com.github.pprochot.uj.android.viewmodels.LoginActivityViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import org.mindrot.jbcrypt.BCrypt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    companion object {
        val toSignUpFragment = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        const val RC_SIGN_IN = 1
    }

    private val loginActivityViewModel: LoginActivityViewModel by activityViewModels()

    @Inject
    lateinit var realm: Realm

    @Inject
    lateinit var userMapper: UserMapper

    @Inject
    lateinit var userService: UserService

    private var nicknameView: TextInputEditText? = null
    private var passwordView: TextInputEditText? = null
    private var signInButton: Button? = null
    private var googleSignInButton: SignInButton? = null
    private lateinit var navController: NavController

    override fun onStart() {
        super.onStart()

        nicknameView = view?.findViewById(R.id.nickname_inputedittext_login)
        passwordView = view?.findViewById(R.id.password_inputedittext_login)
        signInButton = view?.findViewById(R.id.sign_in_button)
        googleSignInButton = view?.findViewById(R.id.google_sign_in_button)
        navController = findNavController()

        googleSignInButton?.setOnClickListener {
            signInByGoogle()
        }

        signInButton?.setOnClickListener {
            signInByRealm()
        }
        view?.findViewById<Button>(R.id.go_to_register_button)?.setOnClickListener {
            navController.navigate(toSignUpFragment)
        }
    }

    private fun signInByGoogle() {
        val signInIntent = loginActivityViewModel.mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val realmUser = realm.where(User::class.java).equalTo("name", account.email)
                .findFirst()
            if (realmUser == null) {
                registerOauthUser(UserRequest(account.email!!, "", true))
            } else if (realmUser.isOauthUser) {
                val toMainActivity =
                    SignInFragmentDirections.actionSignInFragmentToMainActivity(realmUser.id)
                findNavController().navigate(toMainActivity)
            } else {
                Toast.makeText(
                    requireContext(), "User is not an Oauth user.", Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(
                requireContext(), "Failed to log in, try again.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun registerOauthUser(userRequest: UserRequest) {
        userService.create(userRequest).enqueue(ServiceRegisterCallBack())
    }

    private inner class ServiceRegisterCallBack :
        Callback<UserResponse> {

        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
            if (response.isSuccessful && response.body() != null) {
                val user = userMapper.map(response.body()!!)
                realm.executeTransaction {
                    it.insert(user)
                }
                val toMainActivity =
                    SignInFragmentDirections.actionSignInFragmentToMainActivity(user.id)
                navController.navigate(toMainActivity)
            } else if (response.code() == 409) { // Conflict, it means user already exists
                Toast.makeText(requireContext(), "User is already registered!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            Toast.makeText(requireContext(), "Could not sign up. Try again.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun signInByRealm() {
        signInButton?.isClickable = false
        val nickname = nicknameView?.text.toString()
        val password = passwordView?.text.toString()

        if (isValidInput(nickname, password)) {
            val user = realm.where(User::class.java).equalTo("name", nickname).findFirst()
            if (user == null) {
                Toast.makeText(
                    requireContext(), "User does not exist. Sign up first.", Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (BCrypt.checkpw(password, user.password)) {
                val toMainActivity =
                    SignInFragmentDirections.actionSignInFragmentToMainActivity(user.id)
                navController.navigate(toMainActivity)
            }
        }
        signInButton?.isClickable = true
    }

    private fun isValidInput(nickname: String, password: String): Boolean {
        return SignInValidator().validate(nickname, password)
    }
}