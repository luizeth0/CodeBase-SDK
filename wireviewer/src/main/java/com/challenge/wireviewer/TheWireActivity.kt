package com.challenge.wireviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.challenge.code_base_sdk.MainSdk
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.wireviewer.databinding.ActivityTheWireBinding
import dagger.hilt.android.AndroidEntryPoint

class TheWireActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTheWireBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MainSdk.launchApplication(applicationContext, AppType.THE_WIRE.endPoint)


    }
}