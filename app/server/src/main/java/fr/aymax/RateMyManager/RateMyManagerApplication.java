package fr.aymax.RateMyManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RateMyManagerApplication 
{	
	public final static Logger logger = LoggerFactory.getLogger(RateMyManagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RateMyManagerApplication.class, args);
	}
}
