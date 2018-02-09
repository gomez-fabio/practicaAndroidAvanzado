package es.fabiogomez.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import es.fabiogomez.repository.db.build
import es.fabiogomez.repository.db.dao.ShopDao
import es.fabiogomez.repository.model.ShopEntity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class ShopDAOTests {
    private val appContext = InstrumentationRegistry.getTargetContext()
    private val dbhelper = build(appContext, "mydb.sqlite_que_no_pase_hambre",1)

    @Test
    @Throws(Exception::class)

    fun given_valid_shopentity_it_gets_inserted_correctly() {


        val shopEntityDao = ShopDao(dbhelper)

        val deletedAll = shopEntityDao.deleteAll()

        val shop = ShopEntity(1,99,"tienda 1"," description 1",1.0f,2.0f,"","","","")

        val shop2 = ShopEntity(2,100,"tienda 2"," description 2",1.0f,2.0f,"","","","")

        val id = shopEntityDao.insert(shop)
        val id2 = shopEntityDao.insert(shop2)

        shopEntityDao.query().forEach{
            Log.d("Tiendecita", it.name + it.description)
        }

        assertTrue(id>0)
    }
}
