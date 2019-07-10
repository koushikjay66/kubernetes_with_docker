package spotify.search;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import spotify.search.resources.SearchService;

@ApplicationPath("/")
@Produces(MediaType.APPLICATION_JSON) // This makes the API Produces JSON
@Consumes(MediaType.APPLICATION_JSON) // This makes the API Consumes JSON
public class MusicApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(SearchService.class);
        return resources;
    }
}
