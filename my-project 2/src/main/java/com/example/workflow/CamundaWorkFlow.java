package com.example.workflow;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("createUser")
public class CamundaWorkFlow {

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final IdentityService identityService;

    @Autowired
    public CamundaWorkFlow(ProcessEngine processEngine){
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
        this.identityService = processEngine.getIdentityService();
        this.repositoryService = processEngine.getRepositoryService();
    }

    @PostConstruct
    public void startWorkFlowTask(){
        System.out.println("WorkFlowStarted");
        String ProcessId = startWorkFlowTaskInstance();
        System.out.println(ProcessId);
        //ClaimUserTask(ProcessId);
    }
    private String startWorkFlowTaskInstance(){

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("diagram_1.bpmn").addClasspathResource("static/form_1.form").deploy();
        String processDefinitionKey = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult().getKey();
        String processId = runtimeService.startProcessInstanceByKey(processDefinitionKey).getId();
        System.out.println(processDefinitionKey);
        return processId;
    }

    private void ClaimUserTask(String processInstanceId){
        Task userTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

        if(userTask != null){
            String userId = "UserName1";
            User existing = identityService.createUserQuery().userId(userId).singleResult();
            assignTaskToUser(processInstanceId,userTask.getId(),existing.getId());
        }
        else{
            System.out.println("No user task");
        }
    }
    public void assignTaskToUser(String processInstanceId, String taskId, String assigneeUserId){
        System.out.println(taskId + " Assigned to "+ assigneeUserId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        task.setAssignee(assigneeUserId);
        taskService.saveTask(task);
    }
}
