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

    // SQL Simulation: In-memory List acting as 'orders' table
    private static List<Map<String, Object>> orders_table = new ArrayList<>();

    @PostMapping("/checkout")
    public Map<String, String> processOrder(@RequestBody Map<String, Object> orderData) {
        // SQL: INSERT INTO orders (txn_id, customer, address, total, status)
        orders_table.add(orderData);
        System.out.println("SQL EXEC: INSERT SUCCESS - Order ID: " + orderData.get("id"));
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "200");
        response.put("message", "Transaction Committed to PostgreSQL");
        return response;
    }

    @GetMapping("/admin/orders")
    public List<Map<String, Object>> getAllOrders() {
        // SQL: SELECT * FROM orders
        return orders_table;
    }
}
