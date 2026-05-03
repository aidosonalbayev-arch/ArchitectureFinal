package com.shop.config;

import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.model.User;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) return; // already initialized

        log.info("Initializing demo data...");

        // Categories
        Category electronics = new Category(null, "Electronics", "Electronic devices and gadgets", null);
        Category clothing = new Category(null, "Clothing", "Apparel and accessories", null);
        Category books = new Category(null, "Books", "Books and educational materials", null);
        categoryRepository.save(electronics);
        categoryRepository.save(clothing);
        categoryRepository.save(books);

        // Products
        Product laptop = new Product();
        laptop.setName("Laptop Pro 15");
        laptop.setDescription("High-performance laptop with 16GB RAM");
        laptop.setPrice(new BigDecimal("999.99"));
        laptop.setStock(50);
        laptop.setCategory(electronics);
        productRepository.save(laptop);

        Product phone = new Product();
        phone.setName("Smartphone X");
        phone.setDescription("Latest flagship phone");
        phone.setPrice(new BigDecimal("599.99"));
        phone.setStock(100);
        phone.setCategory(electronics);
        productRepository.save(phone);

        Product tshirt = new Product();
        tshirt.setName("Basic T-Shirt");
        tshirt.setDescription("Comfortable cotton t-shirt");
        tshirt.setPrice(new BigDecimal("19.99"));
        tshirt.setStock(200);
        tshirt.setCategory(clothing);
        productRepository.save(tshirt);

        Product book = new Product();
        book.setName("Spring Boot in Action");
        book.setDescription("Comprehensive Spring Boot guide");
        book.setPrice(new BigDecimal("39.99"));
        book.setStock(75);
        book.setCategory(books);
        productRepository.save(book);

        // Admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@shop.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);

        // Regular user
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(User.Role.USER);
        userRepository.save(user);

        log.info("Demo data initialized: {} categories, {} products, {} users",
                categoryRepository.count(), productRepository.count(), userRepository.count());
    }
}
