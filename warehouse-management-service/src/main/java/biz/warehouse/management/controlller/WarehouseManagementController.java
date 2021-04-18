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

import biz.warehouse.management.WarehouseApplication;
import biz.warehouse.management.DTO.SoftwareWarehouseDTO;
import biz.warehouse.management.model.Inventory;
import biz.warehouse.management.model.Product;
import biz.warehouse.management.repository.InventoryRepository;
import biz.warehouse.management.repository.ProductArticleRepository;
import biz.warehouse.management.repository.ProductRepository;
import biz.warehouse.management.support.ProductStatus;

/**
 * @author Anushree
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class WarehouseManagementController {

	private static final Logger LOGGER=LoggerFactory.getLogger(WarehouseManagementController.class);
	
	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductArticleRepository productArticleRepository;

	boolean articleStockAvailable = true;


	/**
	 * This method is to store the inventory articles uploaded through file.
	 * 
	 * @param articleFile
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/storeInventory")
	public ResponseEntity<?> storeInventoryData(@RequestParam("file") MultipartFile articleFile) throws Exception {
		SoftwareWarehouseDTO inventoryWarehouse = new ObjectMapper().readValue(articleFile.getBytes(),
				SoftwareWarehouseDTO.class);
		try {
			inventoryWarehouse.getInventory().stream().forEach(article -> {
				Inventory inventory = new Inventory();
				inventory.setArticleId(Integer.parseInt(article.getArt_id()));
				inventory.setArticleName(article.getName());
				inventory.setArticleStock(Integer.parseInt(article.getStock()));
				inventoryRepository.save(inventory);
			});
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity("Error while saving the data. Please contact your Administrator!",
					HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<?> getInventoryData() throws Exception {
		List<Inventory> inventory = new ArrayList<Inventory>();
		try {
			inventory = inventoryRepository.findAll();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>("Error while fetching data. Please contact your Administrator!",
					HttpStatus.NOT_FOUND);
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
	public ResponseEntity<?> deleteInventoryData(@PathVariable("articleId") int articleId) throws Exception {
		try {
			Optional<Inventory> inventory = inventoryRepository.findByArticleId(articleId);
			if (inventory.isPresent()) {
				inventoryRepository.deleteByArticleId(articleId);
			}else {
				return new ResponseEntity<>(
						"Unfortunately Article " + articleId+" cannot be found in the inventory. Please check with the administrator!",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>("Error while deleting article!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Article has been deleted successfully!", HttpStatus.OK);
	}

	/**
	 * This method is to store the product uploaded through file.
	 * 
	 * @param productFile
	 * @throws Exception
	 */
	@PostMapping("/storeProducts")
	public ResponseEntity<?> storeProductsData(@RequestParam("file") MultipartFile productFile) throws Exception {
		SoftwareWarehouseDTO productWarehouse = new ObjectMapper().readValue(productFile.getBytes(),
				SoftwareWarehouseDTO.class);
		try {
			productWarehouse.getProducts().stream().forEach(products -> {
				Product product = new Product(products.getName(), products.getPrice(), products.getContain_articles());
				product.setProductName(products.getName());
				product.setStatus(ProductStatus.IN_STOCK.toString());
				productRepository.save(product);
			});
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity("Error while saving the data. Please contact your Administrator!",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Products has been saved successfully!", HttpStatus.OK);
	}

	/**
	 * Fetches Articles Data From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getProducts")
	public ResponseEntity<?> getProductsData() throws Exception {

		List<Product> product = new ArrayList<Product>();
		try {
			product = productRepository.findAll();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>("Error while fetching data. Please contact your Administrator!",
					HttpStatus.NOT_FOUND);
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
	public ResponseEntity<?> deleteProductsData(@PathVariable("productName") String productName) throws Exception {
		try {
			Optional<Product> product = productRepository.findByProductName(productName);
			if (product.isPresent()) {
				product.get().getProductArticleMap().clear();
				productRepository.deleteByProductName(productName);
				productArticleRepository.deleteByProductName(productName);
			}else {
				return new ResponseEntity<>(
						"Unfortunately " + productName+" cannot be found in the inventory. Please check with the administrator!",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>("Error while deleting product!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Product has been deleted successfully!", HttpStatus.OK);
	}

	/**
	 * Fetches Articles Data From dB
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getProduct/{productName}")
	public ResponseEntity<?> getProductsData(@PathVariable("productName") String productName) throws Exception {
		try {
			Optional<Product> product = productRepository.findByProductName(productName);
			if (product.isPresent()) {
				return new ResponseEntity<>(product, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						"Unfortunately " + productName
								+ " cannot be found in the inventory. Please check with the administrator!",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>("Error while fetching data. Please contact your Administrator!",
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Updates the product when product is sold
	 * 
	 * @param productName
	 * @throws Exception
	 */
	@PutMapping("/sellProduct/{productName}")
	public ResponseEntity<?> sellProduct(@PathVariable("productName") String productName) throws Exception {
		try {
			Optional<Product> product = productRepository.findByProductName(productName);
			if (product.isPresent() && product.get().getStatus().equalsIgnoreCase(ProductStatus.IN_STOCK.toString())) {
				updateInventoryStock(product);
				if (articleStockAvailable) {
					productRepository.updateProductStatus(productName, ProductStatus.OUT_OF_STOCK.toString());
				} else {
					return new ResponseEntity<>("Product has insufficient inventory articles. Hence it cannot be sold!",
							HttpStatus.NOT_FOUND);
				}

			}else {
				return new ResponseEntity<>("Product Not Found. Please contact your Administrator!",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>("Error while updating products. Please contact your Administrator!",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Product Has been sold successfully!", HttpStatus.OK);
	}

	/**
	 * Updates the inventory if the product has been sold
	 * 
	 * @param product
	 * @throws Exception
	 */
	private void updateInventoryStock(Optional<Product> product) {
		List<Inventory> inventoryFound = new ArrayList<Inventory>();
		product.get().getProductArticleMap().stream().forEach(productArticleMap -> {
			int productArticleSold = productArticleMap.getAmount();
			Optional<Inventory> inventory = inventoryRepository.findByArticleId(productArticleMap.getArticleId());
			if (inventory.isPresent() && inventory.get().getArticleStock() >= productArticleSold) {
				int stockRemaining = inventory.get().getArticleStock() - productArticleSold;
				inventory.get().setArticleStock(stockRemaining);
				inventoryFound.add(inventory.get());
			} else {
				articleStockAvailable = false;
			}
		});
		if (articleStockAvailable) {
			inventoryRepository.saveAll(inventoryFound);
		}
	}
}
