
package com.Training.BankingApp.customerrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRequestController {

    @Autowired
    private CustomerRequestService customerRequestService;

    @PostMapping("/v2/auth/request")
    public ResponseEntity<?> customerRequest(@RequestBody CustomerRequestDTO customerRequestDTO) {
        try {
            customerRequestService.createCustomerRequest(customerRequestDTO);
            return ResponseEntity.ok("Customer Request Sent Successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/v2/requests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CustomerRequest> getAllRequests() {
        return customerRequestService.getAllRequests();
    }

    @GetMapping("/v2/request/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CustomerRequest getRequest(@PathVariable int id) {
        return customerRequestService.getRequest(id);
    }

    @DeleteMapping("/v2/request/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteRequest(@PathVariable int id) {
        customerRequestService.deleteRequest(id);
    }
}
