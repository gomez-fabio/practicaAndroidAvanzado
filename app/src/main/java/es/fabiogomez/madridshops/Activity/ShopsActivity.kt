package es.fabiogomez.madridshops.Activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import es.fabiogomez.domain.interactor.ErrorCompletion
import es.fabiogomez.domain.interactor.SuccessCompletion
import es.fabiogomez.domain.interactor.getallshops.GetAllShopsInteractor
import es.fabiogomez.domain.interactor.getallshops.GetAllShopsInteractorImpl
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.domain.model.Shops
import es.fabiogomez.madridshops.Fragment.ShopsListFragment
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.adapter.MarkerInfoWindowAdapter
import kotlinx.android.synthetic.main.activity_main.*


class ShopsActivity : AppCompatActivity(), GoogleMap.OnInfoWindowClickListener {

    val madridLatitude  = 40.427786f
    val madridLongitude = -3.695894f
    var shopsListFragment: ShopsListFragment? = null
    private var map: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setup()

/*        // Funcional, pero desactivando las opciones de celular en el emulador
        if(isNetworkStatusAvailable()) {
            Log.d("NET", "Available")
        } else {
            Log.d("NET", "Unavailable")
        }*/

    }

/*    fun Context.isNetworkStatusAvailable(): Boolean {
        val connectivityManager = this
                .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        connectivityManager?.let {
            val netInfo = it.activeNetworkInfo
            netInfo?.let {
                if (netInfo.isConnected) return true
            }
        }
        return false
    }*/



    private fun setup() {
        val getAllShopsInteractor : GetAllShopsInteractor = GetAllShopsInteractorImpl(this)

        getAllShopsInteractor.execute(object: SuccessCompletion<Shops>{
            override fun successCompletion(shops: Shops) {
                initializeMap(shops)

                shopsListFragment = fragmentManager.findFragmentById(R.id.activity_main_list_fragment) as ShopsListFragment?
                shopsListFragment?.setShops(shops)
            }

        }, object: ErrorCompletion{
            override fun errorCompletion(errorMessage: String) {
                Toast.makeText(baseContext, "💩 Error setupMap", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initializeMap(shops: Shops) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.activity_main_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa ->
            centerMapInPosition(mapa,madridLatitude.toDouble(), madridLongitude.toDouble())
            mapa.uiSettings.isRotateGesturesEnabled = false
            mapa.uiSettings.isZoomControlsEnabled = true
            showUserPosition(baseContext, mapa)
            map = mapa
            addAllPins(shops)

        })
    }

    fun addAllPins(shops: Shops){
        for (i in 0 until shops.count()){
            val shop = shops.get(i)
            try{
                addPin(this.map!!, shop)
            } catch(e: Exception){
                Log.d("Error","💩 ShopsActivity.AddAllPin Error")
            }
        }
    }

    fun addPin(map: GoogleMap, shop: Shop) {
        map.addMarker(MarkerOptions()
                .position(LatLng(shop.latitude!!.toDouble(), shop.longitude!!.toDouble()))
                .title(shop.name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fistro)))
                .tag = shop

        map.setOnInfoWindowClickListener(this)

        map.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
    }

    fun centerMapInPosition(map:GoogleMap, latitude: Double, longitude: Double) {
        val coordinate = LatLng(latitude, longitude)
        val cameraPosition = CameraPosition.Builder().
                target(coordinate).
                zoom(15f).
                build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun showUserPosition(context: Context, map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 88)
            return
        }

        map.isMyLocationEnabled = true

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 88) {
            try {
                map?.isMyLocationEnabled = true
            }catch(e: SecurityException){

            }
        }

    }

    override fun onInfoWindowClick(marker: Marker?) {
        Log.d("CLICK", "Pulsado en: " + marker )
    }

}
