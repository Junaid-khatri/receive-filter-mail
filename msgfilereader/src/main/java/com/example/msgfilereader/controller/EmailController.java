package com.example.msgfilereader.controller;

import com.example.msgfilereader.service.ReadEmail;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final ReadEmail readEmail;

    @GetMapping("/readEmail")
    public void readEmail() throws MessagingException {
        readEmail.receiveEmailUsingJakarta();
    }
}
