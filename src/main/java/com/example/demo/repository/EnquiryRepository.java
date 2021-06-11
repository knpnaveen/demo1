package com.example.demo.repository;

import com.example.demo.model.Enquiry;
import org.springframework.data.repository.CrudRepository;


public interface EnquiryRepository  extends CrudRepository<Enquiry,Long> {
}
