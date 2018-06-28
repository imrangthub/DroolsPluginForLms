package com.imran;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DroolsPluginForLmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroolsPluginForLmsApplication.class, args);
	}
	
   @Bean  
   public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
       return hemf.getSessionFactory();  
   }
}
