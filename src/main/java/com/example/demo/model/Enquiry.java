package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
public class Enquiry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    @Enumerated
    private JobType jobType;
    private Double loanAmount;
    private Float interestRate;

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
}
