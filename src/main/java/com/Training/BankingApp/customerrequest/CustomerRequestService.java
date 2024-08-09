package com.Training.BankingApp.customerrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRequestService {

    @Autowired
    private CustomerRequestRepository customerRequestRepository;

    public void createCustomerRequest(CustomerRequestDTO customerRequestDto) {
        if (customerRequestRepository.existsByUsername(customerRequestDto.getUsername())) {
            throw new RuntimeException("Request already sent!");
        }
        if (customerRequestRepository.existsByEmail(customerRequestDto.getEmail())) {
            throw new RuntimeException("Request already sent!");
        }

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setId(System.currentTimeMillis());
        customerRequest.setUsername(customerRequestDto.getUsername());
        customerRequest.setEmail(customerRequestDto.getEmail());
        customerRequest.setPassword(customerRequestDto.getPassword());
        customerRequest.setName(customerRequestDto.getName());
        customerRequest.setAddress(customerRequestDto.getAddress());
        customerRequest.setCnic(customerRequestDto.getCnic());
        customerRequest.setAccountType(customerRequestDto.getAccountType());
        customerRequest.setPhoneNumber(customerRequestDto.getPhoneNumber());

        customerRequestRepository.save(customerRequest);
    }

    public List<CustomerRequest> getAllRequests() {
        return customerRequestRepository.findAll();
    }

    public CustomerRequest getRequest(int id) {
        CustomerRequest customerRequest = customerRequestRepository.findById(id).orElse(null);
        return customerRequest;
    }

    public void deleteRequest(int id) {
        CustomerRequest customerRequest = customerRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        customerRequestRepository.delete(customerRequest);
    }
}
