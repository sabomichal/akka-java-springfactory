akka-java-springfactory
=======================
Akka Spring integration for Java with additional spring factories for actor creation with ease.

* provides factory method for actor system creation as a spring singleton bean
* provides factory method for actor creation as a spring prototype bean
* provides autowiring of created actors
* router, mailbox and dispatcher can be configured directly via spring

## How to create an actor system
Just define actor system singleton in the spring context using ActorSystemFactoryBean.
```xml
<bean id="actorSystem" class="com.github.sabomichal.akkaspringfactory.ActorSystemFactoryBean">
	<!-- actor system name -->
	<property name="name" value="MyActorSystem"/>
	<!-- actor system configuration -->
	<property name="config">
		<bean class="com.typesafe.config.ConfigFactory" factory-method="load">
			<constructor-arg value="akkaActor.conf"/>
		</bean>
	</property>
</bean>
```

## How to create actors
### Programmatic use case
Enable Spring auto scanning of componnents,
```xml
<context:component-scan base-package="..." />
```

annotate actor with @Actor annotation,
```java
import com.github.sabomichal.akkaspringfactory.Actor;

@Actor
public class MyActor extends UntypedActor {

	// additionally inject some dependencies
	@Inject
	private MyService injectedService;

	@Override
	public void onReceive(Object message) throws Exception {
		// receive method
	}
}
```

and finally create an ActorRef programmatically.
```java
@Inject
private ActorSystem actorSystem;
...
ActorRef myActor = actorSystem.actorOf(SpringProps.create(actorSystem, MyActor.class));
```

### Spring managed use case
Just create a spring managed actor reference,
```xml
<bean id="springManagedActor" class="com.github.sabomichal.akkaspringfactory.ActorFactoryBean">
	<!-- actor system reference -->
	<property name="actorSystem" ref="actorSystem"/>
	
	<!-- class name of actor to create -->
	<property name="actorClass" value="com.github.sabomichal.akkaspringfactory.test.GreetingActor"/>
	
	<!-- bean name of actor to create -->
	<!--<property name="actorBeanClass" value=""/>-->
	
	<!-- router configuration -->
	<!--<property name="routerConfig">
		<bean class="akka.routing.SmallestMailboxPool">
			<constructor-arg value="10"/>
		</bean>
	</property>-->
	
	<!-- mailbox settings -->
	<!--<property name="mailbox" value=""/>-->
	
	<!-- dispatcher settings -->
	<!--<property name="dispatcher" value=""/>-->
</bean>
```

and use it as a spring managed dependency wherever you want. And since ActorFactoryBean is producing prototype beans, a new instance of ActorRef is created with all dependencies resolved.
```java
@Inject
private ActorRef springManagedActor;
...
springManagedActor.tell(...);
```

For detailed examples see the provided unit tests.

If you like it, give it a star, if you don't, write an issue.
