package com.munchies.ping.events;

import org.springframework.context.ApplicationEvent;

import com.munchies.ping.message.Message;

public class MessageEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Message message;

	public MessageEvent(Message message) {
		super(message);
		this.message=message;
	}

	public Message getMessage() {
		return message;
	}
}
