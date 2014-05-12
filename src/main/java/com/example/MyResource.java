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

import jersey.repackaged.com.google.common.base.Preconditions;

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
    @Path("/items")
    public Response createItem(@FormParam("id") String id, @FormParam("body") String body, @FormParam("done") String done) {   
    	TodoItem item = itemFieldVerification(id, body, done);   	
    	DAOFactory.getDAO().save(item);
    	return resourceResponseCheck(item, Status.ACCEPTED, Status.BAD_REQUEST);    	  	
    }
    
    @GET
    @Path("/get/items")
    public Response getAllItems() {
    	String allFormattedItems = DAOFactory.getDAO().getAllItems();    	
    	return resourceResponseCheck(allFormattedItems, Status.OK, Status.NOT_FOUND, 
    			"\n\n--------------\n\n"+allFormattedItems+"\n\n--------------\n\n");
    }
    
    @GET
    @Path("/get/items/{id}")
    public Response getItem(@PathParam("id") String id){    	
    	TodoItem item = DAOFactory.getDAO().get(id);
    	return resourceResponseCheck(item, Status.ACCEPTED, Status.NOT_FOUND, 
    			"\n\n--------------\n\nItem: " + id + 
    			"\nBody: " + item.getBody() + 
    			" \nStatus: " + item.isDone()+"\n\n--------------\n\n");  	
    }
    
    
    @PUT
    @Path("/items/")
    public Response updateTodoItem(@FormParam("id") String id, @FormParam("body") String body, @FormParam("done") String done){
    	TodoItem item = itemFieldVerification(id, body, done);   	
    	DAOFactory.getDAO().updatePut(item);
    	return resourceResponseCheck(item, Status.RESET_CONTENT, Status.BAD_REQUEST);
    }
    
    //TODO - figure out how to turn this into a PATCH
    @PUT
    @Path("/items/{id}")
    public Response updateItem(@PathParam("id") String id, @FormParam("body") String body, @FormParam("done") String done){
    	TodoItem itemNewValues = itemFieldVerification(id, body, done);
    	//TODO _ one last thing here allow passing of empty body or status !!!
    	TodoItem itemOldValues = DAOFactory.getDAO().updatePatch(itemNewValues);
    	return resourceResponseCheck(itemOldValues, Status.ACCEPTED, Status.BAD_REQUEST, "Updated!");
    }
    
    @DELETE
    @Path("/items/{id}")
    public Response deleteItem(@PathParam("id") String id){
    	DAOFactory.getDAO().delete(id);
    	return Response.ok("\n\n--------------\n\nItem: " + id + " deleted!\n\n").build();
    }
    
    @DELETE
    @Path("/items/")
    public Response deleteAll(){    	
    	return Response.status(Status.BAD_REQUEST).build();
    }
    
    private Response resourceResponseCheck(Object object, Status successStatus, Status errorStatus){
    	if(object != null){
    		return Response.status(successStatus).build();
    	} else {
    		return Response.status(errorStatus).build();
    	}
    }
    
    private Response resourceResponseCheck(Object object, Status successStatus, Status errorStatus, String successMessage){
    	if(object != null){
    		return Response.status(successStatus).entity(successMessage).build();
    	} else {
    		return Response.status(errorStatus).build();
    	}
    }
    
    private TodoItem itemFieldVerification(String id, String body, String done){
		TodoItem todoItem = new TodoItem();
		if("".equals(id) || "".equals(body) || "".equals(done)){
			return null;			
		} else {
			todoItem.setId(id);
			todoItem.setBody(body);
			if(done.equals("true") || done.equals("false")){
				todoItem.setDone(Boolean.getBoolean(done));
				return todoItem;
			} else {
				return null;
			}			
		}
	}
    
     
    /* 
    	//// OTHER EXAMPLES //////
    */
    
    // NOTES
    // return Response.ok("\n\n--------------\n\n"+allFormattedItems+"\n\n--------------\n\n").build();
	//return Response.accepted(item).build();  
    
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
