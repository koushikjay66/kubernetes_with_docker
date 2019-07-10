package spotify.charts;

import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ContextResolver;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import spotify.configurations.Configuration;

public class ChartsServer {
    private static Properties properties = Configuration.loadProperties();
    
    
    public static void main(String[] args) {
        String serverUri = properties.getProperty("restServerUri");

        URI baseUri = UriBuilder.fromUri(serverUri).build();
        System.out.println(baseUri.toString());
        
        ContextResolver<?> jsonConfigResolver = new MoxyJsonConfig().resolver();
        
		ResourceConfig config = ResourceConfig.forApplicationClass(MusicApi.class)
				.packages("org.glassfish.jersey.moxy.json.MoxyJsonFeature")
				.register(MoxyJsonFeature.class)
				.register(jsonConfigResolver);
        JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Server ready to serve your JAX-RS requests...");
    }
}
