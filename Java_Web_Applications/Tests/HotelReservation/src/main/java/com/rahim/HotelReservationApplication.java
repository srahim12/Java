package com.rahim;
/*
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelReservationApplication.class, args);
    }
}*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class HotelReservationApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(HotelReservationApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<HotelReservationApplication> applicationClass = HotelReservationApplication.class;

}

