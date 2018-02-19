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
import es.fabiogomez.domain.model.Shops
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.adapter.ShopRecyclerViewAdapter


class ShopsListFragment : Fragment() {

    lateinit var root: View
    lateinit var shopsList: RecyclerView
    var list : Shops? = null

    var onShopClickListener: ShopRecyclerViewAdapter.OnShopSelectedListener? = null

    lateinit var adapter: ShopRecyclerViewAdapter

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
        adapter = ShopRecyclerViewAdapter(list, onShopClickListener)
        shopsList.adapter = adapter

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
        if (context is ShopRecyclerViewAdapter.OnShopSelectedListener) {
            onShopClickListener = context
        }
    }

    // on deattach from activity
    override fun onDetach() {
        super.onDetach()
        onShopClickListener = null
    }


}