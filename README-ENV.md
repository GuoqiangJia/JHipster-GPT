# 准备环境
## JDK环境准备
从以下地址下载JDK-17且解压，完成后配置本机的Java开发环境：
https://download.java.net/openjdk/jdk17/ri/openjdk-17+35_windows-x64_bin.zip
完成后用以下命令确认JDK环境以及正确就绪：
```
java -version
```

## 准备NodeJS环境
从以下地址中下载并且安装Node.js-v18，并且配置环境变量：
https://nodejs.org/en/download
完成后用以下命令确认NodeJS正确就绪：
```
node -v
npm -v
```

## 准备git环境
参照以下教程完成git的安装：
https://git-scm.com/download/win

## 准备生产环境数据库（postgresql）
从以下链接下载数据库且安装：

https://www.enterprisedb.com/postgresql-tutorial-resources-training-1?uuid=c70fc67b-ca1f-4dc2-b73b-ccb7367fb6b8&campaignId=Product_Trial_PostgreSQL_15
安装完成后，可以在pgAdmin客户端测试数据库是否正确安装                         

# 安装Jhipster 
在Terminal中运行以下命令：
```
npm install -g generator-jhipster
```

# 准备jhipster需要的jh文件
## 在Claude中，用如下prompt生成jh文件的内容
```
你是一个高级Java工程师，精通Jhipster。以下是软件开发的需求分析，请根据需求分析生成jhipster的jdl文件内容，并且自动生成实体的必要属性：

我在开发一款电子商务网站的<后端管理软件>
1.用户在登陆后端管理软件后，可以管理<商品>，<客户>，<地址>，<订单>，<快递>
2.<商品>有一个属性是<类别>，一个<商品>可包含多个<类别>，一个<类别>也可对应多个<商品>
3.<类别>其中的一个属性是<类别状态>，是一个枚举，包含AVAILABLE, RESTRICTED, DISABLED这几个状态
4.一个<客户>可以有多个<地址>
5.一个<客户>可以有多个<订单>
6.一个<订单>可以包含多个<商品>
7.一个<快递>可以包含多个<订单>

以下是<后端管理软件>其他的系统要求：
1.包名是com.genhot.shopper
2.生产环境数据库使用的是postgresql
3.权限认证使用jwt
4.客户端开发使用react
5.服务端口号是8081
6.需要reactive是true
7.默认语言是英文，可切换的语言包含中文和英文
8.全部实体使用dto传输数据

所以，根据以上需求描述和系统要求，对应的jhipster的jdl文件的内容是：
```
## 保存生成的内容到shopper.jh文件
在你的工作目录下创建一个shopper的目录，并且创建shopper.jh文件，然后将claude生成的jh文件内容保存到该目录下的shopper.jh文件中

# 创建工程并跑起来
## 创建工程
用如下命令生成shopper工程，过程可能会花费数分钟时间
```
jhipster jdl shopper.jh
```
## 开发环境跑起来
在自动创建好的工程目录下，分别运行如下命令
```
run 'mvnw' to start the backend application
run 'npm start' to start the frontend application
```

在应用启动成功后，通过http://localhost:9000做测试