package com.rohan.demovehicleverification.other

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rohan.demovehicleverification.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    fun getRandomImagePath() = arrayListOf(
        "https://source.unsplash.com/user/c_v_r/400x300",
        "https://source.unsplash.com/user/c_v_r/300x300",
        "https://source.unsplash.com/user/c_v_r/200x300",
        "https://source.unsplash.com/user/c_v_r/300x200",
        "https://source.unsplash.com/user/c_v_r/400x400",
        "https://source.unsplash.com/user/c_v_r/450x400",
        "https://source.unsplash.com/user/c_v_r/100x200",
    ).random()

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun checkNetworkEnableAndShowDialog(context: Context): Boolean {
        val result = isNetworkAvailable(context)
        if (!result) {
            MaterialAlertDialogBuilder(context)
                .setTitle(context.resources.getText(R.string.error))
                .setMessage(context.resources.getText(R.string.network_error))
                .setNeutralButton(context.resources.getText(R.string.ok)) { _, _ ->
                }
                .show()
        }
        return result
    }

    @SuppressLint("SimpleDateFormat")
    fun millsToText(createdDate: Long): String {
        return try {
            val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            formatter.format(Date(createdDate))
        } catch (e: Exception) {
            ""
        }
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
