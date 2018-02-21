package es.fabiogomez.madridshops.Fragment


import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.fabiogomez.domain.model.Activities
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.adapter.ActivityRecyclerViewAdapter


class ActivitiesListFragment : Fragment() {

    lateinit var root: View
    lateinit var activitiesList: RecyclerView
    var list : Activities? = null

    var onActivityClickListener: ActivityRecyclerViewAdapter.OnActivitySelectedListener? = null

    lateinit var adapter: ActivityRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        if (inflater != null){
            root = inflater.inflate(R.layout.fragment_activity_list,container,false)
            activitiesList = root.findViewById(R.id.content_activity_list)

            activitiesList.layoutManager = GridLayoutManager(activity,1)

            activitiesList.itemAnimator = DefaultItemAnimator()
        }

        return root
    }

    fun setActivities(activities:Activities) {
        list = activities
        adapter = ActivityRecyclerViewAdapter(list, onActivityClickListener)
        activitiesList.adapter = adapter

        }


    // Fragment life cycle functions
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonOnAttach(context)
    }

    @Suppress("OverridingDeprecatedMember")
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonOnAttach(activity)
    }

    // reference to attached activity
    private fun commonOnAttach(context: Context?) {
        if (context is ActivityRecyclerViewAdapter.OnActivitySelectedListener) {
            onActivityClickListener = context
        }
    }

    // on deattach from activity
    override fun onDetach() {
        super.onDetach()
        onActivityClickListener = null
    }


}