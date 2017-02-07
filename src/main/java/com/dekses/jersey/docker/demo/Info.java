package com.dekses.jersey.docker.demo;

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "helloworld" path)
 */
@Path("/api/info")
public class Info {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "JSON" media type.
     *
     * @return String that will be returned as a JSON response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ServerInfo getInfo() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setVersion(Main.getVersion());
        String ip = "0.0.0.0";
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverInfo.setIp(ip);
        return serverInfo;
    }
}
