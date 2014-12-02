package com.github.sabomichal.akkaspringfactory.test;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author Michal Sabo
 */
public class GreetingActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Inject
	private CountingService countingService;

	public static class Greeting implements Serializable {
		public final String who;
		public Greeting(String who) { this.who = who; }
	}

	public void onReceive(Object message) throws Exception {
		if (message instanceof Greeting) {
			log.info("Hello " + ((Greeting) message).who);
			countingService.increment();
		}
	}
}