package com.example.workflow;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.User;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component

public class createUser {


    private IdentityService identityService;

    @Autowired
    public createUser(ProcessEngine processEngine) {
        this.identityService = processEngine.getIdentityService();
    }

    @PostConstruct
    public void userCreate(){
        System.out.println("Users");
        for(int i = 1; i<=5 ; i++){
            String userId = "UserName" + i;
            User existing = identityService.createUserQuery().userId(userId).singleResult();
            if (existing == null){
                User user = identityService.newUser(userId);
                user.setFirstName("John");
                user.setLastName("Doe" + i);
                user.setEmail("John.Doe" + i + "@example.com");
                user.setPassword("password123");
                identityService.saveUser(user);
            }
        }
    }
}
