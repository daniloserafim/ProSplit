package com.desafioSoftexpert.ProSplit.domain.service.bill;

import com.desafioSoftexpert.ProSplit.domain.Bill;
import com.desafioSoftexpert.ProSplit.domain.FriendBill;
import java.util.List;

public interface IBillService {
    List<FriendBill> calculateBill(Bill bill);
}
