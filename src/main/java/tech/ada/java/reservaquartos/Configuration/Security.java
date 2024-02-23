package tech.ada.java.reservaquartos.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    //Spring irá reconhecer que a função sobrescrita faz parte da @Configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {

                            //Permitirá todas as requisições GET pra "quarto"
                            auth.requestMatchers(HttpMethod.GET,"/quarto").permitAll();
                            //todas rotas autenticadas
                            auth.anyRequest().authenticated();
                        }
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }
}