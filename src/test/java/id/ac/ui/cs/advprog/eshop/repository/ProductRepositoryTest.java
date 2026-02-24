package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0ee-281c623059a1");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Update the product
        product.setProductName("Sampo Cap Baru");
        product.setProductQuantity(50);
        productRepository.update(product);

        Product updatedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd7");
        assertEquals("Sampo Cap Baru", updatedProduct.getProductName());
        assertEquals(50, updatedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNegative() {
        // Attempting to update a product that was never created
        Product product = new Product();
        product.setProductId("non-existent-id");
        product.setProductName("Ghost Product");

        productRepository.update(product);

        Product found = productRepository.findById("non-existent-id");
        assertNull(found); // Or check that the list size is still 0
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product.setProductName("Sampo Cap Bambang");
        productRepository.create(product);

        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd7");

        Product deletedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd7");
        assertNull(deletedProduct);
    }

    @Test
    void testDeleteProductNegative() {
        // Deleting a non-existent ID should not throw an exception or delete the wrong thing
        productRepository.delete("random-id");

        // If you have existing data, verify the size remains the same
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product product = new Product();
        product.setProductId("non-existent");

        Product result = productRepository.update(product);
        assertNull(result); // Assuming your repo returns null if not found
    }

    @Test
    void testDeleteNonExistentProduct() {
        // This ensures no exception is thrown and coverage hits the 'else' or 'not found' branch
        assertDoesNotThrow(() -> productRepository.delete("non-existent"));
    }
}