package com.github.pprochot.uj.android.fragments

import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.pprochot.uj.android.R

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    companion object {
        val toMainActivity = SignUpFragmentDirections.actionSignUpFragmentToMainActivity()
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController()
        view?.findViewById<Button>(R.id.sign_up_button)?.setOnClickListener {
            navController.navigate(toMainActivity)
        }
    }
}