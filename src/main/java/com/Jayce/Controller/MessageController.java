package com.Jayce.Controller;

import com.Jayce.producer.QueueSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestMapping
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private QueueSender queueSender;


    @RequestMapping(value = "/SendMessage", method = RequestMethod.POST)
    @ResponseBody
    public void send(HttpServletRequest request) {

        String msg = request.getParameter("msg");
        //producer.sendMessage(msg);
        long waitTime = 9000;
        for (int i = 0;i<100;i++) {
            queueSender.sendStringWait("mdb.queue.custAhrCommit",msg+",第几次："+i,waitTime);
        }
    }

}
