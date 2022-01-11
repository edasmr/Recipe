package com.example.deneme

import android.app.Application
import com.bumptech.glide.request.target.ViewTarget
import com.example.deneme.presentation.common.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ViewTarget.setTagId(R.id.glide_tag)
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@App)
           modules(listOf(NetworkModule.module))
        }
    }
}