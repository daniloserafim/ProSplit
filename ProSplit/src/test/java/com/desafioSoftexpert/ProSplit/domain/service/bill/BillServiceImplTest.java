package com.desafioSoftexpert.ProSplit.domain.service.bill;

import com.desafioSoftexpert.ProSplit.domain.Addition;
import com.desafioSoftexpert.ProSplit.domain.Bill;
import com.desafioSoftexpert.ProSplit.domain.Discount;
import com.desafioSoftexpert.ProSplit.domain.FriendBill;
import com.desafioSoftexpert.ProSplit.domain.exception.EmptyListException;
import com.desafioSoftexpert.ProSplit.domain.exception.InvalidValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BillServiceImplTest {

    @InjectMocks
    private BillServiceImpl billServiceImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculateCorrectlyWhenBillHasNormalValues() {
        FriendBill friendBill01 = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        FriendBill friendBill02 = new FriendBill("Friend02", Arrays.asList(15.5f));
        Addition addition01 = new Addition("Delivery fee", 10.5f, false);
        Addition addition02 = new Addition("App fine", 10f, true);
        Discount discount01 = new Discount("Store coupon", 4.99f, false);
        Discount discount02 = new Discount("App coupon", 20f, true);
        List<FriendBill> friendBills = Arrays.asList(friendBill01, friendBill02);
        List<Addition> additions = Arrays.asList(addition01, addition02);
        List<Discount> discounts = Arrays.asList(discount01, discount02);
        Bill bill = new Bill(friendBills, additions, discounts);

        List<FriendBill> friendBillsCalculated = billServiceImpl.calculateBill(bill);

        Assertions.assertEquals(30f, friendBillsCalculated.get(0).getTotalExpend());
        Assertions.assertEquals(9.29f, friendBillsCalculated.get(0).getTotalDiscount());
        Assertions.assertEquals(9.92f, friendBillsCalculated.get(0).getTotalAddition());
        Assertions.assertEquals(30.63f, friendBillsCalculated.get(0).getTotalPayment());

        Assertions.assertEquals(15.5f, friendBillsCalculated.get(1).getTotalExpend());
        Assertions.assertEquals(4.8f, friendBillsCalculated.get(1).getTotalDiscount());
        Assertions.assertEquals(5.13f, friendBillsCalculated.get(1).getTotalAddition());
        Assertions.assertEquals(15.83f, friendBillsCalculated.get(1).getTotalPayment());
    }

    @Test
    void shouldCalculateCorrectlyWhenBillHasNoAdditions() {
        FriendBill friendBill01 = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        FriendBill friendBill02 = new FriendBill("Friend02", Arrays.asList(15.5f));
        Discount discount01 = new Discount("Store coupon", 4.99f, false);
        Discount discount02 = new Discount("App coupon", 20f, true);
        List<FriendBill> friendBills = Arrays.asList(friendBill01, friendBill02);
        List<Addition> additions = new ArrayList<>();
        List<Discount> discounts = Arrays.asList(discount01, discount02);
        Bill bill = new Bill(friendBills, additions, discounts);

        List<FriendBill> friendBillsCalculated = billServiceImpl.calculateBill(bill);

        Assertions.assertEquals(30f, friendBillsCalculated.get(0).getTotalExpend());
        Assertions.assertEquals(9.29f, friendBillsCalculated.get(0).getTotalDiscount());
        Assertions.assertEquals(0f, friendBillsCalculated.get(0).getTotalAddition());
        Assertions.assertEquals(20.71f, friendBillsCalculated.get(0).getTotalPayment());

        Assertions.assertEquals(15.5f, friendBillsCalculated.get(1).getTotalExpend());
        Assertions.assertEquals(4.8f, friendBillsCalculated.get(1).getTotalDiscount());
        Assertions.assertEquals(0f, friendBillsCalculated.get(1).getTotalAddition());
        Assertions.assertEquals(10.7f, friendBillsCalculated.get(1).getTotalPayment());
    }

    @Test
    void shouldCalculateCorrectlyWhenBillHasNoDiscounts() {
        FriendBill friendBill01 = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        FriendBill friendBill02 = new FriendBill("Friend02", Arrays.asList(15.5f));
        Addition addition01 = new Addition("Delivery fee", 10.5f, false);
        Addition addition02 = new Addition("App fine", 10f, true);
        List<FriendBill> friendBills = Arrays.asList(friendBill01, friendBill02);
        List<Addition> additions = Arrays.asList(addition01, addition02);
        List<Discount> discounts = new ArrayList<>();
        Bill bill = new Bill(friendBills, additions, discounts);

        List<FriendBill> friendBillsCalculated = billServiceImpl.calculateBill(bill);

        Assertions.assertEquals(30f, friendBillsCalculated.get(0).getTotalExpend());
        Assertions.assertEquals(0f, friendBillsCalculated.get(0).getTotalDiscount());
        Assertions.assertEquals(9.92f, friendBillsCalculated.get(0).getTotalAddition());
        Assertions.assertEquals(39.92f, friendBillsCalculated.get(0).getTotalPayment());

        Assertions.assertEquals(15.5f, friendBillsCalculated.get(1).getTotalExpend());
        Assertions.assertEquals(0f, friendBillsCalculated.get(1).getTotalDiscount());
        Assertions.assertEquals(5.13f, friendBillsCalculated.get(1).getTotalAddition());
        Assertions.assertEquals(20.63f, friendBillsCalculated.get(1).getTotalPayment());
    }

    @Test
    void shouldCalculateCorrectlyWhenFriendBillHasNoFriendName() {
        FriendBill friendBill01 = new FriendBill(null, Arrays.asList(10f, 20f));
        FriendBill friendBill02 = new FriendBill("Friend02", Arrays.asList(15.5f));
        Addition addition01 = new Addition("Delivery fee", 10.5f, false);
        Addition addition02 = new Addition("App fine", 10f, true);
        Discount discount01 = new Discount("Store coupon", 4.99f, false);
        Discount discount02 = new Discount("App coupon", 20f, true);
        List<FriendBill> friendBills = Arrays.asList(friendBill01, friendBill02);
        List<Addition> additions = Arrays.asList(addition01, addition02);
        List<Discount> discounts = Arrays.asList(discount01, discount02);
        Bill bill = new Bill(friendBills, additions, discounts);

        List<FriendBill> friendBillsCalculated = billServiceImpl.calculateBill(bill);

        Assertions.assertEquals(30f, friendBillsCalculated.get(0).getTotalExpend());
        Assertions.assertEquals(9.29f, friendBillsCalculated.get(0).getTotalDiscount());
        Assertions.assertEquals(9.92f, friendBillsCalculated.get(0).getTotalAddition());
        Assertions.assertEquals(30.63f, friendBillsCalculated.get(0).getTotalPayment());

        Assertions.assertEquals(15.5f, friendBillsCalculated.get(1).getTotalExpend());
        Assertions.assertEquals(4.8f, friendBillsCalculated.get(1).getTotalDiscount());
        Assertions.assertEquals(5.13f, friendBillsCalculated.get(1).getTotalAddition());
        Assertions.assertEquals(15.83f, friendBillsCalculated.get(1).getTotalPayment());
    }

    @Test
    void shouldReturnTotalPaymentSetToZeroWhenTotalDiscountIsGreaterThanTotalPayment() {
        FriendBill friendBill01 = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        FriendBill friendBill02 = new FriendBill("Friend02", Arrays.asList(15.5f));
        Addition addition01 = new Addition("Delivery fee", 10.5f, false);
        Addition addition02 = new Addition("App fine", 10f, true);
        Discount discount01 = new Discount("Huge coupon", 100f, false);
        List<FriendBill> friendBills = Arrays.asList(friendBill01, friendBill02);
        List<Addition> additions = Arrays.asList(addition01, addition02);
        List<Discount> discounts = Arrays.asList(discount01);
        Bill bill = new Bill(friendBills, additions, discounts);

        List<FriendBill> friendBillsCalculated = billServiceImpl.calculateBill(bill);

        Assertions.assertEquals(30f, friendBillsCalculated.get(0).getTotalExpend());
        Assertions.assertEquals(65.93f, friendBillsCalculated.get(0).getTotalDiscount());
        Assertions.assertEquals(9.92f, friendBillsCalculated.get(0).getTotalAddition());
        Assertions.assertEquals(0f, friendBillsCalculated.get(0).getTotalPayment());

        Assertions.assertEquals(15.5f, friendBillsCalculated.get(1).getTotalExpend());
        Assertions.assertEquals(34.07f, friendBillsCalculated.get(1).getTotalDiscount());
        Assertions.assertEquals(5.13f, friendBillsCalculated.get(1).getTotalAddition());
        Assertions.assertEquals(0f, friendBillsCalculated.get(1).getTotalPayment());
    }

    @Test
    void shouldThrowExceptionWhenBillHasNoFriendBills() {
        Bill bill = new Bill(null, null, null);

        Exception thrown = Assertions.assertThrows(EmptyListException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        Assertions.assertEquals("Bill must have at least one FriendBill.", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNoFriendBillsHasIncomingExpenses() {
        FriendBill friendBill01 = new FriendBill("Friend01", new ArrayList<>());
        FriendBill friendBill02 = new FriendBill("Friend02", new ArrayList<>());
        Addition addition01 = new Addition("Delivery fee", 10.5f, false);
        Addition addition02 = new Addition("App fine", 10f, true);
        Discount discount01 = new Discount("Store coupon", 4.99f, false);
        Discount discount02 = new Discount("App coupon", 20f, true);
        List<Addition> additions = Arrays.asList(addition01, addition02);
        List<Discount> discounts = Arrays.asList(discount01, discount02);
        Bill bill = new Bill(Arrays.asList(friendBill01, friendBill02), additions, discounts);

        Exception exception = assertThrows(EmptyListException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Each FriendBill must have at least one valid incoming expense.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAnIncomingExpenseIsNegative() {
        FriendBill friendBill01 = new FriendBill("Friend01", Arrays.asList(-10f, 20f));
        Addition addition01 = new Addition("Delivery fee", 10.5f, false);
        Addition addition02 = new Addition("App fine", 10f, true);
        Discount discount01 = new Discount("Huge coupon", 100f, false);
        List<Addition> additions = Arrays.asList(addition01, addition02);
        List<Discount> discounts = Arrays.asList(discount01);
        Bill bill = new Bill(Arrays.asList(friendBill01), additions, discounts);

        Exception exception = assertThrows(InvalidValueException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Incoming expenses must be positive int values.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenADiscountIsNegative() {
        FriendBill friendBill01 = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        Discount discount01 = new Discount("Negative discount", -10f, false);
        Bill bill = new Bill(Arrays.asList(friendBill01), null, Arrays.asList(discount01));

        Exception exception = assertThrows(InvalidValueException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Bill Discounts must have positive int values.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAnAdditionIsNegative() {
        FriendBill friendBill01 = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        Addition addition01 = new Addition("Negative addition", -10f, false);
        Bill bill = new Bill(Arrays.asList(friendBill01), Arrays.asList(addition01), new ArrayList<>());

        Exception exception = assertThrows(InvalidValueException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Bill Additions must have positive int values.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFriendBillsIsNull() {
        Bill bill = new Bill(null, new ArrayList<>(), new ArrayList<>());

        Exception exception = assertThrows(EmptyListException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Bill must have at least one FriendBill.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDiscountsIsNull() {
        FriendBill friendBill = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        Bill bill = new Bill(Arrays.asList(friendBill), new ArrayList<>(), null);

        Exception exception = assertThrows(InvalidValueException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Bill Discounts must have positive int values.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAdditionsIsNull() {
        FriendBill friendBill = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        Bill bill = new Bill(Arrays.asList(friendBill), null, new ArrayList<>());

        Exception exception = assertThrows(InvalidValueException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Bill Additions must have positive int values.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAnIncomingExpenseIsNull() {
        FriendBill friendBill = new FriendBill("Friend01", Arrays.asList(null, 20f));
        Bill bill = new Bill(Arrays.asList(friendBill), new ArrayList<>(), new ArrayList<>());

        Exception exception = assertThrows(InvalidValueException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Incoming expenses must be positive int values.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAdjustmentIsNull() {
        FriendBill friendBill = new FriendBill("Friend01", Arrays.asList(10f, 20f));
        List<Addition> additions = new ArrayList<>();
        additions.add(null);
        Bill bill = new Bill(Arrays.asList(friendBill), additions, new ArrayList<>());

        Exception exception = assertThrows(InvalidValueException.class, () -> {
            billServiceImpl.calculateBill(bill);
        });

        assertEquals("Bill Additions must have positive int values.", exception.getMessage());
    }
}