package com.github.sabomichal.akkaspringfactory;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Michal Sabo
 */
public class SpringProps {

    public static Props create(ActorSystem actorSystem, String actorBeanName) {
        return SpringExtension.instance().get(actorSystem).create(actorBeanName);
    }

    public static Props create(ActorSystem actorSystem, Class<? extends AbstractActor> requiredType) {
        return SpringExtension.instance().get(actorSystem).create(requiredType);
    }

    public static Props create(ActorSystem actorSystem, String actorBeanName, Class<? extends AbstractActor> requiredType) {
        return SpringExtension.instance().get(actorSystem).create(actorBeanName, requiredType);
    }
}
