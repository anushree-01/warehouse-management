/**
 * 
 */
package biz.warehouse.management.DTO;

import java.util.List;

/**
 * @author Anushree
 *
 */
public class ProductDTO {

	String name;
	String price;
	List<ArticleDTO> contain_articles;

	/**
	 * 
	 */
	public ProductDTO() {
	}

	/**
	 * @param name
	 * @param contain_articles
	 */
	public ProductDTO(String name,String price, List<ArticleDTO> contain_articles) {
		this.name = name;
		this.price=price;
		this.contain_articles = contain_articles;
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
	 * @return the contain_articles
	 */
	public List<ArticleDTO> getContain_articles() {
		return contain_articles;
	}

	/**
	 * @param contain_articles the contain_articles to set
	 */
	public void setContain_articles(List<ArticleDTO> contain_articles) {
		this.contain_articles = contain_articles;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
