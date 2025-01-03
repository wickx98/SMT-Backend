package com.smt.QA.responses;

public class LoginResponse {
    private String token;
    private long expiresIn;

    // Getter for token
    public String getToken() {
        return token;
    }

    // Setter for token
    public LoginResponse setToken(String token) {
        this.token = token;
        return this; // Enable method chaining
    }

    // Getter for expiresIn
    public long getExpiresIn() {
        return expiresIn;
    }

    // Setter for expiresIn
    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this; // Enable method chaining
    }
}
