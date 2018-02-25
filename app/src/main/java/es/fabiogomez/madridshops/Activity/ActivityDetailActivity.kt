package es.fabiogomez.madridshops.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.fabiogomez.domain.model.Activity
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.utils.GOOGLE_MAPS_API
import es.fabiogomez.madridshops.utils.INTENT_ACTIVITY_DETAIL_ACTIVITY

class ActivityDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_detail)


        val activity = intent.getSerializableExtra(INTENT_ACTIVITY_DETAIL_ACTIVITY) as Activity

        title = activity.name

        val activityImage       = this.findViewById<ImageView>(R.id.detail_activity_image)
        val activityName        = this.findViewById<TextView>(R.id.detail_activity_name)
        val activityAddress     = this.findViewById<TextView>(R.id.detail_activity_address)
        val activityHours       = this.findViewById<TextView>(R.id.activity_detail_opening_hours)
        val activityDescription = this.findViewById<TextView>(R.id.detail_activity_description)
        val activityMap         = this.findViewById<ImageView>(R.id.detail_activity_map)
        val activityMapURL: String

        activityMapURL = GOOGLE_MAPS_API + activity.latitude + "," + activity.longitude

        Picasso.with(this)
                .load(activity.img)
                .fit()
                .error(R.drawable.fistro_place_holder)
                .into(activityImage)

        activityName.text    = activity.name
        activityAddress.text = activity.address
        activityHours.text   = activity.openingHours_en
        activityDescription.text = activity.description_en

        Picasso.with(this)
                .load(activityMapURL)
                .fit()
                .error(R.drawable.fistro_place_holder)
                .into(activityMap)

    }
}
