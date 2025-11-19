package com.verma.loginapi.dto;

import java.util.List;

public class GeminiChatResponse {
    private List<Candidate> candidates;

    // Getters and Setters
    public List<Candidate> getCandidates() { return candidates; }
    public void setCandidates(List<Candidate> candidates) { this.candidates = candidates; }

    public static class Candidate {
        private Content content;
        private String finishReason;

        // Getters and Setters
        public Content getContent() { return content; }
        public void setContent(Content content) { this.content = content; }
        public String getFinishReason() { return finishReason; }
        public void setFinishReason(String finishReason) { this.finishReason = finishReason; }
    }

    public static class Content {
        private List<Part> parts;
        private String role;

        // Getters and Setters
        public List<Part> getParts() { return parts; }
        public void setParts(List<Part> parts) { this.parts = parts; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    public static class Part {
        private String text;

        // Getters and Setters
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    // Helper method to extract the response text
    public String getResponseText() {
        if (candidates != null && !candidates.isEmpty() &&
                candidates.get(0).getContent() != null &&
                candidates.get(0).getContent().getParts() != null &&
                !candidates.get(0).getContent().getParts().isEmpty()) {
            return candidates.get(0).getContent().getParts().get(0).getText();
        }
        return "No response from Gemini";
    }
}