package es.fabiogomez.madridshops.Fragment


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.adapter.ShopsRecyclerViewAdapter


class ListFragment : Fragment() {

/*    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_list, container, false)
    }*/
    var list : MutableList<Shop>? = null
    var onShopClickListener:  ShopsRecyclerViewAdapter.OnShopSelectedListener? = null

    lateinit var shopListRecyclerView: RecyclerView
    lateinit var root: View


    lateinit var adapter: ShopsRecyclerViewAdapter



    fun setShops(shops:  MutableList<Shop>?) {
        list = shops
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_list, container, false)
            shopListRecyclerView = root.findViewById(R.id.content_list)
            shopListRecyclerView.layoutManager = GridLayoutManager(activity,1)
            shopListRecyclerView.itemAnimator = DefaultItemAnimator()

            adapter = ShopsRecyclerViewAdapter(list,onShopClickListener)
            shopListRecyclerView.adapter = ShopsRecyclerViewAdapter(list,onShopClickListener)
        }

        return root

    }

}