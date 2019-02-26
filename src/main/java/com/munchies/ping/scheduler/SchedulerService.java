package com.munchies.ping.scheduler;

import java.time.Clock;
import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.DelayQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.munchies.ping.events.MessageEvent;
import com.munchies.ping.message.Message;
import com.munchies.ping.message.ScheduledMessage;

@Service
public class SchedulerService implements Scheduler {

	private Logger logger = LoggerFactory.getLogger(SchedulerService.class);

	// 20 seconds
	private final Duration defaultFrequency = Duration.ofSeconds(20);

	private boolean started = false;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	public boolean schedule(Message message, Optional<Long> seconds) {

		long scheduleAt = seconds.map(s -> Clock.offset(Clock.systemDefaultZone(), Duration.ofSeconds(s)).millis())
				.orElse(Clock.offset(Clock.systemDefaultZone(), defaultFrequency).millis());

		return queue.add(new ScheduledMessage(message, scheduleAt,
				Duration.ofSeconds(seconds.orElse(defaultFrequency.getSeconds()))));
	}

	public void start() {
		if (!this.started) {
			this.started = true;
			new Thread(worker).start();
			return;
		}
		throw new RuntimeException("Already started..");
	}

	private DelayQueue<ScheduledMessage> queue = new DelayQueue<>();

	private Runnable worker = () -> {
		while (true) {
			
			ScheduledMessage message;
			try {
				//blocking call
				message = queue.take();
				publisher.publishEvent(new MessageEvent(message.getMessage()));
				
				// reschedule
				schedule(message.getMessage(), Optional.of(message.getDuration().getSeconds()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 			
		}
	};
}
