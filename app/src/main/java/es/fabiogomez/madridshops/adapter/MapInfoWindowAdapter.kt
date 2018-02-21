package es.fabiogomez.madridshops.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.fabiogomez.domain.model.Activity
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.madridshops.R


class MarkerInfoWindowAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    val v = LayoutInflater.from(context).inflate(R.layout.map_info_window, null)

    override fun getInfoContents(marker: Marker): View {

        if (marker.tag is Shop) {
            val shop = marker.tag as Shop
            val name = shop.name
            val logo = shop.logo
            val shopName = v.findViewById(R.id.callout_name) as TextView
            val shoplogo = v.findViewById(R.id.callout_logo) as ImageView

            shopName.text = name

            Picasso
                    .with(context)
                    .load(logo)
                    .into(shoplogo, MarkerCallback(marker, logo, shoplogo, context))
        } else {
            val activity = marker.tag as Activity
            val name = activity.name
            val logo = activity.logo
            val activityName = v.findViewById(R.id.callout_name) as TextView
            val activitylogo = v.findViewById(R.id.callout_logo) as ImageView

            activityName.text = name

            Picasso
                    .with(context)
                    .load(logo)
                    .error(R.drawable.fistro_place_holder)
                    .into(activitylogo, MarkerCallback(marker, logo, activitylogo, context))
        }
        return v
    }

}

// credits to manu y miguel who found this workaround link
// see: https://stackoverflow.com/questions/32725753/picasso-image-loading-issue-in-googlemap-infowindowadapter

class MarkerCallback(val marker: Marker,
                     val url: String,
                     val imageView: ImageView,
                     val context: Context): Callback {

    override fun onSuccess() {
        if (marker.isInfoWindowShown) {
            marker.hideInfoWindow()

            Picasso
                    .with(context)
                    .load(url)
                    .error(R.drawable.fistro_place_holder)
                    .into(imageView)

            marker.showInfoWindow()
        }
    }

    override fun onError() { Log.d("Error", "💩 MarkerCallback Error") }
}
