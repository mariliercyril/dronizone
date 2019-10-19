package com.scp.dronizone.fleet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fleet")
public class FleetController {

    @PostMapping("/assign")
    public String assignNewOrder(Integer idOrder){
        String message = "OK";
        return message;
    }
}
