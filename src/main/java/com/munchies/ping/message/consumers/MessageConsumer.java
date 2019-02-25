package com.munchies.ping.message.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.munchies.ping.events.MessageEvent;

@Component
public class MessageConsumer {

	private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	
	@EventListener({MessageEvent.class})
	public void consume(MessageEvent event) {
		logger.info("Sending to SQS {} ", event.getMessage());
	}
}
