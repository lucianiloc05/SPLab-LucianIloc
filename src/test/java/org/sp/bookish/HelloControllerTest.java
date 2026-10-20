package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.controllers.HelloController;
import org.sp.bookish.difexample.SingletonComponent;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HelloControllerTest {

    @Test
    void testHelloEndpointDirect() {
        SingletonComponent singleton = new SingletonComponent();
        HelloController controller = new HelloController(singleton);

        String response = controller.hello();
        assertNotNull(response);
        assertTrue(response.contains("Hello from "));
        assertTrue(response.contains("SingletonComponent"));
    }

    @Test
    void testHelloEndpointViaMockMvc() throws Exception {
        SingletonComponent singleton = new SingletonComponent();
        HelloController controller = new HelloController(singleton);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Hello from ")));
    }
}
