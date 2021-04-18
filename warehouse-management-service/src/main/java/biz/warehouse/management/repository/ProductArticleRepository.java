/**
 * 
 */
package biz.warehouse.management.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import biz.warehouse.management.model.ProductArticleMap;

/**
 * @author AANUSHRE
 *
 */
public interface ProductArticleRepository extends JpaRepository<ProductArticleMap, Integer>{

	@Modifying
	@Transactional
	@Query(value = "delete Product_Article_tab where product_name= :product_name", nativeQuery = true)
	void deleteByProductName(String product_name);

}
