package com.greenish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@RestController
@RequestMapping("/api")
@CrossOrigin
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static Map<String, Map<String, Object>> orders = new ConcurrentHashMap<>();
    private static List<Map<String, Object>> products = new ArrayList<>();

    static {
        addProduct("Money Plant", 299);
        addProduct("Snake Plant", 399);
        addProduct("Aloe Vera", 199);
        addProduct("Peace Lily", 499);
        addProduct("Bamboo Plant", 349);
    }

    private static void addProduct(String name, int price) {
        Map<String, Object> p = new HashMap<>();
        p.put("id", UUID.randomUUID().toString());
        p.put("name", name);
        p.put("price", price);
        products.add(p);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {

        String name = body.getOrDefault("name", "");
        String phone = body.getOrDefault("phone", "");

        Map<String, Object> res = new HashMap<>();
        res.put("name", name);
        res.put("phone", phone);

        if (phone.equals("9999999999")) {
            res.put("role", "ADMIN");
        } else {
            res.put("role", "USER");
        }

        return res;
    }

    @GetMapping("/products")
    public List<Map<String, Object>> getProducts() {
        return products;
    }

    @PostMapping("/order")
    public Map<String, Object> placeOrder(@RequestBody Map<String, Object> payload) {

        String orderId = "GREENISH-" + System.currentTimeMillis();
        payload.put("orderId", orderId);
        payload.put("status", "PLACED");

        orders.put(orderId, payload);

        return Map.of(
                "orderId", orderId,
                "message", "Order Successful"
        );
    }

    @GetMapping("/orders")
    public Collection<Map<String, Object>> getOrders() {
        return orders.values();
    }

    @PostMapping("/update")
    public Map<String, String> update(@RequestBody Map<String, String> body) {

        if (orders.containsKey(body.get("orderId"))) {
            orders.get(body.get("orderId")).put("status", body.get("status"));
            return Map.of("message", "Updated");
        }

        return Map.of("message", "Not Found");
    }

    @GetMapping("/track/{id}")
    public Map<String, Object> track(@PathVariable String id) {
        return orders.getOrDefault(id, Map.of("error", "Not Found"));
    }
}
