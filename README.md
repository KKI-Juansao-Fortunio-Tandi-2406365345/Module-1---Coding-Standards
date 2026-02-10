# AdPro - E-Shop Reflection

## Reflection 1

### Clean Code Principles Applied
During the development of the "Edit" and "Delete" features, I focused on applying several Clean Code standards to ensure the project remains maintainable:

1. **Meaningful Names:** I avoided vague naming conventions. Methods like `findById` and `update` clearly describe their behavior, and variable names like `productQuantity` allow anyone reading the code to understand the data type without checking the model class.
2. **Single Responsibility Principle (SRP):** Each class in my project has a dedicated purpose. The `ProductRepository` only manages data storage logic, while the `ProductController` handles web requests. This separation makes it much easier to debug specific issues.
3. **High Cohesion and Tooling:** By using Spring Boot's annotations like `@Service` and `@Repository`, I ensured that the components are well-organized and that the logic for specific features is grouped together logically.
4. **DRY (Don't Repeat Yourself):** I made sure to reuse the existing `Product` model for both the creation and editing processes, ensuring that the business logic remains centralized.

### Secure Coding Practices
While this is a basic implementation, I applied a few fundamental secure coding concepts:

1. **UUID Generation:** Instead of using simple, predictable integers (1, 2, 3) for Product IDs, I implemented `java.util.UUID.randomUUID().toString()`. This makes it impossible for a user to guess the ID of another product, which is a basic defense against ID Enumeration attacks.
2. **Path Variables:** I used `@PathVariable` to ensure that the ID passed in the URL is correctly mapped to a String, which helps prevent basic injection attempts in the URL structure.

### Potential Improvements
Looking back at the source code, I believe there is one area that could be improved:
* **Validation:** Currently, the application allows a user to save a product with an empty name or a negative quantity. To improve this, I should add validation logic (using `@Min` or `@NotBlank`) in the `Product` model to ensure data integrity before it reaches the repository. This would make the application more robust and prevent "garbage data" from being stored.
