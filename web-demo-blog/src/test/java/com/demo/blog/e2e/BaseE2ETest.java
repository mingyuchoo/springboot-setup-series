// [REQ-N009] Playwright E2E 테스트 베이스 클래스 (2026-02-07)
package com.demo.blog.e2e;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("e2e")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseE2ETest {

    @LocalServerPort
    int port;

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    @AfterAll
    void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        if (context != null) context.close();
    }

    /** 베이스 URL */
    String baseUrl() {
        return "http://localhost:" + port;
    }

    /** 특정 경로로 네비게이션 */
    void navigateTo(String path) {
        page.navigate(baseUrl() + path);
    }

    /** 로그인 수행 */
    void login(String username, String password) {
        navigateTo("/auth/login");
        page.locator("input#username").fill(username);
        page.locator("input#password").fill(password);
        page.locator("button[type=submit]").click();
        page.waitForURL(baseUrl() + "/posts");
    }

    /** 기본 계정(user1/password)으로 로그인 */
    void loginAsUser1() {
        login("user1", "password");
    }

    /** JavaScript confirm 다이얼로그 자동 수락 */
    void acceptNextDialog() {
        page.onDialog(dialog -> dialog.accept());
    }
}
