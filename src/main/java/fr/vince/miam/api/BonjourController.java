package fr.vince.miam.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonjourController {
    @GetMapping("/bonjour")
    public String bonjour() {
        return "Bonjour depuis miam-api !";
    }

}