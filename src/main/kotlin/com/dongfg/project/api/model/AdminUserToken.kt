package com.dongfg.project.api.model

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AdminUserToken(
        private val principal: Any,
        authorities: Collection<GrantedAuthority>
) : UsernamePasswordAuthenticationToken(principal, authorities) {
    override fun getCredentials(): Any {
        return Any()
    }

    override fun getPrincipal(): Any {
        return principal
    }

    override fun isAuthenticated(): Boolean {
        return true
    }
}