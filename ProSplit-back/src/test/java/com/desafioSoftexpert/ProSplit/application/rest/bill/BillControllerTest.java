package com.desafioSoftexpert.ProSplit.application.rest.bill;

import com.desafioSoftexpert.ProSplit.application.rest.addition.AdditionDTO;
import com.desafioSoftexpert.ProSplit.application.rest.dicount.DiscountDTO;
import com.desafioSoftexpert.ProSplit.application.rest.friendBill.FriendBillDTO;
import com.desafioSoftexpert.ProSplit.domain.Bill;
import com.desafioSoftexpert.ProSplit.domain.FriendBill;
import com.desafioSoftexpert.ProSplit.domain.service.bill.IBillService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    IBillService billService;
    private Gson gson = new Gson();

    @Test
    void shouldCalculateBillSuccessfully() throws Exception {
        FriendBillDTO friendBillDTO1 = new FriendBillDTO("Friend01", Arrays.asList(10f, 20f));
        FriendBillDTO friendBillDTO2 = new FriendBillDTO("Friend02", Arrays.asList(15.5f));
        AdditionDTO additionDTO1 = new AdditionDTO("Delivery fee", 10.5f, false);
        AdditionDTO additionDTO2 = new AdditionDTO("App fine", 10f, true);
        DiscountDTO discountDTO1 = new DiscountDTO("Store coupon", 4.99f, false);
        DiscountDTO discountDTO2 = new DiscountDTO("App coupon", 20f, true);
        List<FriendBillDTO> friendBillsDTO = Arrays.asList(friendBillDTO1, friendBillDTO2);
        List<AdditionDTO> additionsDTO = Arrays.asList(additionDTO1, additionDTO2);
        List<DiscountDTO> discountsDTO = Arrays.asList(discountDTO1, discountDTO2);
        BillDTO billDTO = new BillDTO(friendBillsDTO, additionsDTO, discountsDTO);

        Bill bill = billDTO.toBill();
        List<FriendBill> friendBills = Arrays.asList(friendBillDTO1.toFriendBill(), friendBillDTO2.toFriendBill());
        BillRequest billRequest = new BillRequest(billDTO);
        String billRequestJson = gson.toJson(billRequest);

        when(billService.calculateBill(bill)).thenReturn(friendBills);

        mockMvc.perform(post("/api/bill")
                        .content(billRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenValidationFails() throws Exception {
        FriendBillDTO friendBillDTO1 = new FriendBillDTO("Friend01", Arrays.asList(10f, null));
        FriendBillDTO friendBillDTO2 = new FriendBillDTO("Friend02", Arrays.asList(15.5f));
        AdditionDTO additionDTO1 = new AdditionDTO("Delivery fee", 10.5f, false);
        AdditionDTO additionDTO2 = new AdditionDTO("App fine", 10f, true);
        DiscountDTO discountDTO1 = new DiscountDTO("Store coupon", 4.99f, false);
        DiscountDTO discountDTO2 = new DiscountDTO("App coupon", 20f, true);
        List<FriendBillDTO> friendBillsDTO = Arrays.asList(friendBillDTO1, friendBillDTO2);
        List<AdditionDTO> additionsDTO = Arrays.asList(additionDTO1, additionDTO2);
        List<DiscountDTO> discountsDTO = Arrays.asList(discountDTO1, discountDTO2);
        BillDTO billDTO = new BillDTO(friendBillsDTO, additionsDTO, discountsDTO);

        BillRequest billRequest = new BillRequest(billDTO);
        String billRequestJson = gson.toJson(billRequest);

        when(billService.calculateBill(billDTO.toBill())).thenThrow(new IllegalArgumentException("Expense cannot be null."));

        mockMvc.perform(post("/api/bill")
                        .content(billRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.details").value("bill.friendBills[0].incomingExpenses[1]: Expense cannot be null."));
    }

    @Test
    void shouldReturnInternalServerErrorOnException() throws Exception {
        String malformedJsonRequest = """
        {
          "bill": "error test"
        }
        """;

        mockMvc.perform(post("/api/bill")
                        .content(malformedJsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Internal server error"))
                .andExpect(jsonPath("$.details").value("An unexpected error occurred."));
    }
}
