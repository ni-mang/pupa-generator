<a name="DIKR0"></a>
## 常用代码生成工具
开发工作中，为了节省时间，一般会使用代码生成工具来生成一些基础代码，如entity、vo、bo等，根据个人使用经验，将常用的代码生成方式归为以下三种。<br />

第一种，基于ORM框架的代码生成功能，如Mybatis。
- <font color=#5C8D07>优点</font>：无需运行额外项目，只要引入相应插件（如mybatis-generator-core），配置数据源、生成路径等信息即可生成代码，简单方便；
- <font color=#DF2A3F>缺点</font>：如果想要生成自定义代码内容，就需要进行比较复杂的配置，技术要求较高，需要手动移植相关配置才能在其它项目使用，不具备团队内成员间分享的便捷性。

第二种，选用第三方开发平台作为项目主体时，平台自带的代码生成器。

- <font color=#5C8D07>优点</font>：已预设相关模板，无需过多配置生成规则，能够快捷生成与平台契合的前后端代码；
- <font color=#DF2A3F>缺点</font>：通常随业务代码一起下载至本地，需要额外启动代码生成器，如要生成自定义内容，需要对平台预设模板进行改动，一旦提交至代码仓库，或单独部署团队公用，则可能影响其他项目成员生成的代码，同时也无法应对多项目定制生成的场景。

第三种，使用三方提供的代码生成器，这类工具繁多。

- <font color=#5C8D07>优点</font>：高自由度，可根据实际需求调整相应模板，操作简单，以zip包的形式下载代码，自行选择需要的文件复制到项目中，降低项目代码被意外覆盖的风险；
- <font color=#DF2A3F>缺点</font>：生成代码时使用的参数内置于系统中，只能通过修改生成器代码进行调整，可能需要针对不同项目定制一套生成器，增加了工作量。

总而言之，常见的代码生成工具中，大体存在的一些缺点是：配置复杂，内置参数不直观，自定义扩展繁琐，部署运行麻烦，不便于组内管理及进行个人定制，不适用于多数据源项目，不支持同时生成多语言数据类型映射，支持的模板语言较为单一等。
<a name="IRr9u"></a>
## PUPA简介
蛹，蜕变的开始，新的起点。<br /><br />
部件（仅罗列开发时使用的版本）：
> - JDK版本：JDK 1.8
> - 数据库：Mysql 5.7.3 
> - 框架：Spring-boot-web
> - 登录控制：sa-token 1.34.0
> - 前端UI框架：Layuimini 2.5.5 单页模式（jQuery + Html5）
> - 编辑器控件：Monaco Editor

特点：
> - 平台化，可管理<font color=#1D7ADE>多个项目</font>，一次部署长期使用
> - 轻量级，mvc单体项目结构，无需依赖额外环境，部署更简单
> - 支持团队内<font color=#1D7ADE>共享</font>，规范代码
> - 支持<font color=#1D7ADE>多数据源</font>，支持多种类型数据库（mysql/maria，支持扩展）
> - 支持<font color=#1D7ADE>多种模板引擎</font>（Enjoy/Freemarker/Vilocity，支持扩展）
> - 支持同时生成<font color=#1D7ADE>多语言数据类型映射</font>，如同时生成匹配Java和TypeScript数据类型的代码
> - 在线模板编辑器使用Monaco控件，使用体验同Visual Studio Code相差无几
> - 支持在项目、成员、数据源、表、字段等维度进行<font color=#1D7ADE>自定义参数</font>的扩展配置，即配即用
> - 可用参数随时查看，清晰了然
> - 可直接<font color=#1D7ADE>克隆</font>项目、配置，快速个性化定制
> - 支持项目、配置<font color=#1D7ADE>导入导出</font>，移植无压力

<a name="woSZP"></a>
## 功能说明
<a name="Ht03I"></a>
### 系统结构
![image.png](/src/main/resources/static/readme/system.png)
<a name="uTMBu"></a>
### 项目管理
<a name="Ax3C9"></a>
#### 项目
展示用户创建或加入的所有项目，可添加新项目，对已有项目进行操作。
> - **添加**：创建新项目
> - **导出全部**：导出所有权属项目至数据文件，含关联配置
> - **导出选中**：导出选中的项目至数据文件，含关联配置
> - **导入**：将数据文件导入为可操作数据，导入项目的所有者为当前用户
> - **编辑**：修改项目相关信息及扩展配置，仅项目所有者可用
> - **删除**：删除整个项目，包括成员、数据源、表单、字段等内容，谨慎使用，仅项目所有者可用
> - **成员**：项目成员管理，添加、删除项目成员，修改相关扩展配置
> - **数据源**：添加源数据库，同一项目内可添加多个数据源
> - **拷贝**：快速拷贝当前项目，新项目所有者为当前用户，对新项目的操作不影响原有项目

