package com.illucit.ejbremote.server;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

/**
 * Implementation of {@link ExampleService}.
 * 
 * @author Christian Simon
 *
 */
@Stateless
public class ExampleServiceImpl implements ExampleService {

	@Override
	public String greet(String name) {
		return "Hello " + name + "!";
	}

	@Override
	public Map<Object, Object> getSystemProperties() {
		return new HashMap<>(System.getProperties());
	}

}
