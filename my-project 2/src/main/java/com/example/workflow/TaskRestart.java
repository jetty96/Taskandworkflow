package com.example.workflow;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.camunda.bpm.engine.ProcessEngine;
import java.util.Random;
@Component
public class TaskRestart implements TaskListener {
    private RuntimeService runtimeService;
    private RepositoryService repositoryService;

    @Autowired
    public TaskRestart(ProcessEngine processEngine) {
        this.runtimeService = processEngine.getRuntimeService();
        this.repositoryService = processEngine.getRepositoryService();
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        Task currentTask = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        Boolean variableValue = (Boolean) processEngine.getRuntimeService()
                .getVariable(currentTask.getExecutionId(), "variableName");
        if (variableValue == null) {
            processEngine.getRuntimeService()
                    .setVariable(currentTask.getExecutionId(), "variableName", true);
            System.out.println("Variable 'variableName' set to true.");
            runtimeService.createProcessInstanceModification(processInstanceId)
                    .startBeforeActivity(currentTask.getTaskDefinitionKey())
                    .execute();

            System.out.println("Task restarted successfully.");
        }





    }

}
