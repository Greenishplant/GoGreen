package com.greenish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Live Orders Database
    private static List<Map<String, Object>> ordersDatabase = new ArrayList<>();

    @PostMapping("/commitTransaction")
    public Map<String, String> saveOrder(@RequestBody Map<String, Object> orderData) {
        ordersDatabase.add(orderData);
        System.out.println("SQL TRANSACTION SUCCESS: Order ID - " + orderData.get("id"));
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Order saved in Java Server!");
        return response;
    }

    @GetMapping("/fetchOrders")
    public List<Map<String, Object>> getOrders() {
        return ordersDatabase;
    }
}
