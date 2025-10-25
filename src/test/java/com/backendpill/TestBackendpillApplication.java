package com.backendpill;

import org.springframework.boot.SpringApplication;

public class TestBackendpillApplication {

	public static void main(String[] args) {
		SpringApplication.from(BackendpillApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
