@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/api/data").hasAuthority("ACCESS_API")
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                }
            }

        return http.build()
    }

    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        converter.setJwtGrantedAuthoritiesConverter(JwtAccessConverter())
        return converter
    }

    class JwtAccessConverter : Function<Jwt, Collection<GrantedAuthority>> {
        override fun apply(jwt: Jwt): Collection<GrantedAuthority> {
            val authorities = mutableListOf<GrantedAuthority>()
            val clientType = jwt.getClaimAsString("clienttype")?.uppercase()
            val roles = jwt.getClaimAsStringList("roles") ?: emptyList()

            if (clientType == "VIP" || (clientType == "NORMAL" && roles.contains("special"))) {
                authorities.add(SimpleGrantedAuthority("ACCESS_API"))
            }

            return authorities
        }
    }
}
