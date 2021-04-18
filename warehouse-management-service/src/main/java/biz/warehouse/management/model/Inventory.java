/**
 * 
 */
package biz.warehouse.management.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * @author AANUSHRE
 *
 */
@Entity
@Table(name = "Article_tab")
public class Inventory {
	
	@Id
	@Column(name = "article_id")
	int articleId;
	
	@Column
	String articleName;
	
	@Column
	int articleStock;

	/**
	 * @param art_id
	 * @param articleName
	 * @param articleStock
	 */
	public Inventory(int art_id, String articleName, int articleStock) {
		this.articleId = art_id;
		this.articleName = articleName;
		this.articleStock = articleStock;
	}

	/**
	 * 
	 */
	public Inventory() {
	}

	/**
	 * @return the articleId
	 */
	public int getArticleId() {
		return articleId;
	}
	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	/**
	 * @return the articleName
	 */
	public String getArticleName() {
		return articleName;
	}
	/**
	 * @param articleName the articleName to set
	 */
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	/**
	 * @return the articleStock
	 */
	public int getArticleStock() {
		return articleStock;
	}
	/**
	 * @param articleStock the articleStock to set
	 */
	public void setArticleStock(int articleStock) {
		this.articleStock = articleStock;
	}
	
}
