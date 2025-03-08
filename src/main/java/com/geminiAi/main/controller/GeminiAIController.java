package com.geminiAi.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.geminiAi.main.entity.AiInput;
import com.geminiAi.main.service.GeminiAIImpl;

import reactor.core.publisher.Mono;

@RestController
public class GeminiAIController {
	
	@Autowired
	private GeminiAIImpl geminiAIImpl;
	
	@PostMapping("/askAI")
	public ResponseEntity<Mono<String>> askAI(@RequestBody AiInput aiInput){
		Mono<String> askAI = geminiAIImpl.askAI(aiInput);
		return new ResponseEntity<Mono<String>>(askAI, HttpStatus.OK);
	}
	
}
