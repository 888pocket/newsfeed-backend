package com.joosangah.ordersystem.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("spring.mail.username")
    private String rootMail;

    private final MailSender mailSender;

    public void sendEmail(String toAddress, String subject, String msgBody) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(rootMail);
        smm.setTo(toAddress);
        smm.setSubject(subject);
        smm.setText(msgBody);

        mailSender.send(smm);
    }
}
