/**
 * 
 */
package biz.warehouse.management.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import biz.warehouse.management.DTO.ArticleDTO;
import biz.warehouse.management.model.Inventory;
import biz.warehouse.management.model.Product;

/**
 * @author Anushree
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WareshouseManagementTest {

	int randomServerPort = 8080;

	@Test
	public void testStoreInventoryError() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/storeInventory";
		Inventory inventory = new Inventory(1, "leg", 12);

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Inventory> request = new HttpEntity<>(inventory, headers);
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
		try {
			result = restTemplate.postForEntity(uri, request, String.class);
		} catch (Exception ex) {
			assertEquals(405, result.getStatusCodeValue());
		}
	}

	@Test
	public void testStoreProductError() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort + "/storeProducts";
		List<ArticleDTO> contain_articles = new ArrayList<ArticleDTO>();
		ArticleDTO article1 = new ArticleDTO("1", "10");
		ArticleDTO article2 = new ArticleDTO("2", "1");
		ArticleDTO article3 = new ArticleDTO("3", "12");
		contain_articles.add(article1);
		contain_articles.add(article2);
		contain_articles.add(article3);
		Product product = new Product("Table", "20", contain_articles);

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Product> request = new HttpEntity<>(product, headers);
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
		try {
			result = restTemplate.postForEntity(uri, request, String.class);
		} catch (Exception ex) {
			assertEquals(405, result.getStatusCodeValue());
		}
	}

	@Test
	public void testGetInventorySuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/getInventory";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(true, !result.getBody().isEmpty());
	}

	@Test
	public void testGetProductSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/getProducts";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(true, !result.getBody().isEmpty());
	}

}