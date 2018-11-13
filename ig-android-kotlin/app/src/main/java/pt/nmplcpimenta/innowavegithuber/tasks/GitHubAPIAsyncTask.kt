package pt.nmplcpimenta.innowavegithuber.tasks

import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import pt.nmplcpimenta.innowavegithuber.datamanagers.RESTManager
import pt.nmplcpimenta.innowavegithuber.datamodels.*
import java.net.HttpURLConnection
import java.net.URL

class GitHubAPIAsyncTask : AsyncTask<Triple<RESTManager.APIRequestType, Map<String, Any>, (GHError?, Any?) -> Unit>, Int, Unit>() {

    override fun doInBackground(vararg params: Triple<RESTManager.APIRequestType, Map<String, Any>, (GHError?, Any?)->Unit>) {

        val type = params[0].first
        val argsMap = params[0].second
        val responseHandler = params[0].third

        when (type) {
            RESTManager.APIRequestType.SEARCH_USERS -> {
                val url = RESTManager.URL_BASE + "search/users?q=" + (argsMap["keyword"] as String) + "+type:user+in:login+in:email"
                val connection = URL(url).openConnection() as HttpURLConnection

                connection.inputStream.bufferedReader().use { reader ->
                    val response = reader.readText()

                    println("http response: $response")

                    try {
                        val jsonResponse = JSONObject(response)

                        val totalCount = jsonResponse.getInt("total_count")
                        val incompleteResults = jsonResponse.getBoolean("incomplete_results")
                        val jsonSearchResults = jsonResponse.getJSONArray("items")

                        val searchResults = mutableListOf<GHSearchUser>()

                        for (i in 0 until jsonSearchResults.length()) {
                            val jsonSR = JSONObject(jsonSearchResults[i].toString())
                            val ghSearchUser = GHSearchUser(jsonSR.getString("login"), jsonSR.getString("avatar_url"))

                            searchResults.add(ghSearchUser)
                        }

                        responseHandler(null, ResponseSearchUsers(totalCount, incompleteResults, searchResults))
                    } catch (je: JSONException) {
                        je.printStackTrace()

                        responseHandler(GHError(je.localizedMessage), null)
                    } catch (t: Throwable) {
                        t.printStackTrace()

                        responseHandler(GHError(t.localizedMessage), null)
                    }
                }
            }
            RESTManager.APIRequestType.GET_USER -> {
                val url = RESTManager.URL_BASE + "users/"+(argsMap["login"] as String)
                val connection = URL(url).openConnection() as HttpURLConnection

                connection.inputStream.bufferedReader().use { reader ->
                    val response = reader.readText()

                    println("http response: $response")

                    try {
                        val jsonResponse = JSONObject(response)

                        val ghUser = GHUser(jsonResponse.getString("login"), jsonResponse.getString("name"), jsonResponse.getString("avatar_url"))

                        responseHandler(null, ResponseGetUser(ghUser))
                    } catch (je: JSONException) {
                        je.printStackTrace()

                        responseHandler(GHError(je.localizedMessage), null)
                    } catch (t: Throwable) {
                        t.printStackTrace()

                        responseHandler(GHError(t.localizedMessage), null)
                    }
                }
            }
            RESTManager.APIRequestType.GET_FOLLOWERS -> {
                val url = RESTManager.URL_BASE + "users/"+(argsMap["login"] as String)+"/followers"
                val connection = URL(url).openConnection() as HttpURLConnection

                connection.inputStream.bufferedReader().use { reader ->
                    val response = reader.readText()

                    println("http response: $response")

                    try {
                        val jsonFollowers = JSONArray(response)

                        val followers = mutableListOf<GHSearchUser>()

                        for (i in 0 until jsonFollowers.length()) {
                            val jsonSR = JSONObject(jsonFollowers[i].toString())
                            val ghSearchUser = GHSearchUser(jsonSR.getString("login"), jsonSR.getString("avatar_url"))

                            followers.add(ghSearchUser)
                        }

                        responseHandler(null, ResponseGetFollowers(followers))
                    } catch (je: JSONException) {
                        je.printStackTrace()

                        responseHandler(GHError(je.localizedMessage), null)
                    } catch (t: Throwable) {
                        t.printStackTrace()

                        responseHandler(GHError(t.localizedMessage), null)
                    }
                }
            }
        }
    }
}