package com.app.blogging.controller;

import com.app.blogging.request.PromptBody;
import com.app.blogging.response.ApiResponse;
import com.app.blogging.service.ChatBotService;
import com.app.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chats")
public class ChatBotController {

    @Autowired
    private  ChatBotService chatBotService;

    @Autowired
    private UserService userSerivce;

    @PostMapping("{blog_id}/bot")
    public ResponseEntity<String> simpleChat(@PathVariable Long blog_id){

        String res = chatBotService.simpleChat(blog_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
