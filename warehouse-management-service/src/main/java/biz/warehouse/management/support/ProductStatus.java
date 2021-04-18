/**
 * 
 */
package biz.warehouse.management.support;

/**
 * @author AANUSHRE
 *
 */
public enum ProductStatus {
	OUT_OF_STOCK("OUT OF STOCK"),
    IN_STOCK("IN STOCK");
    
    public final String status;

	/**
	 * @param status
	 */
	private ProductStatus(String status) {
		this.status = status;
	}
    
}
