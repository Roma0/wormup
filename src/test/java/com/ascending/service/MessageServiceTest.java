package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class MessageServiceTest {
    @Autowired
    public MessageService messageService;

    @Test
    public void sendMessage(){
        messageService.sendMessage();
    }
}
