package com.greenish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static Map<String, Map<String, Object>> users = new ConcurrentHashMap<>();
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
        p.put("stock", 50);
        products.add(p);
    }

    // OTP Login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String name = body.get("name");

        Map<String, Object> user = new HashMap<>();
        user.put("phone", phone);
        user.put("name", name);
        user.put("role", phone.equals("9999999999") ? "ADMIN" : "USER");

        users.put(phone, user);

        return user;
    }

    @GetMapping("/products")
    public List<Map<String, Object>> getProducts() {
        return products;
    }

    @PostMapping("/order")
    public Map<String, Object> placeOrder(@RequestBody Map<String, Object> payload) {
        String id = "GREENISH-" + System.currentTimeMillis();
        payload.put("orderId", id);
        payload.put("status", "PLACED");
        orders.put(id, payload);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Order Successful");
        res.put("orderId", id);
        return res;
    }

    @GetMapping("/orders")
    public Collection<Map<String, Object>> getAllOrders() {
        return orders.values();
    }

    @GetMapping("/track/{id}")
    public Map<String, Object> track(@PathVariable String id) {
        return orders.getOrDefault(id, Map.of("error", "Order Not Found"));
    }

    @PostMapping("/admin/update-status")
    public Map<String, String> updateStatus(@RequestBody Map<String, String> body) {
        String id = body.get("orderId");
        String status = body.get("status");

        if (orders.containsKey(id)) {
            orders.get(id).put("status", status);
            return Map.of("message", "Updated");
        }
        return Map.of("message", "Order Not Found");
    }
}
