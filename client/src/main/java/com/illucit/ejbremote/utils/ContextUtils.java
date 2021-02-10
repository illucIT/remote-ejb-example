package com.illucit.ejbremote.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

import static javax.naming.Context.*;

public class ContextUtils {

    /**
     * Create Remote EJB Context.
     *
     * @param host host to connect to (e.g. "127.0.0.1")
     * @param port port to connect to (wildfly HTTP port, e.g. 8080)
     * @return remote EJB context
     * @throws NamingException if creating the context fails
     */
    public static Context createRemoteEjbContext(String host, String port) throws NamingException {

        Hashtable<Object, Object> props = new Hashtable<>();

        props.put(INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        props.put(URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        props.put("jboss.naming.client.ejb.context", false);
        props.put("org.jboss.ejb.client.scoped.context", true);

        props.put("endpoint.name", "client-endpoint");
        props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false);
        props.put("remote.connections", "default");
        props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", false);

        props.put(PROVIDER_URL, "http-remoting://" + host + ":" + port);
        props.put("remote.connection.default.host", host);
        props.put("remote.connection.default.port", port);

        return new InitialContext(props);
    }

    /**
     * Get a proxy for a remote EJB.
     *
     * @param remotingContext   remote EJB context
     * @param ejbUrl            URL of the EJB
     * @param ejbInterfaceClass class of the remote interface of the EJB
     * @param <T>               type of the EJB remote interface
     * @return EJB proxy
     * @throws NamingException    if the name resolving fails
     * @throws ClassCastException if the EJB proxy is not of the given type
     */
    @SuppressWarnings("unchecked")
    public static <T> T createEjbProxy(Context remotingContext, String ejbUrl, Class<T> ejbInterfaceClass)
            throws NamingException, ClassCastException {
        Object resolvedproxy = remotingContext.lookup(ejbUrl);
        return (T) resolvedproxy;
    }
}
