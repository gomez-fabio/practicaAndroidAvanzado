package es.fabiogomez.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.madridshops.R

class ShopsRecyclerViewAdapter(val shops: List<Shop>?, val listener:OnShopSelectedListener?): RecyclerView.Adapter<ShopsRecyclerViewAdapter.ShopListViewHolder>(){

    private var onShopSelectedListener: ShopsRecyclerViewAdapter.OnShopSelectedListener? = null
    override fun getItemCount() = shops?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.shop_item, parent, false)
        onShopSelectedListener = listener
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder?, position: Int) {
        if (shops != null) {
            holder?.bindShop(shops[position], position)
        }
    }

    inner class ShopListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val shopName = itemView.findViewById<TextView>(R.id.shop_name)
        val logo     = itemView.findViewById<ImageView>(R.id.logo_image)
        val image    = itemView.findViewById<ImageView>(R.id.backdrop_image)

        fun bindShop(shop: Shop, position: Int){

            val context = itemView.context
            shopName.text = shop.name

            //shop logo image
            Picasso.with(context).
                    load(shop.logo).
                    fit().
                    into(logo)

            //backdrop shop image
            Picasso.with(context).
                    load(shop.img).
                    fit().
                    into(image)

            itemView.setOnClickListener {
                onShopSelectedListener?.onShopSelected(shop,position)
            }
        }


        }

    interface OnShopSelectedListener {
        fun onShopSelected(shop: Shop?, position: Int)
    }

}
