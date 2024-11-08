package com.example.msgfilereader.service;

import jakarta.mail.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
public class ReadEmail {

    public void receiveEmailUsingJakarta() throws MessagingException {
        final String HOST = "imap.gmail.com";
        String username = "";
        String password = "";

        // Set properties for connecting to the email server
        Properties props = new Properties();
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", HOST);
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        try {
            // Connect to the email server
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imap");
            store.connect(HOST, username, password);

            // Open the inbox folder in read/write mode
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.getMessages();

            System.out.println("Total messages: " + messages.length);

            for (Message message : messages) {
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Sent Date: " + message.getSentDate());
                System.out.println("Body: " + getTextFromMessage(message));
                System.out.println("=================================");
            }

            // Close the folder and store
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException, IOException {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        }
        return "";
    }

//    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
//        StringBuilder result = new StringBuilder();
//        int count = mimeMultipart.getCount();
//        for (int i = 0; i < count; i++) {
//            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
//            if (bodyPart.isMimeType("text/plain")) {
//                result.append(bodyPart.getContent());
//            } else if (bodyPart.isMimeType("text/html")) {
//                result.append(org.jsoup.Jsoup.parse(bodyPart.getContent().toString()).text());
//            }
//        }
//        return result.toString();
//    }
}


