package com.faf.storage.dto.request;

public record SignUpRequest(String login, String password, String firstName,
                            String lastName, String email, String imageUrl) {
}
