package com.interview.willyweathertest.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.interview.willyweathertest.data.model.Movie
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {


        fun getDate(date: String?): String {
            try {
                val localDateFormat = SimpleDateFormat.getDateInstance()
                val serverDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val serverDate: Date = serverDateFormat.parse(date)
                return localDateFormat.format(serverDate)
            } catch (e: Exception) {
            }
            return ""
        }

        @JvmStatic
        @BindingAdapter("releaseAndDuration")
        fun getReleaseAndDuration(textView: TextView, movie: Movie?) {
            try {
                val localDateFormat = SimpleDateFormat.getDateInstance()
                val serverDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val serverDate: Date = serverDateFormat.parse(movie?.releaseDate)
                if (movie!!.runtime != 0) {
                    val hours: Int = movie?.runtime?.div(60) ?: 0
                    val minutes: Int = movie?.runtime?.rem(60) ?: 0
                    textView.text = localDateFormat.format(serverDate) + " - ${hours}h ${minutes}m"
                } else {
                    textView.text = localDateFormat.format(serverDate)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                textView.text = "NA"
            }

        }

        fun getReleaseAndDuration(movie: Movie): String {
            try {
                val localDateFormat = SimpleDateFormat.getDateInstance()
                val serverDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val serverDate: Date = serverDateFormat.parse(movie.releaseDate)
                if (movie.runtime != 0) {
                    val hours: Int = movie.runtime / 60
                    val minutes: Int = movie.runtime % 60
                    return localDateFormat.format(serverDate) + " - ${hours}h ${minutes}m"
                }
                return localDateFormat.format(serverDate)
            } catch (e: Exception) {
            }
            return ""
        }

        @SuppressLint("MissingPermission")
        fun isNetworkAvailable(context: Context): Boolean {
            try {
                return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
            } catch (e: Exception) {

            }
            return false;
        }
    }
}