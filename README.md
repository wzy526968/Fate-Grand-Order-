# 人理保障机构 CHALDEA 管理系统

> 基于 Fate/Grand Order 世界观的全栈管理系统

## 技术栈

### 前端

| 技术 | 用途 |
|------|------|
| Vue 3 (Composition API) | UI 框架 |
| Vite | 构建工具 |
| Pinia | 状态管理 |
| Vue Router | 路由 |
| Axios | HTTP 客户端 |
| ECharts 6 / ECharts-GL | 数据可视化 / 3D 图表 |
| Three.js | 3D 渲染（地球仪页面） |

### 后端

| 技术 | 用途 |
|------|------|
| Java 21 | 语言 |
| Spring Boot 3.2.5 | Web 框架 |
| Spring Data JPA | 数据库 ORM |
| Spring Security | 认证与授权 |
| MySQL | 数据库 |
| JJWT 0.12.5 | JWT 令牌 |

## 项目结构

```
FATE/
├── src/                  # Vue 3 前端源码
│   ├── api/              # Axios 实例与 API 调用
│   ├── components/       # 公共组件
│   ├── router/           # 路由配置
│   ├── stores/           # Pinia 状态管理
│   ├── views/            # 页面视图
│   └── utils/            # 工具函数
├── backend/              # Spring Boot 后端
│   └── src/main/java/com/chaldea/
│       ├── config/       # 安全、CORS、数据初始化配置
│       ├── controller/   # REST 控制器
│       ├── dto/          # 数据传输对象
│       ├── exception/    # 全局异常处理
│       ├── model/        # JPA 实体
│       ├── repository/   # 数据访问层
│       ├── security/     # JWT 认证过滤器与提供者
│       └── service/      # 业务逻辑层
├── index.html            # 前端入口 HTML
├── package.json          # npm 依赖配置
└── vite.config.js        # Vite 构建配置
```

## 快速开始

### 前置要求

- Node.js >= 18
- JDK 21
- Maven >= 3.6
- MySQL 8.0+

### 1. 创建数据库

```sql
CREATE DATABASE chaldea DEFAULT CHARACTER SET utf8mb4;
```

### 2. 配置后端

编辑 `backend/src/main/resources/application.yml`，修改数据库用户名和密码：

```yaml
spring:
  datasource:
    username: your_db_username
    password: your_db_password
```

同时建议修改 JWT 密钥：

```yaml
jwt:
  secret: your-own-secret-key-here
```

### 3. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端默认运行在 `http://localhost:8080`。

### 4. 启动前端

```bash
# 在项目根目录
npm install
npm run dev
```

前端默认运行在 `http://localhost:5173`。

### 5. 访问系统

打开浏览器访问 `http://localhost:5173`。

## 默认账户

系统启动时会自动初始化测试数据，包含以下默认账户：

| 用户名 | 角色 |
|--------|------|
| `admin` | 管理员 (ADMIN) |
| `master` | 御主 (USER) |

（详细账户信息请查看 `backend/.../config/DataInitializer.java`）

## 功能模块

- **登录认证** — JWT 令牌认证，自动过期处理
- **从者管理** — 从者数据展示与雷达图分析
- **人员管理** — 员工信息 CRUD
- **技术设施** — 模拟迦勒底各项技术设备
- **特异点 / 异闻带** — 时间线可视化
- **个人档案** — 用户资料与头像上传

## 截图

> 待添加

## 许可证

[MIT](LICENSE)
