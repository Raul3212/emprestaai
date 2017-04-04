package br.ufc.npi.emprestaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan("br.ufc.quixada.npi.*")
@ComponentScan("br.ufc.npi.*")
public class EmprestaAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmprestaAiApplication.class, args);
	}
}
