# SauceDemo Web Application — Automated Testing Project

---

## Problem Statement

The objective of this test automation project is to develop a robust, maintainable, and scalable automated testing framework for the **SauceDemo** e-commerce web application (https://www.saucedemo.com/). The framework validates the end-to-end user journey — from login through product browsing, cart management, checkout, and order confirmation — ensuring functional correctness, UI reliability, and regression safety.

The project involves the development of a **Selenium WebDriver–based test automation suite** using Java, Maven, TestNG, and Allure Reporting. The framework follows the **Page Object Model (POM)** design pattern to ensure separation of concerns, reusability, and ease of maintenance. It supports **multi-browser execution** (Chrome, Firefox, Edge), **headless mode**, **parallel test execution**, and **data-driven testing** via TestNG DataProviders.

> **Note** — This requirement documentation contains details on user stories, test cases, and acceptance criteria for each module to provide clarity and context for the QA / development team.

The project aims to provide comprehensive test coverage across all critical user workflows and ensure early detection of defects in the SauceDemo application. The target application is a sample e-commerce platform used for practicing test automation skills.

---

## Requirements Summary

| # | Requirement | Description | Priority | Acceptance Criteria |
|---|-------------|-------------|----------|---------------------|
| 1 | **Login** | Users should be able to log in to the system with valid credentials and be prevented from accessing the system with invalid credentials. | High | – User can access the login page at the base URL. <br> – User can enter username and password and click the "Login" button. <br> – User is redirected to the inventory/products page upon successful login. <br> – Appropriate error messages are displayed for invalid credentials. <br> – Locked-out users are denied access with an error message. |
| 2 | **Logout** | Users should be able to log out from the system securely. | High | – User can open the hamburger menu and click "Logout". <br> – User is redirected to the login page after logout. <br> – User cannot access protected pages via direct URL after logout. |
| 3 | **Product Inventory** | Users should be able to browse and view the product catalog. | High | – The inventory page loads successfully after login. <br> – All 6 products are displayed with image, name, description, and price. <br> – Product cards are fully visible and interactive. |
| 4 | **Product Sorting** | Users should be able to sort products by name and price. | Medium | – User can sort products A→Z (ascending name). <br> – User can sort products Z→A (descending name). <br> – User can sort products by price low-to-high. <br> – User can sort products by price high-to-low. <br> – The sorted order is verified to be correct. |
| 5 | **Add to Cart** | Users should be able to add products to the shopping cart from the inventory page. | High | – User can click "Add to Cart" on any product card. <br> – The shopping cart badge updates to reflect the correct item count. <br> – Multiple products can be added to the cart. |
| 6 | **Cart Management** | Users should be able to view and manage items in their cart. | High | – User can navigate to the cart page and see added products. <br> – Product name, description, and price are displayed correctly. <br> – User can remove individual items from the cart. <br> – User can continue shopping from the cart page. |
| 7 | **Checkout — Step One (Your Information)** | Users should be able to enter their shipping information during checkout. | High | – User can access the checkout form from the cart page. <br> – User can enter first name, last name, and zip/postal code. <br> – Validation errors are shown for missing required fields. <br> – User can dismiss the error dialog. <br> – User can cancel checkout and return to the cart. |
| 8 | **Checkout — Step Two (Order Summary)** | Users should be able to review their order summary before finalizing. | High | – The correct products and prices are displayed in the order summary. <br> – Subtotal is calculated correctly as the sum of item prices. <br> – Tax amount is calculated correctly (8% tax rate). <br> – Total price equals subtotal + tax. <br> – User can proceed to finalize the order. |
| 9 | **Checkout Complete (Order Confirmation)** | Users should receive confirmation after placing an order. | High | – The order confirmation page loads with a success message. <br> – The "Thank you for your order!" message is displayed. <br> – User can navigate back to the home/products page. |
| 10 | **Security — Direct URL Access Prevention** | Unauthorized users should not be able to access protected pages. | High | – Unauthenticated users accessing the inventory page directly are redirected to login. <br> – Logged-out users accessing the inventory page directly are redirected to login. <br> – Unauthenticated users accessing the checkout page directly are redirected to login. |

---

## Detailed Requirements

---

### 1. Login

#### User Stories

- As a **new user**, I want to be able to log in with valid credentials so that I can access the product catalog.
- As a **returning user**, I want to see an appropriate error message when I enter invalid credentials so that I know what went wrong.
- As a **locked-out user**, I want to see a clear message indicating my account is locked.
- As a **security-conscious user**, I want the system to reject login attempts with empty fields, leading/trailing spaces, special characters, and excessively long inputs.

#### Test Cases

| # | Test Case | Description | Severity | Test Data |
|---|-----------|-------------|----------|-----------|
| TC-L01 | Verify valid user login | Login with all 6 user types and verify redirection or error. | Critical | `standard_user`, `locked_out_user`, `problem_user`, `performance_glitch_user`, `error_user`, `visual_user` / password: `secret_sauce` |
| TC-L02 | Verify smoke user login | Quick login with standard_user and verify inventory page redirect. | Critical | `standard_user` / `secret_sauce` |
| TC-L03 | Verify empty fields | Submit empty username & password — error should contain "required". | Critical | `""` / `""` |
| TC-L04 | Verify empty username | Submit empty username — error should say "Username is required". | Critical | `""` / `sauce_demo` |
| TC-L05 | Verify empty password | Submit empty password — error should say "Password is required". | Critical | `standard_user` / `""` |
| TC-L06 | Verify leading space in username | Login with leading space — should fail. | Critical | `" standard_user"` / `secret_sauce` |
| TC-L07 | Verify trailing space in username | Login with trailing space — should fail. | Critical | `"standard_user "` / `secret_sauce` |
| TC-L08 | Verify case-sensitive username | Login with incorrect case — should fail. | Critical | `"Standard_user"` / `secret_sauce` |
| TC-L09 | Verify special characters | Login with special characters as username — should fail. | Critical | `"@#%^&*"` / `secret_sauce` |
| TC-L10 | Verify excessively long input | Login with a 200+ character username — should fail. | Critical | Long string / `secret_sauce` |
| TC-L11 | Verify error dialog close | Close the error dialog after failed login and verify dismissal. | Minor | `admin` / `admin` |
| TC-L12 | Verify direct URL access (unauthenticated) | Access inventory page directly without login — should redirect to login. | Normal | Direct URL: `/inventory.html` |
| TC-L13 | Verify direct URL access (after logout) | Login, logout, then access inventory page directly — should redirect to login. | Normal | `standard_user` / `secret_sauce` |

---

### 2. Product Inventory

#### User Stories

- As a **customer**, I want to see the products page load successfully after I log in so that I can start browsing.
- As a **customer**, I want to see all available products displayed with their name, description, price, and image.
- As a **customer**, I want to click on a product to navigate to its detail page.

#### Test Cases

| # | Test Case | Description | Severity |
|---|-----------|-------------|----------|
| TC-I01 | Verify inventory page load | Login and confirm the page title, sort dropdown, and inventory list are displayed. | Critical |
| TC-I02 | Verify product count | Confirm exactly 6 product cards are displayed. | Critical |
| TC-I03 | Verify product card elements | Verify each card shows image, name, description, price, and "Add to Cart" button. | Critical |
| TC-I04 | Verify product navigation | Click on "Sauce Labs Backpack" and verify redirect to product detail page URL. | Critical |

---

### 3. Product Sorting

#### User Stories

- As a **customer**, I want to sort products by name (A–Z and Z–A) so that I can quickly find what I need.
- As a **customer**, I want to sort products by price (low–high and high–low) so that I can shop within my budget.

#### Test Cases

| # | Test Case | Description | Severity |
|---|-----------|-------------|----------|
| TC-S01 | Verify A→Z sort | Sort by name ascending and verify the product list is alphabetically ordered. | Normal |
| TC-S02 | Verify Z→A sort | Sort by name descending and verify the product list is in reverse alphabetical order. | Normal |
| TC-S03 | Verify low-to-high price sort | Sort by price ascending and verify prices are in increasing order. | Normal |
| TC-S04 | Verify high-to-low price sort | Sort by price descending and verify prices are in decreasing order. | Normal |

---

### 4. Add to Cart

#### User Stories

- As a **customer**, I want to add products to my cart directly from the inventory page so that I can build my order.
- As a **customer**, I want to see the cart badge update with the number of items I've added.

#### Test Cases

| # | Test Case | Description | Severity |
|---|-----------|-------------|----------|
| TC-A01 | Verify adding products to cart | Add "Sauce Labs Backpack" and "Sauce Labs Bike Light" — verify badge shows count of 2. | Critical |
| TC-A02 | Verify cart badge display | After adding items, verify the cart badge element is displayed. | Critical |

---

### 5. Cart Management

#### User Stories

- As a **customer**, I want to view my cart and see all the items I have added.
- As a **customer**, I want to remove items from my cart if I change my mind.
- As a **customer**, I want to proceed to checkout from the cart page.

#### Test Cases

| # | Test Case | Description | Severity |
|---|-----------|-------------|----------|
| TC-C01 | Verify cart page load | Add a product, open cart, and verify the page loads with title and buttons. | Critical |
| TC-C02 | Verify product present in cart | Add "Sauce Labs Backpack", navigate to cart, and verify it appears. | Critical |
| TC-C03 | Verify multiple products in cart | Add "Sauce Labs Backpack" and "Sauce Labs Bike Light" individually and verify each is present. | Critical |
| TC-C04 | Verify remove single item | Add and then remove "Sauce Labs Backpack" — verify it no longer appears in cart. | Critical |
| TC-C05 | Verify remove multiple items | Add and remove each product individually — verify each is no longer present. | Critical |
| TC-C06 | Verify checkout navigation | Click "Checkout" from cart and verify redirect to checkout-step-one URL. | Normal |

---

### 6. Checkout — Step One (Your Information)

#### User Stories

- As a **customer**, I want to enter my shipping details so that my order can be delivered.
- As a **customer**, I want to see clear validation errors if I miss any required field.
- As a **customer**, I want to be able to cancel checkout and go back to my cart.

#### Test Cases

| # | Test Case | Description | Severity |
|---|-----------|-------------|----------|
| TC-CO1-01 | Verify checkout form page load | Navigate to checkout step one and verify the form and title are displayed. | Critical |
| TC-CO1-02 | Verify happy path form submission | Submit with valid data ("John", "Doe", "700001") — verify redirect to step two. | Critical |
| TC-CO1-03 | Verify missing first name | Submit without first name — error should contain "First Name". | Critical |
| TC-CO1-04 | Verify missing last name | Submit without last name — error should contain "Last Name". | Critical |
| TC-CO1-05 | Verify missing zip code | Submit without zip — error should contain "Postal Code". | Critical |
| TC-CO1-06 | Verify all fields empty | Submit all empty — error should contain "First Name". | Critical |
| TC-CO1-07 | Verify error dialog close | Trigger an error and close the dialog — verify it is dismissed. | Normal |
| TC-CO1-08 | Verify cancel navigation | Click "Cancel" — verify redirect back to cart page. | Critical |
| TC-CO1-09 | Verify proceed navigation | Submit valid form — verify redirect to checkout-step-two URL. | Critical |

---

### 7. Checkout — Step Two (Order Summary)

#### User Stories

- As a **customer**, I want to review my order with correct products and prices before finalizing.
- As a **customer**, I want the subtotal, tax, and total to be calculated correctly.
- As a **security-conscious user**, I want to be prevented from accessing the order summary page directly without completing prior steps.

#### Test Cases

| # | Test Case | Description | Severity |
|---|-----------|-------------|----------|
| TC-CO2-01 | Verify order summary page load | Complete checkout step one and verify the summary container is displayed. | Critical |
| TC-CO2-02 | Verify correct products in summary | Add "Sauce Labs Backpack" and "Sauce Labs Bike Light" — verify both appear in the order summary. | Critical |
| TC-CO2-03 | Verify subtotal calculation | Verify the displayed subtotal equals the sum of individual product prices. | Critical |
| TC-CO2-04 | Verify tax calculation | Verify the displayed tax equals subtotal × 8% (rounded up to nearest cent). | Critical |
| TC-CO2-05 | Verify total calculation | Verify the displayed total equals subtotal + tax. | Critical |
| TC-CO2-06 | Verify direct URL access prevention | Access checkout-step-two URL directly without login — verify redirect to login page. | Critical |

---

### 8. Checkout Complete (Order Confirmation)

#### User Stories

- As a **customer**, I want to see a confirmation message after my order is placed.
- As a **customer**, I want to easily navigate back to the products page after completing my purchase.

#### Test Cases

| # | Test Case | Description | Severity |
|---|-----------|-------------|----------|
| TC-CC-01 | Verify confirmation page load | Complete full checkout flow and verify the confirmation page loads with title, message, and back button. | Blocker |
| TC-CC-02 | Verify home page redirection | Click "Back Home" and verify redirect to the inventory page. | Normal |

---

## Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| Programming Language | Java | JDK 11+ |
| Build Tool | Apache Maven | 3.x |
| Test Framework | TestNG | 7.12.0 |
| Browser Automation | Selenium WebDriver | 4.40.0 |
| Driver Management | WebDriverManager | 6.3.3 |
| Reporting | Allure TestNG | 2.24.0 |
| AOP (Allure integration) | AspectJ Weaver | 1.9.20.1 |
| Design Pattern | Page Object Model (POM) | — |
| Browsers Supported | Chrome, Firefox, Edge | Latest |
| Execution Mode | Headless + Headed | — |
| Parallel Execution | TestNG (thread-count=4) | — |

---

## Project Architecture

```
saucedemo-automation/
├── pom.xml                         # Maven project configuration & dependencies
├── testng.xml                      # Complete test suite (all 6 test classes)
├── regression_test.xml             # Regression suite (grouped tests)
├── smoke_test.xml                  # Smoke suite (critical path tests)
├── src/
│   ├── main/java/
│   │   ├── base/
│   │   │   ├── BaseTest.java       # Test setup/teardown, driver initialization
│   │   │   └── DriverFactory.java  # ThreadLocal WebDriver factory (multi-browser)
│   │   ├── pages/
│   │   │   ├── LoginPage.java      # Login page actions & verifications
│   │   │   ├── InventoryPage.java  # Product catalog page actions
│   │   │   ├── CartPage.java       # Shopping cart page actions
│   │   │   ├── CheckoutOnePage.java    # Checkout form (Step 1)
│   │   │   ├── CheckoutTwoPage.java    # Order summary (Step 2)
│   │   │   └── CheckoutCompletePage.java # Order confirmation
│   │   └── utils/
│   │       └── ConfigReader.java   # Properties file reader utility
│   ├── main/resources/
│   │   └── config.properties       # Base URL, browser, wait configurations
│   └── test/java/tests/
│       ├── LoginTest.java          # 13 login test cases (data-driven)
│       ├── InventoryTest.java      # 7 inventory & sorting tests
│       ├── CartTests.java          # 6 cart management tests
│       ├── CheckoutOneTest.java    # 5 checkout form tests
│       ├── CheckoutTwoTest.java    # 5 order summary tests
│       └── CompleteCheckoutTest.java # 2 order confirmation tests
├── allure-results/                 # Generated Allure report data
└── logs/                           # Application logs
```

---

## Test Suite Configuration

### Complete Suite (`testng.xml`)
- Runs **all 6 test classes** in parallel at the class level with 4 threads.

### Regression Suite (`regression_test.xml`)
- Runs tests tagged with `@Test(groups = {"regressionTests"})` — covers comprehensive negative and edge-case scenarios.

### Smoke Suite (`smoke_test.xml`)
- Runs tests tagged with `@Test(groups = {"smokeTests"})` — covers critical happy-path scenarios (login + page load).

---

## Test Execution Summary

| Test Class | Test Count | Groups |
|------------|-----------|--------|
| LoginTest | 13 | smokeTests, regressionTests |
| InventoryTest | 7 | smokeTests, regressionTests |
| CartTests | 6 | — |
| CheckoutOneTest | 5 | — |
| CheckoutTwoTest | 5 | — |
| CompleteCheckoutTest | 2 | — |
| **Total** | **38** | |

---

## References

- **Application Under Test**: https://www.saucedemo.com/
- **Selenium WebDriver**: https://www.selenium.dev/
- **TestNG**: https://testng.org/
- **Allure Reporting**: https://allurereport.org/
- **WebDriverManager**: https://bonigarcia.dev/webdrivermanager/
- **Maven**: https://maven.apache.org/
