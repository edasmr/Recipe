package com.example.deneme.presentation.common

import androidx.room.Room
import com.example.deneme.data.home.local.HomeDatabase
import com.example.deneme.data.home.remote.api.HomeApi
import com.example.deneme.data.home.repository.HomeRepo
import com.example.deneme.data.home.repository.HomeRepoImpl
import com.example.deneme.data.save.repo.SaveRepoImpl
import com.example.deneme.data.save.repo.SaveRepository
import com.example.deneme.domain.usecase.HomeUseCase
import com.example.deneme.presentation.home.HomeViewModel
import com.example.deneme.presentation.save.SaveViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

   companion object{
        val module = module{

            viewModel() { HomeViewModel(get()) }
            viewModel() { SaveViewModel(get()) }

            single <HomeApi> {

                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.spoonacular.com")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


                retrofit.create(HomeApi::class.java)
            }

            single<HomeRepo> { HomeRepoImpl(get(), get()) }
            single<SaveRepository> { SaveRepoImpl(get(), get()) }


            single { HomeUseCase(get()) }


            single {
                Room.databaseBuilder(
                    androidContext(),
                    HomeDatabase::class.java, "HomeDatabase"
                ).allowMainThreadQueries().build()
            }
        }
    }
}