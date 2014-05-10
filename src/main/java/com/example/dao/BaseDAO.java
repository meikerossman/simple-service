package com.example.dao;

public interface BaseDAO {

	int tasksLeft();
	String getAllItems();
	
	TodoItem save(TodoItem todoItem);
	
	void delete(TodoItem todoItem);
	void delete(String id);
	
	TodoItem get(String id);
	
	//PUT
	TodoItem updatePut(TodoItem newItem);
	//PATCH
	TodoItem updatePatch(String id);
}
