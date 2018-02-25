package es.fabiogomez.madridshops.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.utils.GOOGLE_MAPS_API
import es.fabiogomez.madridshops.utils.INTENT_SHOP_DETAIL_ACTIVITY

class ShopDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)

        val shop = intent.getSerializableExtra(INTENT_SHOP_DETAIL_ACTIVITY) as Shop

        title = shop.name

        val shopImage       = this.findViewById<ImageView>(R.id.detail_shop_image)
        val shopName        = this.findViewById<TextView>(R.id.detail_shop_name)
        val shopAddress     = this.findViewById<TextView>(R.id.detail_shop_address)
        val shopHours       = this.findViewById<TextView>(R.id.detail_opening_hours)
        val shopDescription = this.findViewById<TextView>(R.id.detail_shop_description)
        val shopMap         = this.findViewById<ImageView>(R.id.detail_shop_map)
        val shopMapURL: String

        shopMapURL = GOOGLE_MAPS_API + shop.latitude + "," + shop.longitude

        Picasso.with(this)
                .load(shop.img)
                .fit()
                .error(R.drawable.fistro_place_holder)
                .into(shopImage)

        shopName.text    = shop.name
        shopAddress.text = shop.address
        shopHours.text   = shop.openingHours_en
        shopDescription.text = shop.description_en

        Picasso.with(this)
                .load(shopMapURL)
                .fit()
                .error(R.drawable.fistro_place_holder)
                .into(shopMap)

    }
}
