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

## Reflection 2

### 1. Unit Testing & Code Coverage
After writing the unit tests, I feel much more confident in the stability of the application. Tests act as a "safety net" that allows me to refactor code without fear of breaking existing logic.

* **How many tests?** There isn't a fixed number; instead, a class should have enough tests to cover all possible execution paths (logic branches, loops, and edge cases).
* **Verification:** To ensure tests are sufficient, we look at **Code Coverage**. It measures which lines of code were executed during testing. However, **100% code coverage does not mean the code is bug-free.** Coverage only proves the code was *executed*, not that it was *correct* under every possible state or edge case (e.g., a logic error that still runs but produces the wrong result).

### 2. Functional Testing Cleanliness
If I were to create a new functional test suite for verifying the number of items in the product list by copying the setup from `CreateProductFunctionalTest.java`, several clean code issues would arise:

* **Code Duplication (Violation of DRY):** Repeating the same setup procedures (like `@LocalServerPort`, `@Value`, and the `setupTest` method) creates "boilerplate" code. If the base URL or the port logic changes, I would have to update it in every single test file.
* **Reduced Maintainability:** High duplication makes the test suite "brittle." More code means more places for bugs to hide and more effort required for future modifications.
* **Readability:** The core intent of the test (verifying item counts) would be buried under lines of setup code that are identical across files.

#### **Suggested Improvements:**
To make the code cleaner, I would:
1.  **Create a Base Class:** Define a `BaseFunctionalTest` class that contains the common setup logic, variables (URL, port), and `setupTest` method.
2.  **Inheritance:** Have `CreateProductFunctionalTest` and the new `ProductListCountFunctionalTest` extend this base class.
3.  **Utility Methods:** Move common actions (like logging in or navigating to the product list) into the base class or a helper "Page Object" class to keep the test methods focused purely on assertions.

## Module 2 Reflection

### 1. Code Quality Issues Fixed
During the exercise, I addressed several code quality and security issues detected by the scanning tools:

* **Token-Permissions (High Severity)**: The OSSF Scorecard flagged that the `GITHUB_TOKEN` had excessive write permissions. I fixed this by adding an explicit permissions block to my workflow files (setting `contents: read`), adhering to the Principle of Least Privilege.
* **Java Version Mismatch**: My initial deployment failed because the environment defaulted to Java 25 while my toolchain required Java 21. I fixed this by adding a `system.properties` file to enforce the correct runtime version.
* **Unused/Dead Code**: I used the **PMD** scan results to identify and remove unused imports and variables in my `ProductController` and `ProductServiceImpl` classes, which improved the overall maintainability of the codebase.

### 2. CI/CD Workflow Assessment
I believe the current implementation fully meets the definition of Continuous Integration and Continuous Deployment. **Continuous Integration** is achieved through GitHub Actions that automatically run my unit test suite and static analysis (PMD/Scorecard) on every push to ensure code integrity. **Continuous Deployment** is implemented via the Heroku integration, which automatically builds and pushes the stable `master` branch to a live production environment whenever changes are merged. This automated pipeline reduces manual errors and ensures that the latest verified version of the software is always available to users at the deployment URL: [https://jft-eshop-module2-7622bd16ec40.herokuapp.com/](https://jft-eshop-module2-7622bd16ec40.herokuapp.com/).

## Module 3 Reflection

### 1) Principles Applied
I applied three SOLID principles to the project:
* **Single Responsibility Principle (SRP)**: I moved `CarController` into its own file separate from `ProductController`. [cite_start]This ensures that each controller is responsible for only one domain (Cars or Products)[cite: 2100].
* **Liskov Substitution Principle (LSP)**: I removed the inheritance where `CarController` extended `ProductController`. [cite_start]Since `CarController` is not a specialized version of `ProductController`, removing this forced relationship ensures that the system remains robust and predictable[cite: 2107].
* **Dependency Inversion Principle (DIP)**: In `CarController`, I changed the dependency from the concrete `CarServiceImpl` to the `CarService` interface. [cite_start]This allows the high-level controller to depend on abstractions rather than low-level implementation details[cite: 2111].

### 2) Advantages of Applying SOLID
* **Better Maintainability**: By applying SRP, when I need to change how Cars are handled, I only need to look at `CarController.java` without risking breaking Product logic.
* **Easier Testing**: DIP allows me to swap the real `CarService` with a "mock" or "test" version easily because the controller only cares about the interface contract.
* **Flexibility**: Removing the forced inheritance (LSP) allows the two controllers to evolve independently without being tied to a parent class that might change unexpectedly.

### 3) Disadvantages of Not Applying SOLID
* **Rigid Code**: Without DIP, if I wanted to change my data storage from a list to a database, I would have to change code in multiple layers (Controller and Service) because they are tightly coupled to concrete classes.
* **Higher Risk of Bugs**: Keeping everything in one file (SRP violation) makes it easy to accidentally delete or modify code for one feature while working on another.
* **Fragile Inheritance**: If I kept the LSP violation, a simple change in `ProductController` (like changing a constructor or a shared mapping) could break the `CarController` functionality without warning.

## Module 4 Reflection

### Reflection 1: TDD and Testing Objectives (Percival 2017)

After going through the TDD flow for the Order and Payment features, I definitely found it useful. Thinking about the questions proposed by Percival (2017) regarding testing objectives, here’s what I’ve realized:

* **Correctness**: The TDD flow acted like a safety net. By defining the "unhappy paths" (like invalid voucher codes or empty product lists) before writing any logic, I was forced to handle those cases immediately. It gave me 100% certainty that the "what if" scenarios were covered.
* **Maintainability**: This was the biggest win for me. When I refactored the hardcoded strings into Enums, the tests were there to tell me if I broke any mappings. Without TDD, I probably would have been much more hesitant to clean up the code.
* **Productive Workflow**: While it takes a second to get used to writing tests first, it actually saved me time. I didn't have to guess why a feature wasn't working; the failing test told me exactly which assertion was falling short.

In the future, I might try to break down my test cases into even smaller units during the RED phase to make the implementation steps even smoother, but overall, this workflow is something I'll keep using.

### Reflection 2: Unit Testing and F.I.R.S.T. Principles

I believe the unit tests I created during the tutorial and exercise successfully followed the **F.I.R.S.T.** principles:

* **Fast**: These tests are purely unit-based. They run in milliseconds because they don't depend on the full Spring context or a real database.
* **Independent**: Each test is isolated. For the Service layer, I used Mockito to mock the repositories, ensuring that a bug in the repository wouldn't cause a false failure in the service tests.
* **Repeatable**: Since we used in-memory storage and mocks, the tests produce the same result every single time, regardless of the environment.
* **Self-Validating**: Every test has clear `assertEquals` or `assertThrows` statements. I don't need to manually check logs; if the test passes green, I know the logic is correct.
* **Timely**: Because we used TDD, the tests were written *before* the code (during the RED phase). This ensured the code was built specifically to be testable from the very beginning.

To keep improving, I’ll focus on ensuring that my test names stay descriptive so that if a test fails in a CI/CD pipeline months from now, the person looking at it (who might be me!) knows exactly what went wrong without digging through the code.