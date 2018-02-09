package es.fabiogomez.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import es.fabiogomez.repository.network.GetJsonManager
import es.fabiogomez.repository.network.GetJsonManagerVolleyImpl
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class VolleyTests {
    val appContext = InstrumentationRegistry.getTargetContext()

    @Test
    @Throws(Exception::class)

    fun given_valid_url_we_get_non_null_json_as_string() {

        val url = "http://madrid-shops.com/json_new/getShops.php"

        val jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(appContext)

        jsonManager.execute(url, success = object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                assertTrue(e.isNotEmpty())
            }
        }, error = object:ErrorCompletion{
            override fun errorCompletion(errorMessage: String) {
                assertTrue(false)
            }
        })
    }
}
