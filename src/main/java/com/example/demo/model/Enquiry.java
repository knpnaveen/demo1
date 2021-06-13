package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
@Entity
public class Enquiry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Customer name cannot be empty.")
    private String customerName;
    @Enumerated
    @NotNull(message = "Job Type cannot be empty.")
    private JobType jobType;
    @NotNull(message = "Loan amount cannot be empty.")
    @Positive
    private Double loanAmount;
    @NotNull(message = "Interest rate cannot be empty.")
    @Positive
    private Float interestRate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;




    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }
}
