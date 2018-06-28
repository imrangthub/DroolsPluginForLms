package com.imran.common;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imran.service.DroolsService;

@Configuration
public class RuleEngineConfig {
	

	private final KieServices ks = KieServices.Factory.get();
	private final String RULE_SOURCE = "rules/discountTwo.xlsx";

	@Bean
	public KieFileSystem getKieFileSystem() {
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write(ResourceFactory.newClassPathResource(RULE_SOURCE));
		System.out.println(RULE_SOURCE);
		return kfs;
	}

	@Bean
	public KieContainer getKieContainer() {
		KieRepository kRep = ks.getRepository();
		kRep.addKieModule(() -> {
			return kRep.getDefaultReleaseId();
		});

		ks.newKieBuilder(getKieFileSystem()).buildAll();
		return ks.newKieContainer(kRep.getDefaultReleaseId());
	}

	@Bean
	public KieSession getKieSession() {
		KieSession kieSession = getKieContainer().newKieSession();
		return kieSession;
	}

}
