package pt.nmplcpimenta.innowavegithuber.datamanagers

import android.app.DownloadManager
import android.graphics.Bitmap
import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import pt.nmplcpimenta.innowavegithuber.datamodels.*
import pt.nmplcpimenta.innowavegithuber.tasks.DownloadImageAsyncTask
import pt.nmplcpimenta.innowavegithuber.tasks.GitHubAPIAsyncTask
import java.net.HttpURLConnection
import java.net.URL

object RESTManager {
    enum class APIRequestType {
        SEARCH_USERS,
        GET_USER,
        GET_FOLLOWERS
    }

    const val URL_BASE = "https://api.github.com/"

    fun searchUsers(keyword: String, responseHandler: (GHError?, List<GHSearchUser>?) -> Unit) {
        val requestMap = mapOf<String, Any>("keyword" to keyword)

        GitHubAPIAsyncTask().execute(Triple(APIRequestType.SEARCH_USERS, requestMap, { ghError, response ->
            if (ghError != null) {
                responseHandler(ghError, null)
            } else {
                val result = response as ResponseSearchUsers

                responseHandler(null, result.searchResults)
            }
        }))
    }

    fun getUser(login: String, responseHandler: (GHError?, GHUser?) -> Unit) {
        val requestMap = mapOf<String, Any>("login" to login)

        GitHubAPIAsyncTask().execute(Triple(APIRequestType.GET_USER, requestMap, { ghError, response ->
            if (ghError != null) {
                responseHandler(ghError, null)
            } else {
                val result = response as ResponseGetUser

                responseHandler(null, result.ghUser)
            }
        }))
    }

    fun getFollowers(login: String, responseHandler: (GHError?, List<GHSearchUser>?) -> Unit) {
        val requestMap = mapOf<String, Any>("login" to login)

        GitHubAPIAsyncTask().execute(Triple(APIRequestType.GET_FOLLOWERS, requestMap, { ghError, response ->
            if (ghError != null) {
                responseHandler(ghError, null)
            } else {
                val result = response as ResponseGetFollowers

                responseHandler(null, result.followers)
            }
        }))
    }

    fun getImage(url: String, responseHandler: (GHError?, Bitmap?) -> Unit) {
        DownloadImageAsyncTask().execute(Pair(url, { ghError, response ->
            responseHandler(ghError, response)
        }))
    }
}

