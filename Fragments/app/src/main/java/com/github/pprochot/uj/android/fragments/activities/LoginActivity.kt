package com.github.pprochot.uj.android.fragments.activities

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.appcompat.app.AppCompatActivity
import com.github.pprochot.uj.android.fragments.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    override fun onStart() {
        super.onStart()
        val accountManager = AccountManager.get(this)
        val accounts: Array<out Account> = accountManager.getAccountsByType("com.google")

        println("accounts: " + accounts.size)

        accounts.forEach {
            println(it.name)
        }

//        val account = accounts[0]
//        val options = Bundle()
//        accountManager.getAuthToken(
//            account,
//            "Manage your tasks",
//            options,
//            this,
//            OnTokenAcquired(),
//            Handler(Looper.myLooper()!!)
//        )
        // Może nadrobię :D
        // Skoro polecasz to faktycznie

//        val url = URL("https://www.googleapis.com/tasks/v1/users/@me/lists?key=$your_api_key")
//        val conn = url.openConnection() as HttpURLConnection
//        conn.apply {
//            addRequestProperty("client_id", your client id)
//            addRequestProperty("client_secret", your client secret)
//            setRequestProperty("Authorization", "OAuth $token")
//        }
    }

    private class OnTokenAcquired : AccountManagerCallback<Bundle> {

        override fun run(result: AccountManagerFuture<Bundle>) {
            // Get the result of the operation from the AccountManagerFuture.
            val bundle: Bundle = result.result

            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            val token = bundle.getString(AccountManager.KEY_AUTHTOKEN)
        }
    }
}