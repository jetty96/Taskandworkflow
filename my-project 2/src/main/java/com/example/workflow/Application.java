package com.example.workflow;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableProcessApplication
@Component
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  }

}