package com.geminiAi.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.geminiAi.main.entity.AiInput;

import reactor.core.publisher.Mono;

@Service
public class GeminiAIImpl {

	@Autowired
	private WebClient webClient;

	private final String apiKey = "AIzaSyB2qNdhnkaYz-rrkrtdGzeKTYfPbKAh_-M"; // Replace with your actual API key
	private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
			+ apiKey;

	public Mono<String> askAI(AiInput aiRequest) {
		String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + aiRequest.getInput() + "\" }]}]}";

		// Call Gemini API asynchronously
		return webClient.post().uri(apiUrl).contentType(MediaType.APPLICATION_JSON).bodyValue(requestBody).retrieve()
				.bodyToMono(Map.class).map(this::extractText);
	}

	@SuppressWarnings("unchecked")
	private String extractText(Map<String, Object> response) {
		try {
			List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
			if (candidates != null && !candidates.isEmpty()) {
				Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
				List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
				if (parts != null && !parts.isEmpty()) {
					return parts.get(0).get("text"); // Extract only the text part
				}
			}
		} catch (Exception e) {
			return "Error extracting text: " + e.getMessage();
		}
		return "No response text found.";
	}
}
