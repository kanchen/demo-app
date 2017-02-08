package com.izops.jersey.docker.demo;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.net.BindException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    //public static final String BASE_URI = "http://localhost:8080/myapp/";
    public static final String PORT = "8080";
    public static final String BASE_URI = "http://0.0.0.0:"+PORT+"/demo-app/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer httpServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.dekses.jersey.docker.demo package
        final ResourceConfig rc = new ResourceConfig().packages("com.izops.jersey.docker.demo");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc, false);
    }

    public static String getVersion() {
        Properties prop = new Properties();
        String version = "undefined";

        InputStream is = null;
        try {
            is = Main.class.getResourceAsStream("/version.properties");
            prop.load(is);

            version = prop.getProperty("version");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return version;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        Thread worker = new WorkerThread();
        worker.start();

        int millis = 7600;

        try {
            if (args.length > 0) {
                millis = Integer.parseInt(args[0]);
            }
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // handle here exception
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (args.length > 0) {
            worker.stop();
        }
    }
}

class WorkerThread extends Thread {

    public WorkerThread() {
        // When false, (i.e. when it's a user thread),
        // the Worker thread continues to run.
        // When true, (i.e. when it's a daemon thread),
        // the Worker thread terminates when the main 
        // thread terminates.
        setDaemon(true); 
    }

    public void run() {
        final HttpServer server = Main.httpServer();
        try {
            server.start(); 
            System.out.println("Grizzly server running at " + Main.BASE_URI);
            //Thread.currentThread().join();
        } catch (BindException be) {
            System.out.println("Cannot bind to port "+Main.PORT+". Is it already in use?");
        } catch (IOException ioe) {
            System.out.println("IO exception while starting server.");
        } catch (Exception ie) {
            System.out.println("Interrupted, shutting down.");
            server.stop();
        }
    }
}



