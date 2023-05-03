package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.model.Email;
import com.salatin.demomailservice.repository.EmailRepository;
import com.salatin.demomailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;

    @Override
    public Email save(Email email) {
        return emailRepository.save(email);
    }
}
