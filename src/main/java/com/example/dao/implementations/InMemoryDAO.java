package com.example.dao.implementations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.dao.BaseDAO;
import com.example.dao.TodoItem;

public class InMemoryDAO implements BaseDAO {

	private Map<String, TodoItem> memoryStore = new HashMap<String, TodoItem>();
	
	@Override
	public int tasksLeft(){
		return this.memoryStore.size();
	}
	
	@Override
	public TodoItem save(TodoItem todoItem) {		
    	this.memoryStore.put(todoItem.getId(), todoItem);
    	return todoItem;		
	}

	@Override
	public void delete(TodoItem todoItem) {
		this.memoryStore.remove(todoItem.getId());
	}

	@Override
	public void delete(String id) {
		this.memoryStore.remove(id);
	}

	@Override
	public TodoItem get(String id) {
		return this.memoryStore.get(id);		
	}
	
	@Override
	public String getAllItems(){  	    
    	String superString = "Todo Items: \n";		
		Iterator<TodoItem> iterator = this.memoryStore.values().iterator();
		while(iterator.hasNext()){
			superString = superString + iterator.next().toString();
		}    	
		return superString;		
	}

	/*
	 *	This method will take an old TodoItem and replace it with a new one 
	 */
	@Override
	public TodoItem updatePut(TodoItem newItem) {
		TodoItem oldItem = this.memoryStore.remove(newItem.getId());
		this.memoryStore.put(newItem.getId(), newItem);
		return oldItem;
	}

	/*
	 *	This method will take an old TodoItem and set done state to true
	 */
	@Override
	public TodoItem updatePatch(String id){
		TodoItem itemToUpdate = this.memoryStore.remove(id);	
		itemToUpdate.setDone(true);
		this.memoryStore.put(itemToUpdate.getId(), itemToUpdate);
		return itemToUpdate;
	}

}
