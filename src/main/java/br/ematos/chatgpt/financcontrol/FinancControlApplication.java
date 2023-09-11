package br.ematos.chatgpt.financcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.ematos.chatgpt")
public class FinancControlApplication {
  public static void main(String[] args) {
    SpringApplication.run(FinancControlApplication.class, args);
  }
}
