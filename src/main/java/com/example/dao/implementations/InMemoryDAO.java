package com.example.dao.implementations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jersey.repackaged.com.google.common.base.Preconditions;

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
		if(todoItem!=null){
			this.memoryStore.put(todoItem.getId(), todoItem);
    		return todoItem;
		} else {
			return null;
		}
	}

	@Override
	public void delete(TodoItem todoItem) {
		this.memoryStore.remove(todoItem.getId());
	}

	@Override
	public void delete(String id) { 		
		isItemPresent(this.memoryStore.get(id));
		this.memoryStore.remove(id);
	}

	@Override
	public TodoItem get(String id) {
		if(isItemPresent(this.memoryStore.get(id))){
			return this.memoryStore.get(id);
		}
		return null;
	}
	
	@Override
	public String getAllItems(){ 
		//TODO return null if no items
		//TODO preconditions appropriate here ?
		Preconditions.checkArgument(memoryStoreHasItems());	
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
		TodoItem oldItem = getItemFromDataStore(newItem.getId());		
		if(isItemPresent(oldItem)){
			this.memoryStore.remove(oldItem.getId());
			this.memoryStore.put(newItem.getId(), newItem);
			return oldItem;
		} else {
			return null;
		}			
	}

	/*
	 *	This method will take an old TodoItem and set done state to true
	 */
	@Override
	public TodoItem updatePatch(TodoItem newItem){
		TodoItem oldItem = getItemFromDataStore(newItem.getId());		
		if(isItemPresent(oldItem)){
			this.memoryStore.remove(oldItem.getId());
			this.memoryStore.put(newItem.getId(), newItem);
			return oldItem;
		} else {
			return null;
		}		
	}
	
	private boolean memoryStoreHasItems(){
		return this.memoryStore.size()>=0;
	}
	
	private boolean isItemPresent(TodoItem item) {
		return memoryStoreHasItems() && this.memoryStore.containsKey(item.getId());
	}
	
	private TodoItem getItemFromDataStore(String id){
		return this.memoryStore.remove(id);
	}
}
