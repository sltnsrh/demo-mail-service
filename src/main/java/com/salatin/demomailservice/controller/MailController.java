package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.dto.request.EmailSendRequestDto;
import com.salatin.demomailservice.service.MailSenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailSenderService mailSenderService;

    @PostMapping
    public ResponseEntity<Void> send(@RequestBody @Valid EmailSendRequestDto requestDto) {
        mailSenderService.send(requestDto.getTo(), requestDto.getSubject(), requestDto.getBody());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
