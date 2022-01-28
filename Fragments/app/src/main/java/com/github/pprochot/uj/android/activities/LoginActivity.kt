package com.github.pprochot.uj.android.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.viewmodels.LoginActivityViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        loginActivityViewModel.mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()

//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        if (account != null) {
//            val navController = findNavController(R.id.nav_host_fragment_container)
//            navController.navigate(R.id.mainActivity)
//        }
    }
}