package com.munchies.ping.message;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ScheduledMessage implements Delayed {

	private Message message;
	
	private long schedulingTime;

	private Duration duration;

	public ScheduledMessage(Message message, long time, Duration duration) {
		Objects.requireNonNull(message, "Message cannot be null");
		this.schedulingTime=time;
		this.message=message;
		this.duration=duration;
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
		
		return (message.getMessage().getCaptain()!=null ? message.getMessage().getCaptain().equals(getMessage().getCaptain()) : getMessage().getCaptain()==null) &&
				(getSchedulingTime()==message.getSchedulingTime()); 
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(message.getCaptain(), schedulingTime);
	}

	@Override
	public int compareTo(Delayed other) {
		ScheduledMessage message=(ScheduledMessage)other;
		return getSchedulingTime() < message.getSchedulingTime() ? -1 : 1;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(getSchedulingTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
}
