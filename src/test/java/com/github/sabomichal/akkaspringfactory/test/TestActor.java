package com.github.sabomichal.akkaspringfactory.test;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import com.github.sabomichal.akkaspringfactory.Actor;

import javax.inject.Inject;

/**
 * @author Michal Sabo
 */
@Actor
public class TestActor extends UntypedActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Inject
	private CountingService countingService;

	public static class Increment {}
	public static class Get {}

	/**
	 * Standard recipe for creating unmanaged @{link TestActor} actor
	 */
	public static Props props() {
		return Props.create(new Creator<TestActor>() {
			private static final long serialVersionUID = 1L;

			@Override
			public TestActor create() throws Exception {
				return new TestActor();
			}
		});
	}

	@Override
	public void onReceive(Object message) throws Exception {
		log.info("Received message {}", message);
		if (message instanceof Increment) {
			countingService.increment();
		} else if (message instanceof Get) {
			getSender().tell(countingService.currentValue(), getSelf());
		} else {
			unhandled(message);
		}
	}
}
