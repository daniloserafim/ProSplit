package com.desafioSoftexpert.ProSplit.application.rest.bill;

import com.desafioSoftexpert.ProSplit.application.rest.utils.ErrorResponse;
import com.desafioSoftexpert.ProSplit.domain.service.bill.IBillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    private final IBillService billService;

    public BillController(IBillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<?> calculateBill(@RequestBody @Valid BillRequest billRequest) {
        try {
            return ResponseEntity.ok(billService.calculateBill(billRequest.getBill().toBill()));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Calculation error", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}
