package com.jappware.cheddar;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests(a -> a
                        .anyRequest().authenticated()
                )
                .oauth2Login(l -> {
                    // проблему бачу в тому, що цей метод напряму в когніто звертається
                    // я от шукав шукав і не міг найти робочих аналогів які робили би то саме получаючи просто токен
                    // тобто для нього треба сетати редірект на логін пейдж і воно по кредах логін робить
                    // я думаю має бути якесь рішення і в інтернатах є пару але вони в мене не працювали ніяк
                            l.userInfoEndpoint().userAuthoritiesMapper(userAuthoritiesMapper());
                        }
                );
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            // цей степ обовʼязковий щоб мати змогу атрибути читати
            Optional<OAuth2UserAuthority> awsAuthority = (Optional<OAuth2UserAuthority>) authorities.stream()
                    .filter(grantedAuthority -> "ROLE_USER".equals(grantedAuthority.getAuthority()))
                    .findFirst();

            if (awsAuthority.isPresent()) {
                //  отак мало би бути, в тій статті так і було, але тут проблеми якісь і cognito:groups відстутній
//                mappedAuthorities = ((JSONArray) awsAuthority.get().getAttributes().get("cognito:groups")).stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                        .collect(Collectors.toSet());

                String name = (String) awsAuthority.get().getAttributes().get("username");

                //  результат нал
                String group = (String) awsAuthority.get().getAttributes().get("cognito:groups");

                // тут має бути запит в базу даних по юзернейму, але я для тесту таку перевірку робив
                if (name.equals("dkhonko")) {
                    //дуже навіть просто створюється спрінговий доступ
                    mappedAuthorities.add(new SimpleGrantedAuthority("admin"));
                } else {
                    mappedAuthorities.add(new SimpleGrantedAuthority("user"));
                }
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                        .collect(Collectors.toSet());
            }

            return mappedAuthorities;
        };
    }
}