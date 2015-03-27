# Remote-ejb-example

Example for EJB remoting in Wildfly

Author: Christian Simon <[simon@illucit.com](mailto:simon@illucit.com)>  
Website: [http://www.illucit.com](http://www.illucit.com)

## About

This simple example should demonstrate how to do EJB remoting with Wildfly from a client application which does not run in an application server itself.

The used application server is [Wildfly 8.1.0.Final](http://wildfly.org), but the example should also run on newer versions of the Wildfly application server.

## Setup and Run Example

There are 2 projects in this repository to show the interaction between a server EJB and the client.

### EJB server

This is a maven project, which can be compiled as EJB JAR:

    cd server
    mvn clean compile package install

The resulting file `target/ejb-remote-server.jar` can then be deployed to a running Wildfly server (e.g. via the management console).
The `install` command in maven will install the package also in your local maven repository, so the EJB client can find the remote interface class of your EJB.

You also need to define an application user in your Wildfly instance in order to use remoting.
Therefore execute the script `wildfly/bin/add_user.sh` or `wildfly/bin/add_user.bat` in your Wildfly application folder and choose to add an application user.

In the example code the username "remote" and password "remote" are used. If you choose differently, you have to change it in the file `client/src/main/java/com/illucit/ejbremote/EjbRemoteClient.java` accordingly.

### EJB Client

The EJB client can connect to a Wildfly instance with the remote EJB example  deployed.
If you are on the same host and have not changed the default Wildfly port settings, you can leave the connection parameters as they are.
Otherwise you need to adapt the variables "host" and "port" in the file `client/src/main/java/com/illucit/ejbremote/EjbRemoteClient.java` (port should always be the HTTP connector port of Wildfly).

To compile and run the example you can simply use a maven call:

    cd client
    mvn clean compile package exec:java

You should see the example result "Hello World" and the the remotely queried Wildfly Home Dir on the output.