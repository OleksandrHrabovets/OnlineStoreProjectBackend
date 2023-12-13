package ua.example.online_store.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(name = "Authorization", scheme = "basic",
    type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SecurityBasicConfiguration {

  @Value("${app.security.admin-password}")
  private String adminPassword;

  @Value("${app.security.user-password}")
  private String userPassword;

  @Bean
  public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User.withUsername("user")
        .password(passwordEncoder.encode(userPassword))
        .roles("USER")
        .build();

    UserDetails admin = User.withUsername("admin")
        .password(passwordEncoder.encode(adminPassword))
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(CsrfConfigurer::disable)
        .cors(CorsConfigurer::disable)
        .formLogin(FormLoginConfigurer::disable)
        .sessionManagement(sessionAuthenticationStrategy ->
            sessionAuthenticationStrategy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(
                        "/swagger-ui/**", "/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.POST,
                        "/api/*/available_quantities",
                        "/api/*/categories",
                        "/api/*/products").authenticated()
                    .requestMatchers(HttpMethod.PUT,
                        "/api/*/orders").authenticated()
                    .requestMatchers(HttpMethod.GET,
                        "/api/*/available_quantities/log").authenticated()
                    .anyRequest().permitAll()
        )
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
