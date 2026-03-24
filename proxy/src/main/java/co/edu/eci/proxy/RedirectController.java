package co.edu.eci.proxy;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class RedirectController {

    private final AtomicLong counter = new AtomicLong();
    private static final String USER_AGENT = "Mozilla/5.0";
    


    @GetMapping("/redirectmathservice")
    public String redirectMathService(@RequestParam(value = "number") Integer number) {
        try {
            System.out.println("Entro a redirect");
            return selectMathService(number);
        } catch (IOException e) {
            System.out.println("Fallo en redirect");
            throw new Error(e.getMessage());
        }
        
    }

    public String selectMathService (Integer number) throws IOException {
        System.out.println("Entro ");
        String GET_URL = "";

        if (counter.get() % 2 == 0) {
            GET_URL = "http://13.223.0.134:8081/collatz/" + number;
            counter.incrementAndGet();
        } else {
            GET_URL = "http://100.48.98.138:8082/collatz/" + number;
            counter.incrementAndGet();
        }

        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "GET request not worked";
        }
    }
}