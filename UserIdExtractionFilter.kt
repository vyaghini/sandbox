package com.example.demo.filter

import com.example.demo.security.CustomUserDetails
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserIdExtractionFilter : Filter {

    companion object {
        private const val USER_ID_HEADER = "X-User-Id"
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest

        var userId: String? = null

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication?.principal

        userId = when (principal) {
            is CustomUserDetails -> principal.userId
            else -> null
        }

        if (userId.isNullOrBlank()) {
            userId = httpRequest.getHeader(USER_ID_HEADER)
        }

        userId?.let {
            httpRequest.setAttribute("userId", it)
            println("Extracted User ID: $it")
        }

        chain.doFilter(request, response)
    }
}
