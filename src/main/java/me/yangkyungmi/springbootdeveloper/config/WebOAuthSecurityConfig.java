package me.yangkyungmi.springbootdeveloper.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.RequiredArgsConstructor;
import me.yangkyungmi.springbootdeveloper.config.jwt.TokenProvider;
import me.yangkyungmi.springbootdeveloper.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import me.yangkyungmi.springbootdeveloper.config.oauth.OAuth2SuccessHandler;
import me.yangkyungmi.springbootdeveloper.config.oauth.OAuth2UserCustomService;
import me.yangkyungmi.springbootdeveloper.repository.RefreshTokenRepository;
import me.yangkyungmi.springbootdeveloper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {

  private final OAuth2UserCustomService oAuth2UserCustomService;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserService userService;

  @Bean
  public WebSecurityCustomizer configure() {
    return (web -> web.ignoring()
        .requestMatchers(toH2Console())
        .requestMatchers("/static/img/**", "/css/**", "/js/**"));
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .httpBasic().disable()
        .formLogin().disable()
        .logout().disable();

    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // 헤더를 확인할 커스텀 필터 추가
    http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    // 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정, 나머지 API URL은 인증 필요
    http.authorizeHttpRequests()
        .requestMatchers("/api/token").permitAll()
        .requestMatchers("/api/**").authenticated()
        .anyRequest().permitAll();

    http.oauth2Login()
        .loginPage("/login")
        .authorizationEndpoint()
        .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
        .and()
        .successHandler(oAuth2SuccessHandler())
        .userInfoEndpoint()
        .userService(oAuth2UserCustomService);

    http.logout()
        .logoutSuccessUrl("/login");

    http.exceptionHandling()
        .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
            new AntPathRequestMatcher("/api/**"));
    return http.build();
  }

  @Bean
  public OAuth2SuccessHandler oAuth2SuccessHandler() {
    return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository,
        oAuth2AuthorizationRequestBasedOnCookieRepository(), userService);
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(tokenProvider);
  }

  @Bean
  public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
    return new OAuth2AuthorizationRequestBasedOnCookieRepository();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