创建项目时必须选择一个配置，可选配置包括公共配置和用户自己的私有配置，均可在“配置管理”中进行维护；
<a name="vAZaW"></a>
#### 成员
> - **添加**：项目所有者为项目添加授权用户，可选用户为除管理员之外，所有非当前项目成员的注册用户，被添加的用户将获得本项目的相关权属，仅项目所有者可用
> - **编辑**：可修改署名及扩展配置，署名默认为注册时的用户名，建议及时修改
> - **删除**：删除成员，除去此成员对于本项目的相关权属，仅项目所有者可用
> - **转让**：将本项目所有权转让给指定成员，转让成功后，原所有者将立即剔除对本项目的相应管理权限，谨慎使用，仅项目所有者可用

<a name="zaxFS"></a>
#### 数据源
> - **添加**：添加数据源，用于连接目标数据库，仅项目所有者可用
> - **编辑**：修改数据源配置信息，仅项目所有者可用
> - **删除**：删除数据源，包括表单、字段等内容，谨慎使用，仅项目所有者可用
> - **库表**：通过数据库获取表、字段数据，进行代码生成操作，字段查阅等

维护数据源信息时，根据所选数据库，需要额外配置数据类型映射，默认采用系统预设的映射关系，如需要添加新的映射项，可登录管理员账号，在数据映射管理中进行设置。<br />
由于是通过读取数据库的信息库获取表、字段信息，因此在数据源中填写的数据库用户必须拥有足够的权限，否则无法同步数据。
<a name="RARCr"></a>
#### 库表
> - **添加**：手动添加表，暂不支持sql生成，仅项目所有者可用
> - **编辑**：手动编辑表，修改扩展配置，仅项目所有者可用
> - **删除**：删除表，包括下属关联字段，谨慎使用，仅项目所有者可用
> - **数据同步**：通过数据源配置，连接数据库，将数据库上的表、字段数据同步至系统，仅项目所有者可用
> - **生成全部**：根据配置，生成当前数据源下属所有表的代码，压缩为zip并下载
> - **生成选中**：根据配置，生成选中表的代码，压缩为zip并下载
> - **字段**：查看当前表的下属字段
> - **预览**：预览当前表生成的代码

数据同步时，只会将上游数据库的数据同步至系统，不做逆向同步，不会影响数据库原有内容；获取的数据根据表名、字段名匹配，对系统中不存在的数据执行新增操作，对系统中已存在的同名数据执行修改操作（不覆盖扩展配置内容），对于系统中存在而数据源中不存在的数据，将此数据的“是否存在于数据库”一列设置为“不存在”。
<a name="C7QdJ"></a>
#### 字段
> - **添加**：手动添加字段，仅项目所有者可用
> - **编辑**：手动编辑字段，修改扩展配置，仅项目所有者可用
> - **删除**：删除字段，仅项目所有者可用

项目、成员、数据源、表单、字段均可设置扩展配置，扩展配置项在“配置管理>扩展”中进行维护，扩展配置作为自定义参数，可在模板中的使用。
<a name="LjcEJ"></a>
### 配置管理
<a name="RDVc5"></a>
#### 配置
> - **添加**：创建新配置
> - **导出全部**：导出所有权属配置至数据文件
> - **导出选中**：导出选中的配置至数据文件
> - **导入**：将数据文件导入为可操作数据，导入配置的所有者为当前用户
> - **编辑**：修改配置，可变更当前设置的所有者，仅配置所有者可用
> - **删除**：删除整个配置，包括扩展、模板等内容，谨慎使用，仅配置所有者可用
> - **映射**：维护数据库列与指定语言的属性类型映射关系
> - **扩展**：扩展配置管理
> - **模板**：模板管理
> - **拷贝**：快速拷贝当前配置，新配置所有者为当前用户，对新配置的操作不影响原有配置

<a name="jCy0V"></a>
#### 映射
> - **添加**：添加映射配置项，仅配置所有者可用
> - **编辑**：编辑映射配置项，仅配置所有者可用
> - **删除**：删除映射配置项，仅配置所有者可用

映射配置，支持不同数据库对不同程序语言的数据类型映射配置。<br />
生成代码时将根据所选语言进行数据类型映射。
<a name="qBQ92"></a>
#### 扩展
> - **添加**：添加扩展配置项，仅配置所有者可用
> - **编辑**：编辑扩展配置项，仅配置所有者可用
> - **删除**：删除扩展配置项，仅配置所有者可用

