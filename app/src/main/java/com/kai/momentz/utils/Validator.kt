package com.kai.momentz.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

object Validator {
    fun isValidInputEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

}