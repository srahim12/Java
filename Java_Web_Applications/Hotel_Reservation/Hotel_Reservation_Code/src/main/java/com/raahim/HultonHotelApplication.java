/*package com.raahim;

import java.sql.DriverManager;
import java.sql.ResultSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@SpringBootApplication
public class HultonHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HultonHotelApplication.class, args);

	}

}*/
/**
Sharable Link: https://drive.google.com/open?id=1OTSanImWRx1DEF9793zdgN5K9TTUUSBx
*/
package com.raahim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class HultonHotelApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(HultonHotelApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<HultonHotelApplication> applicationClass = HultonHotelApplication.class;

}
