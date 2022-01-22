package com.github.pprochot.uj.android.fragments.fragments

import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.pprochot.uj.android.fragments.R

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    companion object {
        val toSignUpFragment = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        val toMainActivity = SignInFragmentDirections.actionSignInFragmentToMainActivity()
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController()
        view?.findViewById<Button>(R.id.sign_in_button)?.setOnClickListener {
            navController.navigate(toMainActivity)
        }
        view?.findViewById<Button>(R.id.go_to_register_button)?.setOnClickListener {
            navController.navigate(toSignUpFragment)
        }
    }
}