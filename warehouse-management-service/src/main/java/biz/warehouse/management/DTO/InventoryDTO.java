/**
 * 
 */
package biz.warehouse.management.DTO;

/**
 * @author Anushree
 *
 */
public class InventoryDTO {

     String art_id;
     String name;
     String stock;
	/**
	 * @return the art_id
	 */
	public String getArt_id() {
		return art_id;
	}
	/**
	 * @param art_id the art_id to set
	 */
	public void setArt_id(String art_id) {
		this.art_id = art_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}
    
     
}
