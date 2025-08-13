# LogFilter

A lightweight log filtering plugin for Minecraft servers with regex support.

## Features

- **Regex Pattern Matching**: Filter logs using Java regular expressions
- **Hot Reload**: Apply config changes without server restart (`/logfilter reload`)
- **Multi-Core Support**: Compatible with Folia, Paper, Bukkit, Purpur, Spigot

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/logfilter` | Show help menu | `logfilter.admin` |
| `/logfilter reload` | Reload configuration file | `logfilter.admin` |

## Permissions
logfilter.admin - Access to all commands (OP by default)

## Configuration

Config file (`plugins/LogFilter/config.yml`) will be generated on first run:

```yaml
# List of regex patterns to filter from logs
filter-patterns:
  - ".*Example filter pattern.*"
  - ".*Another example.*"
```

--------------------------------------------------

一个轻量级的 Minecraft 服务器日志过滤插件，支持正则表达式匹配。  

## 功能特性  

✅ **正则表达式过滤** - 使用 Java 正则表达式精准匹配日志内容  
✅ **热重载配置** - 修改规则后无需重启服务器，执行 `/logfilter reload` 即可生效  
✅ **多核心兼容** - 支持 Folia、Paper、Spigot、Bukkit、Purpur 等主流服务端  

## 命令说明  

| 命令 | 描述 | 权限节点 |  
|------|------|----------|  
| `/logfilter` | 显示插件帮助信息 | `logfilter.admin` |  
| `/logfilter reload` | 重新加载配置文件 | `logfilter.admin` |  

## 权限配置  

▸ 默认 OP 自动拥有以下权限：  
- `logfilter.admin` - 使用所有插件命令  

## 配置文件  

插件目录：`plugins/LogFilter/config.yml`  

```yaml
# 日志过滤规则（支持正则表达式）
filter-patterns:
filter-patterns:
  - ".*Example filter pattern.*"
  - ".*Another example.*"
```

### bStats
![bStats](https://bstats.org/signatures/bukkit/LogFilter.svg)
