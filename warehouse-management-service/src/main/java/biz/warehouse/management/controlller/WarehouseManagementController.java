/**
 * 
 */
package biz.warehouse.management.controlller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import biz.warehouse.management.DTO.SoftwareWarehouseDTO;
import biz.warehouse.management.exception.ArticleNotFoundException;
import biz.warehouse.management.exception.InsufficientInventoryArticles;
import biz.warehouse.management.exception.InventoryProcessingException;
import biz.warehouse.management.exception.ProductNotFoundException;
import biz.warehouse.management.model.Inventory;
import biz.warehouse.management.model.Product;
import biz.warehouse.management.repository.InventoryRepository;
import biz.warehouse.management.repository.ProductArticleRepository;
import biz.warehouse.management.repository.ProductRepository;
import biz.warehouse.management.service.WarehouseManagementService;
import biz.warehouse.management.support.ProductStatus;

/**
 * @author Anushree
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class WarehouseManagementController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseManagementController.class);

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductArticleRepository productArticleRepository;

	@Autowired
	private WarehouseManagementService warehouseManagementService;

	boolean articleStockAvailable = true;

	/**
	 * This method is to store the inventory articles uploaded through file.
	 * 
	 * @param articleFile
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/storeInventory")
	@CacheEvict(value = { "warehouseArticlesCache" }, allEntries = true)
	public ResponseEntity<?> storeInventoryData(@RequestParam("file") MultipartFile articleFile) {
		SoftwareWarehouseDTO inventoryWarehouse;
		try {
			inventoryWarehouse = new ObjectMapper().readValue(articleFile.getBytes(), SoftwareWarehouseDTO.class);
			inventoryWarehouse.getInventory().stream().forEach(article -> {
				Inventory inventory = new Inventory();
				inventory.setArticleId(Integer.parseInt(article.getArt_id()));
				inventory.setArticleName(article.getName());
				inventory.setArticleStock(Integer.parseInt(article.getStock()));
				inventoryRepository.save(inventory);
			});
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new InventoryProcessingException();
		}
		return new ResponseEntity<>("Inventory has been saved successfully!", HttpStatus.OK);
	}

	/**
	 * Fetches Articles Data From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getInventory")
	@Cacheable(value = "warehouseArticlesCache")
	public ResponseEntity<?> getAllInventoryData() {
		List<Inventory> inventory = new ArrayList<Inventory>();
		inventory = inventoryRepository.findAll();
		if (inventory.size() == 0) {
			throw new ArticleNotFoundException();
		}
		return new ResponseEntity<>(inventory, HttpStatus.OK);
	}

	/**
	 * Delete Articles Data From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/deleteInventory/{articleId}")
	@CacheEvict(value = { "warehouseArticlesCache" }, allEntries = true)
	public ResponseEntity<?> deleteInventoryData(@PathVariable("articleId") int articleId) {
		Optional<Inventory> inventory = inventoryRepository.findByArticleId(articleId);
		if (inventory.isPresent()) {
			inventoryRepository.deleteByArticleId(articleId);
		} else {
			throw new ArticleNotFoundException();
		}
		return new ResponseEntity<>("Article has been deleted successfully!", HttpStatus.OK);
	}

	/**
	 * Fetches Article by id From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getInventory/{articleId}")
	public ResponseEntity<?> getByArticleId(@PathVariable("articleId") int articleId) {
		Optional<Inventory> inventory = inventoryRepository.findByArticleId(articleId);
		if (inventory.isPresent()) {
			return new ResponseEntity<>(inventory, HttpStatus.OK);
		} else {
			throw new ArticleNotFoundException();
		}
	}

	/**
	 * This method is to store the product uploaded through file.
	 * 
	 * @param productFile
	 * @throws Exception
	 */
	@PostMapping("/storeProducts")
	@CacheEvict(value = { "warehouseProductsCache" }, allEntries = true)
	public ResponseEntity<?> storeProductsData(@RequestParam("file") MultipartFile productFile) {
		try {
			SoftwareWarehouseDTO productWarehouse = new ObjectMapper().readValue(productFile.getBytes(),
					SoftwareWarehouseDTO.class);

			productWarehouse.getProducts().stream().forEach(products -> {
				Product product = new Product(products.getName(), products.getPrice(), products.getContain_articles());
				product.setProductName(products.getName());
				product.setStatus(ProductStatus.IN_STOCK.toString());
				productRepository.save(product);
			});
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new InventoryProcessingException();
		}
		return new ResponseEntity<>("Products has been saved successfully!", HttpStatus.OK);
	}

	/**
	 * Fetches Products Data From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getProducts")
	@Cacheable(value = "warehouseProductsCache")
	public ResponseEntity<?> getAllProductsData() {

		List<Product> product = new ArrayList<Product>();
		product = productRepository.findAll();
		if (product.size() == 0) {
			throw new ProductNotFoundException();
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	/**
	 * Delete Products Data From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/deleteProducts/{productName}")
	@CacheEvict(value = { "warehouseProductsCache" }, allEntries = true)
	public ResponseEntity<?> deleteProductsData(@PathVariable("productName") String productName) {
		Optional<Product> product = productRepository.findByProductName(productName);
		if (product.isPresent()) {
			product.get().getProductArticleMap().clear();
			productRepository.deleteByProductName(productName);
			productArticleRepository.deleteByProductName(productName);
		} else {
			throw new ProductNotFoundException();
		}
		return new ResponseEntity<>("Product has been deleted successfully!", HttpStatus.OK);
	}

	/**
	 * Fetches Products Data From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getProduct/{productName}")
	public ResponseEntity<?> getByProductName(@PathVariable("productName") String productName) {
		Optional<Product> product = productRepository.findByProductName(productName);
		if (product.isPresent()) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException();
		}
	}

	/**
	 * Updates the product when product is sold
	 * 
	 * @param productName
	 * @throws Exception
	 */
	@PutMapping("/sellProduct/{productName}")
	@CacheEvict(value = { "warehouseProductsCache" }, allEntries = true)
	public ResponseEntity<?> sellProduct(@PathVariable("productName") String productName) {
		Optional<Product> product = productRepository.findByProductName(productName);
		if (product.isPresent() && product.get().getStatus().equalsIgnoreCase(ProductStatus.IN_STOCK.toString())) {
			articleStockAvailable = warehouseManagementService.updateInventoryStock(product, articleStockAvailable);
			if (articleStockAvailable) {
				productRepository.updateProductStatus(productName, ProductStatus.OUT_OF_STOCK.toString());
			} else {
				throw new InsufficientInventoryArticles();
			}
		} else {
			throw new ProductNotFoundException();
		}
		return new ResponseEntity<>("Product Has been sold successfully!", HttpStatus.OK);
	}

}
