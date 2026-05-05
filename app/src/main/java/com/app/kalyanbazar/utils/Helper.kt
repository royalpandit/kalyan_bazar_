package com.app.kalyanbazar.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Base64
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.app.kalyanbazar.R
import com.app.kalyanbazar.databinding.DatePickerBinding
import com.google.android.material.snackbar.Snackbar
 import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.sql.Time
import java.text.*
import java.time.LocalDate
import java.util.*
 import java.util.regex.Pattern
 import kotlin.math.roundToInt


/**
 * Created by dheerajpandey on 6/22/18.
 */
class Helper {
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun getDurationString(seconds: Int): String {
        //   int hours = seconds / 3600;
        var seconds = seconds
        val minutes = seconds % 3600 / 60
        seconds = seconds % 60
        return twoDigitString(minutes) + " : " + twoDigitString(seconds)
    }

    private fun twoDigitString(number: Int): String {
        if (number == 0) {
            return "00"
        }
        return if (number / 10 == 0) {
            "0$number"
        } else number.toString()

    }

    companion object {
        //     var RoleList: ArrayList<UserRolesModel> = ArrayList()

        private var snackbar: Snackbar? = null
        private val dialog: Dialog? = null
        private val mToast: Toast? = null
        private const val isApplied = false
        private const val isPopUp = false
        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS
        var alertIsBeingShown = false
        var alertIsBeingShownDialogBox = false
        fun replaceFragment(frameId: Int, fragment: Fragment, activity: AppCompatActivity) {
            activity.supportFragmentManager.beginTransaction().replace(frameId, fragment).commit()
        }


        /**
         * for showing the messages in the bottom
         */
        fun showSnackBar(view1: View?, message: String?) {
            try {
                snackbar = Snackbar.make(view1!!, message!!, Snackbar.LENGTH_LONG)
                snackbar!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * for showing the messages in the bottom
         */
        fun showSnackBar(view1: TextView?, message: String?) {
            try {
                snackbar = Snackbar.make(view1!!, message!!, Snackbar.LENGTH_LONG)
                snackbar!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



        fun startDialer(activity: Activity, s: String) {
            var s = s
            s = s.replace("[^\\d.]".toRegex(), "")
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$s"))
            activity.startActivity(intent)
        }

        fun findPercentage(view1: TextView, amt: Int): Int {

            if (view1.text.toString() != "") {
                val amount = view1.text.toString().toDouble()
                val res = (amount / amt) * 100.0
                return res.roundToInt().toInt()

            } else {
                return 0
            }
        }


        fun findPercentage(view1: Int, amt: Int): Int {

            if (view1 != 0) {
                val amount = view1.toDouble()
                val res = (amount / amt) * 100.0
                return res.roundToInt().toInt()

            } else {
                return 0
            }
        }

        fun getCurrentDateForApi(): String {
            val c: Date = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return df.format(c)
        }


        fun getEndDateForApi(mon: String): String {
            val date = "$mon/01/" + Helper.getCurrentYear()

            val dateFormat = SimpleDateFormat("MM/dd/yyyy")
            val convertedDate = dateFormat.parse(date)
            val c = Calendar.getInstance()
            c.time = convertedDate
            c.add(Calendar.MONTH, 1)
            c[Calendar.DAY_OF_MONTH] = 1
            c.add(Calendar.DATE, -1)
            val lastDayOfMonth = c.time
            val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            sdf.format(lastDayOfMonth)
            return sdf.format(lastDayOfMonth)

        }

        @JvmStatic
        fun getCurrentDateWithMonthName(): String {
            val c: Date = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
            return df.format(c)
        }

        @JvmStatic
        fun getCurrentDateWithMonthNameWithoutYear(): String {
            val c: Date = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("dd/MMMM", Locale.getDefault())
            return df.format(c).replace("/", " ")
        }

        @JvmStatic
        fun getYesterdayDateWithMonthNameWithoutYear(): String {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("dd/MMMM", Locale.getDefault())
            val calendar = Calendar.getInstance()
            c.add(Calendar.DAY_OF_YEAR, -1)
            val tomorrow = c.time
            val yesterday = df.format(tomorrow)


            return yesterday.toString().replace("/", " ")
        }


        @JvmStatic
        fun getWeekDateWithMonthNameWithoutYear(): String {
            val c: Date = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("dd/MMMM", Locale.getDefault())
            return (getDateWeak().substring(0, 2) + "-" + df.format(c)).replace("/", " ")
        }

        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDate = sdf.format(Date())

            return currentDate.toString()
        }

        // 2022-09-19
        fun getCurrentDateYMD(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = sdf.format(Date())

            return currentDate.toString()
        }


        fun getCurrentYear(): Int {

            return Calendar.getInstance().get(Calendar.YEAR)
        }

        fun getCurrentMonth(): Int {
            val c = Calendar.getInstance()
            return c[Calendar.MONTH]
        }

        @JvmStatic
        fun getCurrentMonthWithName(): String {
            val cal = Calendar.getInstance()
            val month_date = SimpleDateFormat("MMMM")
            val month_name: String = month_date.format(cal.time)
            return month_name
        }

        fun getCurrentDay(): String {
            val sdf = SimpleDateFormat("EEEE")
            val d = Date()
            val dayOfTheWeek = sdf.format(d)
            return dayOfTheWeek
        }

        fun getTimeDiff(): Int {
            val sdf = SimpleDateFormat("hh:mm a")
            val currentDate = sdf.format(Date())


            val simpleDateFormat = SimpleDateFormat("hh:mm a")

            val date1 = simpleDateFormat.parse("05:00 PM")
            val date2 = simpleDateFormat.parse(currentDate)

            val difference: Long = date2.getTime() - date1.getTime()
            val days = (difference / (1000 * 60 * 60 * 24)).toInt()
            var hours =
                ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toLong().toInt()
            var min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toLong()
                .toInt() / (1000 * 60)
//            hours = if (hours < 0) -hours else hours
            hours = if (hours < 0) 0 else hours
//            Log.i("======= Hours", " :: $hours")
            return hours
        }

        fun getTimeDiffForReportSubmit(): Int {
            val sdf = SimpleDateFormat("hh:mm a")
            val currentDate = sdf.format(Date())


            val simpleDateFormat = SimpleDateFormat("hh:mm a")

            val date1 = simpleDateFormat.parse("03:00 PM")
            val date2 = simpleDateFormat.parse(currentDate)

            val difference: Long = date2.getTime() - date1.getTime()
            val days = (difference / (1000 * 60 * 60 * 24)).toInt()
            var hours =
                ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toLong().toInt()
            var min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toLong()
                .toInt() / (1000 * 60)
//            hours = if (hours < 0) -hours else hours
            hours = if (hours < 0) 0 else hours
//            Log.i("======= Hours", " :: $hours")
            return hours
        }

        fun getCurrentStartDate(pos: Int): String {
            var year = Calendar.getInstance().get(Calendar.YEAR)
            return when (pos) {
                1 -> {
                    "$year-01-01"
                }
                2 -> {
                    "$year-02-01"
                }
                3 -> {
                    "$year-03-01"
                }
                4 -> {
                    "$year-04-01"
                }
                5 -> {
                    "$year-05-01"
                }
                6 -> {
                    "$year-06-01"
                }
                7 -> {
                    "$year-07-01"
                }
                8 -> {
                    "$year-08-01"
                }
                9 -> {
                    "$year-09-01"
                }
                10 -> {
                    "$year-10-01"
                }
                11 -> {
                    "$year-11-01"
                }
                else -> {
                    "$year-12-01"
                }
            }
        }

        fun getCurrentDateWithTimeForApi(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = sdf.format(Date())

            return currentDate.toString()
        }




        fun getTomarrowDate(): String {
            val calendar = Calendar.getInstance()
            val today = calendar.time
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrow = calendar.time
            val todayAsString = dateFormat.format(today)
            val tomorrowAsString = dateFormat.format(tomorrow)


            return tomorrowAsString.toString()
        }

        fun getYesterdayDate(): String {
            val calendar = Calendar.getInstance()
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val tomorrow = calendar.time
            val tomorrowAsString = dateFormat.format(tomorrow)


            return tomorrowAsString.toString()
        }

        fun getTomarrowDateForApi(): String {
            val calendar = Calendar.getInstance()
            val today = calendar.time
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrow = calendar.time
            val todayAsString = dateFormat.format(today)
            val tomorrowAsString = dateFormat.format(tomorrow)

            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
            val d = dateFormat.parse(tomorrowAsString)
            return dateFormat1.format(d)
        }

        fun getYesterdayDateForApi(): String {
            val calendar = Calendar.getInstance()
            val today = calendar.time
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val tomorrow = calendar.time
            val todayAsString = dateFormat.format(today)
            val tomorrowAsString = dateFormat.format(tomorrow)

            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
            val d = dateFormat.parse(tomorrowAsString)
            return dateFormat1.format(d)
        }

        fun getDayBeforeYesterdayDateForApi(): String {
            val calendar = Calendar.getInstance()
            val today = calendar.time
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            calendar.add(Calendar.DAY_OF_YEAR, -2)
            val tomorrow = calendar.time
            val todayAsString = dateFormat.format(today)
            val tomorrowAsString = dateFormat.format(tomorrow)

            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
            val d = dateFormat.parse(tomorrowAsString)
            return dateFormat1.format(d)
        }

        @JvmStatic
        fun getPreviousMonth(): String {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val now = LocalDate.now()
                val earlier = now.minusMonths(1) // 2015-10-24
                return earlier.month.toString()
            } else {
                return ""
            } // 2015-11-24
        }


        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun getISATime(str: String): String {
            val time = str.subSequence(0, 19)
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val d = df.parse(time.toString())
            val cal = Calendar.getInstance()
            cal.time = d
            cal.add(Calendar.HOUR, 5)
            cal.add(Calendar.MINUTE, 30)
            return df.format(cal.time).toString()

        }


        @JvmStatic
        fun calculateAmount(amt: Int?, str: String): Int {
            if (amt == null) {
                return 0
            }
            return when (str) {
                "Lakh" -> {
                    amt * 100000
                }
                "Cr" -> {
                    amt * 10000000
                }
                else -> {
                    0
                }

            }
        }


        @JvmStatic
        fun calculateAmount(amt: Long?, str: String): Long {
            if (amt == null) {
                return 0
            }
            return when (str) {
                "Lakh" -> {
                    amt * 100000
                }
                "Cr" -> {
                    amt * 10000000
                }
                else -> {
                    0
                }

            }
        }


        @JvmStatic
        fun calculateAmountBySpinner(str: String): Long {
            try {
                if (str.contains("Hnrd")) {
                    return Integer.valueOf(str.substring(0, str.indexOf(" "))).toLong() * 100
                }
                if (str.contains("Thsd")) {
                    return Integer.valueOf(str.substring(0, str.indexOf(" "))).toLong() * 1000
                }
                if (str.contains("Lakh")) {
                    return Integer.valueOf(str.substring(0, str.indexOf(" "))).toLong() * 100000
                }
                if (str.contains("Cr")) {
                    return Integer.valueOf(str.substring(0, str.indexOf(" "))).toLong() * 10000000
                } else {
                    return 0
                }
            } catch (e: java.lang.Exception) {
                return 0
            }
        }

        @JvmStatic
        fun nullCheckForDateCounts(startDate: String?, endDate: String?): String {
            try {
                // val sdf: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val start = Calendar.getInstance()
                start.time = sdf.parse(startDate)
                val end = Calendar.getInstance()
                end.time = sdf.parse(endDate)

                var monthsBetween = 0
                // Log.e("xx1","xx1")
                var dateDiff = end[Calendar.DAY_OF_MONTH] - start[Calendar.DAY_OF_MONTH]
                // Log.e("xx1","diff"+dateDiff)

                if (dateDiff < 0) {
                    val borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH)
                    dateDiff = end[Calendar.DAY_OF_MONTH] + borrrow - start[Calendar.DAY_OF_MONTH]
                    monthsBetween--
                    //  Log.e("xx1","xx2")
                    if (dateDiff > 0) {
                        monthsBetween++
                    }
                } else if (dateDiff == 1) {
                    return "${1} Day"
                } else {
                    // Log.e("xx1","xx3")
                    monthsBetween++
                    // Log.e("monthsBetween++","==>"+monthsBetween)
                }

                monthsBetween += end[Calendar.MONTH] - start[Calendar.MONTH]
                monthsBetween += (end[Calendar.YEAR] - start[Calendar.YEAR]) * 12
                // Log.e("xx1","xx4")
                // Log.e("monthsBetween","==>"+monthsBetween)
                if (monthsBetween == 1) {
                    // Log.e("xx1","xx5")

                    if (getCountOfDaysNew(startDate, endDate) < 7)
                        return "${dateDiff} Days"
                    //return "${getCountOfDaysNew(startDate, endDate)} Days"
                    else {
                        var week = getCountOfDaysNew(startDate, endDate) / 7f
                        return "${week.roundToInt()} Weeks"
                    }
                    // Log.e("xx1","xx6")

                }
                return "${monthsBetween} Months"
            } catch (e: java.lang.Exception) {
                return "1 Months"
            }
        }

        @JvmStatic
        fun getFormatedNumber(number: String): String? {
            return if (!number.isEmpty()) {
                val `val` = number.toDouble()
                NumberFormat.getNumberInstance(Locale.US).format(`val`)
            } else {
                "0"
            }
        }

        @JvmStatic
        fun getTomorrow(): String? {
            val cal: Calendar = GregorianCalendar()
            cal.add(Calendar.DATE, 1)
            return SimpleDateFormat("yyyy-MM-dd").format(cal.time)
        }

        @JvmStatic
        fun getCountOfDays(createdDateString: String?, expireDateString: String?): Int {
            try {
                //   val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                var createdConvertedDate: Date? = null
                var expireCovertedDate: Date? = null
                var todayWithZeroTime: Date? = null
                try {
                    createdConvertedDate = dateFormat.parse(createdDateString)
                    expireCovertedDate = dateFormat.parse(expireDateString)
                    val today = Date()
                    todayWithZeroTime = dateFormat.parse(dateFormat.format(today))
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                var cYear = 0
                var cMonth = 0
                var cDay = 0
                if (createdConvertedDate!!.after(todayWithZeroTime)) {
                    val cCal = Calendar.getInstance()
                    cCal.time = createdConvertedDate
                    cYear = cCal[Calendar.YEAR]
                    cMonth = cCal[Calendar.MONTH]
                    cDay = cCal[Calendar.DAY_OF_MONTH]
                } else {
                    val cCal = Calendar.getInstance()
                    cCal.time = todayWithZeroTime
                    cYear = cCal[Calendar.YEAR]
                    cMonth = cCal[Calendar.MONTH]
                    cDay = cCal[Calendar.DAY_OF_MONTH]
                }


                val eCal = Calendar.getInstance()
                eCal.time = expireCovertedDate
                val eYear = eCal[Calendar.YEAR]
                val eMonth = eCal[Calendar.MONTH]
                val eDay = eCal[Calendar.DAY_OF_MONTH]
                val date1 = Calendar.getInstance()
                val date2 = Calendar.getInstance()
                date1.clear()
                date1[cYear, cMonth] = cDay
                date2.clear()
                date2[eYear, eMonth] = eDay
                val diff = date2.timeInMillis - date1.timeInMillis
                val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000)
                return dayCount.toInt()


            } catch (e: java.lang.Exception) {
                return -1
            }
        }

        @JvmStatic
        fun getCountOfDaysNew(createdDateString: String?, expireDateString: String?): Int {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                var createdConvertedDate: Date? = null
                var expireCovertedDate: Date? = null
                var todayWithZeroTime: Date? = null
                try {
                    createdConvertedDate = dateFormat.parse(createdDateString)
                    expireCovertedDate = dateFormat.parse(expireDateString)
                    val today = Date()
                    todayWithZeroTime = dateFormat.parse(dateFormat.format(today))
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                var cYear = 0
                var cMonth = 0
                var cDay = 0
                if (createdConvertedDate!!.after(todayWithZeroTime)) {
                    val cCal = Calendar.getInstance()
                    cCal.time = createdConvertedDate
                    cYear = cCal[Calendar.YEAR]
                    cMonth = cCal[Calendar.MONTH]
                    cDay = cCal[Calendar.DAY_OF_MONTH]
                } else {
                    val cCal = Calendar.getInstance()
                    cCal.time = todayWithZeroTime
                    cYear = cCal[Calendar.YEAR]
                    cMonth = cCal[Calendar.MONTH]
                    cDay = cCal[Calendar.DAY_OF_MONTH]
                }


                val eCal = Calendar.getInstance()
                eCal.time = expireCovertedDate
                val eYear = eCal[Calendar.YEAR]
                val eMonth = eCal[Calendar.MONTH]
                val eDay = eCal[Calendar.DAY_OF_MONTH]
                val date1 = Calendar.getInstance()
                val date2 = Calendar.getInstance()
                date1.clear()
                date1[cYear, cMonth] = cDay
                date2.clear()
                date2[eYear, eMonth] = eDay
                val diff = date2.timeInMillis - date1.timeInMillis
                val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000)
                return dayCount.toInt()
            } catch (e: java.lang.Exception) {
                return -1
            }
        }

        fun getDateFormateForApi(date: String): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
            val d = dateFormat.parse(date)
            return dateFormat1.format(d)
        }

