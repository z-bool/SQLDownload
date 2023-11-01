# SQLDownload - 内网大型数据下载的解决方案

内网大型数据拖库解决方案，隧道不稳定时将内网数据库保存为csv格式文件

**郑重声明：文中所涉及的技术、思路和工具仅供以安全为目的的学习交流使用，<u>任何人不得将其用于非法用途以及盈利等目的，否则后果自行承担</u>** 。

<p align="center"><a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/license-MIT-_red.svg"></a><a href="https://github.com/z-bool/SQLDownload"><img  src="https://goreportcard.com/badge/github.com/projectdiscovery/httpx"></a></p>

<p align="center"><a href="#install">工具介绍</a> · <a href="#tall">使用说明</a> · <a href="#notice">注意事项</a> · <a href="#communicate">技术交流</a></p>

<div id="install"></div>

<h3>工具介绍</h3>

该工具诞生背景：

在打内网时候会有隧道不稳定情况无论是数据读取还是数据下载都会被影响(人穷vps垃圾)，所以为了解决这个问题，写了这个小工具内网本地执行，为应对多种场景提供多种选项解决。

该工具应用场景：

1. 数据库取证
2. 数据库读取/下载
3. 联表导出

该工具的使用方案：

1. 配置文件读取`info.txt` (优先)
2. 终端交互写入
3. 配置与终端混合交互

<div id= "tall"></div>

<h3>使用说明</h3>

**配置文件导入**：

<small>在当前目录下保存一个info.txt</small>

```txt
# jdbc mysql url
db_url=jdbc:mysql://localhost:3306/a
# jdbc mysql username
db_user=root
# jdbc mysql password
db_passwd=123456
# function mode
db_function=1
```

![image-20231101102158773](https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101102158773.png)

**交互模式导入**:

<small>无配置文件情况下</small>



