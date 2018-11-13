package pt.nmplcpimenta.innowavegithuber.tasks

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import pt.nmplcpimenta.innowavegithuber.datamodels.GHError


class DownloadImageAsyncTask : AsyncTask<Pair<String, (GHError?, Bitmap?) -> Unit>, Unit, Unit>() {

    override fun doInBackground(vararg params: Pair<String, (GHError?, Bitmap?)->Unit>) {

        val url = params[0].first
        val responseHandler = params[0].second

        var bmp: Bitmap? = null
        try {
            val `in` = java.net.URL(url).openStream()
            bmp = BitmapFactory.decodeStream(`in`)

            responseHandler(null, bmp)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()

            responseHandler(GHError(e.localizedMessage), null)
        }
    }
}