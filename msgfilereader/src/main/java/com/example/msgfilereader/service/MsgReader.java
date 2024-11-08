package com.example.msgfilereader.service;

import org.apache.poi.hsmf.MAPIMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

@Service
public class MsgReader {

    public void readMsg(MultipartFile file){
        try{

            InputStream inputStream = file.getInputStream();
            MAPIMessage message = new MAPIMessage(inputStream);
            String subject = message.getSubject();
            String from = message.getDisplayFrom();
            String to = message.getDisplayTo();
            String cc = message.getDisplayCC();
            String body = message.getTextBody();
            System.out.println(message.getSubject());


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
