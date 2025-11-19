package com.verma.loginapi.service;

import com.verma.loginapi.dto.GeminiChatRequest;
import com.verma.loginapi.dto.GeminiChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model:gemini-pro}")
    private String model;

    public GeminiService(WebClient geminiWebClient) {
        this.webClient = geminiWebClient;
    }

    public String generateContent(String prompt) {
        try {
            GeminiChatRequest request = new GeminiChatRequest(prompt);

            String apiUrl = "/models/" + model + ":generateContent?key=" + apiKey;

            GeminiChatResponse response = webClient.post()
                    .uri(apiUrl)
                    .body(Mono.just(request), GeminiChatRequest.class)
                    .retrieve()
                    .bodyToMono(GeminiChatResponse.class)
                    .block();

            return response.getResponseText();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // SINGLE HEALTH CHAT METHOD - SHORT & CRISP
    public String healthChat(String userMessage) {
        String prompt = "You are a health AI advisor. Only answer health-related questions.\n\n" +
                "RULES:\n" +
                "1. Keep answers SHORT - maximum 2-3 sentences\n" +
                "2. Be direct and concise\n" +
                "3. No long explanations\n" +
                "4. If not health-related: 'I only answer health questions.'\n\n" +
                "Question: " + userMessage + "\n\n" +
                "Short answer:";

        return generateContent(prompt);
    }
}