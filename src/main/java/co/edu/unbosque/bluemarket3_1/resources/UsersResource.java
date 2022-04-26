package co.edu.unbosque.bluemarket3_1.resources;

import co.edu.unbosque.bluemarket3_1.dtos.User1;
import co.edu.unbosque.bluemarket3_1.dtos.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("/users")
public class UsersResource {

    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<User1> users = new UserService().getUsers();

            return Response.ok()
                    .entity(users)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        String contextPath = context.getRealPath("") + File.separator;

        try {
            user = new UserService().createUser(user.getUsername(), user.getPassword(), user.getRole(), contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(user.getUsername()).build())
                    .entity(user)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username) {
        try {
            List<User> users = new UserService().getUsers();

            User user = users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                return Response.ok()
                        .entity(user)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("role") String role
    ) {
        String contextPath = context.getRealPath("") + File.separator;

        try {
            User user = new UserService().createUser(username, password, role, contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                    .entity(user)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
}