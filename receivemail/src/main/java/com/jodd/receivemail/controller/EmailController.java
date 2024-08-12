package com.jodd.receivemail.controller;

import com.jodd.receivemail.newservice.ReceiveEmail;
import jodd.mail.EmailAddress;
import jodd.mail.EmailMessage;
import jodd.mail.ReceivedEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EmailController {

    @Autowired
    ReceiveEmail receiveEmail;
    private static List<ReceivedEmail> filteredMails;

    @GetMapping("/getEmail")
    public String getAndFilterEmail(Model model) throws Exception {
        filteredMails = receiveEmail.receiveMail();
        model.addAttribute("emails", filteredMails);
        model.addAttribute("index", 0);
        return "listPage";
    }
    @GetMapping("/email/{id}")
    public String getEmail(@PathVariable int id, Model model){
        ReceivedEmail email = filteredMails.get(id);
        EmailAddress emailAddress = email.to()[0];
        System.out.println(emailAddress.toString());
        String message = getMessage(email);
        model.addAttribute("email", email);
        model.addAttribute("message", message);
        return "emailPage";
    }

    private String getMessage(ReceivedEmail email){
        List<EmailMessage> messages = email.messages();
        String s = "";
        for (EmailMessage msg : messages) {
            if (msg.getMimeType().equals("text/plain")) {
                System.out.println("------");
                System.out.println(msg.getEncoding());
                System.out.println(msg.getMimeType());
                 s = msg.getContent();
            }
        }
        return s;
    }

}
