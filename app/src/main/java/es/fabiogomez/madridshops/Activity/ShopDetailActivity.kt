package es.fabiogomez.madridshops.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.madridshops.R
import es.fabiogomez.madridshops.utils.INTENT_SHOP_DETAIL_ACTIVITY

class ShopDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)


        val shop = intent.getSerializableExtra(INTENT_SHOP_DETAIL_ACTIVITY) as Shop

        val shopName = this.findViewById<TextView>(R.id.shop_name_detail)

        shopName.text =  shop.name

    }
}
