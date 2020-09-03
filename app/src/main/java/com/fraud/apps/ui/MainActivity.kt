package com.fraud.apps.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fraud.apps.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onSupportNavigateUp() = findNavController(this, R.id.navHostFragment).navigateUp()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base))
    }
}
