package com.example.localizationHelper

import java.text.DecimalFormat
import java.util.*


object LocaleHelper {

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
    }
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