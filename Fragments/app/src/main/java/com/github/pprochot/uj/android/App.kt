package com.github.pprochot.uj.android

import android.app.Application
import com.github.pprochot.uj.android.domain.ListResponse
import com.github.pprochot.uj.android.mappers.MapperContainer
import com.github.pprochot.uj.android.mappers.RealmModelMapper
import com.github.pprochot.uj.android.services.ServiceContainer
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var services: ServiceContainer

    @Inject
    lateinit var mappers: MapperContainer

    @Inject
    lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()
        realm.executeTransaction {
            realm.deleteAll()
        }
        fetchDataFromServicesAsync(realm)
        PaymentConfiguration.init(
            applicationContext,
            BuildConfig.SECRET_KEY_CLIENT
        )
    }

    private fun fetchDataFromServicesAsync(realm: Realm) {
        services.userService.getAll()
            .enqueue(ServiceFetchCallback(realm, mappers.userMapper))
        services.categoryService.getAll()
            .enqueue(ServiceFetchCallback(realm, mappers.categoryMapper))
        services.productService.getAll()
            .enqueue(ServiceFetchCallback(realm, mappers.productMapper))
        services.cartService.getAll()
            .enqueue(ServiceFetchCallback(realm, mappers.cartMapper))
        services.orderService.getAll()
            .enqueue(ServiceFetchCallback(realm, mappers.orderMapper))
    }

    private class ServiceFetchCallback<ResponseModel, out RealmModel : RealmObject>(
        private val realm: Realm,
        private val mapper: RealmModelMapper<ResponseModel, RealmModel>
    ) : Callback<ListResponse<ResponseModel>> {

        override fun onResponse(
            call: Call<ListResponse<ResponseModel>>,
            response: Response<ListResponse<ResponseModel>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                val list = response.body()!!.list
                val realmObjects = mapper.mapList(list)
                realm.executeTransactionAsync {
                    it.insertOrUpdate(realmObjects)
                }
            } else {
                println("Not success")
                throw RuntimeException("Couldn't fetch data from service")
            }
        }

        override fun onFailure(call: Call<ListResponse<ResponseModel>>, t: Throwable) {
            println("Failrue")
            throw RuntimeException("Couldn't fetch data", t)
        }
    }
}
