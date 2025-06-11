package com.yiqi.yiqi_hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yiqi.yiqi_hotel", "com.yiqi.api_backend"})
public class YiqiHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiqiHotelApplication.class, args);
    }

}
