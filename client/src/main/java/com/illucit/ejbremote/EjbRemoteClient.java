package com.illucit.ejbremote;

import com.illucit.ejbremote.server.ExampleService;
import com.illucit.ejbremote.utils.EjbUrlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Map;

import static com.illucit.ejbremote.utils.ContextUtils.createEjbProxy;
import static com.illucit.ejbremote.utils.ContextUtils.createRemoteEjbContext;

/**
 * Remote EJB Client.
 *
 * @author Christian Simon
 */
public class EjbRemoteClient {

    /**
     * Run example.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {

        Logger log = LogManager.getLogger(EjbRemoteClient.class);

        // Connection to Wildfly Server instance
        String host = "127.0.0.1";
        String port = "8080"; // Wildfly HTTP port

        Context remotingContext;
        try {
            remotingContext = createRemoteEjbContext(host, port);
        } catch (NamingException e) {
            log.error("Error setting up remoting context");
            return;
        }


        final String appName = "ejb-remote-server";
        final String moduleName = "";
        final String beanName = "ExampleServiceImpl";
        final String remoteInterface = "com.illucit.ejbremote.server.ExampleService";

        String ejbUrl = EjbUrlUtils.getEjbUrl(appName, moduleName, beanName, remoteInterface);

        ExampleService service = null;
        try {
            service = createEjbProxy(remotingContext, ejbUrl, ExampleService.class);
            log.info(" Connexion to Remote is Done !");
        } catch (NamingException e) {
            log.error("Error resolving bean");
        } catch (ClassCastException e) {
            log.error("Resolved EJB is of wrong type");
        }

        // Call remote method with parameter

        String toGreet = "World";

        String exampleResult;
        try {
            assert service != null;
            exampleResult = service.greet(toGreet);
        } catch (Exception e) {
            log.error("Error accessing remote bean");
            return;
        }

        // Hello World!
        System.out.println("Example result: " + exampleResult);

        // Retrieve result from EJB call

        Map<Object, Object> systemProperties;
        try {
            systemProperties = service.getSystemProperties();
        } catch (Exception e) {
            log.error("Error accessing remote bean");
            return;
        }

        System.out.println("Wildfly Home Dir: " + systemProperties.get("jboss.home.dir"));

    }


}
