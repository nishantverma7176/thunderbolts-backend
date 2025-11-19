package com.verma.loginapi.dto;

import java.util.ArrayList;
import java.util.List;

public class GeminiChatRequest {
    private List<Content> contents;

    public GeminiChatRequest() {
        this.contents = new ArrayList<>();
    }

    public GeminiChatRequest(String text) {
        this();
        this.contents.add(new Content(new Part(text)));
    }

    // Getters and Setters
    public List<Content> getContents() { return contents; }
    public void setContents(List<Content> contents) { this.contents = contents; }

    public static class Content {
        private List<Part> parts;

        public Content(Part part) {
            this.parts = new ArrayList<>();
            this.parts.add(part);
        }

        // Getters and Setters
        public List<Part> getParts() { return parts; }
        public void setParts(List<Part> parts) { this.parts = parts; }
    }

    public static class Part {
        private String text;

        public Part(String text) {
            this.text = text;
        }

        // Getters and Setters
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
}