/**
 * 
 */
package biz.warehouse.management.controlller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import biz.warehouse.management.exception.ArticleNotFoundException;
import biz.warehouse.management.exception.InsufficientInventoryArticles;
import biz.warehouse.management.exception.InventoryProcessingException;
import biz.warehouse.management.exception.ProductNotFoundException;

/**
 * @author Anushree
 *
 */
@ControllerAdvice
public class ResourceExceptionController extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> productException(ProductNotFoundException exception) {
	      return new ResponseEntity<>("Unfortunately product cannot be found in the inventory. Please check with the administrator!", HttpStatus.NOT_FOUND);
	   }
	
	@ExceptionHandler(ArticleNotFoundException.class)
	public ResponseEntity<Object> articleException(ArticleNotFoundException exception) {
	      return new ResponseEntity<>("Unfortunately article cannot be found in the inventory. Please check with the administrator!", HttpStatus.NOT_FOUND);
	   }
	
	@ExceptionHandler(InsufficientInventoryArticles.class)
	public ResponseEntity<Object> insufficientArticleException(InsufficientInventoryArticles exception) {
	      return new ResponseEntity<>("Product has insufficient inventory articles. Hence it cannot be sold!", HttpStatus.UNPROCESSABLE_ENTITY);
	   }
	
	@ExceptionHandler(InventoryProcessingException.class)
	public ResponseEntity<Object> inventoryProceessingException(InventoryProcessingException exception) {
	      return new ResponseEntity<>("Error while processing the data. Please contact your Administrator!", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception exception) {
	      return new ResponseEntity<>("Internal Server Error!", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
}
