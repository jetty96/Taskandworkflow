package com.example.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanPrinter implements CommandLineRunner {

    private final ApplicationContext context;

    @Autowired
    public BeanPrinter(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }
}
