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
import android.widget.ViewSwitcher
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
import es.fabiogomez.madridshops.adapter.ShopRecyclerViewAdapter
import es.fabiogomez.madridshops.router.Router
import es.fabiogomez.madridshops.utils.MADRID_LATITUDE
import es.fabiogomez.madridshops.utils.MADRID_LONGITUDE


class ShopsActivity : AppCompatActivity(), GoogleMap.OnInfoWindowClickListener, ShopRecyclerViewAdapter.OnShopSelectedListener {

    enum class SWITCHER_INDEX(val index: Int) {
        LOADING(0),
        WHOLE_SCREEN(1)
    }

    val madridLatitude  = MADRID_LATITUDE
    val madridLongitude = MADRID_LONGITUDE
    var shopsListFragment: ShopsListFragment? = null
    private var map: GoogleMap? = null
    lateinit var viewSwitcher: ViewSwitcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shops)

        viewSwitcher = findViewById(R.id.shop_view_switcher)
        viewSwitcher.setInAnimation(this, android.R.anim.fade_in)
        viewSwitcher.setOutAnimation(this, android.R.anim.fade_out)

        setup()
    }

    private fun setup() {
        val getAllShopsInteractor : GetAllShopsInteractor = GetAllShopsInteractorImpl(this)

        viewSwitcher.displayedChild = SWITCHER_INDEX.LOADING.index

        getAllShopsInteractor.execute(object: SuccessCompletion<Shops>{
            override fun successCompletion(shops: Shops) {
                viewSwitcher.displayedChild = SWITCHER_INDEX.WHOLE_SCREEN.index

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



    // MAP

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



    // Permissions

    fun showUserPosition(context: Context, map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 88)
            return
        } else {
            map.isMyLocationEnabled = true
        }

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



    // Navigation from the maps "call outs"

    override fun onInfoWindowClick(marker: Marker?) {
        //Log.d("CLICK", "Pulsado en: " + marker )
        Router().navigateFromShopsActivityToShopsDetailActivity(this, marker?.tag as Shop)
    }



    // Navigation from the recyclerView Elements

    override fun onShopSelected(shop: Shop?, position: Int) {
        //Log.d("CLICK", "Pulsado en: " + shop + " que está en la pos:" + position )
        shop.let {
            Router().navigateFromShopsActivityToShopsDetailActivity(this, shop!!)
        }
    }

}
