package com.github.pprochot.uj.android.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.activities.LoginActivity
import com.github.pprochot.uj.android.viewmodels.LoginActivityViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    companion object {
        val toSignUpFragment = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        val toMainActivity = SignInFragmentDirections.actionSignInFragmentToMainActivity()
        const val RC_SIGN_IN = 1
    }

    private val loginActivityViewModel: LoginActivityViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()

        view?.findViewById<SignInButton>(R.id.google_sign_in_button)?.setOnClickListener {
            signInByGoogle()
        }

        val navController = findNavController()
        view?.findViewById<Button>(R.id.sign_in_button)?.setOnClickListener {
            navController.navigate(toMainActivity)
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

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun updateUI() {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainActivity())
    }

}