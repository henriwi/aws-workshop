package no.bekk.aws;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

@Controller
public class PingController {

    @RequestMapping("/ping")
    @ResponseBody
    String home() throws Exception {
        URLConnection connection = new URL("http://169.254.169.254/latest/meta-data/hostname").openConnection();

        try (InputStream inputStream = connection.getInputStream()) {
            String hostname = read(inputStream);
            return "Hello world from " + hostname;
        }
    }

    public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
