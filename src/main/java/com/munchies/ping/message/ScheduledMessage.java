package com.munchies.ping.message;

import java.util.Objects;

public class ScheduledMessage {

	private Message message;
	
	private long schedulingTime;

	public ScheduledMessage(Message message, long time) {
		Objects.requireNonNull(message, "Message cannot be null");
		this.schedulingTime=time;
		this.message=message;
	}
	
	public long getSchedulingTime() {
		return schedulingTime;
	}
	
	public Message getMessage() {
		return message;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ScheduledMessage)) return false;
		ScheduledMessage message=(ScheduledMessage)obj;
		
		return (message.getMessage().getCaptian()!=null ? message.getMessage().getCaptian().equals(getMessage().getCaptian()) : getMessage().getCaptian()==null) &&
				(getSchedulingTime()==message.getSchedulingTime()); 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(message.getCaptian(), schedulingTime);
	}
}
