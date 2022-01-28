package com.github.pprochot.uj.android.fragments

import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.domain.request.UserRequest
import com.github.pprochot.uj.android.domain.response.UserResponse
import com.github.pprochot.uj.android.mappers.UserMapper
import com.github.pprochot.uj.android.services.UserService
import com.github.pprochot.uj.android.valdiators.SignUpValidator
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var realm: Realm

    @Inject
    lateinit var userMapper: UserMapper

    private var nicknameView: TextInputEditText? = null
    private var firstPasswordView: TextInputEditText? = null
    private var secondPasswordView: TextInputEditText? = null
    private var signUpButton: Button? = null
    private lateinit var navController: NavController

    override fun onStart() {
        super.onStart()

        nicknameView = view?.findViewById(R.id.nickname_inputedittext_registration)
        firstPasswordView = view?.findViewById(R.id.firstpassword_inputedittext_registration)
        secondPasswordView = view?.findViewById(R.id.secondpassword_inputedittext_registration)
        signUpButton = view?.findViewById(R.id.sign_up_button)
        navController = findNavController()

        signUpButton?.setOnClickListener {
            it.isClickable = false
            val nickname = nicknameView?.text.toString()
            val firstPassword = firstPasswordView?.text.toString()
            val secondPassword = secondPasswordView?.text.toString()

            if (isValidInput(nickname, firstPassword, secondPassword)) {
                registerViaService(UserRequest(nickname, firstPassword, false))
            } else {
                it.isClickable = true
            }
        }
    }

    private fun isValidInput(nickname: String, password: String, secondPassword: String): Boolean {
        return SignUpValidator().validate(
            nickname,
            password,
            secondPassword
        )
    }

    private fun registerViaService(userRequest: UserRequest) {
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
                    SignUpFragmentDirections.actionSignUpFragmentToMainActivity(user.id)
                navController.navigate(toMainActivity)
            } else if (response.code() == 409) { // Conflict, it means user already exists
                Toast.makeText(requireContext(), "User is already registered!", Toast.LENGTH_SHORT)
                    .show()
            }
            signUpButton?.isClickable = true
        }

        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            Toast.makeText(requireContext(), "Could not sign up. Try again.", Toast.LENGTH_SHORT)
                .show()
            signUpButton?.isClickable = true
        }
    }
}