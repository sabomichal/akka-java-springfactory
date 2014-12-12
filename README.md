akka-java-springfactory
=======================
Akka Spring integration for Java with additional spring factories for actor creation with ease.

* provides factory method for actor system creation as a spring singleton bean
* provides factory method for actor creation as a spring prototype bean
* provides autowiring of created actors

## Basic usage
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

	<bean id="springManagedActor" class="com.github.sabomichal.akkaspringfactory.ActorFactoryBean">
		<!-- actor system reference -->
		<property name="actorSystem" ref="actorSystem"/>
		
		<!-- class name of actor to create -->
		<property name="actorClass" value="com.github.sabomichal.akkaspringfactory.test.GreetingActor"/>
		
		<!-- bean name of actor to create -->
		<!-- property name="actorBeanClass" value=""/ -->
		
		<!-- router configuration -->
		<!--property name="routerConfig">
			<bean class="akka.routing.SmallestMailboxPool">
				<constructor-arg value="10"/>
			</bean>
		</property-->
		
		<!-- mailbox settings -->
		<!--property name="mailbox" value=""/>
		
		<!-- dispatcher settings -->
		<!--property name="dispatcher" value=""/>
	</bean>
```
For further examples see the tests.
