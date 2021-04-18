/**
 * 
 */
package biz.warehouse.management.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import biz.warehouse.management.model.Product;

/**
 * @author AANUSHRE
 *
 */
//@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	Optional<Product> findByProductName(String productName);

	@Transactional
	@Modifying
	@Query(value = "delete Products_tab where product_name= :product_name", nativeQuery = true)
	void deleteByProductName(String product_name);

	@Modifying
	@Transactional
	@Query(value = "Update Products_tab set status = :status where product_name = :product_name", nativeQuery = true)
	void updateProductStatus(String product_name, String status);

}
