package com.illucit.ejbremote.server;

import java.util.Map;

import javax.ejb.Remote;

/**
 * Remote interface for {@link ExampleService}.
 * 
 * @author Christian Simon
 *
 */
@Remote
public interface ExampleService {

	/**
	 * Greet a name.
	 * 
	 * @param name
	 *            name to greet
	 * @return "Hello $name!"
	 */
	public String greet(String name);

	/**
	 * Get the system properties from the server.
	 * 
	 * @return system properties as map
	 */
	public Map<Object, Object> getSystemProperties();

}
