package cn.riseopc.playwright;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 打开百度首页并截图到工程下的 screenshots 目录。
 */
public class PlaywrightDemoTest extends BasePlaywrightTest {

    @Test
    @DisplayName("打开百度并截图")
    void openBaiduAndScreenshot() {
        // 访问百度
        page.navigate("https://www.baidu.com/");
        page.waitForLoadState(); // 等页面加载完成

        try {
            // 确保目标目录存在：screenshots/
            Path dir = Paths.get("screenshots");
            Files.createDirectories(dir);

            // 截图保存到工程下的 screenshots/baidu-home.png
            page.screenshot(new Page.ScreenshotOptions()
                    .setFullPage(true)
                    .setPath(dir.resolve("baidu-home.png")));
        } catch (Exception e) {
            throw new RuntimeException("保存截图失败", e);
        }
    }
}


