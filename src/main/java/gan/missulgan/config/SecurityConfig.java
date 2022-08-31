package gan.missulgan.config;

import gan.missulgan.security.auth.JwtAuthenticationEntryPoint;
import gan.missulgan.security.auth.JwtAuthenticationFilter;
import gan.missulgan.security.auth.JwtService;
import gan.missulgan.security.oauth.CookieOAuth2AuthorizationRequestRepository;
import gan.missulgan.security.oauth.OAuth2FailureHandler;
import gan.missulgan.security.oauth.OAuth2SuccessHandler;
import gan.missulgan.security.oauth.ThirdPartyOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import static org.springframework.http.HttpMethod.GET;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final ThirdPartyOAuth2UserService thirdPartyOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;
    private final JwtService jwtService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final CookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()

                .authorizeRequests()
                .antMatchers("/docs", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs").permitAll()
                .antMatchers("/auth/renew").permitAll()
                .mvcMatchers(GET, "/image/{fileName}").permitAll()
                .mvcMatchers(GET, "/tag/all").permitAll()
                .mvcMatchers(GET, "/drawing/member/{memberId}").permitAll()
                .mvcMatchers("/drawing/heart/**").permitAll()
                .mvcMatchers("/drawing/random/**").permitAll()
                .mvcMatchers(GET,"/heart/{drawingId}", "/scrap/{drawingId}").permitAll()
                .mvcMatchers(GET,"/member/{memberId}").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()

                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
                .and()
                .userInfoEndpoint()
                .userService(thirdPartyOAuth2UserService)
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);

        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}