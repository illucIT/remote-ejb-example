package com.illucit.ejbremote;

import com.illucit.ejbremote.server.ExampleService;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Map;

import static com.illucit.ejbremote.utils.ContextUtils.createEjbProxy;
import static com.illucit.ejbremote.utils.ContextUtils.createRemoteEjbContext;

/**
 * Remote EJB Client.
 * 
 * @author Christian Simon
 *
 */
public class EjbRemoteClient {

	/**
	 * Run example.
	 * 
	 * @param args
	 *            (not used)
	 */
	public static void main(String[] args) {

		// Connection to Wildfly Server instance
		String host = "127.0.0.1";
		String port = "8080"; // Wildfly HTTP port

		Context remotingContext;
		try {
			remotingContext = createRemoteEjbContext(host, port);
		} catch (NamingException e) {
			System.err.println("Error setting up remoting context");
			e.printStackTrace();
			return;
		}

		// Syntax: ejb:${appName}/${moduleName}/${beanName}!${remoteView}
		// appName = name of EAR deployment (or empty for single EJB/WAR
		// deployments)
		// moduleName = name of EJB/WAR deployment
		// beanName = name of the EJB (Simple name of EJB class)
		// remoteView = fully qualified remote interface class
		String ejbUrl = "ejb:/ejb-remote-server/ExampleServiceImpl!com.illucit.ejbremote.server.ExampleService";

		ExampleService service;
		try {
			service = createEjbProxy(remotingContext, ejbUrl, ExampleService.class);
		} catch (NamingException e) {
			System.err.println("Error resolving bean");
			e.printStackTrace();
			return;
		} catch (ClassCastException e) {
			System.err.println("Resolved EJB is of wrong type");
			e.printStackTrace();
			return;
		}

		// Call remote method with parameter

		String toGreet = "World";

		String exampleResult;
		try {
			exampleResult = service.greet(toGreet);
		} catch (Exception e) {
			System.err.println("Error accessing remote bean");
			e.printStackTrace();
			return;
		}

		// Hello World!
		System.out.println("Example result: " + exampleResult);

		// Retrieve result from EJB call

		Map<Object, Object> systemProperties;
		try {
			systemProperties = service.getSystemProperties();
		} catch (Exception e) {
			System.err.println("Error accessing remote bean");
			e.printStackTrace();
			return;
		}

		System.out.println("Wildfly Home Dir: " + systemProperties.get("jboss.home.dir"));

	}


}
