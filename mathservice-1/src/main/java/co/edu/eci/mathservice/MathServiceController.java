package co.edu.eci.mathservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathServiceController {
    
    @GetMapping("/collatz")
    public String calculateCollatz(@RequestParam(value = "number") Integer number) {
        System.out.println("Entroooo" + number);
        Integer bandera = number;
        String secuencia = "";
        while (bandera > 1){
            System.out.println(bandera);
            if (bandera % 2 == 0) {
                bandera /= 2;
            } else {
                bandera *= 3;
                bandera += 1;
            }

            secuencia += String.valueOf(bandera);
            secuencia += String.valueOf(bandera) != "1" ? "->" : "";
        }

        return "{" + "\"operation\": \"collatzsequence\"" + "," + "\"input\":" + number + "," +  "\"output\":"+ "\" " + secuencia + "\" " + "}";
    }
}
