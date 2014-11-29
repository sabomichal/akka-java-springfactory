package com.github.sabomichal.akkaspringfactory;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * @author Michal Sabo
 */
public class SpringActorProducer implements IndirectActorProducer {
	final ApplicationContext applicationContext;
	final String actorBeanName;
	final Class<?> requiredType;

	public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName) {
		this(applicationContext, actorBeanName, null);
	}

	public SpringActorProducer(ApplicationContext applicationContext, Class<?> requiredType) {
		this(applicationContext, null, requiredType);
	}

	public SpringActorProducer(ApplicationContext applicationContext,
	                           String actorBeanName, Class<?> requiredType) {
		this.applicationContext = applicationContext;
		this.actorBeanName = actorBeanName;
		this.requiredType = requiredType;
	}

	@Override
	public Actor produce() {
		Actor result;
		if (actorBeanName != null && requiredType != null) {
			result = (Actor) applicationContext.getBean(actorBeanName, requiredType);
		} else if (requiredType != null) {
			result = (Actor) applicationContext.getBean(requiredType);
		} else {
			result = (Actor) applicationContext.getBean(actorBeanName);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends Actor> actorClass() {
		return (Class<? extends Actor>) (requiredType != null ? requiredType : applicationContext.getType(actorBeanName));
	}
}
