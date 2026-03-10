# Playwright Java Demo

[![Java](https://img.shields.io/badge/Java-21-orange)](https://adoptium.net/)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue)](https://maven.apache.org/)
[![Playwright](https://img.shields.io/badge/Playwright-Java-green)](https://playwright.dev/java/)
[![License](https://img.shields.io/badge/license-MIT-lightgrey)](LICENSE)

基于 **Java 21 + Maven + Playwright for Java** 的简单示例工程，用来体验和验证 Playwright 的功能。

## 技术栈

- **语言**：Java 21
- **构建工具**：Maven 3.x
- **测试框架**：JUnit 5
- **浏览器自动化**：Playwright for Java

## 项目结构（核心部分）

- `pom.xml`：Maven 配置，包含 Playwright、JUnit5 等依赖

## 本地运行

在项目根目录执行（首次使用建议先确保浏览器已安装）：

```bash
mvn test
```

执行成功后，你会在项目根目录看到一张截图文件：

- `baidu-home.png`：访问 `https://www.baidu.com/` 后生成的整页截图

如果你尚未安装 Playwright 浏览器，可以先执行一次（可选）：

```bash
mvn -Dplaywright.skipBrowserDownload=false test
```

Playwright 会自动拉取所需浏览器。

## 基于 Docker 运行（推荐：docker compose）

本项目已提供 `docker-compose.yml`，会使用官方 Playwright Java 镜像（包含浏览器依赖），在容器内执行 `mvn test`。

前置条件：

- 已安装 Docker Desktop（并启用 Docker Compose）

在项目根目录执行：

```bash
docker compose down --remove-orphans
docker compose up --abort-on-container-exit --exit-code-from playwright-java-demo
```

执行成功后，你会在项目根目录看到截图文件：

- `screenshots/baidu-home.png`

### 调整截图清晰度（Viewport / DPR）

截图清晰度主要由浏览器上下文的视口大小（Viewport）和设备像素比（DPR / deviceScaleFactor）决定。

你可以在 `docker-compose.yml` 的 `environment` 中调整：

- `PW_VIEWPORT_WIDTH`（默认 1920）
- `PW_VIEWPORT_HEIGHT`（默认 1080）
- `PW_DEVICE_SCALE_FACTOR`（默认 2）

修改后重新运行 compose 即可生效。


更多 Playwright Java 文档可参考：[Playwright Java 官方文档](https://playwright.dev/java/docs/intro)
