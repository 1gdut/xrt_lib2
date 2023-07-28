package com.example.intelligentinsertion.Bean;

public class User {
    private String phone;
    private String password;
    private String question;
    private String answer;

    public User() {
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public User(String question, String answer, String password) {
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    public User(String phone, String question, String answer, String password) {
        this.phone = phone;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
