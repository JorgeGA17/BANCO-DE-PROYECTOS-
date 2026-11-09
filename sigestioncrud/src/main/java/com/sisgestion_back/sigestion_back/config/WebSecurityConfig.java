package com.sisgestion_back.sigestion_back.config;

import com.sisgestion_back.sigestion_back.security.JWTConfigurer;
import com.sisgestion_back.sigestion_back.security.JWTFilter;
import com.sisgestion_back.sigestion_back.security.JwtAuthenticationEntryPoint;
import com.sisgestion_back.sigestion_back.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity //Importante para anotaciones @PreAuthorize
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JWTFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable) // TODO: Desactiva la protección CSRF, ya que en APIs REST no se usa (se autentica con tokens, no con cookies)

                .authorizeHttpRequests(authorize -> authorize
                        // TODO: Permitir acceso público a las rutas de login, registro y endpoints públicos como Swagger UI
                        .requestMatchers(antMatcher("/auth/login")).permitAll()
                        .requestMatchers(antMatcher("/auth/forgot-password")).permitAll()
                        .requestMatchers(antMatcher("/auth/reset-password")).permitAll()
                        .requestMatchers(antMatcher("/auth/register/general")).permitAll()

                        .requestMatchers(antMatcher("/Cargos")).permitAll()
                        .requestMatchers(antMatcher("/Cargos/{cargopk}")).permitAll()
                        .requestMatchers(antMatcher("/Comisiones")).permitAll()
                        .requestMatchers(antMatcher("/Comisiones/{comisionpk}")).permitAll()
                        .requestMatchers(antMatcher("/Cortes")).permitAll()
                        .requestMatchers(antMatcher("/Cortes/{cortepk}")).permitAll()
                        .requestMatchers(antMatcher("/Ejes")).permitAll()
                        .requestMatchers(antMatcher("/Ejes/{ejepk}")).permitAll()
                        .requestMatchers(antMatcher("/Especialidades")).permitAll()
                        .requestMatchers(antMatcher("/Especialidades/{especialidadpk}")).permitAll()
                        .requestMatchers(antMatcher("/Estados")).permitAll()
                        .requestMatchers(antMatcher("/Estados/{estadopk}")).permitAll()
                        .requestMatchers(antMatcher("/Etiquetas")).permitAll()
                        .requestMatchers(antMatcher("/Etiquetas/{etiquetapk}")).permitAll()
                        .requestMatchers(antMatcher("/Jerarquias")).permitAll()
                        .requestMatchers(antMatcher("/Jerarquias{jerarquiapk}")).permitAll()
                        .requestMatchers(antMatcher("/Miembros")).permitAll()
                        .requestMatchers(antMatcher("/Miembros/{miembropk}")).permitAll()
                        .requestMatchers(antMatcher("/Periodos")).permitAll()
                        .requestMatchers(antMatcher("/Periodos/{periodopk}")).permitAll()
                        .requestMatchers(antMatcher("/Personas")).permitAll()
                        .requestMatchers(antMatcher("/Personas/{personalpk}")).permitAll()
                        .requestMatchers(antMatcher("/Presidentes")).permitAll()
                        .requestMatchers(antMatcher("/Presidentes/{presidentepk}")).permitAll()
                        .requestMatchers(antMatcher("/Proyectos")).permitAll()
                        .requestMatchers(antMatcher("/Proyectos/{proyectopk}")).permitAll()

                        // TODO: Cualquier otra solicitud requiere autenticación (JWT u otra autenticación configurada)
                        .anyRequest().authenticated()
                )

                // TODO: Permite la autenticación básica (para testing con Postman, por ejemplo)
                //.httpBasic(Customizer.withDefaults())
                // TODO: Desactiva el formulario de inicio de sesión predeterminado, ya que se usará JWT
                .formLogin(AbstractHttpConfigurer::disable)
                // TODO: Configura el manejo de excepciones para autenticación. Usa JwtAuthenticationEntryPoint para manejar errores 401 (no autorizado)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // TODO: Configura la política de sesiones como "sin estado" (stateless), ya que JWT maneja la autenticación, no las sesiones de servidor
                .sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // TODO: Agrega la configuración para JWT en el filtro antes de los filtros predeterminados de Spring Security
                .with(new JWTConfigurer(tokenProvider), Customizer.withDefaults());

        // TODO: Añadir el JWTFilter antes del filtro de autenticación de nombre de usuario/contraseña.
        //  Esto permite que el JWTFilter valide el token antes de la autenticación
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // TODO: Proporciona el AuthenticationManager que gestionará la autenticación basada en los detalles de usuario y contraseña
        return authenticationConfiguration.getAuthenticationManager();
    }


}
