package com.example.msgfilereader.controller;

import com.example.msgfilereader.service.MsgReader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MsgController {

    private final MsgReader msgReader;

    @GetMapping("/read")
    public void readMsg(@RequestParam MultipartFile file){
        msgReader.readMsg(file);
    }

}
