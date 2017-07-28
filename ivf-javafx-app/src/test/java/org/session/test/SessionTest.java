package org.session.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.Couple;
import org.cf.card.ui.session.Session;
import org.cf.card.ui.session.SessionObject;
import org.junit.Test;

public class SessionTest {

	@SuppressWarnings("unchecked")
	@Test
	public void user1Test() {
		Session session =  Session.getInstance();
		SessionObject<String, String> user =  session.getSessionObject();
		user.setComponent("user", "Test");
		//System.out.println("1 object	"+user.getUser("user"));
		assertNotNull(user.getComponent("user"));
		
		Session session2 =  Session.getInstance();
		SessionObject<String, String> user2 =  session2.getSessionObject();
		//System.out.println("2 object	"+user2.getUser("user"));
		assertNotNull(user2.getComponent("user"));
		
		Session session3 =  Session.getInstance();
		SessionObject<String, String> user3 =  session3.getSessionObject();
		//System.out.println("3 object	"+user3.getUser("user"));
		user3.removeComponent("user");
		
		Session session4 =  Session.getInstance();
		SessionObject<String, String> user4 =  session4.getSessionObject();
		//System.out.println("4 object	"+user4.getUser("user"));
		
		assertNull(user4.getComponent("user"));
		
		
		SessionObject<String, Couple> coupleObject = session.getSessionObject();
		coupleObject.setComponent("couple", new Couple());
		
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void user2Test(){
		Session session =  Session.getInstance();
		SessionObject<String, String> user =  session.getSessionObject();
		//System.out.println("2 object	"+user2.getUser("user"));
		assertNull(user.getComponent("user"));
	}

	
	@Test
	public void ListTest(){
		List<String> aoList =  new ArrayList<String>();
		System.out.println(aoList.get(aoList.size()));
	}
	
}
