package com.Training.BankingApp.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailSenderService emailService;

    private static final SecureRandom SECURE_RANDOM;

    // Define a constant for the maximum value of the OTP
    private static final int OTP_MAX_VALUE = 999999;

    static {
        try {
            SECURE_RANDOM = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No strong secure random available", e);
        }
    }

    private String generateOtp() {
        return String.format("%06d", SECURE_RANDOM.nextInt(OTP_MAX_VALUE + 1));
    }

    public String generateAndSendOtp(String email) {
        String generatedOtp = generateOtp();
        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setOtp(generatedOtp);
        otp.setExpiration(LocalDateTime.now().plusMinutes(2));
        otpRepository.save(otp);
        emailService.sendEmail(email, "OTP Generated", "Your generated otp is: " + generatedOtp);
        return generatedOtp;
    }

    public boolean validateOtp(String email, String otp) {
        Otp savedOtp = otpRepository.findByEmailAndOtp(email, otp);
        if (savedOtp != null && LocalDateTime.now().isBefore(savedOtp.getExpiration())) {
            otpRepository.delete(savedOtp); // OTP can be used only once
            return true;
        }
        return false;
    }
}
