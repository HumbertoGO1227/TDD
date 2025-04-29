package com.humberto.versiculos.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import com.humberto.versiculos.GerenciadorVersiculosApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = GerenciadorVersiculosApplication.class)
public class CucumberSpringConfiguration {
}
