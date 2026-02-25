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

    // In-memory storage (Demo purpose)
    private static Map<String, Map<String, Object>> orders = new ConcurrentHashMap<>();
    private static List<Map<String, Object>> products = new ArrayList<>();

    // Static product initialization
    static {
        addProduct("Money Plant", 299);
        addProduct("Snake Plant", 399);
        addProduct("Aloe Vera", 199);
        addProduct("Peace Lily", 499);
        addProduct("Bamboo Plant", 349);
    }

    private static void addProduct(String name, int price) {
        Map<String, Object> product = new HashMap<>();
        product.put("id", UUID.randomUUID().toString());
        product.put("name", name);
        product.put("price", price);
        products.add(product);
    }

    // =========================
    // LOGIN API
    // =========================
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {

        String name = body.getOrDefault("name", "");
        String phone = body.getOrDefault("phone", "");

        Map<String, Object> response = new HashMap<>();
        response.put("name", name);
        response.put("phone", phone);

        // Admin Condition
        if (phone.equals("9999999999")) {
            response.put("role", "ADMIN");
        } else {
            response.put("role", "USER");
        }

        return response;
    }

    // =========================
    // GET PRODUCTS
    // =========================
    @GetMapping("/products")
    public List<Map<String, Object>> getProducts() {
        return products;
    }

    // =========================
    // PLACE ORDER
    // =========================
    @PostMapping("/order")
    public Map<String, Object> placeOrder(@RequestBody Map<String, Object> payload) {

        String orderId = "GREENISH-" + System.currentTimeMillis();

        payload.put("orderId", orderId);
        payload.put("status", "PLACED");

        orders.put(orderId, payload);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", orderId);
        response.put("message", "Order Placed Successfully");

        return response;
    }

    // =========================
    // ADMIN VIEW ALL ORDERS
    // =========================
    @GetMapping("/orders")
    public Collection<Map<String, Object>> getAllOrders() {
        return orders.values();
    }

    // =========================
    // UPDATE ORDER STATUS (ADMIN)
    // =========================
    @PostMapping("/update")
    public Map<String, String> updateStatus(@RequestBody Map<String, String> body) {

        String orderId = body.get("orderId");
        String status = body.get("status");

        if (orders.containsKey(orderId)) {
            orders.get(orderId).put("status", status);
            return Map.of("message", "Status Updated");
        }

        return Map.of("message", "Order Not Found");
    }

    // =========================
    // TRACK ORDER (USER)
    // =========================
    @GetMapping("/track/{id}")
    public Map<String, Object> trackOrder(@PathVariable String id) {

        if (orders.containsKey(id)) {
            return orders.get(id);
        }

        return Map.of("error", "Order Not Found");
    }
}
