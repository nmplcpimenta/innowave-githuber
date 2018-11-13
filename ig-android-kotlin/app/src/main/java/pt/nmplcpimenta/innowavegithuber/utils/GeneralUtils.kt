package pt.nmplcpimenta.innowavegithuber.utils

import android.os.Handler
import android.os.Looper

var mainHandler: Handler? = null

fun runOnMainThread(runnable: ()->Unit) {
    if (mainHandler == null)
        mainHandler = Handler(Looper.getMainLooper())

    mainHandler!!.post(runnable)
}