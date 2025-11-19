package com.verma.loginapi.service;

import com.verma.loginapi.dto.GeminiChatRequest;
import com.verma.loginapi.dto.GeminiChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MedicineService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model:gemini-pro}")
    private String model;

    public MedicineService(WebClient geminiWebClient) {
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

    // SINGLE MEDICINE CHAT METHOD - HANDLES ALL TYPES OF MEDICINE QUERIES
    public String medicineChat(String userMessage) {
        String prompt = """
        You are a precise medicine and pharmaceutical AI advisor.
        Respond ONLY about medicines and drugs.

        TASK:
        - Extract medicine-related details based on the user's question.
        - Reply strictly in the following 4-line format:
        
        Benefits: <one-line benefit or therapeutic use>
        Usage: <one-line description of how it is generally used>
        Side Effects: <one-line list of common side effects>
        Price: <approximate medicine prices by india location>

        RULES:
        1. Each line must start with the given label exactly as shown.
        2. Each line must be ONE short sentence only.
        3. No explanations, introductions, or disclaimers outside this format.
        4. If the question is not about a medicine or drug, reply exactly:
           "I only answer medicine and drug-related questions."

        User Query: %s
        """.formatted(userMessage);

        return generateContent(prompt);
    }

}