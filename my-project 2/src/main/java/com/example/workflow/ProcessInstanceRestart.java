package com.example.workflow;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessInstanceRestart implements JavaDelegate {


    private RuntimeService runtimeService;
    private RepositoryService repositoryService;

    @Autowired
    public ProcessInstanceRestart(ProcessEngine processEngine) {
        this.runtimeService = processEngine.getRuntimeService();
        this.repositoryService = processEngine.getRepositoryService();
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        String processDefinitionKey = ((ProcessDefinitionEntity) ((ExecutionEntity) execution).getProcessDefinition()).getKey();
        System.out.println(processDefinitionKey);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
        String processDefinitionId = processDefinition.getId();
        runtimeService.deleteProcessInstance(processInstanceId, "Deleted for restart");
        runtimeService.startProcessInstanceById(processDefinitionId);
    }
}
