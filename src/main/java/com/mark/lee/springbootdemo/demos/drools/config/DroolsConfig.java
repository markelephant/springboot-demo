package com.mark.lee.springbootdemo.demos.drools.config;


import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    private final KieServices kieServices = KieServices.Factory.get();


    @Bean
    @ConditionalOnMissingBean
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        PathMatchingResourcePatternResolver resourcePatternResolver
                = new PathMatchingResourcePatternResolver();

        Resource[] files = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.*");

        String path = null;
        for (Resource file : files) {
            path = RULES_PATH + file.getFilename();
            kieFileSystem.write(ResourceFactory.newClassPathResource(path,"UTF-8"));
        }
        return kieFileSystem;

    }

    @Bean
    @ConditionalOnMissingBean
    public KieContainer kieContainer() throws IOException{

        KieRepository repository = kieServices.getRepository();
        repository.addKieModule(repository::getDefaultReleaseId);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return kieServices.newKieContainer(repository.getDefaultReleaseId());
    }


    @Bean
    @ConditionalOnMissingBean
    public KieBase kieBase() throws IOException{

        return kieContainer().getKieBase();
    }


    @Bean
    @ConditionalOnMissingBean
    public KModuleBeanFactoryPostProcessor kiePostProcessor(){
        return new KModuleBeanFactoryPostProcessor();
    }

}
