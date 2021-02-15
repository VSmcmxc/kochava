package com.appsender.config

import java.lang.RuntimeException

class AuthenticationException(override val message: String) : RuntimeException(message) {
}