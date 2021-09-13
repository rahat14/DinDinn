package com.syntex_error.dindinn

import android.annotation.SuppressLint
import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.log

class Utils {

    companion object {

        fun dateConverter(string: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'+'SS'Z'")
            val outputFormat = SimpleDateFormat("hh:mm a")
            val date: Date = inputFormat.parse(string)
            val formattedDate: String = outputFormat.format(date)

            return formattedDate
        }

        @SuppressLint("SimpleDateFormat")
        fun getInterval(startDate: String, endDate: String): Long {
            val sdf = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm'+'SS'Z'"
            )
            try {

                val d1 = sdf.parse(startDate)
                val d2 = sdf.parse(endDate)

                val difference_In_Time = d2.time - d1.time



                return difference_In_Time/1000

            } catch (ex: Exception) {
                Log.d("TAG", "getInterval:  ${ex.localizedMessage}")
                return 0
            }

        }


    }
}