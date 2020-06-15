package com.stavros.demo.android.form

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.stavros.demo.android.R
import com.stavros.demo.android.util.FormType
import com.stavros.demo.android.util.startJsonFormActivity

class MainActivity : AppCompatActivity() {

    var uAddLandlord: ImageView? = null
    var uAddTenant: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(getString(R.string.agent_app))

        uAddLandlord = findViewById(R.id.add_landlord)
        uAddTenant = findViewById(R.id.add_tenant)

        uAddLandlord?.setOnClickListener {
            /*val intent = Intent(this@MainActivity, LandlordRegistration::class.java)
            startActivity(intent)*/
            var intent = startJsonFormActivity(this, "Landlord Registration", "landlord-registration.neat.json", LandlordRegistrationActivity::class.java)
            startActivity(intent)
        }

        uAddTenant?.setOnClickListener {
            var intent = startJsonFormActivity(this, "Tenant Registration", "tenant-registration.neat.json", TenantRegistrationActivity::class.java)
            startActivity(intent)

            /*val intent = Intent(this@MainActivity, LandlordListActivity::class.java)
            startActivity(intent)*/
        }
    }
}