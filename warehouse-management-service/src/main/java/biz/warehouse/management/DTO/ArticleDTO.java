/**
 * 
 */
package biz.warehouse.management.DTO;

/**
 * @author Anushree
 *
 */
public class ArticleDTO {

     String art_id;
     String amount_of;
	/**
	 * 
	 */
	public ArticleDTO() {
	}
	/**
	 * @param art_id
	 * @param amount_of
	 */
	public ArticleDTO(String art_id, String amount_of) {
		this.art_id = art_id;
		this.amount_of = amount_of;
	}
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
	 * @return the amount_of
	 */
	public String getAmount_of() {
		return amount_of;
	}
	/**
	 * @param amount_of the amount_of to set
	 */
	public void setAmount_of(String amount_of) {
		this.amount_of = amount_of;
	}

     
}
