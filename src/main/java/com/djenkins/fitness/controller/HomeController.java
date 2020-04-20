package com.djenkins.fitness.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController implements ErrorController {
	@RequestMapping(
			value = { "/index", "/graph", "/create", "/sumsAnnual", "/sums", "/404", "/500" })
	public String index() {
		return "index";
	}

	@RequestMapping(value = { "/" })
	public String redirectToIndex() {
		return "redirect:/index";
	}

	@RequestMapping(value = "/error")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleError() {
		return "forward:/404";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
