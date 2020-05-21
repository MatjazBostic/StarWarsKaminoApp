package com.mbostic.starwarskaminoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HelperFunctions {

    companion object {

        const val CONNECTION_RETRY_DELAY_MS = 1000L
        const val DEFAULT_PLANET_ID = 10

        fun forceHttpsInUrl(url: String?): String {
            if(url != null && url.substring(0, 5) == "http:"){
                return "https" + url.substring(4)
            }
            return url.toString()
        }

        fun showProperty(layoutInflater: LayoutInflater, insertPoint: ViewGroup, name: String, value: String?){
            layoutInflater.inflate(R.layout.planet_information_row, null).let {
                it.findViewById<TextView>(R.id.propertyName).text = name + ':'
                it.findViewById<TextView>(R.id.propertyValue).text = value
                insertPoint.addView(it)
            }
        }

        /**
         * Convert one date format string  to another date format string in android
         * https://androidwave.com/format-datetime-in-android/
         *
         * @param inputDateFormat Input SimpleDateFormat
         * @param outputDateFormat Output SimpleDateFormat
         * @param inputDate input Date String
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun formatDateFromDateString(
            inputDateFormat: String?, outputDateFormat: String?,
            inputDate: String?
        ): String? {
            val mParsedDate: Date
            val mOutputDateString: String
            val mInputDateFormat =
                SimpleDateFormat(inputDateFormat, Locale.getDefault())
            val mOutputDateFormat =
                SimpleDateFormat(outputDateFormat, Locale.getDefault())
            mParsedDate = mInputDateFormat.parse(inputDate)
            mOutputDateString = mOutputDateFormat.format(mParsedDate)
            return mOutputDateString
        }
    }
}
