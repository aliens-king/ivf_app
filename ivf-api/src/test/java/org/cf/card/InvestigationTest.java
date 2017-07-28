/**
 * 
 */
package org.cf.card;

import static org.junit.Assert.*;

import java.util.List;

import org.cf.card.model.Investigation;
import org.cf.card.service.InvestigationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author insonix
 *
 */
public class InvestigationTest extends BaseTest {

	@Autowired
	InvestigationService investigationService;
	
	@Test
	public void test() {
		List< Investigation> list = investigationService.findAll();
		System.out.println("size : "+list.size());
		assertNotNull(list);
	}

}
