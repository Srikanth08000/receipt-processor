package com.example.receipt_processor.controller;

import com.example.receipt_processor.model.Receipt;
import com.example.receipt_processor.service.ReceiptService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptService.process(receipt);
        return Map.of("id", id);
    }

    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        return Map.of("points", receiptService.getPoints(id));
    }
}