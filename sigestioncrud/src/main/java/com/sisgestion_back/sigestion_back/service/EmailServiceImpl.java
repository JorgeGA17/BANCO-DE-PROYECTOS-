package com.sisgestion_back.sigestion_back.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("================== [CORREO SIMULADO (MODO LOCAL)] ==================");
        log.info("Para: {}", to);
        log.info("Asunto: {}", subject);
        log.info("Contenido:\n{}", body);
        log.info("====================================================================");
    }
}
