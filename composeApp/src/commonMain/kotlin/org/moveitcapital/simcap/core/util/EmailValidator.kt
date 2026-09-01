package org.moveitcapital.simcap.core.util

object EmailValidator {
    private val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")

    fun isValidEmail(email: String) = email.matches(emailRegex)
}