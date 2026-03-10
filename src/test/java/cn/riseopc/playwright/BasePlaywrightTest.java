package cn.riseopc.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * 基础 Playwright 测试基类
 */
public abstract class BasePlaywrightTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    @BeforeAll
    static void setUp() {
        boolean headless = resolveHeadlessMode();
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(headless)
        );
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(resolveViewportWidth(), resolveViewportHeight())
                .setDeviceScaleFactor(resolveDeviceScaleFactor()));
        page = context.newPage();
    }

    private static boolean resolveHeadlessMode() {
        String pwHeadless = System.getenv("PW_HEADLESS");
        if (pwHeadless != null && !pwHeadless.isBlank()) {
            return Boolean.parseBoolean(pwHeadless.trim());
        }

        // 容器/CI 环境默认 headless，避免依赖图形界面
        String ci = System.getenv("CI");
        return ci != null && !ci.isBlank() && Boolean.parseBoolean(ci.trim());
    }

    private static int resolveViewportWidth() {
        return resolveIntEnv("PW_VIEWPORT_WIDTH", 1920);
    }

    private static int resolveViewportHeight() {
        return resolveIntEnv("PW_VIEWPORT_HEIGHT", 1080);
    }

    private static double resolveDeviceScaleFactor() {
        return resolveDoubleEnv("PW_DEVICE_SCALE_FACTOR", 2.0);
    }

    private static int resolveIntEnv(String name, int defaultValue) {
        String v = System.getenv(name);
        if (v == null || v.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static double resolveDoubleEnv(String name, double defaultValue) {
        String v = System.getenv(name);
        if (v == null || v.isBlank()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(v.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @AfterAll
    static void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}

