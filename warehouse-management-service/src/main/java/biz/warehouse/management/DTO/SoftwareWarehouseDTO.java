/**
 * 
 */
package biz.warehouse.management.DTO;

import java.util.List;;

/**
 * @author Anushree
 *
 */
public class SoftwareWarehouseDTO {

	List<InventoryDTO> inventory;
	List<ProductDTO> products;
	
	/**
	 * 
	 */
	public SoftwareWarehouseDTO() {
	}
	
	/**
	 * @param inventory
	 * @param products
	 */
	public SoftwareWarehouseDTO(List<InventoryDTO> inventory, List<ProductDTO> products) {
		this.inventory = inventory;
		this.products = products;
	}
	/**
	 * @return the inventory
	 */
	public List<InventoryDTO> getInventory() {
		return inventory;
	}
	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(List<InventoryDTO> inventory) {
		this.inventory = inventory;
	}
	/**
	 * @return the products
	 */
	public List<ProductDTO> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
}
