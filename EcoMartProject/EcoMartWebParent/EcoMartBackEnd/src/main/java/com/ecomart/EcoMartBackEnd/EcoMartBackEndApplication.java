package com.ecomart.EcoMartBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.ecomart.EcoMartCommon.entity"})
public class EcoMartBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoMartBackEndApplication.class, args);
	}

}
