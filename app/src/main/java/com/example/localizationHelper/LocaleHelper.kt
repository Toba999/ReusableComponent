package com.example.localizationHelper

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*


object LocaleHelper {

    fun localizeNumber(
        number: Number,
        minimumFractionDigits: Int = 0,
        maximumFractionDigits: Int = 2,
        minimumIntegerDigits: Int = 1
    ): String {
        val decimalFormat = DecimalFormat.getInstance(Locale.getDefault())
        decimalFormat.isGroupingUsed = true
        decimalFormat.minimumFractionDigits = minimumFractionDigits
        decimalFormat.maximumFractionDigits = maximumFractionDigits
        decimalFormat.minimumIntegerDigits = minimumIntegerDigits
        return decimalFormat.format(number)
    }
}