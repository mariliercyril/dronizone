package services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WarehouseController {

    @GetMapping("/connected")
    public String connected() {
        return "Connected !";
    }

    @GetMapping("/greeting")
    public String getOrders() {

        return "Order 1 ; Order 2";
    }
}
