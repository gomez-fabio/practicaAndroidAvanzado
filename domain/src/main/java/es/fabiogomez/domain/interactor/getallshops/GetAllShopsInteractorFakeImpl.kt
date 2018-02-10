package es.fabiogomez.domain.interactor.getallshops

import es.fabiogomez.domain.interactor.ErrorClosure
import es.fabiogomez.domain.interactor.ErrorCompletion
import es.fabiogomez.domain.interactor.SuccessClosure
import es.fabiogomez.domain.interactor.SuccessCompletion
import es.fabiogomez.domain.model.Shop
import es.fabiogomez.domain.model.Shops



class GetAllShopsInteractorFakeImpl : GetAllShopsInteractor {

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        var allOk = false


        // Connect to the repository
        if (allOk) {
            val shops =  createFakeListOfShops()

            success.successCompletion(shops)
        } else {
            error.errorCompletion("💩 Error while accesing repository JARL")
        }
    }

    fun execute(success: SuccessClosure, error: ErrorClosure) {
        var allOk = false

        // Connect to the repository
        if (allOk) {
            val shops =  createFakeListOfShops()

            success(shops)
        } else {
            error("💩E rror while accesing repository JARL")
        }
    }

    fun createFakeListOfShops() : Shops {
        val list = ArrayList<Shop>()

        for (i in 0..100) {
            val shop = Shop(
                            i,
                    "Shop#" + i,
                    "Address#" + i,
                    "Description#" +i,
                    null,
                    null,
                    "https://pre00.deviantart.net/d5e5/th/pre/i/2016/165/6/7/capsule_corp_logo___cdr_by_dominik_skowera-da67d8q.png",
                    "https://consequenceofsound.files.wordpress.com/2015/10/screen-shot-2015-10-17-at-6-57-13-pm.png",
                    "10-14" + i)
            list.add(shop)
        }

        val shops = Shops(list)
        return shops
    }

}
