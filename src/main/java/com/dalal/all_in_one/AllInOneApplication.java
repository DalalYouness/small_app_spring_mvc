package com.dalal.all_in_one;

import com.dalal.all_in_one.entities.Product;
import com.dalal.all_in_one.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication()
public class AllInOneApplication  {


    public static void main(String[] args) {
        SpringApplication.run(AllInOneApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .id(null)
                    .name("laptop")
                    .price(100.6)
                    .quantity(10)
                    .build());
            productRepository.save(Product.builder()
                    .id(null)
                    .name("Ordinateur")
                    .price(80.6)
                    .quantity(5)
                    .build());
            productRepository.save(Product.builder()
                    .id(null)
                    .name("Book")
                    .price(50.3)
                    .quantity(12)
                    .build());
            productRepository.findAll().forEach(System.out::println);
        };
    }

}
