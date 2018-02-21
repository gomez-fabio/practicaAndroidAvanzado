package es.fabiogomez.madridshops.Activity

import android.Manifest
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
import es.fabiogomez.domain.interactor.getallactivities.GetAllActivitiesInteractor
import es.fabiogomez.domain.interactor.getallactivities.GetAllActivitiesInteractorImpl
import es.fabiogomez.domain.model.Activities
import es.fabiogomez.domain.model.Activity
import es.fabiogomez.madridshops.Fragment.ActivitiesListFragment
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.adapter.ActivityRecyclerViewAdapter
import es.fabiogomez.madridshops.adapter.MarkerInfoWindowAdapter
import es.fabiogomez.madridshops.router.Router
import es.fabiogomez.madridshops.utils.MADRID_LATITUDE
import es.fabiogomez.madridshops.utils.MADRID_LONGITUDE

class ActivitiesActivity : AppCompatActivity(), GoogleMap.OnInfoWindowClickListener, ActivityRecyclerViewAdapter.OnActivitySelectedListener {

    enum class SWITCHER_INDEX(val index: Int) {
        LOADING(0),
        WHOLE_SCREEN(1)
    }

    val madridLatitude  = MADRID_LATITUDE
    val madridLongitude = MADRID_LONGITUDE
    var activitiesListFragment: ActivitiesListFragment? = null
    private var map: GoogleMap? = null
    lateinit var viewSwitcher: ViewSwitcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)

        viewSwitcher = findViewById(R.id.activity_view_switcher)
        viewSwitcher.setInAnimation(this, android.R.anim.fade_in)
        viewSwitcher.setOutAnimation(this, android.R.anim.fade_out)

        setup()
    }

    private fun setup() {
        val getAllActivitiesInteractor : GetAllActivitiesInteractor = GetAllActivitiesInteractorImpl(this)

        viewSwitcher.displayedChild = SWITCHER_INDEX.LOADING.index

        getAllActivitiesInteractor.execute(object: SuccessCompletion<Activities> {
            override fun successCompletion(activities: Activities) {
                viewSwitcher.displayedChild = SWITCHER_INDEX.WHOLE_SCREEN.index

                initializeMap(activities)
                activitiesListFragment = fragmentManager.findFragmentById(R.id.activity_activities_list_fragment) as ActivitiesListFragment?
                activitiesListFragment?.setActivities(activities)
            }

        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Toast.makeText(baseContext, "💩 Error setupMap", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initializeMap(activities: Activities) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.activity_activities_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa ->
            centerMapInPosition(mapa,madridLatitude.toDouble(), madridLongitude.toDouble())
            mapa.uiSettings.isRotateGesturesEnabled = false
            mapa.uiSettings.isZoomControlsEnabled = true
            showUserPosition(baseContext, mapa)
            map = mapa
            addAllPins(activities)

        })
    }



    // MAP

    fun addAllPins(activities: Activities){
        for (i in 0 until activities.count()){
            val activity = activities.get(i)
            try{
                addPin(this.map!!, activity)
            } catch(e: Exception){
                Log.d("Error","💩 ActivitiesActivity.AddAllPin Error")
            }
        }
    }

    fun addPin(map: GoogleMap, activity: Activity) {
        map.addMarker(MarkerOptions()
                .position(LatLng(activity.latitude!!.toDouble(), activity.longitude!!.toDouble()))
                .title(activity.name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fistro)))
                .tag = activity

        map.setOnInfoWindowClickListener(this)

        map.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
    }

    fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double) {
        val coordinate = LatLng(latitude, longitude)
        val cameraPosition = CameraPosition.Builder().
                target(coordinate).
                zoom(15f).
                build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }



    // Permissions

    fun showUserPosition(context: Context, map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 88)
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
        Log.d("CLICK", "Pulsado en: " + marker )
        //Router().navigateFromActivitiesActivityToActivitiesDetailActivity(this, marker?.tag as Activity)
    }



    // Navigation from the recyclerView Elements

    override fun onActivitySelected(activity: Activity?, position: Int) {
        Log.d("CLICK", "Pulsado en: " + activity + " que está en la pos:" + position )
        /*activity.let {
            Router().navigateFromActivitiesActivityToActivitiesDetailActivity(this, activity!!)
        }*/
    }

}