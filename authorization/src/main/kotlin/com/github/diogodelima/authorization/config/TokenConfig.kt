package com.github.diogodelima.authorization.config

import com.github.diogodelima.authorization.domain.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer


@Configuration
class TokenConfig {

    @Bean
    fun tokenCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return OAuth2TokenCustomizer { context: JwtEncodingContext ->

            if (OAuth2TokenType.ACCESS_TOKEN == context.tokenType) {

                val authentication = context.getPrincipal<Authentication>()
                val user = authentication.principal as User

                context.claims.claims { claims: MutableMap<String?, Any?> ->
                    claims["user_id"] = user.id
                }

            }

        }
    }

}