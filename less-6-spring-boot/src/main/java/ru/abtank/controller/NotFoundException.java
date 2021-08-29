package ru.abtank.controller;

public class NotFoundException extends RuntimeException {

    private String className;
    private String errorMessage;
    private Integer id;

    public NotFoundException(String className, Integer id, String errorMessage) {
        this.className = className;
        this.errorMessage = errorMessage;
        this.id = id;
    }

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        this.errorMessage = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMessage() {
        return String.format("%s id=%d %s", className, id, errorMessage);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
