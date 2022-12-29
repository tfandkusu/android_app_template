package com.tfandkusu.template.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.view.WindowCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.tfandkusu.template.R
import com.tfandkusu.template.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To change status bar color by using dynamic color.
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // To show oss licenses
        OssLicensesMenuActivity.setActivityTitle(getString(R.string.title_oss_license))
        val intent = Intent(this, OssLicensesMenuActivity::class.java)
        //
        setContent {
            MyAppTheme {
                AppContent(callOssLicense = {
                    startActivity(intent)
                })
            }
        }
    }
}
