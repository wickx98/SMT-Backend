package com.smt.QA.dtos;

public class LoginUserDto {
    private String epfNumber;
    private String password;

    // Getter and Setter for epfNumber
    public String getEpfNumber() {
        return epfNumber;
    }

    public void setEpfNumber(String epfNumber) {
        this.epfNumber = epfNumber;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
