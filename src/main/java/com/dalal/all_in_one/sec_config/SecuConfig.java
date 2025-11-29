package com.dalal.all_in_one.sec_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/*
cette annotation c'est pour activer la securiter web , c'est pour dire a spring
que je vais faire ma propre configuration et merci de remplacer ce que je configure avec
ce qui est par default , et les chose lequelles, je n'ai pas les configurer, ils vont rester
en configuration par default , c'est une annotation tres importanate  je doit l'ajouter
dans la classe de configuration pour indiquer a spring que c'est une configuration de security
*/
@Configuration
@EnableWebSecurity
public class SecuConfig {

    /*
    * On sait bien que les mots de passe doivent être stocké d'une manière hacher dans la base
    * de donnéé, c'est pour ça on va générer du hachage par l'utilisation de Bcrypt
    * comme algorithme de hash.
    * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*je vais specifier les utilisateurs et aussi leurs roles mais je vais les stockers
     au niveau de la memoire pour faire du test
     */
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").roles("USER").password(passwordEncoder().encode("1234")).build(),
                User.withUsername("user2").roles("USER").password(passwordEncoder().encode("1234")).build(),
                User.withUsername("admin").roles("ADMIN","USER").password(passwordEncoder().encode("1234")).build()
        );
    }

    /*
    *  configuer les filtres qui verifies d'apres les requettes si li'utilisateur
    *  est authentifier si non il va le rediruger vers un form login , si authentifier
    * il va verifier d'apres son role si a le droit d'acceder a cette resource si non il va retourner
    * un 403 not authorized
    * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //csrf(Customizer.withDefaults()) // c'est ca la par default
                //.csrf(csrf -> csrf.disable())
                // on le desactive seulement si on travaille dans le mode stateless sinon c'est dangereux dans le cas de statfull de le desactiver
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(ar -> ar.requestMatchers("/products").hasRole("USER"))
                .authorizeHttpRequests(ar -> ar.requestMatchers("/delete","/saveProduct","/update").hasRole("ADMIN"))
                .authorizeHttpRequests(ar -> ar.requestMatchers("/").permitAll())
                .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
                .exceptionHandling(eh -> eh.accessDeniedPage("/notAuthorized"))
                .build();
    }
}
