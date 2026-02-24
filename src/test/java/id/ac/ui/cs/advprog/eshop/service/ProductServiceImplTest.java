package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreate() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);
        Product result = productService.create(product);
        assertEquals(product, result);
    }

    @Test
    void testFindAll() {
        Product p1 = new Product();
        Product p2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2).iterator());
        List<Product> result = productService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");

        when(productRepository.update(product)).thenReturn(product);
        Product result = productService.update(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd7";

        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("id-123");

        when(productRepository.findById("id-123")).thenReturn(product);
        Product result = productService.findById("id-123");

        assertEquals("id-123", result.getProductId());
    }
}