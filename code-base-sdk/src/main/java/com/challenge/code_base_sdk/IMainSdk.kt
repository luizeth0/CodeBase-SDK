package com.challenge.code_base_sdk

import android.content.Context
import android.content.Intent
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.code_base_sdk.views.BaseMainActivity

interface IMainSdk {
    fun launchApplication(context: Context, appType: String)
}

// This object implements the IMainSdk interface and provides an implementation for the launchApplication function.
object MainSdk: IMainSdk {

    /**
     * This function launches the application by creating an Intent to start the BaseMainActivity and passing the appType as an extra.
     */
    override fun launchApplication(context: Context, appType: String) {
        Intent(context, BaseMainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("APP_TYPE", appType)
            context.startActivity(this)
        }
    }

}