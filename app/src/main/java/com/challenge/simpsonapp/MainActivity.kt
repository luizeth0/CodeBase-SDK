package com.challenge.simpsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.challenge.code_base_sdk.MainSdk
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.simpsonapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //BaseSdk.init(applicationcontext, character)

        MainSdk.launchApplication(applicationContext, AppType.SIMPSONS.endPoint)

    }
}