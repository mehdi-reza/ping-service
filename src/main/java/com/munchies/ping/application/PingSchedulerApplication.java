package com.munchies.ping.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextStartedEvent;

import com.munchies.ping.scheduler.Scheduler;

@SpringBootApplication(scanBasePackages={"com.munchies.ping"})
public class PingSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingSchedulerApplication.class, args);
	}
	
	@Autowired Scheduler scheduler;
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> ready() {
		return e -> {
			scheduler.start();
		};
	}

}
