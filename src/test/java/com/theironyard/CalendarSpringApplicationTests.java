package com.theironyard;

import com.theironyard.services.EventRepository;
import com.theironyard.services.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CalendarSpringApplication.class)
@WebAppConfiguration
public class CalendarSpringApplicationTests {

    @Autowired
    EventRepository events;

    @Autowired
    UserRepository users;

    @Autowired
    WebApplicationContext wap;

    MockMvc mockMvc;

    //if you want something to run before every test
    @Before
    public void before() {
        events.deleteAll();
        users.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                    .param("username", "testu")
                    .param("password", "testp")
        );
        //should be exactly one user on file
        assertTrue(users.count() == 1); //passed
    }

    @Test
    public void testAddEvent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-event")
                    .param("description", "Test event description")
                    .param("date", LocalDateTime.now().toString())
                    //add phony session so we dont get 'not logged in" error message
                    .sessionAttr("username", "testu")
        );

        assertTrue(events.count() == 1); //passed
    }

}
