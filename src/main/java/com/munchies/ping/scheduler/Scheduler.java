package com.munchies.ping.scheduler;

import java.time.Duration;
import java.util.Optional;

import com.munchies.ping.message.Message;

public interface Scheduler {
	public boolean schedule(Message message, Optional<Long> schedulingTime);
	public void start();
}
