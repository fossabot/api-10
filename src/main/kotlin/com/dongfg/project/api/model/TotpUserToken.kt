package com.dongfg.project.api.model

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class TotpUserToken : UsernamePasswordAuthenticationToken("TOTP", arrayListOf(SimpleGrantedAuthority("TOTP"))) {
    override fun getCredentials(): Any {
        return Any()
    }

    override fun isAuthenticated(): Boolean {
        return true
    }
}