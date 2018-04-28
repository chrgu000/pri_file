package com.gzjky;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringCloudApplication
@EnableEurekaClient
public class PublicMnageApplication {
	public static void main(String[] args) {
		SpringApplication.run(PublicMnageApplication.class, args);
	}
}