扩展配置可满足用户添加自定义参数的需求，应用于模板维护中，生成代码时将根据项目中的具体配置填入模板的相应位置。<br />
扩展配置根据作用域进行区分，分别作用于项目、成员、数据源、表单、字段等维度。
<a name="qBQ91"></a>
#### 模板
> - **添加**：添加模板，仅配置所有者可用
> - **编辑**：编辑模板，仅配置所有者可用
> - **删除**：删除模板，仅配置所有者可用
> - **查看**：非配置所有者不可编辑模板，但可查看

模板名称不影响代码生成，文件生成地址允许使用模板，必需与选择的模板引擎一致，生成的文件将根据填写的地址进行命名，因此生成地址必须末尾必须指定文件名及文件类型，且同一个配置中，不允许存在多个生成地址相同的模板。<br />
<font color=#DF2A3F>错误示例：</font>business/controller/#(table.classNameUp)Controller<br />
<font color=#5C8D07>正确示例：</font>business/controller/#(table.classNameUp)Controller<font color=#5C8D07>.java</font><br />
系统集成了主流的三种模板引擎，即Enjoy、Freemarker、Vilocity，可根据自己的喜好选择，允许不同模板分别采用不同的模板语言，但同一模板内不允许混用多种模板语言。<br />
Monaco Editor 编辑器根据所选语言调整代码规范，生成代码时将根据所选语言进行数据类型映射，为了更好的编辑体验，也为了数据类型映射的准确性，请务必选择正确的语言。<br />
![image.png](/src/main/resources/static/readme/lang.png)<br />
编辑模板时，可通过左侧“参数”按钮查看所有可用参数，参数根据作用域分块展示，并用不同颜色标识系统<br />
<font color=#b1eaba>内置参数</font>及<font color=#eab1d3>自定义扩展配置</font>，可点击参数快速复制。<br />
![image.png](/src/main/resources/static/readme/param.png)
<a name="XmBPP"></a>
### 列类型管理
仅管理员可用，维护不同数据库中的常用列类型，用于配置映射关系时，根据指定库获取列类型清单并维护与程序语言的映射关系。
<a name="AuJIn"></a>
### 人员管理
仅管理员可用，管理系统用户，可进行手动添加新用户，重置用户密码，启用、禁用账户等操作。
<a name="We3n5"></a>
## 规范
获取表、字段数据时，系统会进行一些加工处理，如获取表名、字段名、字段注释、关联枚举、字段取值限制等，为了使获取的数据更加统一，同时也为了生成的代码更适用于开发，建议遵循以下数据库设计规范：
> 1. 中间表建议加上本表的主键字段，不要使用关联字段作为主键，避免使用MybatisPlus的updateById更新数据时无法有效更新；
> 2. 表示时间的数据，使用datetime类型，只需精确到日期的，使用date类型；
> 3. 表示布尔的数据，使用tinyint类型；
> 4. 表示金额等需要高精度保存的数据，使用decimal类型；
> 5. 字符串类型字段建议标明长度，默认填充空字符；
> 6. 整数类型建议标明长度，如不可能为负数，则设置为无符号，默认0；<br />
     <font color=#5C8D07>例：int 4 即表示取值范围为-9999—9999，设置为无符号后表示取值范围为0—9999</font>
> 7. 浮点类型建议标明长度、精度，如不可能为负数，则设置为无符号，默认0；<br />
     <font color=#5C8D07>例：decimal 4,2 即表示取值范围为-99.99—99.99，设置为无符号后表示取值范围为0.00—99.99</font>
> 8. 表名、字段名使用下划线间隔的形式命名，尽量简短，符合业务应用场景，建议小写，可读性更高；
> 9. 字段名不以“is_”作为开头；
> 10. <font color=#DF2A3F>★</font> 表注释、字段注释必填；
> 11. <font color=#DF2A3F>★</font> 表注释格式为“<font color=#2E8AEB>所属模块</font><font color=#ED740C>/</font><font color=#2E8AEB>...</font><font color=#ED740C>/</font><font color=#9773DA>表中文名</font>”，如不设置模块，可直接填写表中文名；<br />
      <font color=#5C8D07>例：</font><font color=#2E8AEB>门户中心</font><font color=#ED740C>/</font><font color=#2E8AEB>账户管理</font><font color=#ED740C>/</font><font color=#9773DA>用户信息</font>
> 12. <font color=#DF2A3F>★</font> 字段注释格式为“<font color=#2E8AEB>[字段中文名]</font><font color=#9773DA>{枚举类名}</font><font color=#ED740C>额外说明</font>”，如没有枚举及额外说明，可不加“[ ]”直接填写字段中文名；<br />
      <font color=#5C8D07>例：</font><font color=#2E8AEB>[类型]</font><font color=#9773DA>{ProConfigTypeEnum}</font><font color=#ED740C>0:公共,1:私人</font><br />
      ![image.png](/src/main/resources/static/readme/notes.png)
