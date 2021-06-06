package com.hackerrank.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.sample.service.ReportsService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportsService reportsService;

	private final String ROLE_ADMIN = "ROLE_ADMIN";

	@GetMapping("/generate")
	@ResponseStatus(HttpStatus.OK)
	@Secured({ ROLE_ADMIN })
	public String generateReport() {
		reportsService.generateReport();
		return "Report generated in C:/temp";
	}
}