        fun convertToCustomFormat(dateStr: String?): String {
            val utc = TimeZone.getTimeZone("UTC")
            val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
            val destFormat = SimpleDateFormat("yyyy-MM-dd")
            sourceFormat.timeZone = utc
            val convertedDate = sourceFormat.parse(dateStr)
            return destFormat.format(convertedDate)
        }

        fun getDateLastWeakStart(): String {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.MONDAY
//            calendar.add(Calendar.WEEK_OF_MONTH, -1)
            calendar.add(Calendar.DATE, -7);
            calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

            val days = arrayOfNulls<String>(7)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            return days[0]!!
        }


        fun getDateLastWeakStartForApi(): String {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.MONDAY
//            calendar.add(Calendar.WEEK_OF_MONTH, -1)
            calendar.add(Calendar.DATE, -7);
            calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

            val days = arrayOfNulls<String>(7)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            return Helper.getDateFormateForApi(days[0]!!)
        }


        fun getDateLastWeakEnd(): String {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.SUNDAY
//            calendar.add(Calendar.WEEK_OF_MONTH, -1)
            calendar.add(Calendar.DATE, -7);
            calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

            val days = arrayOfNulls<String>(7)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            return days[6]!!
        }


        fun getDateLastWeakEndForApi(): String {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.SUNDAY
//            calendar.add(Calendar.WEEK_OF_MONTH, -1)
            calendar.add(Calendar.DATE, -7);
            calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

            val days = arrayOfNulls<String>(7)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            return Helper.getDateFormateForApi(days[6]!!)
        }


