# Test Plan — SauceDemo Web Application

---

## Table of Contents

1. [Objective](#objective)
2. [Scope](#scope)
3. [Inclusions](#inclusions)
4. [Test Environments](#test-environments)
5. [Defect Reporting Procedure](#defect-reporting-procedure)
6. [Test Strategy](#test-strategy)
7. [Test Schedule](#test-schedule)
8. [Test Deliverables](#test-deliverables)
9. [Entry and Exit Criteria](#entry-and-exit-criteria)
10. [Tools](#tools)
11. [Risks and Mitigations](#risks-and-mitigations)
12. [Approvals](#approvals)

---

## Objective

The objective of this test plan is to ensure that the **SauceDemo e-commerce web application** (https://www.saucedemo.com/) meets all functional requirements, provides a seamless and user-friendly shopping experience, and behaves correctly across all supported browsers and execution modes.

This test plan covers the **automated testing** of the application using a Selenium WebDriver–based framework built with:

- **Java** (JDK 11+)
- **Selenium WebDriver** 4.40.0
- **TestNG** 7.12.0
- **Maven** (Build & Dependency Management)
- **Allure Reporting** 2.24.0
- **WebDriverManager** 6.3.3

---

## Scope

The scope of this test plan includes the following functional areas of the SauceDemo application:

- **Login and Authentication** — Valid login, invalid login, locked-out users, input validation, error handling, and session security.
- **Product Inventory & Browsing** — Page load verification, product card display, product count validation, and product detail navigation.
- **Product Sorting** — Sorting by name (A→Z, Z→A) and by price (low→high, high→low).
- **Add to Cart** — Adding single/multiple products to the cart, cart badge updates.
- **Cart Management** — Viewing cart contents, removing items, continue shopping, and proceeding to checkout.
- **Checkout — Step One (Your Information)** — Form validation, required field errors, error dialog dismissal, cancel and proceed navigation.
- **Checkout — Step Two (Order Summary)** — Product verification, subtotal/tax/total calculations, direct URL access prevention.
- **Checkout Complete (Order Confirmation)** — Confirmation page load, success message, back-to-home navigation.
- **Security** — Direct URL access prevention for unauthenticated and logged-out users.

### Out of Scope

The following areas are **not** covered by this test plan:

- Performance/Load testing
- API testing
- Mobile-specific responsive testing
- Accessibility (WCAG) testing
- Database-level testing
- Payment gateway integration (SauceDemo does not have real payment processing)

---

## Inclusions

The following items are included in this test plan:

- Test Strategy document
- Test Cases document (38 automated test cases)
- Test Execution reports (Allure Reports)
- Defect reports
- Test Suite configurations (Complete, Regression, Smoke)
- System Requirement Specification (SRS) document

---

## Test Environments

### Application Environment

| Name | Environment URL |
|------|----------------|
| Production (AUT) | https://www.saucedemo.com/ |

### Supported Browsers & Operating Systems

| Operating System | Browsers |
|-----------------|----------|
| Windows 10/11 | Chrome (Latest), Firefox (Latest), Edge (Latest) |
| macOS | Chrome (Latest), Firefox (Latest), Safari (Latest) |

### Execution Modes

| Mode | Description |
|------|-------------|
| Headless | Default mode — browser runs without GUI for CI/CD pipelines and faster execution |
| Headed | Browser GUI visible — used for debugging and exploratory testing |

### Hardware Requirements

| Component | Minimum Requirement |
|-----------|-------------------|
| Processor | Dual-core 2.0 GHz or higher |
| RAM | 4 GB minimum, 8 GB recommended |
| Storage | 500 MB free disk space |
| Network | Stable internet connection required (application is web-hosted) |

### Software Requirements

| Software | Version |
|----------|---------|
| Java JDK | 11 or higher |
| Apache Maven | 3.x |
| Git | Latest |
| IDE (optional) | IntelliJ IDEA / Eclipse |

### Test User Credentials

| Username | Password | Expected Behavior |
|----------|----------|--------------------|
| `standard_user` | `secret_sauce` | Successful login |
| `locked_out_user` | `secret_sauce` | Login denied — locked account |
| `problem_user` | `secret_sauce` | Login succeeds — UI glitches present |
| `performance_glitch_user` | `secret_sauce` | Login succeeds — slow response times |
| `error_user` | `secret_sauce` | Login succeeds — intermittent errors |
| `visual_user` | `secret_sauce` | Login succeeds — visual inconsistencies |

---

## Defect Reporting Procedure

### Defect Identification Criteria

A defect is identified when any of the following conditions are met:

- Deviation from the expected behavior defined in the SRS / acceptance criteria
- UI elements not rendering or behaving as expected
- Incorrect calculations (subtotal, tax, total)
- Broken navigation or incorrect URL redirections
- Security violations (e.g., unauthorized direct URL access)
- Error messages not displayed or displaying incorrectly

### Defect Reporting Steps

1. **Reproduce** the defect and confirm it is consistent.
2. **Document** using the defect report template with:
   - Title and summary
   - Environment details (browser, OS, execution mode)
   - Steps to reproduce
   - Expected vs. actual result
   - Screenshots / Allure report links
   - Severity and Priority levels
3. **Log** the defect in the bug tracking tool.
4. **Assign** to the appropriate team member for investigation.

### Defect Severity Levels

| Severity | Description |
|----------|-------------|
| **Blocker** | Application crash, complete feature failure, no workaround |
| **Critical** | Major feature broken, significant impact on user flow |
| **Normal** | Feature works but with issues, workaround available |
| **Minor** | Cosmetic issues, low-impact UI glitches |

### Defect Lifecycle

```
New → Assigned → In Progress → Fixed → Retest → Closed
                                  ↓
                              Reopened (if retest fails)
```

### Defect Tracking

| Area | POC |
|------|-----|
| Test Automation Framework | Debanjan Chowdhury |
| Frontend UI Issues | Development Team |
| Backend / API Issues | Development Team |

**Tool**: JIRA / GitHub Issues

---

## Test Strategy

### Strategy Overview

| Component | Description |
|-----------|-------------|
| **Objectives** | Validate all functional requirements of the SauceDemo application through automated UI testing. Ensure correctness, reliability, and security of all user workflows. |
| **Test Levels** | **System Testing** — End-to-end validation of the complete application. **Integration Testing** — Verifying page-to-page navigation and data flow across the checkout pipeline. |
| **Test Types** | **Functional Testing** — Verify each feature works per requirements. **Regression Testing** — Ensure existing features remain intact after changes. **Smoke Testing** — Quick validation of critical paths. **Negative Testing** — Verify proper error handling for invalid inputs. **Security Testing** — Validate direct URL access prevention for unauthenticated users. |
| **Test Techniques** | **Equivalence Class Partition** — Grouping valid/invalid user types. **Boundary Value Analysis** — Edge cases for input fields (empty, special chars, long strings). **Decision Table Testing** — Login with various credential combinations. **State Transition Testing** — User session states (logged-in, logged-out, locked). **Use Case Testing** — End-to-end purchase flows. **Error Guessing** — Based on expertise (leading spaces, special characters). |
| **Test Deliverables** | Test Plan, Test Cases, Test Execution Reports (Allure), Defect Reports, SRS Document. |
| **Test Environment** | Multi-browser (Chrome, Firefox, Edge), headless & headed modes, parallel execution with 4 threads. |
| **Resource Allocation** | Debanjan Chowdhury — Test Automation Lead, Framework Development, Test Execution & Reporting. |
| **Risk Management** | See [Risks and Mitigations](#risks-and-mitigations) section. |
| **Test Exit Criteria** | See [Entry and Exit Criteria](#entry-and-exit-criteria) section. |

### Test Design Approach

**Step 1 — Test Case Creation:**

- Create test scenarios and test cases for all features in scope.
- Apply test design techniques:
  - Equivalence Class Partition
  - Boundary Value Analysis
  - Decision Table Testing
  - State Transition Testing
  - Use Case Testing
- Additionally apply:
  - Error Guessing
  - Exploratory Testing
- Prioritize test cases by severity (Blocker > Critical > Normal > Minor).

**Step 2 — Test Execution Procedure:**

1. First, conduct **Smoke Testing** to verify that critical functionalities (login, page load) are working.
2. If Smoke Testing fails, reject the build and wait for a stable version.
3. Once the build passes Smoke Testing, perform in-depth testing using all created test cases.
4. Execute tests across **multiple supported browsers simultaneously** using parallel execution (4 threads).
5. Report defects in the tracking tool and communicate daily status.

**Step 3 — Testing Best Practices:**

- **Context-Driven Testing** — Testing tailored to the SauceDemo application's specific features and constraints.
- **Shift-Left Testing** — Test execution starts from the earliest development stages, not waiting for a final build.
- **Exploratory Testing** — Leveraging QA expertise to discover edge cases beyond scripted test cases.
- **End-to-End Flow Testing** — Validating the complete user journey: Login → Browse → Add to Cart → Checkout → Order Confirmation.

### Types of Testing Performed

| Testing Type | Description |
|-------------|-------------|
| Smoke Testing | Quick validation of login and inventory page load |
| Sanity Testing | Focused check on specific modules after fixes |
| Regression Testing | Full suite execution to catch unintended side effects |
| Functional Testing | Feature-level verification of all 8 modules |
| UI Testing | Verification of page elements, buttons, and navigation |
| Negative Testing | Invalid inputs, empty fields, special characters |
| Security Testing | Direct URL access prevention, session management |
| Data-Driven Testing | Multiple input combinations via TestNG DataProviders |

---

## Test Schedule

Following is the test schedule planned for the project:

| Task | Duration | Dates |
|------|----------|-------|
| Creating Test Plan | 1 day | Sprint 1 — Day 1 |
| SRS / Requirement Analysis | 1 day | Sprint 1 — Day 1–2 |
| Test Case Design & Creation | 3 days | Sprint 1 — Day 2–4 |
| Framework Setup (POM, Drivers, Config) | 2 days | Sprint 1 — Day 3–4 |
| Test Case Automation | 5 days | Sprint 1 — Day 5 to Sprint 2 — Day 2 |
| Test Execution (Smoke Suite) | 1 day | Sprint 2 — Day 3 |
| Test Execution (Regression Suite) | 2 days | Sprint 2 — Day 3–4 |
| Test Execution (Complete Suite) | 1 day | Sprint 2 — Day 5 |
| Defect Reporting & Retesting | Ongoing | Sprint 2 |
| Summary Reports & Test Closure | 1 day | Sprint 2 — Day 5 |

> **Total Estimated Duration**: 2 Sprints to test the application.

---

## Test Deliverables

The following deliverables will be produced as part of this testing effort:

| # | Deliverable | Format | Status |
|---|-------------|--------|--------|
| 1 | Test Plan Document | Markdown (.md) | ✅ Complete |
| 2 | System Requirement Specification (SRS) | Markdown (.md) | ✅ Complete |
| 3 | Automated Test Scripts | Java (TestNG) | ✅ Complete |
| 4 | Test Execution Report | Allure HTML Report | Generated per run |
| 5 | Defect Report | JIRA / GitHub Issues | As needed |
| 6 | Test Summary Report | Markdown / PDF | End of Sprint 2 |

---

## Entry and Exit Criteria

### Requirement Analysis

**Entry Criteria:**
- The testing team receives the requirement documents (SRS) or details about the SauceDemo application.
- Access to the application URL (https://www.saucedemo.com/) is available.

**Exit Criteria:**
- All requirements are explored and understood by the testing team.
- User stories and acceptance criteria are reviewed.
- Doubts and ambiguities are clarified.

---

### Test Execution

**Entry Criteria:**
- Test scenarios and test cases are created and reviewed.
- Test automation framework is set up and functional.
- Application build is stable (passes Smoke Testing).
- Test environment and browser drivers are configured.

**Exit Criteria:**
- All test cases have been executed across supported browsers.
- Test execution reports (Allure) are generated.
- All defects are logged with appropriate severity/priority.
- Defect reports are ready for review.

---

### Test Closure

**Entry Criteria:**
- Test case execution reports and defect reports are ready.
- All critical and blocker defects are resolved and retested.

**Exit Criteria:**
- Test Summary Report is prepared and submitted.
- All defects have been resolved or deferred with justification.
- Test artifacts are archived for future reference.
- Sign-off from stakeholders is obtained.

---

## Tools

The following tools are used in this project:

| Tool | Purpose |
|------|---------|
| **IntelliJ IDEA** | IDE for Java development and test authoring |
| **Apache Maven** | Build automation and dependency management |
| **Selenium WebDriver** | Browser automation for UI testing |
| **TestNG** | Test framework for execution, grouping, and data-driven tests |
| **Allure Report** | Rich HTML test execution reporting with screenshots |
| **WebDriverManager** | Automatic browser driver management |
| **Git / GitHub** | Version control and source code management |
| **JIRA / GitHub Issues** | Bug/defect tracking and project management |
| **Snipping Tool / Lightshot** | Screenshot capture for defect documentation |
| **Chrome DevTools** | Browser debugging and element inspection |

---

## Risks and Mitigations

The following are potential risks and their mitigation strategies:

| # | Risk | Impact | Mitigation |
|---|------|--------|------------|
| 1 | **Non-availability of a resource** | Testing delayed | Backup resource planning; cross-train team members on the framework |
| 2 | **Application URL not accessible** | Testing blocked | Resources will work on framework improvements, documentation, or other tasks; notify stakeholders immediately |
| 3 | **Less time for testing** | Incomplete coverage | Ramp up resources dynamically; prioritize critical test cases; leverage parallel execution |
| 4 | **Browser version incompatibility** | Flaky tests | Use WebDriverManager for automatic driver version management; test on stable browser releases |
| 5 | **Frequent UI changes in AUT** | Test maintenance overhead | Page Object Model (POM) pattern minimizes locator changes; centralized locator management |
| 6 | **Network latency / instability** | Timeouts and false failures | Implement explicit waits (WebDriverWait) and retry mechanisms; configure appropriate timeout values |
| 7 | **Test data dependency** | Tests fail due to stale state | Use `resetAppState()` before tests; each test starts with fresh login and clean state |
| 8 | **Parallel execution conflicts** | Thread-safety issues | ThreadLocal WebDriver via DriverFactory ensures isolated browser sessions per thread |

---

## Approvals

The team will send the following documents for client/stakeholder approval:

| # | Document | Status |
|---|----------|--------|
| 1 | Test Plan | 📝 Pending Approval |
| 2 | System Requirement Specification (SRS) | 📝 Pending Approval |
| 3 | Test Scenarios & Test Cases | 📝 Pending Approval |
| 4 | Test Execution Reports | Generated per test run |
| 5 | Test Summary Report | Produced at test closure |

> **Note**: Testing will only proceed to the next phase once the above approvals are obtained.

---

### Prepared By

| Field | Details |
|-------|---------|
| **Author** | Debanjan Chowdhury |
| **Project** | SauceDemo Web Automation |
| **Version** | 1.0 |
| **Date** | February 28, 2026 |
