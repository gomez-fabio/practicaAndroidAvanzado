package es.fabiogomez.domain.interactor.getallshops

import android.content.Context
import es.fabiogomez.domain.interactor.ErrorCompletion
import es.fabiogomez.domain.interactor.SuccessCompletion
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.domain.model.Shops
import es.fabiogomez.repository.Repository
import es.fabiogomez.repository.RepositoryImpl
import es.fabiogomez.repository.db.stringToFloat
import es.fabiogomez.repository.model.ShopEntity
import java.lang.ref.WeakReference

class GetAllShopsInteractorImpl (context: Context) : GetAllShopsInteractor {
    private val weakContext = WeakReference<Context>(context)
    private val repository : Repository = RepositoryImpl(weakContext.get()!!)

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        repository.getAllShops(
                success = {
                    val shops: Shops = entityMapper(it)
                    success.successCompletion(shops)
                }, error = {
                   error(it)
                })
            }

    private fun entityMapper(list: List<ShopEntity>): Shops {
        val tempList = ArrayList<Shop>()
        list.forEach {
            val shop = Shop(
                        it.id.toInt(),
                        it.name,
                        it.address,
                        it.description,
                        stringToFloat(it.latitude),
                        stringToFloat(it.longitude),
                        it.img,
                        it.logo,
                        it.openingHours)

            tempList.add(shop)
        }

        val shops = Shops(tempList)
        return shops


    }
}


