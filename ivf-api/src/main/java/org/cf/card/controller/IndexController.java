/**
 *
 */
package org.cf.card.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nikhil Mahajan
 * Controller for Index page
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String index(){
		System.out.println("--XXX---");
		return "index";
	}

}
