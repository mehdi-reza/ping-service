package com.munchies.ping.scheduler;

import java.time.Clock;
import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

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
		
		long scheduleAt = 
		seconds.map(s -> Clock.offset(Clock.systemDefaultZone(), Duration.ofSeconds(s)).millis())
		.orElse(Clock.offset(Clock.systemDefaultZone(), defaultFrequency).millis());

		return queue.add(new ScheduledMessage(message, scheduleAt, Duration.ofSeconds(seconds.orElse(defaultFrequency.getSeconds()))));
	}

	@Override
	public void scheduleAfter(Message message, Duration after) {
	}

	public void start() {
		if (!this.started) {
			this.started = true;
			new Thread(worker).start();
			return;
		}
		throw new RuntimeException("Already started..");
	}

	private PriorityQueue<ScheduledMessage> queue = new PriorityQueue<>(
			Comparator.<ScheduledMessage>comparingLong(m -> m.getSchedulingTime()));

	private Runnable worker = () -> {
		while (true) {

			Optional<ScheduledMessage> available = Optional.ofNullable(queue.peek());

			if (!available.isPresent()) {
				logger.debug("No message found in queue, sleeping for 3 seconds");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			ScheduledMessage message = available.get();

			if (System.currentTimeMillis() >= message.getSchedulingTime()) {
				publisher.publishEvent(new MessageEvent(queue.poll().getMessage()));

				// reschedule
				schedule(message.getMessage(), Optional.of(message.getDuration().getSeconds()));
			} else {
				try {
					Thread.sleep(message.getSchedulingTime() - System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
}
