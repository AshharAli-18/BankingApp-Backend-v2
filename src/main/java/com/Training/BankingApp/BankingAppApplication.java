package com.Training.BankingApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAppApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        // Fetch environment variables with null checks
        String dbProdUsername = dotenv.get("DB_PROD_USERNAME");
        String dbProdPassword = dotenv.get("DB_PROD_PASSWORD");
        String dbTestUsername = dotenv.get("DB_TEST_USERNAME");
        String dbTestPassword = dotenv.get("DB_TEST_PASSWORD");

        if (dbProdUsername != null) {
            System.setProperty("DB_PROD_USERNAME", dbProdUsername);
        } else {
            // Handle missing environment variable as needed
            System.err.println("Warning: DB_PROD_USERNAME is not set.");
        }

        if (dbProdPassword != null) {
            System.setProperty("DB_PROD_PASSWORD", dbProdPassword);
        } else {
            // Handle missing environment variable as needed
            System.err.println("Warning: DB_PROD_PASSWORD is not set.");
        }

        if (dbTestUsername != null) {
            System.setProperty("DB_TEST_USERNAME", dbTestUsername);
        } else {
            // Handle missing environment variable as needed
            System.err.println("Warning: DB_TEST_USERNAME is not set.");
        }

        if (dbTestPassword != null) {
            System.setProperty("DB_TEST_PASSWORD", dbTestPassword);
        } else {
            // Handle missing environment variable as needed
            System.err.println("Warning: DB_TEST_PASSWORD is not set.");
        }

        SpringApplication.run(BankingAppApplication.class, args);
    }
}
