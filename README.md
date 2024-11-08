## WYSIWYG

## 简介

**What You See Is What You Get 所见即所得**

## 规范

### Mysql

- 表名须以**wysiwyg_模块名**开头，所有字段大写，注释须完善，注释中须体现字典group。

- 表中必须有主键，且注解类型须根据业务发展确定生成策略（确定数据量不会很大可使用自增，若无法预估则使用雪花算法）。

  

### JAVA

- 实体类（见名识意）

  | VO(View Object) | DO(Domain Object) | DTO(Data Transfer Object) |
  |-----------------|-------------------|-------|
  | 视图层对象           | DO字段须和数据库字段一一对应   | 数据传输对象  |

- 所有代码须遵循阿里规约

- 增删改查方法命名规范

  | 查询                            | 新增     | 修改      | 删除      |
  | ------------------------------- |--------|---------| --------- |
  | queryXxxTableData查询表格<br /> | addXxx | editXxx | deleteXxx |

## 模块介绍

```
com.wysiwyg     
├── wysiwyg-server             // 微服务模块
        └── wysiwyg-admin      // 项目后端管理系统，前端框架 [8083]
├── wysiwyg-base          	   // 基础模块
│       └── wysiwyg-common                      // 通用模块
│       └── wysiwyg-domain                      // 实体类统一管理（所有的VO,DO,DTO）
├──pom.xml           	       // 依赖管理
```

