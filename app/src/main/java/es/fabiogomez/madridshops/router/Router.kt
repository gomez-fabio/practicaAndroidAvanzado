package es.fabiogomez.madridshops.router

import android.content.Intent
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.madridshops.Activity.MainActivity
import es.fabiogomez.madridshops.Activity.ShopDetailActivity
import es.fabiogomez.madridshops.Activity.ShopsActivity
import es.fabiogomez.madridshops.utils.INTENT_SHOP_DETAIL_ACTIVITY

class Router {

 /*   fun navigateFromMainActivityToXXXXActivity(main: ShopsActivity){
        main.startActivity(Intent(main,XXXXXActivity::class.java))
    }*/

    fun navigateFromShopsActivityToShopsDetailActivity(activity: ShopsActivity, shop: Shop){
        val intent = Intent(activity, ShopDetailActivity::class.java)

        intent.putExtra(INTENT_SHOP_DETAIL_ACTIVITY, shop)
        activity.startActivity(intent)
    }

    fun navigateFromMainActivityToShopsActivity(activity: MainActivity) {
        val intent = Intent(activity, ShopsActivity::class.java)
        activity.startActivity(intent)
    }
}