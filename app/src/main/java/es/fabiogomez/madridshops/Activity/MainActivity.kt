package es.fabiogomez.madridshops.Activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import es.fabiogomez.domain.interactor.deleteallactivities.DeleteAllActivities
import es.fabiogomez.domain.interactor.deleteallactivities.DeleteAllActivitiesImpl
import es.fabiogomez.domain.interactor.deleteallshops.DeleteAllShops
import es.fabiogomez.domain.interactor.deleteallshops.DeleteAllShopsImpl
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.router.Router

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonShops).setOnClickListener { goShops() }
        findViewById<Button>(R.id.buttonActivities).setOnClickListener { goActivities() }
        findViewById<Button>(R.id.buttonRefresh).setOnClickListener { isNetworkAvailable() }

        isNetworkAvailable()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_settings) {
            clearCacheMadridShops()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun clearCacheMadridShops() {
        Log.d("CACHE", "Clearing Cache")

        val deleteAllShopsInteractor : DeleteAllShops = DeleteAllShopsImpl(this)
        val deleteAllActivitiesInteractor : DeleteAllActivities = DeleteAllActivitiesImpl(this)

        deleteAllShopsInteractor.execute({
            Toast
                    .makeText(this,"Shops Cache Successfully cleared", Toast.LENGTH_SHORT)
                    .show()
        },{
            Toast
                    .makeText(this,"Something gone wrong trying to clear the Shops Cache", Toast.LENGTH_SHORT)
                    .show()
        })

        deleteAllActivitiesInteractor.execute({
            Toast
                    .makeText(this,"Activities Cache Successfully cleared", Toast.LENGTH_SHORT)
                    .show()
        },{
            Toast
                    .makeText(this,"Something gone wrong trying to clear the Activities Cache", Toast.LENGTH_SHORT)
                    .show()
        })

    }

    private fun isNetworkAvailable() {
        if(isNetworkStatusAvailable()) {
            Log.d("NET", "Available")
            findViewById<Button>(R.id.buttonShops).isEnabled=true
            findViewById<Button>(R.id.buttonShops).visibility = VISIBLE
            findViewById<TextView>(R.id.shopsLabel).visibility = VISIBLE
            findViewById<Button>(R.id.buttonActivities).isEnabled=true
            findViewById<Button>(R.id.buttonActivities).visibility = VISIBLE
            findViewById<TextView>(R.id.activitiesLabel).visibility = VISIBLE
            findViewById<Button>(R.id.buttonRefresh).visibility = GONE
            findViewById<Button>(R.id.buttonRefresh).isEnabled = false
        } else {
            Log.d("NET", "Unavailable")
            findViewById<Button>(R.id.buttonShops).isEnabled=false
            findViewById<Button>(R.id.buttonShops).visibility = GONE
            findViewById<TextView>(R.id.shopsLabel).visibility = GONE
            findViewById<Button>(R.id.buttonActivities).isEnabled=false
            findViewById<Button>(R.id.buttonActivities).visibility = GONE
            findViewById<TextView>(R.id.activitiesLabel).visibility = GONE
            findViewById<Button>(R.id.buttonRefresh).visibility = VISIBLE
            findViewById<Button>(R.id.buttonRefresh).isEnabled = true
            AlertDialog.Builder(this)
                    .setTitle("Looks like a problem to me")
                    .setMessage("It seems there is no active connection to the internet")
                    .setPositiveButton("OK", { dialog, which ->
                        dialog.dismiss()
                    })
                    .show()
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
        Router().navigateFromMainActivityToActivitiesActivity(this)
    }



}
