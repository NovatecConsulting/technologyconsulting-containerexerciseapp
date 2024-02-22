package de.maeddes;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

    @ConfigProperty(name = "HOSTNAME", defaultValue = "localhost")
    String myHostName;
    @ConfigProperty(name = "PROPERTY", defaultValue = "somebody")
    String myProperty;

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World (from " + myHostName +") to " + myProperty;
    }

    @GET
    @Path("fail")
    @Produces(MediaType.TEXT_PLAIN)
    public String fail() {
        System.exit(1);
        return "Hello World!";
    }
}
