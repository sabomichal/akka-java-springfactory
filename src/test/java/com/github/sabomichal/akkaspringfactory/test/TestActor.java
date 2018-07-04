package com.github.sabomichal.akkaspringfactory.test;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import com.github.sabomichal.akkaspringfactory.Actor;

import javax.inject.Inject;

/**
 * @author Michal Sabo
 */
@Actor
public class TestActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Inject
    private CountingService countingService;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Increment.class, increment -> {
                    log.info("Received Increment message");
                    countingService.increment();
                })
                .match(Get.class, get -> {
                    log.info("Received Get message");
                    getSender().tell(countingService.currentValue(), getSelf());
                })
                .matchAny(this::unhandled)
                .build();
    }

    public static class Increment {
    }

    public static class Get {
    }

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
}
