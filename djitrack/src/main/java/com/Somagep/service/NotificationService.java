package com.Somagep.service;


import com.Somagep.entity.Client;
import com.Somagep.entity.Notification;
import com.Somagep.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Async
    public void envoyerNotification(Client client, String sujet, String message) {
        if (client.getEmail() != null && !client.getEmail().isEmpty()) {
            envoyerEmail(client.getEmail(), sujet, message);
        }
        if (client.getTelephone() != null && !client.getTelephone().isEmpty()) {
            envoyerSms(client.getTelephone(), message);
        }

        Notification notif = new Notification();
        notif.setType("EMAIL");
        notif.setDestinataire(client.getEmail());
        notif.setContenu(message);
        notif.setSujet(sujet);
        notif.setDateEnvoi(new Date());
        notif.setEnvoye(true);
        notificationRepository.save(notif);
    }

    private void envoyerEmail(String to, String subject, String text) {
        if (mailSender != null) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            mailSender.send(msg);
        }
    }

    private void envoyerSms(String phone, String message) {
        System.out.println("SMS envoyé à " + phone + " : " + message);
    }
}