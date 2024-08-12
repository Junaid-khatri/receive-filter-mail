//package com.jodd.receivemail.service;
//
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMultipart;
//import org.eclipse.angus.mail.pop3.POP3Store;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.*;
//
//
////import java.io.BufferedReader;
////import java.io.IOException;
////import java.io.InputStreamReader;
////import java.io.UnsupportedEncodingException;
////import java.util.*;
////import javax.mail.Folder;
////import javax.mail.Message;
////import javax.mail.MessagingException;
////import javax.mail.NoSuchProviderException;
////import javax.mail.Session;
//
//
//
//@Service
//class ReceiveMail {
//
//    public static void receiveEmail(String pop3Host, String storeType,
//                                    String user, String password) throws Exception {
//        try {
//            //1) get the session object
//            Properties properties = new Properties();
//            properties.put("mail.pop3.host", pop3Host);
//            properties.put("mail.pop3.port", "995");
//            properties.put("mail.pop3.ssl.enable", "true"); // Enable SSL
//            properties.put("mail.pop3.auth", "true");
//            Session emailSession = Session.getDefaultInstance(properties);
//
//            //2) create the POP3 store object and connect with the pop server
//            POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);
//            emailStore.connect(user, password);
//
//            //3) create the folder object and open it
//            Folder emailFolder = emailStore.getFolder("INBOX");
//            emailFolder.open(Folder.READ_ONLY);
//
//            //4) retrieve the messages from the folder in an array and print it
//            Message[] messages = emailFolder.getMessages();
//            filterMail(messages);
//
//            //5) close the store and folder objects
//            emailFolder.close(false);
//            emailStore.close();
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void filterMail(Message[] messages) throws Exception {
//        Mail mail;
//        int priority ;
//
//        String transactionRegex = "^[A-Z]\\d{22}$";
//        List<String> keywords = List.of("account","account number", "payment", "transaction reference number");
//        List<Mail> filteredMail = new ArrayList<>();
//
//        for(Message message: messages){
//            priority = 4;
//            if(message.getSubject().matches(transactionRegex)){
//                priority--;
//            }
//            if(keywords.stream().anyMatch(getTextFromMessage(message)::contains)){
//                priority--;
//            }
//            if(Arrays.stream(message.getAllRecipients()).anyMatch(address -> {
//                try {
//                    return address.equals(new InternetAddress("test@outlook.com","test person"));
//                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
//                }
//            })){
//                priority--;
//            }
//            if(priority<4){
//                filteredMail.add(Mail.builder()
//                                .to("narendrapallaki2607@outlook.com")
//                                .from(message.getFrom()[0].toString())
//                                .subject(message.getSubject())
//                                .body(getTextFromMessage(message))
//                                .priority(priority)
//                        .build());
//            }
//        }
//        Collections.sort(filteredMail);
//        System.out.println(filteredMail);
//    }
//
//
//    private static String getTextFromMessage(Message message) throws Exception {
//        Object content = message.getContent();
//        if (content instanceof String) {
//            return (String) content;
//        } else if (content instanceof MimeMultipart) {
//            MimeMultipart mimeMultipart = (MimeMultipart) content;
//            StringBuilder text = new StringBuilder();
//            for (int i = 0; i < mimeMultipart.getCount(); i++) {
//                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
//                if (bodyPart.getContentType().contains("text/plain")) {
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(bodyPart.getInputStream()));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        text.append(line).append("\n");
//                    }
//                }
//            }
//            return text.toString();
//        }
//        return "No content";
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        String host = "outlook.office365.com";
//        String mailStoreType = "pop3";
//        String username = "narendrapallaki2607@outlook.com";
//        String password = "mkxedepjfneqlonu";
//
//        receiveEmail(host, mailStoreType, username, password);
//
//    }
//}