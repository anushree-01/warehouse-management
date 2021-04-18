package biz.warehouse.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Anushree
 *
 */
@SpringBootApplication
public class WarehouseApplication {

	private static final Logger LOGGER=LoggerFactory.getLogger(WarehouseApplication.class);
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WarehouseApplication.class, args);
		LOGGER.info("Application Started!");
	}
}