> 13. 本规范适用于mysql、maria数据库，其它品牌数据库视其规则调整；


<a name="xUHgN"></a>
## 部署
> 1. 确保服务器已安装Mysql数据库，在数据库内运行项目目录下的 <font color=#5C8D07>resources/sql/pupa.sql</font> 初始化数据；
> 2. 修改项目配置文件 <font color=#5C8D07>resources/application.yml</font> 中的 <font color=#ED740C>datasource</font> 数据库连接配置；
> 3. 修改前端配置 <font color=#5C8D07>static/js/common/common.js</font>，将 <font color=#ED740C>serverPath</font> 参数中的地址设置为相应服务器地址；
> 4. 构建项目；
> 5. 复制项目目录下的 <font color=#5C8D07>target/pupa-1.0-SNAPSHOT.java</font> 到服务器自定目录，修改文件名为 <font color=#5C8D07>pupa.jar</font>；
> 6. 复制项目目录下的 <font color=#5C8D07>resources/run/start.sh</font> 到服务器，与jar文件同一目录；
> 7. 进入服务器项目目录，执行 <font color=#2E8AEB>./start.sh</font> 命令启动项目，如 <font color=#5C8D07>start.sh</font> 未授权，则执行 <font color=#2E8AEB>chmod u+x start.sh</font> 命令为进行授权，再启动项目；
> 8. 在浏览器输入实际项目地址进行访问，如：http://192.168.0.1:7384/view/index.html
<a name="HMKuG"></a>
## 扩展
<a name="ViPyV"></a>
### 编辑器语言

- Monaco Editor 编辑器目前只添加了部分常用语言，如需扩展，需改动项目中的枚举类：	<br />
  <font color=#5C8D07>com/jinyuan/pupa/common/enums/proTemp/ProTempLangEnum.java</font><br />
  ![image.png](/src/main/resources/static/readme/ext_ed_01.png)
- 可参考以下目录内容添加相应语言的枚举项：<br />
  <font color=#5C8D07>static/js/monaco/vs/basic-languages</font><br />
  ![image.png](/src/main/resources/static/readme/ext_ed_02.png)
  <a name="Dl2if"></a>
### 数据库
PUPA目前仅支持Mysql及MariaDB两种数据库，可根据需求自行添加对其它数据库的扩展，方式如下：

- 在 <font color=#5C8D07>pom.xml</font> 中引入相应数据库驱动依赖；<br />
  ![image.png](/src/main/resources/static/readme/ext_db_01.png)
- 在 <font color=#5C8D07>com/jinyuan/pupa/dbExtends</font> 创建相应数据库包，如mysql；<br />
  ![image.png](/src/main/resources/static/readme/ext_db_02.png)
- 在包内添加相应的表、列数据对象entity及mapper，添加 service 实现类，必须实现 <font color=#5C8D07>IMetadataService.java</font>；<br />
  ![image.png](/src/main/resources/static/readme/ext_db_03.png)<br />
  ![image.png](/src/main/resources/static/readme/ext_db_04.png)
- 在 <font color=#5C8D07>resources/mapper</font> 中添加相应数据映射文件；<br />
  ![image.png](/src/main/resources/static/readme/ext_db_05.png)
- 在 <font color=#5C8D07>DatasourceBrandEnum.java</font> 中添加相应枚举数据；<br />
  ![image.png](/src/main/resources/static/readme/ext_db_06.png)
  <a name="s2Pjs"></a>
### 模板引擎

- 在 <font color=#5C8D07>pom.xml</font> 中引入相应模板引擎依赖；<br />
  ![image.png](/src/main/resources/static/readme/ext_render_01.png)
- 在 <font color=#5C8D07>com/jinyuan/pupa/common/tool/render</font> 中添加相应render渲染类，该类必须继承 <font color=#5C8D07>Render.java</font>，并实现其抽象方法；<br />
  ![image.png](/src/main/resources/static/readme/ext_render_02.png)
- 修改 <font color=#5C8D07>com/jinyuan/pupa/common/enums/proTemp/ProTempTypeEnum.java</font>，添加相应枚举项；<br />
  ![image.png](/src/main/resources/static/readme/ext_render_03.png)
- 修改 <font color=#5C8D07>com/jinyuan/pupa/business/service/impl/BizGenServiceImpl.java</font>，在 <font color=#5C8D07>getRenderMap</font> 方法中，初始化渲染类，并添加进map；<br />
  ![image.png](/src/main/resources/static/readme/ext_render_04.png)