        fun getDateWeak(): String {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()

            calendar.firstDayOfWeek = Calendar.MONDAY
            calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

            val days = arrayOfNulls<String>(7)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            return days[0]!!
        }

        fun getDateWeakForApi(): String {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.MONDAY
            calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

            val days = arrayOfNulls<String>(7)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            return Helper.getDateFormateForApi(days[0]!!)
        }


        fun TextView.getDateFormateForApi(): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
            val d = dateFormat.parse(this.text.toString())
            return dateFormat1.format(d)
        }


        fun capitalize(s: String?): String {
            if (s == null || s.length == 0) {
                return ""
            }
            val first = s[0]
            return if (Character.isUpperCase(first)) {
                s
            } else {
                Character.toUpperCase(first).toString() + s.substring(1)
            }
        }

        fun capitalizeFirstlatter(firstname: String?, lastname: String?): String {
            if (firstname == null || firstname.length == 0 || lastname == null || lastname.length == 0) {
                return ""
            }
            val shortName = firstname?.first().toString() + lastname?.first().toString()
            return shortName

        }


        val deviceName: String
            get() {
                val manufacturer = Build.MANUFACTURER
                val model = Build.MODEL
                return if (model.startsWith(manufacturer)) {
                    capitalize(model)
                } else {
                    capitalize(manufacturer) + " " + model
                }
            }


        fun setTextViewDrawableColor(editText: EditText, color: Int) {
            for (drawable in editText.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
            }
        }

