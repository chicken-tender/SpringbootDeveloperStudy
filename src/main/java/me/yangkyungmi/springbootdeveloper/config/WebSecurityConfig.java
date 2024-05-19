package me.yangkyungmi.springbootdeveloper.config;

//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//
//  private final UserDetailService userService;
//
//  // 스프링 시큐리티 기능 비활성화
//  @Bean
//  public WebSecurityCustomizer configure() {
//    return (web -> web.ignoring()
//        .requestMatchers(toH2Console())
//        .requestMatchers("/static/**"));
//  }
//
//  // 특정 HTTP 요청에 대한 웹 기반 보안 구성
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    return http
//        .authorizeHttpRequests()
//        .requestMatchers("/login", "/signup", "/user").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .formLogin()
//        .loginPage("/login")
//        .defaultSuccessUrl("/articles")
//        .and()
//        .logout()
//        .logoutSuccessUrl("/login")
//        .invalidateHttpSession(true)
//        .and()
//        .csrf().disable()
//        .build();
//  }
//
//  // 인증 관리자 관련 설정
//  @Bean
//  public AuthenticationManager authenticationManager(HttpSecurity http,
//      BCryptPasswordEncoder bCryptPasswordEncoder,
//      UserDetailService userDetailService) throws Exception {
//    return http.getSharedObject(AuthenticationManagerBuilder.class)
//        .userDetailsService(userService) // 사용자 정보 서비스 설정
//        .passwordEncoder(bCryptPasswordEncoder)
//        .and()
//        .build();
//  }
//
//  // 패스워드 인코더로 사용할 빈 등록
//  @Bean
//  public BCryptPasswordEncoder bCryptPasswordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//}
