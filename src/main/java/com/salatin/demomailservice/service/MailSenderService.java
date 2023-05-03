package com.salatin.demomailservice.service;

public interface MailSenderService {

    void send(String to, String subject, String body);
}
