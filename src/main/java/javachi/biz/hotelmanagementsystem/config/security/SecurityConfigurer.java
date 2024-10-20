package javachi.biz.hotelmanagementsystem.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigurer {

    private final Environment env;
    private final JwtFilter jwtFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public static final String[] WHITE_LIST = {
            "/auth/**",
            "/user/**",
            "/hotel/**",
            "/comment/**",
            "/room/**",
            "/order/**",
            "/payment/**",
            "/admin/**",
            "/v3/api-docs/**", // OpenAPI hujjatlari
            "/v2/api-docs/**", // Swagger v2 hujjatlari
            "/webjars/**", // WebJars uchun
            "/swagger-ui/**", // Swagger UI
            "/swagger-resources/**", // Swagger resurslari
            "/swagger-resources/*", // Swagger resurslarining muayyan qismi
            "*.html", // HTML fayllar
            "/api/v1/swagger.json" // API hujjatlari
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()));

        http.userDetailsService(customUserDetailsService);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest()
                .authenticated()
        ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(
                exceptions ->
                        exceptions
                                .accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint)
        );

        http.rememberMe(rememberMe ->
                rememberMe
                        .rememberMeParameter("rememberMe")
                        .rememberMeCookieName("remember-me")
                        .key(env.getProperty("remember_security_key"))
                        .tokenValiditySeconds(24 * 60 * 60)
                        .userDetailsService(customUserDetailsService)
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Allow all origins (adjust as needed)
        configuration.addAllowedMethod("*"); // Allow all HTTP methods
        configuration.addAllowedHeader("*"); // Allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
