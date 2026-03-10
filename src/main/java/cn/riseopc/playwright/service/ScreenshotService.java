package cn.riseopc.playwright.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScreenshotService {

    private static final DateTimeFormatter FILE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    @Value("${app.screenshot-dir:screenshots}")
    private String screenshotDir;

    /**
     * 根据传入 URL 生成截图，保存到工程下配置的截图目录。
     *
     * @param url 目标网站 URL
     * @return 截图相对路径，例如 screenshots/20250309_153045_123.png
     */
    public String capture(String url) {
        try {
            Path dir = Paths.get(screenshotDir);
            Files.createDirectories(dir);

            String fileName = "screenshot_" + FILE_TIME_FORMATTER.format(LocalDateTime.now()) + ".png";
            Path filePath = dir.resolve(fileName);

            try (Playwright playwright = Playwright.create();
                 Browser browser = playwright.chromium().launch(
                         new BrowserType.LaunchOptions().setHeadless(true))) {

                Page page = browser.newPage();
                page.navigate(url);
                page.waitForLoadState();

                page.screenshot(new Page.ScreenshotOptions()
                        .setFullPage(true)
                        .setPath(filePath));
            }

            return filePath.toString().replace("\\", "/");
        } catch (Exception e) {
            throw new RuntimeException("生成截图失败，url=" + url, e);
        }
    }
}

