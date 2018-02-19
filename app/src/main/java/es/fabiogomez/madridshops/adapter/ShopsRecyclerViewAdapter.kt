package es.fabiogomez.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.domain.model.Shops
import es.fabiogomez.madridshops.R


class ShopRecyclerViewAdapter (val shops: Shops?, val listener:OnShopSelectedListener?) : RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopViewHolder>() {

    private var onShopSelectedListener: ShopRecyclerViewAdapter.OnShopSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.shop_item, parent, false)
        onShopSelectedListener = listener
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder?, position: Int) {
        shops?.let {
            holder?.bindShop(shops[position], position)
        }
    }

    override fun getItemCount() : Int {
       shops?.let {
           return shops.count()
       }
        return 0
    }

    inner class ShopViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val context = itemView.context
        val logoImage = itemView.findViewById<ImageView>(R.id.logo_image)
        val backImage = itemView.findViewById<ImageView>(R.id.backdrop_image)
        val shopName  = itemView.findViewById<TextView>(R.id.shop_name)

        fun bindShop(shop: Shop, position: Int) {

            // sync views
            shopName.text = shop.name

            Picasso.with(context)
                    .load(shop.logo)
                    .fit()
                    .into(logoImage)

            Picasso.with(context)
                    .load(shop.img)
                    .fit()
                    .centerCrop()
                    .into(backImage)

            itemView.setOnClickListener {
                onShopSelectedListener?.onShopSelected(shop,position)
            }
        }
    }

    interface OnShopSelectedListener {
        fun onShopSelected(shop: Shop?, position: Int)
    }
}
