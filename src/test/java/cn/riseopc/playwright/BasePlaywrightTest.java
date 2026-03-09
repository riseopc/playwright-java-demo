package cn.riseopc.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * 基础 Playwright 测试基类
 */
public abstract class BasePlaywrightTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static Page page;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false) // 方便你观察浏览器行为
        );
        page = browser.newPage();
    }

    @AfterAll
    static void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}

