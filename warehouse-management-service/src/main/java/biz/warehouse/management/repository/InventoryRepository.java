/**
 * 
 */
package biz.warehouse.management.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import biz.warehouse.management.model.Inventory;

/**
 * @author AANUSHRE
 *
 */
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	Optional<Inventory> findByArticleId(int articleId);

	@Modifying
	@Transactional
	@Query(value = "delete Article_tab where article_id= :article_id", nativeQuery = true)
	void deleteByArticleId(int article_id);

}
