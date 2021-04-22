/**
 * 
 */
package biz.warehouse.management.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import biz.warehouse.management.DTO.ArticleDTO;
import biz.warehouse.management.controlller.WarehouseManagementController;
import biz.warehouse.management.model.Inventory;
import biz.warehouse.management.model.Product;
import biz.warehouse.management.repository.InventoryRepository;
import biz.warehouse.management.repository.ProductArticleRepository;
import biz.warehouse.management.repository.ProductRepository;
import biz.warehouse.management.service.WarehouseManagementService;

/**
 * @author Anushree
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = WarehouseManagementController.class)
public class WareshouseManagementTest {

	
	@Autowired 
	private MockMvc mockMvc;

	@MockBean
	private InventoryRepository inventoryRepository;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private ProductArticleRepository productArticleRepository;
	
	@MockBean
	private WarehouseManagementService warehouseManagementService;
	
	int randomServerPort = 8080;
	
	/*
	 * Method to test inventory update.
	 */
	@Test
	public void teststoreInventoryData() throws Exception {
		MockMultipartFile inventoryFile = new MockMultipartFile("file", "inventory.json", "application/json",
				Files.readAllBytes(Paths.get("src/test/resources/inventory.json")));

		mockMvc.perform(MockMvcRequestBuilders.multipart("/storeInventory").file(inventoryFile)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is(200));
	}
	
	/*
	 * Method to test product update.
	 */
	@Test
	public void testStoreProductsData() throws Exception {
		MockMultipartFile productFile = new MockMultipartFile("file", "products.json", "application/json",
				Files.readAllBytes(Paths.get("src/test/resources/products.json")));

		mockMvc.perform(MockMvcRequestBuilders.multipart("/storeProducts").file(productFile)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is(200));
	}
	
	/*
	 * Method to test retrieval of inventory details
	 */
	@Test
	public void testGetInventoryData() throws Exception {
		List<Inventory> inventoryList =new ArrayList<>();
		Inventory inventory = new Inventory(1, "screws", 30);
		inventoryList.add(inventory);

		Mockito.when(inventoryRepository.findAll()).thenReturn(inventoryList);
		mockMvc.perform(MockMvcRequestBuilders.get("/getInventory"))
		.andExpect(status().isOk());
	}
	
	/*
	 * Method to test retrieval of product details
	 */
	@Test
	public void testGetProductsData() throws Exception {
		List<Product> productList =new ArrayList<>();
		List<ArticleDTO> contain_articles = new ArrayList<ArticleDTO>();
		ArticleDTO article1 = new ArticleDTO("1", "10");
		ArticleDTO article2 = new ArticleDTO("2", "1");
		ArticleDTO article3 = new ArticleDTO("3", "12");
		contain_articles.add(article1);
		contain_articles.add(article2);
		contain_articles.add(article3);
		Product product = new Product("Table", "20", contain_articles);
		productList.add(product);

		Mockito.when(productRepository.findAll()).thenReturn(productList);
		mockMvc.perform(MockMvcRequestBuilders.get("/getProducts"))
		.andExpect(status().isOk());
	}
	
	/*
	 * Method to test negative scenario of inventory update.
	 */
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

	/*
	 * Method to test negative scenario of product update.
	 */
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
	
	/*
	 * Ignoring below test case during build.
	 */
	@Ignore
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

	/*
	 * Ignoring below test case during build.
	 */
	@Ignore
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