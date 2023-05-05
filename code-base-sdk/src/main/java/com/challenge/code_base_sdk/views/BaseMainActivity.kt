package com.challenge.code_base_sdk.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.challenge.code_base_sdk.R
import com.challenge.code_base_sdk.databinding.ActivityBaseMainBinding
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.code_base_sdk.viewmodel.MainBaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseMainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBaseMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainBaseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //viewModel.appType = intent.getSerializableExtra("APP_TYPE") as AppType

        val host = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        setupActionBarWithNavController(host.navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        // Navigate up or call the default implementation if navigating up is not possible
        val navController = findNavController(R.id.fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}