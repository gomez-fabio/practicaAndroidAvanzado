package es.fabiogomez.repository

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import es.fabiogomez.repository.model.ShopEntity
import es.fabiogomez.repository.model.ShopsResponseEntity
import es.fabiogomez.repository.network.json.JsonEntitiesParser
import es.fabiogomez.repository.util.ReadJsonFile
import org.junit.Assert.*
import org.junit.Test

class JSONParsingTests {
    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_it_parses_correctly() {

        val shopsJson = ReadJsonFile().loadJSONFromAsset("shop.json")

        assertTrue(shopsJson.isNotEmpty())

        // parsing
        val parser = JsonEntitiesParser()
        val shop = parser.parse<ShopEntity>(shopsJson)

        assertNotNull(shop)
        assertEquals("Cortefiel - Preciados",shop.name)
        assertEquals(40.4180563f, shop.latitude, 0.1f)
    }


    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_with_wrong_latitude_it_parses_correctly() {

        val shopsJson = ReadJsonFile().loadJSONFromAsset("shopWrongLatitude.json")

        assertTrue(shopsJson.isNotEmpty())

        // parsing
        val parser = JsonEntitiesParser()
        var shop: ShopEntity
        try {
            shop = parser.parse<ShopEntity>(shopsJson)
        }catch(e:InvalidFormatException){
            shop = ShopEntity(1, 1, "Parsing Failed", "", 10.0f,11.0f,"", "", "", "")
        }

        assertNotNull(shop)
        assertEquals("Parsing Failed",shop.name)
        assertEquals(10.0f, shop.latitude, 0.1f)
    }

    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_shops_it_parses_correctly_all_shops() {

        val shopsJson = ReadJsonFile().loadJSONFromAsset("shops.json")

        assertTrue(shopsJson.isNotEmpty())

        // parsing
        val parser = JsonEntitiesParser()
        val responseEntity = parser.parse<ShopsResponseEntity>(shopsJson)

        assertNotNull(responseEntity)
        assertEquals(6, responseEntity.result.count())
        assertEquals("Cortefiel - Preciados",responseEntity.result[0].name)
        assertEquals(40.4180563f, responseEntity.result[0].latitude, 0.1f)
    }
}