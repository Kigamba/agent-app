package com.stavros.demo.android.form

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.stavros.demo.android.R

class MainActivity : AppCompatActivity() {

    var uAddLandlord: ImageView? = null
    var uAddTenant: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uAddLandlord = findViewById(R.id.add_landlord)
        uAddTenant = findViewById(R.id.add_tenant)

        uAddLandlord?.setOnClickListener {
            val intent = Intent(this@MainActivity, LandlordRegistration::class.java)
            startActivity(intent)
        }

        uAddTenant?.setOnClickListener {
            val intent = Intent(this@MainActivity, LandlordRegistration::class.java)
            startActivity(intent)
        }
    }
}