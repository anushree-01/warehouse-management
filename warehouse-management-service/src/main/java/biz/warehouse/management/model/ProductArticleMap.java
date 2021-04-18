/**
 * 
 */
package biz.warehouse.management.model;

import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import biz.warehouse.management.DTO.ArticleDTO;

/**
 * @author AANUSHRE
 *
 */
@Entity
@Table(name = "Product_Article_tab")
@JsonIgnoreProperties(ignoreUnknown = true,   value = {"product","productName"})
public class ProductArticleMap {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@Column(name= "article_id")
	int articleId;
	
	@Column
	int amount;
	
	@Column
	String productName;
	
	@ManyToMany(mappedBy = "productArticleMap", cascade = CascadeType.ALL)
	Set<Product> product;
	/**
	 * @return the product
	 */
	public Set<Product> getProduct() {
		return product;
	}



	/**
	 * @param product the product to set
	 */
	public void setProduct(Set<Product> product) {
		this.product = product;
	}



	/**
	 * @param articleId
	 * @param productId
	 * @param amount
	 */
	public ProductArticleMap(int articleId, int amount) {
		this.articleId = articleId;
		this.amount = amount;
	}
	
	

	/**
	 * 
	 */
	public ProductArticleMap() {
	}

	public ProductArticleMap(ArticleDTO containArticle, String productName) {
		this.amount=Integer.parseInt(containArticle.getAmount_of());
		this.articleId=Integer.parseInt(containArticle.getArt_id());
		this.productName=productName;
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
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}

	
}
