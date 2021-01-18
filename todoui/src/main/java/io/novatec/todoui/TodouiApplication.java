package io.novatec.todoui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;

@SpringBootApplication
@Controller
public class TodouiApplication {

	@Value("${backend.url}")
	String endpoint;
	RestTemplate template = new RestTemplate();

	@PostConstruct
	public void postConstruct(){

		System.out.println(" UI initialized for backend at "+endpoint);
	}

	@GetMapping
	public String getItems(Model model){

		System.out.println(" Invoking: "+ endpoint + "/todos/");
		ResponseEntity<String[]> response = template.getForEntity(endpoint+"/todos/", String[].class);
		if(response != null) model.addAttribute("items", response.getBody());
		return "items";

	}

	@GetMapping("/stress")
	public String stress(){

		System.out.println(java.time.LocalDateTime.now() + " : Starting stress");
		double result = 0;
		for (int i = 0; i < 100000000; i++) {
			result += System.currentTimeMillis();
		}
		System.out.println(java.time.LocalDateTime.now() + " : Ending stress, result: " + result);
		return "redirect:/";

	}

	public static List<Double> list = new ArrayList<>(); // can never be GC'ed
	@GetMapping("/leak")
	public String leak(){

		System.out.println(java.time.LocalDateTime.now() + " : Start leaking");
		for (int i = 0; i < 10000000; i++) {
			list.add(Math.random());
		}
		System.out.println(java.time.LocalDateTime.now() + " : End leaking");
		return "redirect:/";

	}

	@GetMapping("/createcookie")
	public String createCookie(HttpServletResponse response) {

		Cookie cookie = new Cookie("featureflag", "on");
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/";

	}

	@GetMapping("/deletecookie")
	public String deleteCookie(HttpServletResponse response) {

		Cookie cookie = new Cookie("featureflag", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/";

	}

	@PostMapping
	public String addItem(String toDo){

		template.postForEntity(endpoint+"/todos/"+toDo, null, String.class);
		return "redirect:/";

	}

	@PostMapping("{toDo}")
	public String setItemDone(@PathVariable String toDo){


		template.delete(endpoint+"/todos/"+toDo);
		return "redirect:/";

	}

	public static void main(String[] args) {
		SpringApplication.run(TodouiApplication.class, args);
	}
}
