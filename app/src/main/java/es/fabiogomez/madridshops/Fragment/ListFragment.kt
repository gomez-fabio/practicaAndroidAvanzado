package es.fabiogomez.madridshops.Fragment


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.fabiogomez.domain.model.Shops
import es.fabiogomez.madridshops.R


class ListFragment : Fragment() {

    lateinit var root: View
    lateinit var shopsList: RecyclerView
    var list : Shops? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        if (inflater != null){
            root = inflater.inflate(R.layout.fragment_list,container,false)
            shopsList = root.findViewById(R.id.content_list)

            shopsList.layoutManager = GridLayoutManager(activity,1)

            shopsList.itemAnimator = DefaultItemAnimator()



        }

        return root
    }

    fun setShops(shops:Shops) {
        list = shops
    }
}