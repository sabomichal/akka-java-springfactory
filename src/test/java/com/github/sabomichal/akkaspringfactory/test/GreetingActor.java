package com.github.sabomichal.akkaspringfactory.test;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author Michal Sabo
 */
public class GreetingActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Inject
    private CountingService countingService;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Greeting.class, greeting -> {
                    log.info("Hello " + greeting.who);
                    countingService.increment();
                })
                .build();
    }

    public static class Greeting implements Serializable {
        public final String who;

        public Greeting(String who) {
            this.who = who;
        }
    }
}