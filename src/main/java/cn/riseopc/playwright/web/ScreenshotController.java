package cn.riseopc.playwright.web;

import cn.riseopc.playwright.service.ScreenshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScreenshotController {

    private final ScreenshotService screenshotService;

    /**
     * 示例：
     * GET /api/screenshot?url=https://www.baidu.com
     */
    @GetMapping("/screenshot")
    public ResponseEntity<Map<String, String>> capture(@RequestParam("url") String url) {
        log.info("收到截图请求, url={}", url);

        String path = screenshotService.capture(url);

        log.debug("截图已生成, url={}, path={}", url, path);

        return ResponseEntity.ok(Map.of(
                "url", url,
                "screenshotPath", path
        ));
    }
}

