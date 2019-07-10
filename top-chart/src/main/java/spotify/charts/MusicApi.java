package spotify.charts;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import spotify.charts.resources.ChartsService;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON) // This makes API always consume JSON
@Produces(MediaType.APPLICATION_JSON) // This makes API always produce JSON
public class MusicApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ChartsService.class);
        return resources;
    }
}
