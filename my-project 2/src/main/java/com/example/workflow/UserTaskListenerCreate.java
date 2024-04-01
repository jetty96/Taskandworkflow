package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;


import java.util.Random;
@Component
public class UserTaskListenerCreate implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Task creation and User Assigning");
        String assignedUser = assignUser();
        //execution.setVariable("assignee", assignedUser);
        delegateTask.setAssignee(assignedUser);
        String taskName = delegateTask.getName();
        String taskId = delegateTask.getId();
        System.out.println("Task " + taskId + " assigned to: " + assignedUser);
        System.out.println("Task Name: " + taskName);
    }
    public String assignUser(){
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;
        return "UserName" + String.valueOf(randomNumber);
    }
}