//        fun getImagePath(uri: Uri?, activity: Activity): String {
//            var cursor = activity.contentResolver.query(uri!!, null, null, null, null)
//            cursor!!.moveToFirst()
//            var document_id = cursor.getString(0)
//            document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
//            cursor.close()
//            cursor = activity.contentResolver.query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
//            )
//            cursor!!.moveToFirst()
//            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
//            cursor.close()
//            return path
//        }

        /* for setting fragment in the container */
        @JvmName("setFragment1")
        @JvmStatic
        fun setFragment(
            fragment: Fragment?,
            removeStack: Boolean,
            activity: FragmentActivity,
            mContainer: Int,
            tag: String,
            bundle: Bundle? = null
        ) {
            fragment?.arguments = bundle
            val fragmentManager = activity.supportFragmentManager
            val ftTransaction = fragmentManager.beginTransaction()
            if (removeStack) {
                val size = fragmentManager.backStackEntryCount
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                ftTransaction.replace(mContainer, fragment!!)
            } else {
                ftTransaction.replace(mContainer, fragment!!)
                ftTransaction.addToBackStack(null)
            }
            ftTransaction.commit()
        }


        fun formatDate(inputDate: String?): String {
            var outputDate: String = ""
            /*  "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        2019-06-17T11:01:24.000Z*/
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM,dd hh:mm", Locale.getDefault())
            var date: Date? = null
            try {
                date = inputFormat.parse(inputDate)
                outputDate = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return outputDate
        }

        fun formatDateTime(inputDate: String?): String {
            var outputDate: String = ""
            /*  "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        2019-06-17T11:01:24.000Z*/
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM,dd HH:mm", Locale.getDefault())
            var date: Date? = null
            try {
                date = inputFormat.parse(inputDate)
                outputDate = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return outputDate
        }


        fun formatDateTimeForDateAndMonthOnly(inputDate: String?): String {
            var outputDate: String = ""

            //     val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MMM", Locale.getDefault())
            var date: Date? = null
            try {
                date = inputFormat.parse(inputDate)
                outputDate = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return outputDate
        }

        @JvmStatic
        fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        @JvmStatic
        fun getCurrentDateTime(): Date {
            return Calendar.getInstance().time
        }

        fun getDifferenceBetweenTime(str_date: String?): String {
            val cal = Calendar.getInstance()
            val tz = cal.timeZone
            val formatter: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            var date: Date? = null
            try {
                date = formatter.parse(str_date)
                return DateUtils.getRelativeTimeSpanString(date.time, System.currentTimeMillis(), 0)
                    .toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            println("Today is " + date!!.time)
            return ""
        }

        /**
         * This method is to return duration in minute and seconds for the playing time
         *
         * @param seconds specifies the total millisceonds played
         */
        fun formatPlayingDuration(seconds: Int): String {
            // int seconds = milliseconds / 1000;
            val minutes = seconds / 60
            val displayedSeconds = seconds % 60
            return if (minutes == 0) "00:" + addZero(
                displayedSeconds
            ) else addZero(minutes) + ":" + addZero(
                displayedSeconds
            )
        }

        /**
         * This method is to return number with zero or not zero i.e. to make a number in two digit
         *
         * @param number specifies the number that needs to be foramtted
         */
        private fun addZero(number: Int): String {
            return if (number < 10) "0$number" else "" + number
        }



        /*------------------------------------------- Method to print Hash key-----------------------------------------------------*/
        fun printHashKey(pContext: Context) {
            try {
                val info = pContext.packageManager.getPackageInfo(
                    pContext.packageName,
                    PackageManager.GET_SIGNATURES
                )
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val hashKey = String(Base64.encode(md.digest(), 0))
                    Log.i(ContentValues.TAG, "printHashKey() Hash Key: $hashKey")
                }
            } catch (e: NoSuchAlgorithmException) {
                Log.e(ContentValues.TAG, "printHashKey()", e)
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "printHashKey()", e)
            }
        }
/*------------------xxxxxxlxxx-----------------------------*/
fun isValidEmail(str: String): Boolean{
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
}

        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )


        fun isMobileValid(str: String): Boolean {
            // 11 digit number start with 011 or 010 or 015 or 012
            // then [0-9]{8} any numbers from 0 to 9 with length 8 numbers
            if(!Pattern.matches("(9|8|7|6)[0-9]{10}", str)) {
                return false
            }
            return true
        }

        /*-------------------------------------xxxxxxxxxxxxxxxxxx----------------------------------------------------------------------*/
        fun checkWhatsAppInstalledOrNot(activity: Activity): Boolean {
            val uri = "com.whatsapp"
            val pm = activity.packageManager
            val app_installed: Boolean
            app_installed = try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
            return app_installed
        }

        @JvmStatic
        fun isIfscCodeValid(IFSCCode: String): Boolean {
            val regExp = "^[A-Z]{4}[0][A-Z0-9]{6}$"
            var isvalid = false
            if (IFSCCode.length > 0) {
                isvalid = Pattern.compile(regExp).matcher(IFSCCode).matches()
            }
            return isvalid
        }

        @JvmStatic
        fun isAccountCodeValid(IFSCCode: String): Boolean {
            val regExp = "^\\d{9,18}$"
            var isvalid = false
            if (IFSCCode.length > 0) {
                isvalid = Pattern.compile(regExp).matcher(IFSCCode).matches()
            }
            return isvalid
        }

        @JvmStatic
        fun validateAadharNumber(aadharNumber: String): Boolean {
            val aadharPattern = Pattern.compile("\\d{12}")
            val isValidAadhar = aadharPattern.matcher(aadharNumber).matches()
//            if (isValidAadhar) {
//                isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber)
//            }
            return isValidAadhar
        }

        @JvmStatic
        fun validatePanNumber(pan: String): Boolean {

            val pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")

            val matcher = pattern.matcher(pan)
            return matcher.matches()
        }

        @JvmStatic
        fun getRandomString(length: Int): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

        @JvmStatic
        fun dateFormate(data: String): String? {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateZ(data: String): String? {

            /*val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val timestamp = data.toLong() // timestamp in Long


            val timestampAsDateString = java.time.format.DateTimeFormatter.ISO_INSTANT
                .format(java.time.Instant.ofEpochSecond(timestamp))

            Log.e("parseTesting", timestampAsDateString) // prints 2019-08-07T20:27:45Z


            val date = LocalDate.parse(timestampAsDateString, secondApiFormat)

            Log.e("parseTesting", date.dayOfWeek.toString()) // prints Wednesday
            Log.e("parseTesting", date.month.toString()) // prints August
*/

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }


        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateZHyphn(data: String): String? {

            /*val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val timestamp = data.toLong() // timestamp in Long


            val timestampAsDateString = java.time.format.DateTimeFormatter.ISO_INSTANT
                .format(java.time.Instant.ofEpochSecond(timestamp))

            Log.e("parseTesting", timestampAsDateString) // prints 2019-08-07T20:27:45Z


            val date = LocalDate.parse(timestampAsDateString, secondApiFormat)

            Log.e("parseTesting", date.dayOfWeek.toString()) // prints Wednesday
            Log.e("parseTesting", date.month.toString()) // prints August
*/

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateddmmyyyForPickerYYYDdMm(data: String): String? {

            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val dateFormat1 = SimpleDateFormat("dd-MM-yyyy")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }


        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateddmmyyyForPicker(data: String): String? {

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateFormat1 = SimpleDateFormat("dd-MM-yyyy")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateddmmyyy(data: String): String? {

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateFormat1 = SimpleDateFormat("dd-MM-yyyy")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        ///formatDateTimeForDateAndMonthOnly
        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateConverter(data: String): String? {

           // val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
         //   val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val dateFormat1 = SimpleDateFormat("dd-MM-yyyy hh:mm a")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }


        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateampm(data: String): String? {


            val dateFormat = SimpleDateFormat("hh:mm")
            val dateFormat1 = SimpleDateFormat("hh:mm a")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }
        @SuppressLint("NewApi")
        @JvmStatic
        fun dateFormateYYYYMMDD(data: String): String? {


            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

            try {
                val d = dateFormat.parse(data)
                return dateFormat1.format(d).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        fun getLastNCharsOfString(str: String, n: Int): String? {
            var lastnChars = str
            if (lastnChars.length > n) {
                lastnChars = lastnChars.substring(lastnChars.length - n, lastnChars.length)
            }
            return lastnChars
        }

        fun getFirstNCharsOfString(str: String, n: Int): String? {
            var firstFourChars = str
            if (firstFourChars.length > n) {
                firstFourChars = firstFourChars.substring(0, 4)
            }

            return firstFourChars
        }

        @JvmStatic
        fun DatePickerDialogBoxAll(context: Context?, activity: Activity, eText: TextView) {
            Helper.hideKeyboard(activity)
            if (alertIsBeingShown) return;
            var dialogView = LayoutInflater.from(context).inflate(R.layout.date_picker, null)
            alertIsBeingShown = true
            val dialogBinding: DatePickerBinding = DataBindingUtil.bind(dialogView)!!
            var dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(true)

   //  dialogBinding.datePicker1.maxDate = System.currentTimeMillis()
          //  dialogBinding.datePicker1.setMaxDate(System.currentTimeMillis())

            dialogBinding.tvCancel.setOnClickListener {
                alertIsBeingShown = false
                dialog.dismiss()

            }
            dialogBinding.tvOkay.setOnClickListener {
                alertIsBeingShown = false
eText.text = dialogBinding.datePicker1.year.toString() + "-" + (dialogBinding.datePicker1.month + 1) + "-" + dialogBinding.datePicker1.dayOfMonth.toString()
               // eText.text = dialogBinding.datePicker1.dayOfMonth.toString() + "-" + (dialogBinding.datePicker1.month + 1) + "-" + dialogBinding.datePicker1.year
                Log.e("==>startsss", "" + eText.toString())

                dialog.dismiss()

            }
            dialog.show()
            val lp = WindowManager.LayoutParams()
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.CENTER
            dialog.window!!.attributes = lp
             //dialog.window!!.setWindowAnimations(R.style.AnimationCenterPopUp)
             dialog.window!!.setWindowAnimations(R.style.datepicker)
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            dialog.window!!
                .setBackgroundDrawable(
                    ColorDrawable(
                        ContextCompat.getColor(
                            context,
                            R.color.seme_transparent
                        )
                    )
                )
        }

        fun getNextDate(month: String): String {
            val currentDate: Calendar = GregorianCalendar()
            currentDate.setTime(Date())
            val sdf = SimpleDateFormat("dd/MM/yyyy") //add space after MMMM and add yyyy for year
            val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            Log.d("NOW ", sdf.format(currentDate.getTime())) // NOW

            currentDate.add(Calendar.MONTH, month.toInt())
            Log.d("One month ago ", dateFormat1.format(currentDate.getTime())) // One month ago

            currentDate.add(Calendar.MONTH, month.toInt())
            Log.d("Two month ago ", sdf.format(currentDate.getTime()))


            try {
                return currentDate.toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return currentDate.toString()
        }


        @JvmStatic
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }



   fun getTime(context: Context, activity: Activity, eText: TextView) {

            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                cal.set(Calendar.SECOND, SECOND_MILLIS)

                eText.text = SimpleDateFormat("HH:mm:ss").format(cal.time)
            }

            eText.setOnClickListener {
                TimePickerDialog(
                    context,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
        }

        fun getTimeAmPm(hr: Int, min: Int): String? {
            val tme = Time(hr, min, 0) //seconds by default set to zero
            val formatter: Format
            formatter = SimpleDateFormat("h:mm a")
            return formatter.format(tme)
        }


    }

    fun removeWords(word: String, remove: String?): String? {
        return word.replace(remove!!, "")
    }





}