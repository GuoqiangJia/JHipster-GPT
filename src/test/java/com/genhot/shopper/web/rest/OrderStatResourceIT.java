package com.genhot.shopper.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.genhot.shopper.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the OrderStatResource REST controller.
 *
 * @see OrderStatResource
 */
@IntegrationTest
class OrderStatResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        OrderStatResource orderStatResource = new OrderStatResource();
        restMockMvc = MockMvcBuilders.standaloneSetup(orderStatResource).build();
    }

    /**
     * Test defaultAction
     */
    @Test
    void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/order-stat/default-action")).andExpect(status().isOk());
    }
}
