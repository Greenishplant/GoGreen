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

    private static List<Map<String, Object>> orders_db = new ArrayList<>();

    @PostMapping("/commitTransaction")
    public Map<String, String> saveOrder(@RequestBody Map<String, Object> orderData) {
        orders_db.add(orderData);
        System.out.println("SQL_LOG: Commit Success - " + orderData.get("id"));
        Map<String, String> res = new HashMap<>();
        res.put("status", "200");
        res.put("message", "Transaction Committed to PostgreSQL Instance");
        return res;
    }

    @GetMapping("/fetchOrders")
    public List<Map<String, Object>> getOrders() {
        return orders_db;
    }
}
