package com.example.demo.model;

public enum JobType {
    SELF_EMPLOYED("Self Employed"),
    SALARIED("Salaried");

    private final String displayText;

    JobType(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
