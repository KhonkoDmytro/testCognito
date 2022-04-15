package com.jappware.cheddar;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.json.JsonArray;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class OAuth2ResourceServerSecurityConfiguration extends ResourceServerConfigurerAdapter {
//
//    це якийсь мутний варік, але то було останнє що я пробував, тут все Deprecated кинув аби не делітати
//
//    private final ResourceServerProperties resource;
//
//    public OAuth2ResourceServerSecurityConfiguration(ResourceServerProperties resource) {
//        this.resource = resource;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.cors();
//
//        http.csrf().disable();
//
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated();
//    }
//
//    @Bean
//    public TokenStore jwkTokenStore() {
//        return new JwkTokenStore(
//                Collections.singletonList(resource.getJwk().getKeySetUri()),
//                new CognitoAccessTokenConverter(),
//                null);
//    }
//}