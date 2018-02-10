package es.fabiogomez.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.madridshops.R
import kotlinx.android.synthetic.main.content_main.*

class ShopsRecyclerViewAdapter(val shops: List<Shop>): RecyclerView.Adapter<ShopsRecyclerViewAdapter.ShopListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.shop_item, parent, false)

        return ShopListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shops.size
    }

    override fun onBindViewHolder(holder: ShopListViewHolder?, position: Int) {
        holder?.bindShop(shops[position], position)
    }

    inner class ShopListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val shopName = itemView.findViewById<TextView>(R.id.shop_name)
        val logo     = itemView.findViewById<ImageView>(R.id.logo_image)
        val image    = itemView.findViewById<ImageView>(R.id.backdrop_image)

        fun bindShop(shop: Shop, position: Int){

            val context = itemView.context
            shopName.text = shop.name

/*            //shop logo image
            Picasso.with(context).
                    load(shop.logo).
                    fit().
                    into(logo)

            //backdrop shop image
            Picasso.with(context).
                    load(shop.image).
                    fit().
                    into(image)*/
        }

/*        itemView.setOnClickListener {
            onShopSelectedLister?.onShopSelected(shop, position)
        }*/

    }
}

