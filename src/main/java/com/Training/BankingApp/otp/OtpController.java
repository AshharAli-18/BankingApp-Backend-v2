package com.Training.BankingApp.otp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/api/requestOtp")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<String> requestOtp(@RequestBody OtpRequest otpRequest) {
//        String otp = otpService.generateAndSendOtp(otpRequest.getEmail());
       otpService.generateAndSendOtp(otpRequest.getEmail());
        return ResponseEntity.ok("OTP sent to your email.");
    }


}
