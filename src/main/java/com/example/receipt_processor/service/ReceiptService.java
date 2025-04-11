
package com.example.receipt_processor.service;

import com.example.receipt_processor.model.Item;
import com.example.receipt_processor.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReceiptService {
    private final ConcurrentHashMap<String, Integer> store = new ConcurrentHashMap<>();

    public String process(Receipt receipt) {
        int points = calculatePoints(receipt);
        String id = UUID.randomUUID().toString();
        store.put(id, points);
        return id;
    }

    public int getPoints(String id) {
        return store.getOrDefault(id, 0);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        BigDecimal total = new BigDecimal(receipt.getTotal());
        if (total.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            points += 50;
        }

        if (total.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }

        List<Item> items = receipt.getItems();
        points += (items.size() / 2) * 5;

        for (Item item : items) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                BigDecimal itemPoints = price.multiply(new BigDecimal("0.2")).setScale(0, RoundingMode.UP);
                points += itemPoints.intValue();
            }
        }

        int day = LocalDate.parse(receipt.getPurchaseDate()).getDayOfMonth();
        if (day % 2 == 1) {
            points += 6;
        }

        LocalTime time = LocalTime.parse(receipt.getPurchaseTime());
        if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}