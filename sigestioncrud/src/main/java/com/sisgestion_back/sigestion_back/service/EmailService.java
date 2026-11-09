package com.sisgestion_back.sigestion_back.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
