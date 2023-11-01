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

![image-20231101103319629](https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101103319629.png)

**混合模式:**

<small>文本配置下，部分不填，解决连接固定减少多次输入的情况，通过交互来解决。</small>

```txt
# jdbc mysql url
db_url=jdbc:mysql://localhost:3306/a
# jdbc mysql username
db_user=root
# jdbc mysql password
db_passwd=123456
# function mode
db_function=
```

![image-20231101104142247](https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101104142247.png)

<img src="https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101104212439.png" alt="image-20231101104212439" style="zoom: 80%;" />

将每张表保存拖到当前目录下对应表名的csv文件中。

**功能介绍**:

1. Retrieve the names of all database repositories under the connection. (查询当前连接的所有数据库名)
2. Read all tables and fields of the specified database.(查询当前数据库的所有表和字段)
3. Drag all the contents of the specified library.(下载当前数据库的所有表数据)
4. Terminal prints the first 50 records of SQL execution results for debugging purposes.(限制下的SQL查询，结果只显示50条且展示在终端上)
5. Save SQL execution results with unlimited queries.(无限制的SQL查询，结果保存在当前目录下的6位随机名字的csv文件中)

**SQL功能举例**:

<small>限制下的SQL执行展示</small>

![image-20231101105055692](https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101105055692.png)

<small>无限制下的SQL结果展示</small>

![image-20231101105227888](https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101105227888.png)

![image-20231101105241607](https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101105241607.png)

![image-20231101105305861](https://cdn.jsdelivr.net/gh/z-bool/images@master/img/image-20231101105305861.png)

<div id="notice"></div>

<h3>注意事项</h3>

合理利用，能用连接工具就没必要上传这。

<div id="communicate"></div>

<h3>技术交流</h3>

<img src="https://cdn.jsdelivr.net/gh/z-bool/images@master/img/qrcode_for_gh_c90beef1e2e7_258.jpg" alt="阿呆攻防公众号" style="zoom:100%;" />
