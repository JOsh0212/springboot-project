package kr.co.fastcampus.board.config;

import jakarta.servlet.DispatcherType;
import kr.co.fastcampus.board.dto.UserAccountDTO;
import kr.co.fastcampus.board.dto.security.BoardPrinciple;
import kr.co.fastcampus.board.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            "/", "/articles", "/articles/search-hashtag", "/h2-console/**"
    };
//    @Bean
//    @Order(1)
//    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/articles/**")
//                .authorizeHttpRequests(auth -> {
//                    auth.anyRequest().authenticated();
//                })
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .httpBasic(withDefaults())
//                .build();
//    }
//    @Bean
//    @Order(2)
//    public SecurityFilterChain config(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
//                })
//                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
//                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
//                .build();
//    }

    @Bean
    @Order(3)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                .requestMatchers(toH2Console()).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                                .requestMatchers(AUTH_WHITELIST).permitAll()
//                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET)).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/articles")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/articles/search-hashtag")).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .logout(logout->logout.logoutSuccessUrl("/"))
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {// 사용을 권장하지 않음
//        //정적 파일 static resource
//        return (web) -> web.ignoring()
//                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                ;
//    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository){
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDTO::from)
                .map(BoardPrinciple::from)
                .orElseThrow(()->new UsernameNotFoundException("유저를 찾을 수 없습니다. username:"+username));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
