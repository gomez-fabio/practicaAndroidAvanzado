package es.fabiogomez.madridshops.Activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.GONE
import android.widget.Button
import android.widget.TextView
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.router.Router

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonShops).setOnClickListener { goShops() }
        findViewById<Button>(R.id.buttonActivities).setOnClickListener { goActivities() }

        if(isNetworkStatusAvailable()) {
            Log.d("NET", "Available")
        } else {
            Log.d("NET", "Unavailable")
            findViewById<Button>(R.id.buttonShops).isEnabled=false
            findViewById<Button>(R.id.buttonShops).visibility = GONE
            findViewById<TextView>(R.id.shopsLabel).visibility = GONE
            findViewById<Button>(R.id.buttonActivities).isEnabled=false
            findViewById<Button>(R.id.buttonActivities).visibility = GONE
            findViewById<TextView>(R.id.activitiesLabel).visibility = GONE
        }

    }

    private fun Context.isNetworkStatusAvailable(): Boolean {
        val connectivityManager = this
                .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        connectivityManager?.let {
            val netInfo = it.activeNetworkInfo
            netInfo?.let {
                if (netInfo.isConnected) return true
            }
        }
        return false
    }

    private fun goShops() {
        Router().navigateFromMainActivityToShopsActivity(this)
    }

    private fun goActivities() {
        Log.d("DUMMY", "This do nothing")
    }



}
