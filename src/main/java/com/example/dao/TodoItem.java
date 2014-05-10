package com.example.dao;

public class TodoItem {

	private String id;
	private String body;
	private boolean done;
	
	public TodoItem(String id, String body, Boolean complete) {
		setId(id);
		setBody(body);
		setDone(done);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	
	@Override
	public String toString(){
		return "TodoItem [title: "+id+", body: "+body+", done: "+done+"]";
	}
}
