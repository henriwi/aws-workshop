package no.bekk.aws;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan
public class App {

    public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
