
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pro_column_type
-- ----------------------------
DROP TABLE IF EXISTS `pro_column_type`;
CREATE TABLE `pro_column_type`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT '[ID]',
  `brand` int(11) NOT NULL COMMENT '[数据库品牌]',
  `column_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[列类型]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `brand`(`brand`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '列类型' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_column_type
-- ----------------------------
INSERT INTO `pro_column_type` VALUES (879410084945768448, 0, 'tinyint');
INSERT INTO `pro_column_type` VALUES (879410084945768449, 0, 'smallint');
INSERT INTO `pro_column_type` VALUES (879410084945768450, 0, 'mediumint');
INSERT INTO `pro_column_type` VALUES (879410084945768451, 0, 'int');
INSERT INTO `pro_column_type` VALUES (879410084945768452, 0, 'integer');
INSERT INTO `pro_column_type` VALUES (879410084945768453, 0, 'bigint');
INSERT INTO `pro_column_type` VALUES (879410084945768454, 0, 'float');
INSERT INTO `pro_column_type` VALUES (879410084945768455, 0, 'double');
INSERT INTO `pro_column_type` VALUES (879410084945768456, 0, 'decimal');
INSERT INTO `pro_column_type` VALUES (879410084945768457, 0, 'bit');
INSERT INTO `pro_column_type` VALUES (879410084945768458, 0, 'char');
INSERT INTO `pro_column_type` VALUES (879410084945768459, 0, 'varchar');
INSERT INTO `pro_column_type` VALUES (879410084945768460, 0, 'tinytext');
INSERT INTO `pro_column_type` VALUES (879410084945768461, 0, 'text');
INSERT INTO `pro_column_type` VALUES (879410084945768462, 0, 'mediumtext');
INSERT INTO `pro_column_type` VALUES (879410084949962752, 0, 'longtext');
INSERT INTO `pro_column_type` VALUES (879410084949962753, 0, 'longblob');
INSERT INTO `pro_column_type` VALUES (879410084949962754, 0, 'date');
INSERT INTO `pro_column_type` VALUES (879410084949962755, 0, 'datetime');
INSERT INTO `pro_column_type` VALUES (879410084949962756, 0, 'timestamp');
INSERT INTO `pro_column_type` VALUES (879424478299729920, 0, 'binary');
INSERT INTO `pro_column_type` VALUES (879424941736767488, 0, 'blob');
INSERT INTO `pro_column_type` VALUES (879425731138334720, 0, 'enum');
INSERT INTO `pro_column_type` VALUES (879781146187444224, 1, 'tinyint');
INSERT INTO `pro_column_type` VALUES (879781146187444225, 1, 'smallint');
INSERT INTO `pro_column_type` VALUES (879781146187444226, 1, 'mediumint');
INSERT INTO `pro_column_type` VALUES (879781146187444227, 1, 'int');
INSERT INTO `pro_column_type` VALUES (879781146187444228, 1, 'integer');
INSERT INTO `pro_column_type` VALUES (879781146187444229, 1, 'bigint');
INSERT INTO `pro_column_type` VALUES (879781146187444230, 1, 'float');
INSERT INTO `pro_column_type` VALUES (879781146187444231, 1, 'double');
INSERT INTO `pro_column_type` VALUES (879781146187444232, 1, 'decimal');
INSERT INTO `pro_column_type` VALUES (879781146187444233, 1, 'bit');
INSERT INTO `pro_column_type` VALUES (879781146187444234, 1, 'char');
INSERT INTO `pro_column_type` VALUES (879781146187444235, 1, 'varchar');
INSERT INTO `pro_column_type` VALUES (879781146187444236, 1, 'tinytext');
INSERT INTO `pro_column_type` VALUES (879781146187444237, 1, 'text');
INSERT INTO `pro_column_type` VALUES (879781146187444238, 1, 'mediumtext');
INSERT INTO `pro_column_type` VALUES (879781146187444239, 1, 'longtext');
INSERT INTO `pro_column_type` VALUES (879781146187444240, 1, 'longblob');
INSERT INTO `pro_column_type` VALUES (879781146187444241, 1, 'date');
INSERT INTO `pro_column_type` VALUES (879781146187444242, 1, 'datetime');
INSERT INTO `pro_column_type` VALUES (879781146187444243, 1, 'timestamp');
INSERT INTO `pro_column_type` VALUES (879781146187444244, 1, 'binary');
INSERT INTO `pro_column_type` VALUES (879781146187444245, 1, 'blob');
INSERT INTO `pro_column_type` VALUES (879781146187444246, 1, 'enum');
INSERT INTO `pro_column_type` VALUES (911945413338509312, 2, 'bigint');
INSERT INTO `pro_column_type` VALUES (911945489771311104, 2, 'timestamp');
INSERT INTO `pro_column_type` VALUES (911945524726640640, 2, 'binary');
INSERT INTO `pro_column_type` VALUES (911945561066090496, 2, 'bit');
INSERT INTO `pro_column_type` VALUES (911945594658271232, 2, 'char');
INSERT INTO `pro_column_type` VALUES (911945649070977024, 2, 'decimal');
INSERT INTO `pro_column_type` VALUES (911945788133126144, 2, 'money');
INSERT INTO `pro_column_type` VALUES (911945824132837376, 2, 'smallmoney');
INSERT INTO `pro_column_type` VALUES (911945983528972288, 2, 'float');
INSERT INTO `pro_column_type` VALUES (911946024557654016, 2, 'int');
INSERT INTO `pro_column_type` VALUES (911946061660467200, 2, 'image');
INSERT INTO `pro_column_type` VALUES (911946122054250496, 2, 'varbinary(max)');
INSERT INTO `pro_column_type` VALUES (911946157194129408, 2, 'varchar(max)');
INSERT INTO `pro_column_type` VALUES (911946214526070784, 2, 'text');
INSERT INTO `pro_column_type` VALUES (911946247854010368, 2, 'nchar');
INSERT INTO `pro_column_type` VALUES (911946280569581568, 2, 'nvarchar');
INSERT INTO `pro_column_type` VALUES (911946326610456576, 2, 'nvarchar(max)');
INSERT INTO `pro_column_type` VALUES (911946388115730432, 2, 'ntext');
INSERT INTO `pro_column_type` VALUES (911946436593496064, 2, 'numeric');
INSERT INTO `pro_column_type` VALUES (911946497939386368, 2, 'real');
INSERT INTO `pro_column_type` VALUES (911946553165787136, 2, 'smallint');
INSERT INTO `pro_column_type` VALUES (911946584807616512, 2, 'datetime');
INSERT INTO `pro_column_type` VALUES (911946613702176768, 2, 'smalldatetime');
INSERT INTO `pro_column_type` VALUES (911946643653701632, 2, 'varbinary');
INSERT INTO `pro_column_type` VALUES (911946704450138112, 2, 'udt');
INSERT INTO `pro_column_type` VALUES (911946737920684032, 2, 'varchar');
INSERT INTO `pro_column_type` VALUES (911946765468872704, 2, 'tinyint');
INSERT INTO `pro_column_type` VALUES (911946806099095552, 2, 'uniqueidentifier');
INSERT INTO `pro_column_type` VALUES (911946834280624128, 2, 'xml');
INSERT INTO `pro_column_type` VALUES (911946881999220736, 2, 'time');
INSERT INTO `pro_column_type` VALUES (911946919034925056, 2, 'date');
INSERT INTO `pro_column_type` VALUES (911946949984694272, 2, 'datetime2');
INSERT INTO `pro_column_type` VALUES (911946987460800512, 2, 'datetimeoffset (2)');

-- ----------------------------
-- Table structure for pro_config
-- ----------------------------
DROP TABLE IF EXISTS `pro_config`;
CREATE TABLE `pro_config`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `type` int(1) UNSIGNED NOT NULL COMMENT '[类型]{ProConfigTypeEnum}0:公共,1:私人',
  `default_flag` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '[是否默认标志]0:否,1:是',
  `user_id` bigint(16) UNSIGNED NOT NULL COMMENT '[用户ID]',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[配置名]',
  `comments` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[说明]',
  `gen_times` int(5) UNSIGNED NULL DEFAULT 0 COMMENT '[生成次数]',
  `status` int(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '[状态]0:禁用,1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '配置' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_config
-- ----------------------------
INSERT INTO `pro_config` VALUES (908349132707901440, 0, 1, 1, '示例配置-Enjoy', '示例配置，模板语言：Enjoy', 0, 1, '2023-04-25 15:59:41', '2023-10-08 13:36:01');

-- ----------------------------
-- Table structure for pro_datasource
-- ----------------------------
DROP TABLE IF EXISTS `pro_datasource`;
CREATE TABLE `pro_datasource`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `project_id` bigint(16) UNSIGNED NOT NULL COMMENT '[项目ID]',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[连接名]',
  `brand` int(1) NOT NULL COMMENT '[数据库品牌]',
  `main_addr` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[主机地址]',
  `schema` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[库]',
  `url_suffix` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[链接后缀]',
  `account` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[账号]',
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[密码]',
  `prefix` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[忽略前缀]',
  `extend` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '[扩展配置]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `create_by` bigint(16) UNSIGNED NULL DEFAULT NULL COMMENT '[创建人]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_project_id`(`project_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据源' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_datasource
-- ----------------------------
INSERT INTO `pro_datasource` VALUES (908389119868448768, 908350977811591168, '示例项目-mysql', 0, '127.0.0.1:3306', 'pupa', 'useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai', 'root', 'root123', 'pro_,sys_', '[{\"key\":\"optimisticLockerCol\",\"value\":\"version\"},{\"key\":\"logicDeleteCol\",\"value\":\"del_flag\"},{\"key\":\"fillCreateTimeCol\",\"value\":\"create_time\"},{\"key\":\"fillUpdateTimeCol\",\"value\":\"update_time\"}]', '2023-10-08 13:35:09', 1, NULL);

-- ----------------------------
-- Table structure for pro_extend
-- ----------------------------
DROP TABLE IF EXISTS `pro_extend`;
CREATE TABLE `pro_extend`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `config_id` bigint(16) UNSIGNED NOT NULL COMMENT '[配置ID]',
  `key` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[键]',
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[值]',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[键名]',
  `comments` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[释义]',
  `scope` int(1) UNSIGNED NOT NULL COMMENT '[作用域]0:项目,1:项目成员,2:数据源,3:表单,4:字段,',
  `status` int(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '[状态]0:禁用,1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_config_id`(`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '扩展' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_extend
-- ----------------------------
INSERT INTO `pro_extend` VALUES (908349132712095745, 908349132707901440, 'package', '', '包目录', '当前类、引用类所在的包目录。例：com.nimang.pupa', 0, 1, '2023-07-10 17:04:51', '2023-07-10 17:33:47');
INSERT INTO `pro_extend` VALUES (908349132712095746, 908349132707901440, 'mainPath', '', '请求路径规则', 'controller默认的前置请求路径。例：nimang/pupa', 0, 1, '2023-07-10 17:05:39', '2023-07-10 17:34:20');
INSERT INTO `pro_extend` VALUES (908349132712095747, 908349132707901440, 'projectName', '', 'apifox项目名', '用于apifox插件生成接口文档。例：pupa', 0, 1, '2023-07-10 17:05:46', '2023-07-10 17:34:55');
INSERT INTO `pro_extend` VALUES (908349132712095748, 908349132707901440, 'email', '', '邮箱', '邮箱。例：tony@xx.com', 1, 1, '2023-07-10 17:10:05', '2023-07-10 17:36:10');
INSERT INTO `pro_extend` VALUES (908349132712095749, 908349132707901440, 'optimisticLockerCol', 'version', '乐观锁列名', '例：opt_lock、version', 2, 1, '2023-07-10 17:13:45', '2023-10-08 11:06:37');
INSERT INTO `pro_extend` VALUES (908349132712095750, 908349132707901440, 'logicDeleteCol', 'del_flag', '逻辑删列名', '例：deleted、del_flag', 2, 1, '2023-07-10 17:13:58', '2023-10-08 11:07:06');
INSERT INTO `pro_extend` VALUES (908349132712095751, 908349132707901440, 'fillCreateTimeCol', 'create_time', '插入时间列名', '例：create_time', 2, 1, '2023-07-10 17:14:15', '2023-10-08 11:07:18');
INSERT INTO `pro_extend` VALUES (908349132712095752, 908349132707901440, 'fillUpdateTimeCol', 'update_time', '更新时间列名', '例|：update_time', 2, 1, '2023-07-10 17:15:19', '2023-10-08 11:07:24');
INSERT INTO `pro_extend` VALUES (908349132712095753, 908349132707901440, 'moduleName', '', '模块名', '表所属模块。例：system', 3, 1, '2023-07-10 17:16:04', '2023-07-10 17:31:34');
INSERT INTO `pro_extend` VALUES (908349132712095754, 908349132707901440, 'directory', '', 'apifox接口目录', '用于apifox插件生成接口文档指定的目录，多级目录用“/”间隔。例：系统/用户', 3, 1, '2023-07-10 17:16:34', '2023-07-10 17:32:45');
INSERT INTO `pro_extend` VALUES (908349132712095755, 908349132707901440, 'export', 'true', '是否导出', '值为true时导出此列', 4, 1, '2023-07-10 17:38:20', NULL);

-- ----------------------------
-- Table structure for pro_field
-- ----------------------------
DROP TABLE IF EXISTS `pro_field`;
CREATE TABLE `pro_field`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `project_id` bigint(16) UNSIGNED NOT NULL COMMENT '[项目ID]',
  `source_id` bigint(16) UNSIGNED NOT NULL COMMENT '[数据源ID]',
  `table_id` bigint(16) UNSIGNED NOT NULL COMMENT '[表单ID]',
  `column_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[列名]',
  `ordinal_position` bigint(21) NOT NULL COMMENT '[顺位]',
  `column_default` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '[默认值]',
  `is_nullable` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[是否允许为空]NO/YES',
  `data_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[物理类型]',
  `character_maximum_length` bigint(21) NULL DEFAULT NULL COMMENT '[字符最大长度]',
  `character_octet_length` bigint(21) NULL DEFAULT NULL COMMENT '[字符有效长度]',
  `numeric_precision` bigint(21) NULL DEFAULT NULL COMMENT '[数字精度]',
  `numeric_scale` bigint(21) NULL DEFAULT NULL COMMENT '[数字刻度]',
  `datetime_precision` bigint(21) NULL DEFAULT NULL COMMENT '[时间精度]',
  `column_type` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '[列类型]',
  `column_key` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[键]',
  `extra` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[附加]常用区分主键值来源',
  `id_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[主键类型]常用区分主键值是否自增',
  `attr_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[属性类型]',
  `attr_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[属性名]',
  `column_cn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[中文名]',
  `column_notes` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[注释]',
  `primary` tinyint(1) UNSIGNED NOT NULL COMMENT '[是否主键]0:否,1:是',
  `required_flag` tinyint(1) UNSIGNED NOT NULL COMMENT '[是否必填]0:否,1:是',
  `unsigned` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '[无符号]0:否,1:是',
  `bound_min` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[下限]',
  `bound_max` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[上限]',
  `extend` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '[扩展配置]',
  `exist_flag` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '[存在标识]0:否,1:是',
  `insert_flag` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '[插入标识]0:否,1:是',
  `view_flag` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '[展示标识]0:否,1:是',
  `query_flag` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '[查询标识]0:否,1:是',
  `enum_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[枚举]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_project_id`(`project_id`) USING BTREE,
  INDEX `i_table_id`(`table_id`) USING BTREE,
  INDEX `i_source_id`(`source_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表字段' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_field
-- ----------------------------

-- ----------------------------
-- Table structure for pro_mapper
-- ----------------------------
DROP TABLE IF EXISTS `pro_mapper`;
CREATE TABLE `pro_mapper`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `config_id` bigint(16) UNSIGNED NOT NULL COMMENT '[配置ID]',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[名称]',
  `brand` int(1) NOT NULL COMMENT '[数据库品牌]',
  `lang` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[语言]',
  `mapper` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[映射规则]json',
  `comments` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[释义]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `brand`(`brand`) USING BTREE,
  INDEX `config_id`(`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据映射' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_mapper
-- ----------------------------
INSERT INTO `pro_mapper` VALUES (908349132707901441, 908349132707901440, 'Mysql-Java', 0, 'java', '[{\"attrType\":\"Boolean\",\"columnType\":\"tinyint\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"smallint\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"mediumint\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"int\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"integer\",\"importPath\":\"\"},{\"attrType\":\"Long\",\"columnType\":\"bigint\",\"importPath\":\"\"},{\"attrType\":\"Float\",\"columnType\":\"float\",\"importPath\":\"\"},{\"attrType\":\"Double\",\"columnType\":\"double\",\"importPath\":\"\"},{\"attrType\":\"BigDecimal\",\"columnType\":\"decimal\",\"importPath\":\"java.math.BigDecimal\"},{\"attrType\":\"Boolean\",\"columnType\":\"bit\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"char\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"varchar\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"tinytext\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"text\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"mediumtext\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"longtext\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"longblob\",\"importPath\":\"\"},{\"attrType\":\"LocalDate\",\"columnType\":\"date\",\"importPath\":\"java.time.LocalDate\"},{\"attrType\":\"LocalDateTime\",\"columnType\":\"datetime\",\"importPath\":\"java.time.LocalDateTime\"},{\"attrType\":\"LocalDateTime\",\"columnType\":\"timestamp\",\"importPath\":\"java.time.LocalDateTime\"},{\"attrType\":\"byte[]\",\"columnType\":\"binary\",\"importPath\":\"\"},{\"attrType\":\"byte[]\",\"columnType\":\"blob\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"enum\",\"importPath\":\"\"}]', 'tinyint >> Boolean;\ndatetime,timestamp >> LocalDateTime;');
INSERT INTO `pro_mapper` VALUES (908349132712095744, 908349132707901440, 'Maria-Java', 1, 'java', '[{\"attrType\":\"Boolean\",\"columnType\":\"tinyint\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"smallint\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"mediumint\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"int\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"integer\",\"importPath\":\"\"},{\"attrType\":\"Long\",\"columnType\":\"bigint\",\"importPath\":\"\"},{\"attrType\":\"Float\",\"columnType\":\"float\",\"importPath\":\"\"},{\"attrType\":\"Double\",\"columnType\":\"double\",\"importPath\":\"\"},{\"attrType\":\"BigDecimal\",\"columnType\":\"decimal\",\"importPath\":\"java.math.BigDecimal\"},{\"attrType\":\"Boolean\",\"columnType\":\"bit\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"char\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"varchar\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"tinytext\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"text\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"mediumtext\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"longtext\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"longblob\",\"importPath\":\"\"},{\"attrType\":\"LocalDate\",\"columnType\":\"date\",\"importPath\":\"java.time.LocalDate\"},{\"attrType\":\"LocalDateTime\",\"columnType\":\"datetime\",\"importPath\":\"java.time.LocalDateTime\"},{\"attrType\":\"LocalDateTime\",\"columnType\":\"timestamp\",\"importPath\":\"java.time.LocalDateTime\"},{\"attrType\":\"byte[]\",\"columnType\":\"binary\",\"importPath\":\"\"},{\"attrType\":\"byte[]\",\"columnType\":\"blob\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"enum\",\"importPath\":\"\"}]', 'tinyint >> Boolean;\ndatetime,timestamp >> LocalDateTime;');
INSERT INTO `pro_mapper` VALUES (912013354784436224, 908349132707901440, 'SqlServer-Java', 2, 'java', '[{\"attrType\":\"Long\",\"columnType\":\"bigint\",\"importPath\":\"\"},{\"attrType\":\"byte[]\",\"columnType\":\"timestamp\",\"importPath\":\"\"},{\"attrType\":\"byte[]\",\"columnType\":\"binary\",\"importPath\":\"\"},{\"attrType\":\"Boolean\",\"columnType\":\"bit\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"char\",\"importPath\":\"\"},{\"attrType\":\"BigDecimal\",\"columnType\":\"decimal\",\"importPath\":\"java.math.BigDecimal\"},{\"attrType\":\"BigDecimal\",\"columnType\":\"money\",\"importPath\":\"java.math.BigDecimal\"},{\"attrType\":\"BigDecimal\",\"columnType\":\"smallmoney\",\"importPath\":\"java.math.BigDecimal\"},{\"attrType\":\"Double\",\"columnType\":\"float\",\"importPath\":\"\"},{\"attrType\":\"Integer\",\"columnType\":\"int\",\"importPath\":\"\"},{\"attrType\":\"byte[]\",\"columnType\":\"image\",\"importPath\":\"\"},{\"attrType\":\"byte[]\",\"columnType\":\"varbinary(max)\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"varchar(max)\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"text\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"nchar\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"nvarchar\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"nvarchar(max)\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"ntext\",\"importPath\":\"\"},{\"attrType\":\"BigDecimal\",\"columnType\":\"numeric\",\"importPath\":\"java.math.BigDecimal\"},{\"attrType\":\"Float\",\"columnType\":\"real\",\"importPath\":\"\"},{\"attrType\":\"Short\",\"columnType\":\"smallint\",\"importPath\":\"\"},{\"attrType\":\"Date\",\"columnType\":\"datetime\",\"importPath\":\"java.sql.Date\"},{\"attrType\":\"Date\",\"columnType\":\"smalldatetime\",\"importPath\":\"java.sql.Date\"},{\"attrType\":\"byte[]\",\"columnType\":\"varbinary\",\"importPath\":\"\"},{\"attrType\":\"byte[]\",\"columnType\":\"udt\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"varchar\",\"importPath\":\"\"},{\"attrType\":\"Short\",\"columnType\":\"tinyint\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"uniqueidentifier\",\"importPath\":\"\"},{\"attrType\":\"String\",\"columnType\":\"xml\",\"importPath\":\"\"},{\"attrType\":\"Date\",\"columnType\":\"time\",\"importPath\":\"java.sql.Date\"},{\"attrType\":\"Date\",\"columnType\":\"date\",\"importPath\":\"java.sql.Date\"},{\"attrType\":\"Date\",\"columnType\":\"datetime2\",\"importPath\":\"java.sql.Date\"},{\"attrType\":\"DateTimeOffset\",\"columnType\":\"datetimeoffset (2)\",\"importPath\":\"microsoft.sql.DateTimeOffset\"}]', '');

-- ----------------------------
-- Table structure for pro_project
-- ----------------------------
DROP TABLE IF EXISTS `pro_project`;
CREATE TABLE `pro_project`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `config_id` bigint(16) UNSIGNED NOT NULL COMMENT '[配置ID]',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[项目名]',
  `comments` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[项目说明]',
  `extend` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '[扩展配置]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `create_by` bigint(16) UNSIGNED NULL DEFAULT NULL COMMENT '[创建人]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_config_id`(`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '项目' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_project
-- ----------------------------
INSERT INTO `pro_project` VALUES (908350977811591168, 908349132707901440, '示例项目', '示例项目，生成的代码仅做参考', '[{\"key\":\"package\",\"value\":\"com.nimang.pupa\"},{\"key\":\"mainPath\",\"value\":\"pupa\"},{\"key\":\"projectName\",\"value\":\"PUPA\"}]', '2023-10-08 11:03:35', 1, '2023-10-08 13:39:38');

-- ----------------------------
-- Table structure for pro_project_user
-- ----------------------------
DROP TABLE IF EXISTS `pro_project_user`;
CREATE TABLE `pro_project_user`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `project_id` bigint(16) UNSIGNED NOT NULL COMMENT '[项目ID]',
  `user_id` bigint(16) UNSIGNED NOT NULL COMMENT '[用户ID]',
  `role` int(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '[角色]0:所有者,1:普通成员',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[作者署名]',
  `extend` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '[扩展配置]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  `opt_lock` bigint(10) UNSIGNED NULL DEFAULT NULL COMMENT '[锁]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_project_id`(`project_id`) USING BTREE,
  INDEX `i_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '项目成员' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_project_user
-- ----------------------------
INSERT INTO `pro_project_user` VALUES (908350977815785472, 908350977811591168, 1, 0, NULL, '[{\"key\":\"email\",\"value\":\"\"}]', '2023-10-08 11:03:35', NULL, 1);

-- ----------------------------
-- Table structure for pro_table
-- ----------------------------
DROP TABLE IF EXISTS `pro_table`;
CREATE TABLE `pro_table`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `project_id` bigint(16) UNSIGNED NOT NULL COMMENT '[项目ID]',
  `source_id` bigint(16) UNSIGNED NOT NULL COMMENT '[数据源ID]',
  `table_schema` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[库]',
  `table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[表名]',
  `infix_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[无前缀名]',
  `engine` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[引擎]',
  `table_rows` bigint(21) UNSIGNED NULL DEFAULT NULL COMMENT '[含有数据行数]',
  `module` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[模块]',
  `table_comment` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[注释]',
  `cn_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[中文名]',
  `extend` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '[扩展配置]',
  `exist_flag` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '[存在标识]0:否,1:是',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_project_id`(`project_id`) USING BTREE,
  INDEX `i_source_id`(`source_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_table
-- ----------------------------

-- ----------------------------
-- Table structure for pro_template
-- ----------------------------
DROP TABLE IF EXISTS `pro_template`;
CREATE TABLE `pro_template`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `config_id` bigint(16) UNSIGNED NOT NULL COMMENT '[配置ID]',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[内容]',
  `content_lang` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[内容语言]',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[名称]',
  `temp_type` int(1) NOT NULL COMMENT '[模板引擎类型]',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '[文件生成路径]',
  `notes` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '[备注]',
  `status` int(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '[状态]0:禁用,1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_config_id`(`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '模板' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of pro_template
-- ----------------------------
INSERT INTO `pro_template` VALUES (908349132720484352, 908349132707901440, 'package #(project.ext.package).base.entity;\r\n\r\nimport com.baomidou.mybatisplus.annotation.*;\r\nimport lombok.AllArgsConstructor;\r\nimport lombok.Builder;\r\nimport lombok.Data;\r\nimport lombok.NoArgsConstructor;\r\n\r\nimport java.io.Serializable;\r\n#for(importPath : table.importPaths)\r\nimport #(importPath);\r\n#end\r\n\r\n/**\r\n * #(table.cnName)\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n@Data\r\n@Builder\r\n@NoArgsConstructor\r\n@AllArgsConstructor\r\n@TableName(\"#(table.table_name)\")\r\npublic class #(table.infixNameUp) implements Serializable {\r\n	private static final long serialVersionUID = 1L;\r\n\r\n#for(field : table.fields)\r\n	/**\r\n	 * #(field.columnCn)\r\n#if(field.columnNotes != \"\")\r\n	 * #(field.columnNotes)\r\n#end\r\n	 */\r\n#if(field.primary == true) \r\n	@TableId(value = \"`#(table.pk.columnName)`\", type = IdType.#(table.pk.idType))\r\n#else\r\n#if(field.columnName == project.ext.fillCreateTimeCol || field.columnName == project.ext.fillCreateByCol || field.columnName == project.ext.optimisticLockerCol)\r\n	@TableField(value = \"`#(field.columnName)`\", fill = FieldFill.INSERT)\r\n#else if(field.columnName == project.ext.fillUpdateTimeCol || field.columnName == project.ext.fillUpdateByCol)\r\n	@TableField(value = \"`#(field.columnName)`\", fill = FieldFill.UPDATE)\r\n#else\r\n	@TableField(value = \"`#(field.columnName)`\")\r\n#end\r\n#if(field.columnName == project.ext.optimisticLockerCol)\r\n	@Version\r\n#end\r\n#end\r\n	private #(field.attrType) #(field.attrName);\r\n\r\n#end\r\n}', 'java', 'Entity', 0, 'base/entity/#(table.infixNameUp).java', '实体类', 1, '2023-07-10 17:45:20', '2023-10-08 11:12:16');
INSERT INTO `pro_template` VALUES (908349132720484353, 908349132707901440, 'package #(project.ext.package).system.mapper;\r\n\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\nimport com.baomidou.mybatisplus.core.mapper.BaseMapper;\r\n\r\n\r\n/**\r\n * #(table.cnName)-Mapper\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\npublic interface #(table.infixNameUp)Mapper extends BaseMapper<#(table.infixNameUp)> {\r\n\r\n}', 'java', 'Mapper', 0, 'base/mapper/#(table.infixNameUp)Mapper.java', '数据操作类', 1, '2023-07-10 17:49:30', '2023-10-08 11:12:36');
INSERT INTO `pro_template` VALUES (908349132720484354, 908349132707901440, '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n<mapper namespace=\"#(project.ext.package).base.mapper.#(table.infixNameUp)Mapper\">\r\n\r\n</mapper>', 'xml', 'MapperXML', 0, 'resources/mapper/#(table.infixNameUp)Mapper.xml', '数据操作映射文件', 1, '2023-07-10 17:52:19', '2023-10-08 11:13:09');
INSERT INTO `pro_template` VALUES (908349132720484355, 908349132707901440, 'package #(project.ext.package).base.service;\r\n\r\nimport com.baomidou.mybatisplus.extension.service.IService;\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\n\r\n/**\r\n * #(table.comments)-数据服务接口\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\npublic interface I#(table.infixNameUp)Service extends IService<#(table.infixNameUp)>{\r\n\r\n}\r\n', 'java', 'IService', 0, 'base/service/I#(table.infixNameUp)Service.java', '数据服务接口', 1, '2023-07-11 13:42:15', '2023-10-08 11:15:09');
INSERT INTO `pro_template` VALUES (908349132720484356, 908349132707901440, 'package #(project.ext.package).base.service.impl;\r\n\r\nimport com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\nimport #(project.ext.package).base.mapper.#(table.infixNameUp)Mapper;\r\nimport #(project.ext.package).base.service.I#(table.infixNameUp)Service;\r\nimport lombok.RequiredArgsConstructor;\r\nimport org.springframework.stereotype.Service;\r\n\r\nimport java.util.Optional;\r\n\r\n/**\r\n * #(table.comments)-数据服务接口实现\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n@RequiredArgsConstructor\r\n@Service\r\npublic class #(table.infixNameUp)ServiceImpl extends ServiceImpl<#(table.infixNameUp)Mapper, #(table.infixNameUp)> implements I#(table.infixNameUp)Service {\r\n\r\n}', 'java', 'ServiceImpl', 0, 'base/service/impl/#(table.infixNameUp)ServiceImpl.java', '数据服务接口实现', 1, '2023-07-11 13:46:18', '2023-10-08 11:14:47');
INSERT INTO `pro_template` VALUES (908349132720484357, 908349132707901440, 'package #(project.ext.package).base.model.#(table.infixName);\r\n\r\nimport lombok.AllArgsConstructor;\r\nimport lombok.NoArgsConstructor;\r\nimport lombok.Builder;\r\nimport lombok.Data;\r\nimport javax.validation.constraints.NotBlank;\r\nimport javax.validation.constraints.NotNull;\r\nimport javax.validation.constraints.Size;\r\n\r\nimport cn.hutool.core.date.DatePattern;\r\nimport org.springframework.format.annotation.DateTimeFormat;\r\nimport java.io.Serializable;\r\n#for(importPath : table.importPaths)\r\nimport #(importPath);\r\n#end\r\n\r\n/**\r\n * #(table.comments)-新增BO\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n@Data\r\n@Builder\r\n@NoArgsConstructor\r\n@AllArgsConstructor\r\npublic class #(table.infixNameUp)AddBO implements Serializable{\r\n	private static final long serialVersionUID = 1L;\r\n\r\n#for (field : table.fields)\r\n#if(field.insertFlag == true)\r\n    /**\r\n     * #(field.columnCn)\r\n#if(field.columnNotes != \"\")\r\n     * #(field.columnNotes)\r\n#end\r\n#if(field.columnDefault == true)\r\n     * @default #(field.columnDefault)\r\n#end\r\n#if(field.enumName != \"\")\r\n	 * @see #(field.enumName)\r\n#end\r\n     */\r\n#if(field.attrType == \"String\")\r\n    #if(field.requiredFlag == true)\r\n    @NotBlank(message = \"缺少“#(field.columnCn)”\")\r\n    #end\r\n    #if(field.columnType != \"text\" && field.columnType != \"longtext\")\r\n    @Size(max = #(field.boundMax),message=\"“#(field.columnCn)”不应超过#(field.boundMax)个字符\")\r\n    #end\r\n#else\r\n    #if(field.requiredFlag  == true)\r\n    @NotNull(message = \"缺少“#(field.columnCn)”\")   \r\n    #end\r\n    #if(field.attrType == \"BigDecimal\" || field.attrType == \"Double\" || field.attrType == \"Float\")\r\n        #if(field.boundMin)\r\n    @DecimalMin(value = \"#(field.boundMin)\", message = \"“#(field.columnCn)”不应低于#(field.boundMin)\")\r\n        #end\r\n        #if(field.boundMax)\r\n    @DecimalMax(value = \"#(field.boundMax)\", message = \"“#(field.columnCn)”不应大于#(field.boundMax)\")\r\n        #end\r\n    #else if(field.attrType == \"Integer\")\r\n        #if(field.boundMin)\r\n    @Min(value = #(field.boundMin)L, message = \"“#(field.columnCn)”不应小于#(field.boundMin)\")\r\n        #end\r\n        #if(field.boundMax)\r\n    @Max(value = #(field.boundMax)L, message = \"“#(field.columnCn)”不应大于#(field.boundMax)\")\r\n        #end\r\n    #end\r\n#end\r\n#if(field.columnType == \"date\")\r\n    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)\r\n#else if(field.columnType == \"datetime\" || field.columnType == \"timestamp\")\r\n    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)\r\n#end\r\n    private #(field.attrType) #(field.attrName);\r\n\r\n#end\r\n#end\r\n}', 'java', 'AddBO', 0, 'base/model/#(table.infixName)/#(table.infixNameUp)AddBO.java', '新增参数类', 1, '2023-07-11 14:16:42', '2023-10-08 11:30:53');
INSERT INTO `pro_template` VALUES (908349132720484358, 908349132707901440, 'package #(project.ext.package).base.model.#(table.infixName);\r\n\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)AddBO;\r\nimport java.io.Serializable;\r\nimport lombok.AllArgsConstructor;\r\nimport lombok.EqualsAndHashCode;\r\nimport lombok.NoArgsConstructor;\r\nimport lombok.Data;\r\n\r\nimport javax.validation.constraints.NotBlank;\r\nimport javax.validation.constraints.NotNull;\r\n\r\n/**\r\n * #(table.comments)-编辑BO\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n@Data\r\n@EqualsAndHashCode(callSuper = true)\r\n@NoArgsConstructor\r\n@AllArgsConstructor\r\npublic class #(table.infixNameUp)EditBO extends #(table.infixNameUp)AddBO implements Serializable {\r\n	private static final long serialVersionUID = 1L;\r\n\r\n	/**\r\n	 * #(table.pk.columnCn)\r\n#if(table.pk.columnNotes != \"\")\r\n	 * #(table.pk.columnNotes)\r\n#end\r\n	 */\r\n#if(table.pk.attrType == \"String\")\r\n	@NotBlank(message = \"缺少“#(table.pk.columnCn)”\")\r\n#else\r\n	@NotNull(message = \"缺少“#(table.pk.columnCn)”\")\r\n#end\r\n	private #(table.pk.attrType) #(table.pk.attrName);\r\n\r\n}', 'java', 'EditBO', 0, 'base/model/#(table.infixName)/#(table.infixNameUp)EditBO.java', '编辑参数类', 1, '2023-07-11 14:31:27', '2023-10-08 11:16:59');
INSERT INTO `pro_template` VALUES (908349132724678656, 908349132707901440, 'package #(project.ext.package).base.model.#(table.infixName);\r\n\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\nimport #(project.ext.package).common.mp.enums.Compare;\r\nimport #(project.ext.package).common.web.domain.PageQuery;\r\nimport #(project.ext.package).common.annotation.MPQuery;\r\nimport lombok.AllArgsConstructor;\r\nimport lombok.Builder;\r\nimport lombok.Data;\r\nimport lombok.NoArgsConstructor;\r\n\r\nimport cn.hutool.core.date.DatePattern;\r\nimport org.springframework.format.annotation.DateTimeFormat;\r\nimport java.io.Serializable;\r\n#for(importPath : table.importPaths)\r\nimport #(importPath);\r\n#end\r\n\r\n/**\r\n * #(table.comments)-查询BO\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n@Data\r\n@Builder\r\n@NoArgsConstructor\r\n@AllArgsConstructor\r\npublic class #(table.infixNameUp)QueryBO extends PageQuery<#(table.infixNameUp)> implements Serializable {\r\n	private static final long serialVersionUID = 1L;\r\n\r\n#for(field : table.fields)\r\n#if(field.columnType == \"date\")\r\n	/**\r\n	 * #(field.columnCn)起始，列名：#(field.columnName)\r\n	 */\r\n	@DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)\r\n	@MPQuery(column = \"#(field.columnName)\", rule = Compare.GE)\r\n	private #(field.attrType) #(field.attrName)Begin;\r\n\r\n	/**\r\n	 * #(field.columnCn)截止\r\n	 */\r\n	@DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)\r\n	@MPQuery(column = \"#(field.columnName)\", rule = Compare.LE)\r\n	private #(field.attrType) #(field.attrName)End;\r\n#else if(field.columnType == \"datetime\" || field.columnType == \"timestamp\")\r\n	/**\r\n	 * #(field.columnCn)起始，列名：#(field.columnName)\r\n	 */\r\n	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)\r\n	@MPQuery(column = \"#(field.columnName)\", rule = Compare.GE)\r\n	private #(field.attrType) #(field.attrName)Begin;\r\n\r\n	/**\r\n	 * #(field.columnCn)截止\r\n	 */\r\n	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)\r\n	@MPQuery(column = \"#(field.columnName)\", rule = Compare.LE)\r\n	private #(field.attrType) #(field.attrName)End;\r\n#else if(field.attrType == \"BigDecimal\")\r\n	/**\r\n	 * #(field.columnCn)下限，列名：#(field.columnName)\r\n 	 */\r\n	@MPQuery(column = \"#(field.columnName)\", rule = Compare.GE)\r\n	private #(field.attrType) #(field.attrName)Min;\r\n\r\n	/**\r\n	 * #(field.columnCn)上限\r\n 	 */\r\n	@MPQuery(column = \"#(field.columnName)\", rule = Compare.LE)\r\n	private #(field.attrType) #(field.attrName)Max;\r\n#else\r\n	/**\r\n	 * #(field.columnCn)，列名：#(field.columnName)\r\n	#if(field.columnNotes != \"\")\r\n	 * #(field.columnNotes)\r\n	#end\r\n	#if(field.enumName != \"\")\r\n	 * @see #(field.enumName)\r\n	#end\r\n	 */\r\n	#if(field.columnName.indexOf(\"Name\") != -1 || field.columnName.indexOf(\"name\") != -1 )\r\n	@MPQuery(rule = Compare.LIKE)\r\n	#else\r\n	@MPQuery\r\n	#end\r\n	private #(field.attrType) #(field.attrName);\r\n#end\r\n\r\n#end\r\n}', 'java', 'QueryBO', 0, 'base/model/#(table.infixName)/#(table.infixNameUp)QueryBO.java', '查询参数类', 1, '2023-07-11 15:13:38', '2023-10-08 11:26:28');
INSERT INTO `pro_template` VALUES (908349132724678657, 908349132707901440, 'package #(project.ext.package).base.model.#(table.infixName);\r\n\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\nimport #(project.ext.package).common.util.ConvertUtil;\r\nimport cn.hutool.core.util.ObjectUtil;\r\n\r\nimport lombok.Data;\r\nimport lombok.Builder;\r\nimport lombok.AllArgsConstructor;\r\nimport lombok.NoArgsConstructor;\r\n\r\nimport javax.validation.constraints.NotBlank;\r\nimport javax.validation.constraints.NotNull;\r\n\r\nimport cn.hutool.core.date.DatePattern;\r\nimport com.fasterxml.jackson.annotation.JsonFormat;\r\nimport java.io.Serializable;\r\n#for(importPath : table.importPaths)\r\nimport #(importPath);\r\n#end\r\n\r\n\r\n/**\r\n * #(table.comments)-结果VO\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n@Data\r\n@Builder\r\n@NoArgsConstructor\r\n@AllArgsConstructor\r\npublic class #(table.infixNameUp)VO implements Serializable {\r\n    private static final long serialVersionUID = 1L;\r\n\r\n#for(field:table.fields)\r\n    /**\r\n     * #(field.columnCn)\r\n#if(field.columnNotes != \"\")\r\n     * #(field.columnNotes)\r\n#end\r\n#if(field.enumName != \"\")\r\n	 * @see #(field.enumName)\r\n#end\r\n     */\r\n#if(field.attrType == \"String\")\r\n#if(field.requiredFlag == true)\r\n    @NotBlank\r\n#end\r\n#else\r\n#if(field.requiredFlag == true)\r\n    @NotNull\r\n#end\r\n#end\r\n#if(field.columnType == \"date\")\r\n     @JsonFormat(timezone=\"GMT+8\", shape = JsonFormat.Shape.STRING, pattern= DatePattern.NORM_DATE_PATTERN)\r\n#else if(field.columnType == \"datetime\" || field.columnType == \"timestamp\")\r\n     @JsonFormat(timezone=\"GMT+8\", shape = JsonFormat.Shape.STRING, pattern= DatePattern.NORM_DATETIME_PATTERN)\r\n#end\r\n    private #(field.attrType) #(field.attrName);\r\n\r\n#end\r\n\r\n}', 'java', 'VO', 0, 'base/model/#(table.infixName)/#(table.infixNameUp)VO.java', '查询返回结果类', 1, '2023-07-11 16:00:05', '2023-10-08 11:29:03');
INSERT INTO `pro_template` VALUES (908349132724678658, 908349132707901440, 'package #(project.ext.package).business.service;\r\n\r\nimport com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)AddBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)EditBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)QueryBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)VO;\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n * #(table.comments)-业务接口\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\npublic interface Biz#(table.infixNameUp)Service {\r\n\r\n    /**\r\n     * 新增\r\n     * @param addBO #(table.infixNameUp)AddBO 新增数据\r\n     * @return #(table.pk.attrType) #(table.pk.columnCn)\r\n     */\r\n    #(table.pk.attrType) add(#(table.infixNameUp)AddBO addBO);\r\n\r\n    /**\r\n     * 修改\r\n     * @param editBO #(table.infixNameUp)EditBO 修改数据\r\n     * @return Boolean\r\n     */\r\n    Boolean edit(#(table.infixNameUp)EditBO editBO);\r\n\r\n    /**\r\n     * 根据主键删除\r\n     * @param #(table.pk.attrName) #(table.pk.attrType) #(table.comments)-#(table.pk.columnCn)\r\n     * @return Boolean\r\n     */\r\n    Boolean remove(#(table.pk.attrType) #(table.pk.attrName));\r\n\r\n    /**\r\n     * 根据主键批量删除\r\n     * @param #(table.pk.attrName)s List<#(table.pk.attrType)> #(table.comments)-#(table.pk.columnCn)集合\r\n     * @return Boolean\r\n     */\r\n    Boolean removeBatch(List<#(table.pk.attrType)> #(table.pk.attrName)s);\r\n\r\n    /**\r\n     * 根据主键获取\r\n     * @param #(table.pk.attrName) #(table.pk.attrType) #(table.comments)-#(table.pk.columnCn)\r\n     * @return #(table.infixNameUp)\r\n     */\r\n    #(table.infixNameUp) get(#(table.pk.attrType) #(table.pk.attrName));\r\n\r\n    /**\r\n     * 条件查询（可分页）\r\n     * @param queryBO #(table.infixNameUp)QueryBO 查询参数\r\n     * @return Page<#(table.infixNameUp)>\r\n     */\r\n    Page<#(table.infixNameUp)> query(#(table.infixNameUp)QueryBO queryBO);\r\n\r\n    /**\r\n     * 数据装配\r\n     * @param #(table.infixName) 源对象\r\n     * @return #(table.infixNameUp)VO\r\n     */\r\n    #(table.infixNameUp)VO assemble(#(table.infixNameUp) #(table.infixName));\r\n\r\n    /**\r\n     * 数据装配-集合\r\n     * @param list 源对象集合\r\n     * @return List<#(table.infixNameUp)VO>\r\n     */\r\n    List<#(table.infixNameUp)VO> assembleList(List<#(table.infixNameUp)> list);\r\n\r\n    /**\r\n     * 数据装配-分页\r\n     * @param page 源分页对象\r\n     * @return Page<#(table.infixNameUp)VO>\r\n     */\r\n    Page<#(table.infixNameUp)VO> assemblePage(Page<#(table.infixNameUp)> page);\r\n}\r\n', 'java', 'BizService', 0, 'business/service/Biz#(table.infixNameUp)Service.java', '业务接口', 1, '2023-07-11 16:28:29', '2023-10-08 11:18:37');
INSERT INTO `pro_template` VALUES (908349132724678659, 908349132707901440, 'package #(project.ext.package).business.service.impl;\r\n\r\nimport cn.hutool.core.util.ObjectUtil;\r\nimport com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\nimport #(project.ext.package).business.service.Biz#(table.infixNameUp)Service;\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)AddBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)EditBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)QueryBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)VO;\r\nimport #(project.ext.package).base.service.I#(table.infixNameUp)Service;\r\nimport #(project.ext.package).common.util.ConvertUtil;\r\nimport #(project.ext.package).common.tool.mp.query.MPQueryWrapper;\r\n#if(table.pk.idType != \"AUTO\")\r\nimport #(project.ext.package).common.util.SnowFlakeIdGen;\r\n#end\r\n\r\nimport lombok.RequiredArgsConstructor;\r\nimport lombok.extern.log4j.Log4j2;\r\nimport org.springframework.stereotype.Service;\r\n\r\nimport java.util.ArrayList;\r\nimport java.util.List;\r\n\r\n\r\n/**\r\n * #(table.comments)-业务接口实现\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n@RequiredArgsConstructor\r\n@Log4j2\r\n@Service\r\npublic class Biz#(table.infixNameUp)ServiceImpl implements Biz#(table.infixNameUp)Service {\r\n\r\n#if(table.pk.idType != \"AUTO\")\r\n	private final SnowFlakeIdGen snowFlakeIdGen;\r\n#end\r\n	private final I#(table.infixNameUp)Service #(table.infixName)Service;\r\n\r\n	/**\r\n	 * 新增\r\n	 * @param addBO #(table.infixNameUp)AddBO 新增数据\r\n	 * @return #(table.pk.attrType) #(table.pk.columnCn)\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@Override\r\n	public #(table.pk.attrType) add(#(table.infixNameUp)AddBO addBO) {\r\n		#(table.infixNameUp) #(table.infixName) = ConvertUtil.convertOfEntity(addBO, #(table.infixNameUp).class);\r\n#if(table.pk.idType == \"AUTO\")\r\n		#(table.infixName)Service.save(#(table.infixName));\r\n		return #(table.infixName).get#(table.pk.attrNameUp)();\r\n#else\r\n	#if(table.pk.attrType == \"String\")\r\n		String #(table.pk.attrName) = \"\" + snowFlakeIdGen.nextId();\r\n	#else\r\n		#(table.pk.attrType) #(table.pk.attrName) = snowFlakeIdGen.nextId();\r\n	#end\r\n		#(table.infixName).set#(table.pk.attrNameUp)(#(table.pk.attrName));\r\n		#(table.infixName)Service.save(#(table.infixName));\r\n		return #(table.pk.attrName);\r\n#end\r\n	}\r\n\r\n	/**\r\n	 * 修改\r\n	 * @param editBO #(table.infixNameUp)EditBO 修改数据\r\n	 * @return Boolean\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@Override\r\n	public Boolean edit(#(table.infixNameUp)EditBO editBO) {\r\n		#(table.infixNameUp) #(table.infixName) = #(table.infixName)Service.getById(editBO.get#(table.pk.attrNameUp)());\r\n		ConvertUtil.convertOfEntity(editBO, #(table.infixName));\r\n		return #(table.infixName)Service.updateById(#(table.infixName));\r\n	}\r\n\r\n	/**\r\n	 * 根据主键删除\r\n	 * @param #(table.pk.attrName) #(table.pk.attrType) #(table.comments)-#(table.pk.columnCn)\r\n	 * @return Boolean\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@Override\r\n	public Boolean remove(#(table.pk.attrType) #(table.pk.attrName)) {\r\n		return #(table.infixName)Service.removeById(#(table.pk.attrName));\r\n	}\r\n\r\n	/**\r\n	 * 根据主键批量删除\r\n	 * @param #(table.pk.attrName)s List<#(table.pk.attrType)> #(table.comments)-#(table.pk.columnCn)集合\r\n	 * @return Boolean\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@Override\r\n	public Boolean removeBatch(List<#(table.pk.attrType)> #(table.pk.attrName)s) {\r\n		return #(table.infixName)Service.removeByIds(#(table.pk.attrName)s);\r\n	}\r\n\r\n	/**\r\n	 * 根据主键获取\r\n	 * @param #(table.pk.attrName) #(table.pk.attrType) #(table.comments)-#(table.pk.columnCn)\r\n	 * @return #(table.infixNameUp)\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@Override\r\n	public #(table.infixNameUp) get(#(table.pk.attrType) #(table.pk.attrName)) {\r\n		return #(table.infixName)Service.getById(#(table.pk.attrName));\r\n	}\r\n\r\n	/**\r\n	 * 条件查询（可分页）\r\n	 * @param queryBO #(table.infixNameUp)QueryBO 查询参数\r\n	 * @return Page<#(table.infixNameUp)>\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@Override\r\n	public Page<#(table.infixNameUp)> query(#(table.infixNameUp)QueryBO queryBO) {\r\n		MPQueryWrapper<#(table.infixNameUp)> wrapper = new MPQueryWrapper<>();\r\n		wrapper.build(queryBO);\r\n		return #(table.infixName)Service.page(queryBO.getPage(),wrapper);\r\n	}\r\n\r\n	/**\r\n	 * 数据装配\r\n	 * @param #(table.infixName) 源对象\r\n	 * @return #(table.infixNameUp)VO\r\n	 */\r\n	@Override\r\n	public #(table.infixNameUp)VO assemble(#(table.infixNameUp) #(table.infixName)) {\r\n		#(table.infixNameUp)VO vo = ConvertUtil.convertOfEntity(#(table.infixName), #(table.infixNameUp)VO.class);\r\n		return vo;\r\n	}\r\n\r\n	/**\r\n	 * 数据装配-集合\r\n	 * @param list 源对象集合\r\n	 * @return List<#(table.infixNameUp)VO>\r\n	 */\r\n	@Override\r\n	public List<#(table.infixNameUp)VO> assembleList(List<#(table.infixNameUp)> list) {\r\n		if(ObjectUtil.isEmpty(list)){\r\n			return new ArrayList<>();\r\n		}\r\n		List<#(table.infixNameUp)VO> voList = new ArrayList<>();\r\n		for (#(table.infixNameUp) #(table.infixName):list){\r\n			#(table.infixNameUp)VO vo = ConvertUtil.convertOfEntity(#(table.infixName), #(table.infixNameUp)VO.class);\r\n			voList.add(vo);\r\n		}\r\n		return voList;\r\n	}\r\n\r\n	/**\r\n	 * 数据装配-分页\r\n	 * @param page 源分页对象\r\n	 * @return Page<#(table.infixNameUp)VO>\r\n	 */\r\n	@Override\r\n	public Page<#(table.infixNameUp)VO> assemblePage(Page<#(table.infixNameUp)> page) {\r\n		Page<#(table.infixNameUp)VO> tPage = Page.of(page.getCurrent(), page.getSize(), page.getTotal(), page.searchCount());\r\n		List<#(table.infixNameUp)VO> t = assembleList(page.getRecords());\r\n		tPage.setRecords(t);\r\n		return tPage;\r\n	}\r\n\r\n}\r\n', 'java', 'BizServiceImpl', 0, 'business/service/impl/Biz#(table.infixNameUp)ServiceImpl.java', '业务接口实现', 1, '2023-07-11 16:47:51', '2023-10-08 11:19:19');
INSERT INTO `pro_template` VALUES (908349132724678660, 908349132707901440, 'package #(project.ext.package).business.controller;\r\n\r\nimport com.alibaba.fastjson.JSON;\r\nimport com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)AddBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)EditBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)QueryBO;\r\nimport #(project.ext.package).base.model.#(table.infixName).#(table.infixNameUp)VO;\r\nimport #(project.ext.package).business.service.Biz#(table.infixNameUp)Service;\r\nimport #(project.ext.package).common.tool.webTool.R;\r\nimport #(project.ext.package).common.tool.webTool.RPage;\r\nimport #(project.ext.package).base.entity.#(table.infixNameUp);\r\n\r\nimport lombok.RequiredArgsConstructor;\r\nimport lombok.extern.log4j.Log4j2;\r\nimport org.springframework.validation.annotation.Validated;\r\nimport org.springframework.web.bind.annotation.RequestParam;\r\nimport org.springframework.web.bind.annotation.*;\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n#if(table.ext.directory != null && table.ext.directory != \"\")\r\n * #(table.ext.directory)/#(table.comments)\r\n#else\r\n * #(table.comments)\r\n#end\r\n * @module #(project.ext.projectName)\r\n * @author #(project.author)\r\n * @date #(common.date)\r\n */\r\n\r\n@Log4j2\r\n@RestController\r\n@RequestMapping(value = \"/#(project.ext.mainPath)/#(table.infixNameHyphen)\")\r\n@RequiredArgsConstructor\r\npublic class #(table.infixNameUp)Controller {\r\n\r\n	private final Biz#(table.infixNameUp)Service #(table.infixName)Service;\r\n\r\n	/**\r\n	 * 新增\r\n	 * @param bizModel #(table.infixNameUp)AddBO 新增-业务数据\r\n	 * @return R<#(table.pk.attrType)> #(table.pk.columnCn)\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@PostMapping(\"/add\")\r\n	public R<#(table.pk.attrType)> add(@Validated @RequestBody #(table.infixNameUp)AddBO bizModel) {\r\n		#(table.pk.attrType) #(table.pk.attrName) = #(table.infixName)Service.add(bizModel);\r\n		return R.ok(#(table.pk.attrName));\r\n	}\r\n\r\n	/**\r\n	 * 修改\r\n	 * @param bizModel #(table.infixNameUp)EditBO 修改-业务数据\r\n	 * @return R<Boolean>\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@PutMapping(\"/edit\")\r\n	public R<Boolean> edit(@Validated @RequestBody #(table.infixNameUp)EditBO bizModel) {\r\n		boolean result = #(table.infixName)Service.edit(bizModel);\r\n		return R.ok(result);\r\n	}\r\n\r\n	/**\r\n	 * 根据主键删除\r\n	 * @param #(table.pk.attrName) #(table.pk.attrType) #(table.comments)-#(table.pk.columnCn)\r\n	 * @return R<Boolean>\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@DeleteMapping(\"/remove\")\r\n	public R<Boolean> remove(@RequestParam(value = \"#(table.pk.attrName)\") #(table.pk.attrType) #(table.pk.attrName)) {\r\n		boolean result = #(table.infixName)Service.remove(#(table.pk.attrName));\r\n		return R.ok(result);\r\n	}\r\n\r\n	/**\r\n	 * 根据主键批量删除\r\n	 * @param #(table.pk.attrName)s List<#(table.pk.attrType)> #(table.comments)-#(table.pk.columnCn)集合\r\n	 * @return R<Boolean>\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@DeleteMapping(\"/removeBatch\")\r\n	public R<Boolean> removeBatch(@RequestParam List<#(table.pk.attrType)> #(table.pk.attrName)s) {\r\n		boolean result = #(table.infixName)Service.removeBatch(#(table.pk.attrName)s);\r\n		return R.ok(result);\r\n	}\r\n\r\n	/**\r\n	 * 根据主键获取\r\n	 * @param #(table.pk.attrName) #(table.pk.attrType) #(table.comments)-#(table.pk.columnCn)\r\n	 * @return R<#(table.infixNameUp)VO>\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@GetMapping(\"/get\")\r\n	public R<#(table.infixNameUp)VO> get(@RequestParam(value = \"#(table.pk.attrName)\") #(table.pk.attrType) #(table.pk.attrName)) {\r\n		#(table.infixNameUp) #(table.infixName) = #(table.infixName)Service.get(#(table.pk.attrName));\r\n		#(table.infixNameUp)VO vo = #(table.infixName)Service.assemble(#(table.infixName));\r\n		return R.ok(vo);\r\n	}\r\n\r\n	/**\r\n	 * 条件查询（可分页）\r\n	 * @param queryBO #(table.infixNameUp)QueryBO 查询参数\r\n	 * @return RPage<#(table.infixNameUp)VO>\r\n	 * @author #(project.author)\r\n	 * @date #(common.date)\r\n	 */\r\n	@GetMapping(\"/query\")\r\n	public RPage<#(table.infixNameUp)VO> query(@Validated #(table.infixNameUp)QueryBO queryBO) {\r\n		// 默认排序：按主键倒序\r\n		if(queryBO.isEmptyOrderItem()){\r\n			queryBO.putOrderItem(#(table.infixNameUp)::get#(table.pk.attrNameUp), false);\r\n		}\r\n		Page<#(table.infixNameUp)> page = #(table.infixName)Service.query(queryBO);\r\n		Page<#(table.infixNameUp)VO> voPage = #(table.infixName)Service.assemblePage(page);\r\n		return RPage.ok(voPage);\r\n	}\r\n}\r\n', 'java', 'Controller', 0, 'business/controller/#(table.infixNameUp)Controller.java', '控制层', 1, '2023-07-11 18:08:33', '2023-10-08 11:40:52');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(16) UNSIGNED NOT NULL COMMENT '[ID]',
  `type` int(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '[用户类型]0:超级管理员,1:普通用户',
  `login_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '[登录名]',
  `phone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '[手机号]',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '[邮箱]',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '[头像地址]',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '[昵称]',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '[密码]',
  `salt` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '[密码加盐]',
  `status` int(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '[状态]0:禁用,1:启用',
  `deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '[逻辑删除]0:正常,1:删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '[创建时间]',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '[修改时间]',
  `opt_lock` bigint(10) UNSIGNED NULL DEFAULT NULL COMMENT '[锁]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 0, 'admin', NULL, NULL, NULL, '管理员', 'eec6af1e3138616490fdd4ed439567d4', 'kx0p', 1, 0, '2023-04-14 11:00:53', '2023-04-24 15:46:42', 1);

SET FOREIGN_KEY_CHECKS = 1;
