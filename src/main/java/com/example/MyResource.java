package com.example;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
//import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import com.example.dao.TodoItem;
import com.example.dao.DAOFactory;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/myresource")
public class MyResource {

	Map<String, TodoItem> items = new HashMap<String, TodoItem>();
    
    @GET
    @Path("/get/todolistsize")
    public Response getSize(){
    	return Response.ok("\n\n--------------\n\nTotal items:  " + DAOFactory.getDAO().tasksLeft() + "\n\n--------------\n\n").build();    	
    }
    
    @POST
    @Path("/create/{id}")
    public Response createItem(@PathParam("id") String id, @FormParam("body") String body, @FormParam("done") String done) {   
    	Boolean status = Boolean.valueOf(done);
    	TodoItem item = new TodoItem(id, body, status);    	
    	DAOFactory.getDAO().save(item);
    	return Response.accepted(item).build();
    }
    
    @GET
    @Path("/get/items")
    public Response getAllItems() {
    	String allFormattedItems = DAOFactory.getDAO().getAllItems();
    	return Response.ok("\n\n--------------\n\n"+allFormattedItems+"\n\n--------------\n\n").build();
    }
    
    @GET
    @Path("/get/items/{id}")
    public Response getItem(@PathParam("id") String id){    	
    	TodoItem item = DAOFactory.getDAO().get(id);
    	    	
    	return Response.ok("\n\n--------------\n\nItem: " + id + "\nBody: " + item.getBody() + " \nStatus: " + item.isDone()+"\n\n--------------\n\n").build();
    }
    
    
    @PUT
    @Path("/items/")
    public Response updateTodoItem(@PathParam("id") String id, @FormParam("body") String body, @FormParam("done") String done){
    	Boolean status = Boolean.valueOf(done);
    	TodoItem item = new TodoItem(id, body, status);
    	DAOFactory.getDAO().updatePut(item);
    	return Response.status(Status.RESET_CONTENT).build();
    }
 
    //@PATCH
    @PUT
    @Path("/items/{id}")
    public Response setComplete(@PathParam("id") String id, @FormParam("done") String done) {   
    	boolean status = Boolean.valueOf(done);
    	if(status==true){
    		TodoItem item = DAOFactory.getDAO().updatePatch(id);    	
        	return Response.status(Status.ACCEPTED).build();
    	} else {
    		return Response.status(Status.BAD_REQUEST).build();
    	}
    		
    }
    
    @DELETE
    @Path("/items/{id}")
    public Response deleteItem(@PathParam("id") String id){
    	DAOFactory.getDAO().delete(id);
    	return Response.ok("\n\n--------------\n\nItem: " + id + " deleted!\n\n").build();
    }
    
    // Security Issue - do not allow requests like that
    @DELETE
    @Path("/items/")
    public Response deleteAll(@PathParam("id") String id){
    	DAOFactory.getDAO().delete(id);
    	return Response.status(Status.BAD_REQUEST).build();
    }
    
     
    /* 
    	//// OTHER EXAMPLES //////
    */
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @POST
    @Path("/hello/{name}")
    public Response sayHello(@PathParam("name") String name, @FormParam("age") String age) {    	
    	return Response.ok("My name is " + name + " and I'm " + age + " yrs old").header("X-Custom", "asd").build();
    
    }
    
    
}
