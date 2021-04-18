/**
 * 
 */
package biz.warehouse.management.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import biz.warehouse.management.DTO.ArticleDTO;

/**
 * @author AANUSHRE
 *
 */
@Entity
@Table(name = "Products_tab")
public class Product {

	@Column(name = "product_name")
	@Id
	String productName;

	@Column
	String price;

	@Column
	String status;

//	@ManyToMany(fetch = FetchType.EAGER, targetEntity = ProductArticleMap.class, cascade = CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "products_articles", joinColumns = { @JoinColumn(name = "productName") }, inverseJoinColumns = {
			@JoinColumn(name = "articleId") })
	Set<ProductArticleMap> productArticleMap;

	/**
	 * @param productId
	 * @param productName
	 */
	public Product(String productName, String price, List<ArticleDTO> contain_articles) {
		this.productName = productName;
		this.price = price;
		this.productArticleMap = getProductArticleMap(contain_articles);
	}

	private Set<ProductArticleMap> getProductArticleMap(List<ArticleDTO> contain_articles) {
		Set<ProductArticleMap> productArticles = new HashSet<>();
		Set<Product> products = new HashSet<>();
		products.add(this);
		for (ArticleDTO containArticle : contain_articles) {
			ProductArticleMap productArticleMap = new ProductArticleMap(containArticle,productName);
			productArticleMap.setProduct(products);
			productArticles.add(productArticleMap);
		}
		return productArticles;
	}

	/**
	 * @param list
	 * @param string
	 * 
	 */
	public Product() {
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productArticleMap
	 */
	public Set<ProductArticleMap> getProductArticleMap() {
		return productArticleMap;
	}

	/**
	 * @param productArticleMap the productArticleMap to set
	 */
	public void setProductArticleMap(Set<ProductArticleMap> productArticleMap) {
		this.productArticleMap = productArticleMap;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
