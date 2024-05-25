/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.configs;

import com.qlcc.pojo.Invoice;
import com.qlcc.pojo.Room;
import com.qlcc.services.InvoiceService;
import com.qlcc.services.InvoicetypeService;
import com.qlcc.services.RoomService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author DELL
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableScheduling
@ComponentScan(basePackages = {
    "com.qlcc.controllers",
    "com.qlcc.repositories",
    "com.qlcc.services",
    "com.qlcc.configs"})
@PropertySource("classpath:application.properties")
public class SpringSecrityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment env;

    @Autowired
    private RoomService roomService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoicetypeService invoicetypeService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/relatives/**").hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/api/relatives/**").hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.POST, "/api/feedbacks/**").hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/api/feedbacks/**").hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/api/feedbacks/**").hasAnyRole("CUSTOMER")
                //                .antMatchers(HttpMethod.GET, ).hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.POST, "/api/surveyAnswers/").hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/api/rooms/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/orders/**").hasAnyRole("ADMIN")
                .antMatchers("/orders", "/orders/**", "/rooms/**", "/lockers",
                        "/invoices", "/invoices/**", "/feedbacks", "/entries",
                        "/api/users/**", "/api/lockers/**", "/entries", "/parkings")
                .hasAnyRole("ADMIN")
                .antMatchers("/api/auth/login", "/", "/api/payments/callback/**/**",
                        "/api/payments/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/")
                .failureUrl("/login?error");
        http.logout().logoutSuccessUrl("/login");
        http.exceptionHandling()
                .accessDeniedPage("/login?accessDenied");

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //Make room rental invoice at 9am on the 25th of each month
    @Scheduled(cron = "0 47 18 14 * ?")
    public void createInvoices() {
        Map<String, String> paramsRoom = new HashMap<>();
        paramsRoom.put("status", "Rented");
        paramsRoom.put("list", "true");
        List<Room> rooms = roomService.getRooms(paramsRoom);

        for (Room room : rooms) {
            Invoice invoice = new Invoice();
            invoice.setRoom(room);
            invoice.setAmount(room.getRoomtype().getPrice());
            invoice.setDescription("");
            invoice.setInvoiceType(invoicetypeService.getInvoicetypeById(3));

            invoiceService.addOrUpdate(invoice);
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
