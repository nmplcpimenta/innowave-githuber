package pt.nmplcpimenta.innowavegithuber.businessmanagers

import android.app.Activity
import android.graphics.Bitmap
import android.support.v4.app.FragmentActivity
import pt.nmplcpimenta.innowavegithuber.datamanagers.RESTManager
import pt.nmplcpimenta.innowavegithuber.datamodels.GHError
import pt.nmplcpimenta.innowavegithuber.datamodels.GHSearchUser
import pt.nmplcpimenta.innowavegithuber.datamodels.GHUser
import pt.nmplcpimenta.innowavegithuber.utils.runOnMainThread

object Consuela {

    fun searchUsers(keyword: String, completion: (GHError?, List<GHSearchUser>?) -> Unit) {
        RESTManager.searchUsers(keyword) { ghError, searchResults ->
            runOnMainThread { completion(ghError, searchResults) }
        }
    }

    fun getUser(login: String, completion: (GHError?, GHUser?) -> Unit) {
        RESTManager.getUser(login) { ghError, ghUser ->
            runOnMainThread { completion(ghError, ghUser) }
        }
    }

    fun getFollowers(login: String, completion: (GHError?, List<GHSearchUser>?) -> Unit) {
        RESTManager.getFollowers(login) { ghError, followers ->
            runOnMainThread { completion(ghError, followers) }
        }
    }

    fun getImage(url: String, completion: (GHError?, Bitmap?) -> Unit) {
        RESTManager.getImage(url) { ghError, bitmap ->
            runOnMainThread { completion(ghError, bitmap) }
        }
    }
}