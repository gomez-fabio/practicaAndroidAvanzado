package es.fabiogomez.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.fabiogomez.domain.model.Activity
import es.fabiogomez.domain.model.Activities
import es.fabiogomez.madridshops.R



class ActivityRecyclerViewAdapter (val activities: Activities?, val listener:OnActivitySelectedListener?) : RecyclerView.Adapter<ActivityRecyclerViewAdapter.AcitivityViewHolder>() {


    private var onActivitySelectedListener: ActivityRecyclerViewAdapter.OnActivitySelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AcitivityViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.activity_item, parent, false)
        onActivitySelectedListener = listener

        return AcitivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AcitivityViewHolder?, position: Int) {
        activities?.let {
            holder?.bindActivity(activities[position], position)
        }
    }

    override fun getItemCount() : Int {
       activities?.let {
           return activities.count()
       }
        return 0
    }

    inner class AcitivityViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val context = itemView.context
        val logoImage = itemView.findViewById<ImageView>(R.id.activity_logo_image)
        val backImage = itemView.findViewById<ImageView>(R.id.activity_backdrop_image)
        val activityName  = itemView.findViewById<TextView>(R.id.activity_name)

        fun bindActivity(activity: Activity, position: Int) {

            // sync views
            activityName.text = activity.name

            Picasso.with(context)
                    .load(activity.logo)
                    .fit()
                    .error(R.drawable.fistro_place_holder)
                    .placeholder(android.R.drawable.presence_away)
                    .into(logoImage)

            Picasso.with(context)
                    .load(activity.img)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.fistro_place_holder)
                    .placeholder(android.R.drawable.presence_away)
                    .into(backImage)

            itemView.setOnClickListener {
                onActivitySelectedListener?.onActivitySelected(activity,position)
            }
        }
    }

    interface OnActivitySelectedListener {
        fun onActivitySelected(activity: Activity?, position: Int)


    }
}


