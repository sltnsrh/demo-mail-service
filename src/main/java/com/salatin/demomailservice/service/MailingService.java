package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.User;

public interface MailingService {

    void sendRestEmailToUser(Integer userId);

    void sendCronEmailToUser(User user);
}
