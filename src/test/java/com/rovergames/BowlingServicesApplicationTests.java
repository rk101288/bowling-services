package com.rovergames;

import com.rovergames.config.BowlingServicesApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BowlingServicesApplication.class)
@WebAppConfiguration
public class BowlingServicesApplicationTests {

	@Test
	public void contextLoads() {
        System.out.println("Simple test to verify application context comes up properly!");
	}

}
