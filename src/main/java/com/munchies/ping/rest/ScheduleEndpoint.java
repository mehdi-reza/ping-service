package com.munchies.ping.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.munchies.ping.events.MessageEvent;
import com.munchies.ping.message.Message;
import com.munchies.ping.scheduler.Scheduler;

@RestController
@RequestMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
public class ScheduleEndpoint {

	@Autowired Scheduler scheduler;
	
	@Autowired
	private ApplicationEventPublisher publisher; 
	
	@PostMapping(path="schedule")
	public @ResponseBody ResponseEntity<HttpStatus> schedule(@RequestBody(required=true) Message message) {
		publisher.publishEvent(new MessageEvent(message));
		scheduler.schedule(message, Optional.empty());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(path="scheduleAt/{seconds}")
	public @ResponseBody ResponseEntity<HttpStatus> scheduleAt(@PathVariable(name="seconds", required=true) Long seconds, @RequestBody(required=true) Message message) {
		publisher.publishEvent(new MessageEvent(message));
		scheduler.schedule(message, Optional.of(seconds));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
