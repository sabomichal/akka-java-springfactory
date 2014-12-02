package com.github.sabomichal.akkaspringfactory.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.github.sabomichal.akkaspringfactory.SpringProps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static org.junit.Assert.assertEquals;

/**
 * @author Michal Sabo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "/applicationContext.xml"})
public class ITActors {

	@Inject
	private ActorSystem actorSystem;

	@Inject
	private ActorRef greeter;

	@Test
	public void testActor() throws Exception {
		ActorRef dummyActor = actorSystem.actorOf(SpringProps.create(actorSystem, TestActor.class));
		dummyActor.tell(new TestActor.Increment(), null);
		dummyActor.tell(new TestActor.Increment(), null);
		dummyActor.tell(new TestActor.Increment(), null);

		FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);
		Future<Object> result = ask(dummyActor, new TestActor.Get(), Timeout.durationToTimeout(duration));
		assertEquals(3, Await.result(result, duration));
	}

	@Test
	public void greetingActor() throws Exception {
		greeter.tell(new GreetingActor.Greeting("Charlie Parker"), ActorRef.noSender());
	}
}
