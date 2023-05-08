package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.User;

public interface MailingService {

    void sendRestEmailToUserById(Integer userId);

    void sendCronEmailToUser(User user);
}
