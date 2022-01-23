package com.github.pprochot.uj.android.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.domain.ListResponse
import com.github.pprochot.uj.android.domain.request.UserRequest
import com.github.pprochot.uj.android.mappers.RealmModelMapper
import com.github.pprochot.uj.android.mappers.UserMapper
import com.github.pprochot.uj.android.services.UserService
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.realm.Realm
import io.realm.RealmObject
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var userService: UserService
    @Inject
    lateinit var userMapper: UserMapper
    @Inject
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personEmail = acct.email
            saveData(UserRequest(personEmail!!, ""))
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navController = findNavController(R.id.nav_host_fragment_container)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setupWithNavController(navController)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun saveData(userRequest: UserRequest) {
        userService.create(userRequest).enqueue(ServiceCreateCallback(realm, userMapper))
    }

    private class ServiceCreateCallback<ResponseModel, out RealmModel : RealmObject>(
        private val realm: Realm,
        private val mapper: RealmModelMapper<ResponseModel, RealmModel>
    ) : Callback<ResponseModel> {

        override fun onResponse(
            call: Call<ResponseModel>,
            response: Response<ResponseModel>
        ) {
            if (response.isSuccessful && response.body() != null) {
                val obj = response.body()!!
                val realmObjects = mapper.map(obj)
                realm.executeTransactionAsync {
                    it.insertOrUpdate(realmObjects)
                }
            } else {
                println("Not success")
//                throw RuntimeException()// todo better exception and error
            }
        }

        override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            println("Failrue")
            throw RuntimeException() //todo better exception and error
        }
    }
}