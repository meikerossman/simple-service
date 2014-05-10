package com.example;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.dao.DAOFactory;

public class DAOFactoryTest {

	@Test
	public void testGetDAO() {
		assertNotNull(DAOFactory.getDAO());
	}
	
}
