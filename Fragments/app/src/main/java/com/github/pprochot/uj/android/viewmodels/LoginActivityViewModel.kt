package com.github.pprochot.uj.android.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class LoginActivityViewModel : ViewModel() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
}