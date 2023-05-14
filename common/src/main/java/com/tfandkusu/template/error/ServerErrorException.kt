package com.tfandkusu.template.error

/**
 * Server error
 */
data class ServerErrorException(val code: Int, val httpMessage: String) : Exception()
