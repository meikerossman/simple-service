package com.example;

public class TodoItem {
	
	String title;
	String body;
	Boolean complete;
	
	public TodoItem(String title, String body, Boolean complete) {
		setTitle(title);
		setBody(body);
		setComplete(complete);
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getBody(){
		return body;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	public Boolean getComplete(){
		return complete;
	}
	
	public void setComplete(Boolean complete){
		this.complete = complete;
	}
	
	@Override
	public String toString(){
		return "TodoItem [title: "+title+", body: "+body+", complete: "+complete+"]";
	}
}
