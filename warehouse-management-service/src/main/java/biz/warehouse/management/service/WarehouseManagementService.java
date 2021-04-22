/**
 * 
 */
package biz.warehouse.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.warehouse.management.model.Inventory;
import biz.warehouse.management.model.Product;
import biz.warehouse.management.repository.InventoryRepository;

/**
 * @author Anushree
 *
 */
@Service
public class WarehouseManagementService {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	boolean isAvailable= true;
	
	/**
	 * Updates the inventory if the product has been sold
	 * 
	 * @param product
	 * @return 
	 * @throws Exception
	 */
	public boolean updateInventoryStock(Optional<Product> product, boolean articleStockAvailable) {
		isAvailable=articleStockAvailable;
		List<Inventory> inventoryFound = new ArrayList<Inventory>();
		product.get().getProductArticleMap().stream().forEach(productArticleMap -> {
			int productArticleSold = productArticleMap.getAmount();
			Optional<Inventory> inventory = inventoryRepository.findByArticleId(productArticleMap.getArticleId());
			if (inventory.isPresent() && inventory.get().getArticleStock() >= productArticleSold) {
				int stockRemaining = inventory.get().getArticleStock() - productArticleSold;
				inventory.get().setArticleStock(stockRemaining);
				inventoryFound.add(inventory.get());
			} else {
				isAvailable = false;
			}
		});
		if (articleStockAvailable) {
			inventoryRepository.saveAll(inventoryFound);
		}
		return isAvailable;
	}
}
