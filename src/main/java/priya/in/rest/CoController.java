package priya.in.rest;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import priya.in.service.CoServiceImpl;

@RestController
public class CoController {

	

	@Autowired
	private CoServiceImpl service;

	@GetMapping("/co/detail")
	public void coDetails() throws MessagingException, Exception {
System.out.println("controller");
		service.processRecord();

	}

}
