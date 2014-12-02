package com.github.sabomichal.akkaspringfactory.test;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Michal Sabo
 */
@Service
public class CountingService {

	private final AtomicInteger counter = new AtomicInteger();

	public void increment() {
		counter.incrementAndGet();
	}

	public int currentValue() {
		return counter.intValue();
	}
}
