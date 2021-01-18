package com.demon.demonjetpack.module.work

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * @author DeMon
 * Created on 2021/1/18.
 * E-mail 757454343@qq.com
 * Desc:
 */
class RetryWorker(val appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val TAG = "RetryWorker"
    override suspend fun doWork(): Result {
        return if (isNetworkAvailable(appContext)) {
            Log.i(TAG, "doWork success: ${System.currentTimeMillis()}")
            Result.success()
        } else {
            Log.i(TAG, "doWork retry: ${System.currentTimeMillis()}")
            Result.retry()
        }

    }


    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity == null) {
        } else {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }
}