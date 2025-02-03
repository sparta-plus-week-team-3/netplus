package com.example.com.netplus.exception;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(Long contentId) {
        super("Content with ID " + contentId + " not found");
    }
}
