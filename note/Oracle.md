## Oracle语句

### 一：Oracle常用语句

#### 1.复制表结构和数据

```shell
# 1. 只复制表结构，不包含数据(这种方法通过添加 WHERE 1=0 条件确保不复制任何数据，只创建表结构)
CREATE TABLE new_table_name AS 
SELECT * FROM original_table_name WHERE 1=0;

# 2. 使用 CREATE TABLE ... LIKE 语法(注意：Oracle不直接支持 LIKE 语法来复制表结构，这是MySQL等其他数据库的语法。)
CREATE TABLE new_table_name LIKE original_table_name;

#3. 完整复制表结构和数据
CREATE TABLE new_table_name AS 
SELECT * FROM original_table_name;

# 4. 手动创建表结构
CREATE TABLE new_table_name (
    # -- 根据原表结构定义列
);
```