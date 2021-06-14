package com.example.demo;

import com.example.demo.model.Approval;
import com.example.demo.model.Enquiry;
import com.example.demo.model.JobType;
import com.example.demo.repository.EnquiryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(EnquiryRepository repo) {
		return args -> {
			Enquiry enquiry = new Enquiry();
			Approval approval = new Approval();
			approval.setApprovalStatus(Boolean.TRUE);
			approval.setApprovedUser("role1");
			approval.setEnquiry(enquiry);
			enquiry.setCustomerName("Customer1");
			enquiry.setLoanAmount(1000.0);
			enquiry.setInterestRate(18f);
			enquiry.setJobType(JobType.SALARIED);
			enquiry.setApproval(approval);
			repo.save(enquiry);

			Enquiry enquiry2 = new Enquiry();
			enquiry2.setCustomerName("Customer2");
			enquiry2.setLoanAmount(1000.0);
			enquiry2.setInterestRate(15f);
			enquiry2.setJobType(JobType.SELF_EMPLOYED);
			repo.save(enquiry2);

			Enquiry enquiry3 = new Enquiry();
			enquiry3.setCustomerName("Customer3");
			enquiry3.setLoanAmount(1000.0);
			enquiry3.setInterestRate(13f);
			enquiry3.setJobType(JobType.SELF_EMPLOYED);
			repo.save(enquiry3);

			Enquiry enquiry4 = new Enquiry();
			enquiry4.setCustomerName("Customer4");
			enquiry4.setLoanAmount(1000.0);
			enquiry4.setInterestRate(11f);
			enquiry4.setJobType(JobType.SELF_EMPLOYED);
			repo.save(enquiry4);
		};
	}

}
