package fr.aymax.managerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@ComponentScan(basePackages="fr.aymax.evalApp")
//@EnableJpaRepositories(basePackages = "fr.aymax.evalApp.dao")
@SpringBootApplication
public class ManagerServiceApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(ManagerServiceApplication.class, args);
	}
}
