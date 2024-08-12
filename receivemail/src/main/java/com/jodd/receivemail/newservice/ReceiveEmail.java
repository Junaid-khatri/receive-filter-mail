package com.jodd.receivemail.newservice;

import jodd.mail.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ReceiveEmail {

    public List<ReceivedEmail> receiveMail() throws Exception {

        Pop3Server popServer = MailServer.create()
                .host("outlook.office365.com")
                .ssl(true)
                .auth("narendrapallaki2607@outlook.com", "mkxedepjfneqlonu")
                .buildPop3MailServer();

        ReceiveMailSession session = popServer.createSession();
        session.open();
        ReceivedEmail[] emails = session.receiveEmail();

        if (emails != null) {
            for (ReceivedEmail email : emails) {
                System.out.println("\n\n===[" + email.messageNumber() + "]===");

                // common info
                System.out.println("FROM:" + email.from());
                System.out.print("TO: ");
                Arrays.stream(email.to()).forEach(i -> System.out.print(i.getEmail() + " "));
                System.out.println();
                System.out.println("SUBJECT:" + email.subject());
                System.out.println("PRIORITY:" + email.priority());
                System.out.println("SENT DATE:" + email.sentDate());
                System.out.println("RECEIVED DATE: " + email.receivedDate());
                System.out.print("CC: ");
                Arrays.stream(email.cc()).forEach(i -> System.out.print(i.getEmail() + " "));
                System.out.println();


                //process messages
                List<EmailMessage> messages = email.messages();
                for (EmailMessage msg : messages) {
                    if (msg.getMimeType().equals("text/html")) {
                        System.out.println("------");
                        System.out.println(msg.getEncoding());
                        System.out.println(msg.getMimeType());
                        System.out.println(msg.getContent());
                    }
                }

                // process attachments
                List<EmailAttachment> attachments = (List) email.attachments();
                if (attachments != null) {
                    System.out.println("+++++");
                    for (EmailAttachment attachment : attachments) {
                        System.out.println("name: " + attachment.getName());
                        System.out.println("cid: " + attachment.getContentId());
                        System.out.println("size: " + attachment.getSize());
                        String filePath = "C:\\Users\\Sreenivas Bandaru\\Desktop\\springsecurity\\email-attachments\\" + email.from().getPersonalName();
                        if (!(new File(filePath).exists())) {
                            new File(filePath).mkdir();
                        }
                        attachment.writeToFile(
                                new File("C:\\Users\\Sreenivas Bandaru\\Desktop\\springsecurity\\email-attachments\\" + email.from().getPersonalName(), attachment.getName()));
                    }
                }
            }
        }

        List<ReceivedEmail> filteredMail = filter(emails);

        session.close();
        return filteredMail;

    }

    public static List<ReceivedEmail> filter(ReceivedEmail[] emails) {
        String caseRef = "\\b\\d{1,}[A-Z,a-z]{1,}\\b";
        Pattern pattern = Pattern.compile(caseRef);
        List<ReceivedEmail> filteredList = new ArrayList<>();

        for (ReceivedEmail email : emails) {
            if (pattern.matcher(email.subject()).find()) {
                filteredList.add(email);
            }
        }

        System.out.println();
        System.out.println("==================Filtered Emails===============");
        System.out.println(filteredList.size());

        return filteredList;
    }

    public static void main(String[] args) throws Exception {
//        String regex = "\\b\\d{1,}[A-Z]{1,}\\b";
//        Pattern pattern = Pattern.compile(regex);
//        String s = "details of this trancastion/11242FASD/1235 USD/hello guys";
//        System.out.println(pattern.matcher(s).find());

    }
}

