package com.djenkins.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping(value = { "/", "/graph", "/create", "/sumsAnnual", "/sums" })
	public String index() {
		return "index";
	}
}
