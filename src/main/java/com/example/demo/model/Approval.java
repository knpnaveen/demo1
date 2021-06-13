package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Approval implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recommendedUser;
    private Boolean approvalStatus;
    private String approvedUser;
    @OneToOne(mappedBy = "approval")
    private Enquiry enquiry;

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecommendedUser() {
        return recommendedUser;
    }

    public void setRecommendedUser(String recommendedUser) {
        this.recommendedUser = recommendedUser;
    }

    public Boolean getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovedUser() {
        return approvedUser;
    }

    public void setApprovedUser(String approvedUser) {
        this.approvedUser = approvedUser;
    }
}
