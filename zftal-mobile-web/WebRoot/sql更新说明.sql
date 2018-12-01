--此sql更新说明暂不做6.0.0版本之前,6.0.0为后台样式完全重构的大版本，从6.0.0开始做归纳


---版本6.0.0---开始

--app端备忘录模块数据库相关
-- Create table 个人备忘录类别表
create table M_MEMO_CATALOG
(
  MEMOCATALOGID   VARCHAR2(32) not null,
  MEMOCATALOGNAME VARCHAR2(500) not null,
  CATALOGCOLOR    VARCHAR2(50) not null,
  USERNAME        VARCHAR2(32),
  SORTNUMBER      NUMBER
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_MEMO_CATALOG
  is '移动_备忘录_个人自定义类别';
-- Add comments to the columns 
comment on column M_MEMO_CATALOG.MEMOCATALOGID
  is '备忘录类别id';
comment on column M_MEMO_CATALOG.MEMOCATALOGNAME
  is '备忘录类别名称';
comment on column M_MEMO_CATALOG.CATALOGCOLOR
  is '类别颜色，对应rgb值';
comment on column M_MEMO_CATALOG.USERNAME
  is '所属用户id';
comment on column M_MEMO_CATALOG.SORTNUMBER
  is '排序码';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_MEMO_CATALOG
  add constraint PK_M_MEMO_CATALOG primary key (MEMOCATALOGID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

  
-- Create table 个人备忘录
create table M_MEMO_SELFMEMO
(
  MEMOFILENAME  VARCHAR2(100) not null,
  USERNAME      VARCHAR2(32) not null,
  MEMOTITLE     VARCHAR2(200),
  MEMOPATH      VARCHAR2(500) not null,
  CREATETIME    DATE not null,
  MEMOCATALOGID VARCHAR2(32) not null,
  MEMOCONTENT   BLOB,
  CONTENTFLAG   VARCHAR2(1)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_MEMO_SELFMEMO
  is '移动_备忘录_个人备忘录';
-- Add comments to the columns 
comment on column M_MEMO_SELFMEMO.MEMOFILENAME
  is '项目下存储的备忘录文件名称';
comment on column M_MEMO_SELFMEMO.USERNAME
  is '所属用户id';
comment on column M_MEMO_SELFMEMO.MEMOTITLE
  is '备忘录标题';
comment on column M_MEMO_SELFMEMO.MEMOPATH
  is '备忘录路径';
comment on column M_MEMO_SELFMEMO.CREATETIME
  is '备忘录创建时间';
comment on column M_MEMO_SELFMEMO.MEMOCATALOGID
  is '备忘录类别id';
comment on column M_MEMO_SELFMEMO.CONTENTFLAG
  is '图文标志位,1为文，2为图，3为图文';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_MEMO_SELFMEMO
  add constraint PK_M_MEMO_SELFMEMO primary key (MEMOFILENAME)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


--初始化备忘录类别 ，备忘录系统级别，app默认就有的类别，不可删除或修改
insert into m_memo_catalog (MEMOCATALOGID, MEMOCATALOGNAME, CATALOGCOLOR, USERNAME, SORTNUMBER)
values ('48944E4B1694545BE0538513470AC4A1', '未标签', '959595', '', 1);

insert into m_memo_catalog (MEMOCATALOGID, MEMOCATALOGNAME, CATALOGCOLOR, USERNAME, SORTNUMBER)
values ('48944E4B1690545BE0538513470AC4A1', '旅游', 'FDD5A6', '', 2);

insert into m_memo_catalog (MEMOCATALOGID, MEMOCATALOGNAME, CATALOGCOLOR, USERNAME, SORTNUMBER)
values ('48944E4B1691545BE0538513470AC4A1', '个人', 'A6EAF6', '', 3);

insert into m_memo_catalog (MEMOCATALOGID, MEMOCATALOGNAME, CATALOGCOLOR, USERNAME, SORTNUMBER)
values ('48944E4B1692545BE0538513470AC4A1', '生活', 'A6A6F8', '', 4);

insert into m_memo_catalog (MEMOCATALOGID, MEMOCATALOGNAME, CATALOGCOLOR, USERNAME, SORTNUMBER)
values ('48944E4B1693545BE0538513470AC4A1', '工作', 'FDB8B8', '', 5);


-- app端第三方登录模块，当门户认证接口没有时，此第三方登录放到后台进行处理
-- Create table 第三方与用户绑定的关系表
create table M_THIRDPARTY
(
  USERID VARCHAR2(32) not null,
  OPENID VARCHAR2(200) not null,
  TYPE   VARCHAR2(1) not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_THIRDPARTY
  is '移动_第三方绑定关系';
-- Add comments to the columns 
comment on column M_THIRDPARTY.USERID
  is '用户ID';
comment on column M_THIRDPARTY.OPENID
  is '第三方唯一性标识';
comment on column M_THIRDPARTY.TYPE
  is '第三方标志，qq为1，微信为2，新浪微3';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_THIRDPARTY
  add constraint PK_OPENIDANDTYPE unique (OPENID, TYPE)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

commit;

--增加备忘录的服务编码
insert into m_fwdyxt (FWBM, FWMC, SSYWXT)
values ('460', '备忘录', '22728AEC9FD96F27E050007F010030E5');

---版本6.0.0---结束

-- Create table 意见反馈主表,存储每条意见反馈记录，但不包含图片信息，图片在m_suggest_picture中
create table M_SUGGEST_MAIN
(
  ID            VARCHAR2(32) not null,
  USERNAME      VARCHAR2(32) not null,
  CREATETIME    DATE not null,
  SCHOOLCODE    VARCHAR2(30) not null,
  VERSIONNUMBER VARCHAR2(30) not null,
  TELEPHONE     VARCHAR2(11),
  QQ            VARCHAR2(30),
  TEXTCONTENT   VARCHAR2(2000),
  REPLYCONTENT  CLOB
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SUGGEST_MAIN
  is '意见反馈主表';
-- Add comments to the columns 
comment on column M_SUGGEST_MAIN.ID
  is 'Id值，唯一性标识';
comment on column M_SUGGEST_MAIN.USERNAME
  is '用户名';
comment on column M_SUGGEST_MAIN.CREATETIME
  is '创建时间';
comment on column M_SUGGEST_MAIN.SCHOOLCODE
  is '学校编码';
comment on column M_SUGGEST_MAIN.VERSIONNUMBER
  is 'App版本号';
comment on column M_SUGGEST_MAIN.TELEPHONE
  is '用户手机号';
comment on column M_SUGGEST_MAIN.QQ
  is '用户qq号码';
comment on column M_SUGGEST_MAIN.TEXTCONTENT
  is '用户建议文本内容';
comment on column M_SUGGEST_MAIN.REPLYCONTENT
  is '回复内容';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_SUGGEST_MAIN
  add constraint PK_M_SUGGEST_MAIN primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  -- Create table 意见反馈图片表,每条意见反馈包含的图片信息
create table M_SUGGEST_PICTURE
(
  SUGGESTID      VARCHAR2(32) not null,
  TITLE          VARCHAR2(200) not null,
  PICTURECONTENT BLOB not null,
  PICTUREPATH    VARCHAR2(200) not null,
  CREATETIME     DATE not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SUGGEST_PICTURE
  is '意见反馈图片表';
-- Add comments to the columns 
comment on column M_SUGGEST_PICTURE.SUGGESTID
  is '关联的意见反馈主表id';
comment on column M_SUGGEST_PICTURE.TITLE
  is '图片标题';
comment on column M_SUGGEST_PICTURE.PICTURECONTENT
  is '图片内容';
comment on column M_SUGGEST_PICTURE.PICTUREPATH
  is '图片项目下相对路径';
comment on column M_SUGGEST_PICTURE.CREATETIME
  is '创建时间';
  
-- Create table 备忘录新增图片表
create table M_MEMO_PICTURE
(
  MEMOFILENAME   VARCHAR2(50) not null,
  TITLE          VARCHAR2(200) not null,
  PICTURECONTENT BLOB not null,
  PICTUREPATH    VARCHAR2(200) not null,
  CREATETIME     DATE not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_MEMO_PICTURE
  is '备忘录图片表';
-- Add comments to the columns 
comment on column M_MEMO_PICTURE.MEMOFILENAME
  is '备忘录名称';
comment on column M_MEMO_PICTURE.TITLE
  is '图片标题';
comment on column M_MEMO_PICTURE.PICTURECONTENT
  is '图片内容';
comment on column M_MEMO_PICTURE.PICTUREPATH
  is '图片项目下相对路径';
comment on column M_MEMO_PICTURE.CREATETIME
  is '创建时间';
  

-- Create table 帮助指南表
create table ZFTAL_XTGL_HELPGUIDE
(
  ID          VARCHAR2(32) not null,
  TITLE       VARCHAR2(100),
  CREATETIME  DATE,
  ORDERNUMBER NUMBER,
  ISHOT       VARCHAR2(8) default 0,
  CONTENT     CLOB,
  ISPLAYED    VARCHAR2(8) default 1
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table ZFTAL_XTGL_HELPGUIDE
  is '帮助说明';
-- Add comments to the columns 
comment on column ZFTAL_XTGL_HELPGUIDE.TITLE
  is '标题';
comment on column ZFTAL_XTGL_HELPGUIDE.CREATETIME
  is '创建时间';
comment on column ZFTAL_XTGL_HELPGUIDE.ORDERNUMBER
  is '排序码';
comment on column ZFTAL_XTGL_HELPGUIDE.ISHOT
  is '是否热门查询，1代表是，0代表否';
comment on column ZFTAL_XTGL_HELPGUIDE.CONTENT
  is '内容';
comment on column ZFTAL_XTGL_HELPGUIDE.ISPLAYED
  is '是否启用，1代表启用，0代表停用';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ZFTAL_XTGL_HELPGUIDE
  add constraint PK_ID primary key (ID)
  disable;
  
---版本6.0.0---结束

---版本6.0.1---开始

-- 服务增加是否单点 
alter table m_fwgl_fwglb add ISSIGNAL varchar2(2) default 0;
--增加业务系统单点系统编码，ca需要
alter table M_FWGL_YWXTGLB add SIGNALXTBM varchar2(100);

 -- Create table 创建失物招领图片表
create table M_LOSSOBJECT_PICTURE
(
  LOSSOBJECTID   VARCHAR2(32) not null,
  TITLE          VARCHAR2(200) not null,
  PICTURECONTENT BLOB not null,
  PICTUREPATH    VARCHAR2(200) not null,
  CREATETIME     DATE not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_LOSSOBJECT_PICTURE
  is '移动_失物招领_图片';
-- Add comments to the columns 
comment on column M_LOSSOBJECT_PICTURE.LOSSOBJECTID
  is '关联的失物招领主表id';
comment on column M_LOSSOBJECT_PICTURE.TITLE
  is '图片标题';
comment on column M_LOSSOBJECT_PICTURE.PICTURECONTENT
  is '图片内容';
comment on column M_LOSSOBJECT_PICTURE.PICTUREPATH
  is '图片项目下相对路径';
comment on column M_LOSSOBJECT_PICTURE.CREATETIME
  is '创建时间';
  
  -- 失物招领增加手机号
ALTER TABLE M_LOSSOBJECT
ADD TELEPHONE varchar(32) ;

---版本6.0.1---结束



---版本6.0.2---开始
-- Create table 学校风景类别表
create table M_SCENERY_CATALOG
(
  SCENERYCATALOGID   VARCHAR2(32) not null,
  SCENERYCATALOGNAME VARCHAR2(20) not null,
  SORTNUMBER         NUMBER not null,
  CREATETIME         DATE default sysdate not null,
  ISACTIVE           VARCHAR2(2) default 1 not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SCENERY_CATALOG
  is '移动_学校风景_类别';
-- Add comments to the columns 
comment on column M_SCENERY_CATALOG.SCENERYCATALOGID
  is '学校风景类别id';
comment on column M_SCENERY_CATALOG.SCENERYCATALOGNAME
  is '类别名称';
comment on column M_SCENERY_CATALOG.SORTNUMBER
  is '排序码';
comment on column M_SCENERY_CATALOG.CREATETIME
  is '创建时间';
comment on column M_SCENERY_CATALOG.ISACTIVE
  is '是否启用';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_SCENERY_CATALOG
  add constraint PK_M_SCENERY_CATALOG primary key (SCENERYCATALOGID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


-- Create table 学校风景
create table M_SCENERY_SCENERY
(
  SCENERYID        VARCHAR2(32) not null,
  SCENERYNAME      VARCHAR2(100) not null,
  SCENERYCATALOGID VARCHAR2(32) not null,
  SCENERYPICID     VARCHAR2(32) not null,
  SCENERYPICURL    VARCHAR2(300) not null,
  SORTNUMBER       NUMBER not null,
  CREATETIME       DATE default sysdate not null,
  ISACTIVE         VARCHAR2(2) default '1' not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SCENERY_SCENERY
  is '移动_学校风景_风景';
-- Add comments to the columns 
comment on column M_SCENERY_SCENERY.SCENERYID
  is '学校风景记录id';
comment on column M_SCENERY_SCENERY.SCENERYNAME
  is '学校风景名称';
comment on column M_SCENERY_SCENERY.SCENERYCATALOGID
  is '学校风景类别id';
comment on column M_SCENERY_SCENERY.SCENERYPICID
  is '学校风景图片id';
comment on column M_SCENERY_SCENERY.SCENERYPICURL
  is '学校风景图片url';
comment on column M_SCENERY_SCENERY.SORTNUMBER
  is '排序码';
comment on column M_SCENERY_SCENERY.CREATETIME
  is '创建时间';
comment on column M_SCENERY_SCENERY.ISACTIVE
  is '是否启用';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_SCENERY_SCENERY
  add constraint PK_M_SCENERY_SCENERY primary key (SCENERYID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

--增加学校风景菜单及菜单权限
insert into zftal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, IS_AUTO, SYSCODE)
values ('N900201', '学校风景类别', 'N9002', '/zxgl/sceneryCatalog_list.html', '1', '0', '');

insert into zftal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, IS_AUTO, SYSCODE)
values ('N9002', '学校风景', 'N90', '', '2', '0', '');

insert into zftal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, IS_AUTO, SYSCODE)
values ('N900202', '学校风景', 'N9002', '/zxgl/schoolScenery_list.html', '2', '0', '');

insert into zftal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM)
values ('admin', 'N900201', '#');

insert into zftal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM)
values ('admin', 'N900202', '#');


-- Create table  我的收藏表
create table XT_FRAVOURITES
(
  FAVOURID                VARCHAR2(40) not null,
  FAVOURITESORT           VARCHAR2(4),
  FAVOURITEDATE           TIMESTAMP(6),
  FAVOURITEAVATAR         VARCHAR2(200),
  FAVOURITECUSTOM         VARCHAR2(100),
  FAVOURITETITLE          VARCHAR2(200),
  FAVOURITECONTENT        VARCHAR2(150),
  FAVOURITEIMAGE          VARCHAR2(100),
  FAVOURITEATTACHMENTSORT VARCHAR2(4),
  FAVOURITEATTACHMENTSIZE LONG,
  ATTACHMENTCON           CLOB,
  USERID                  VARCHAR2(40)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table XT_FRAVOURITES
  is '我的收藏表';
-- Add comments to the columns 
comment on column XT_FRAVOURITES.FAVOURID
  is '收藏id';
comment on column XT_FRAVOURITES.FAVOURITESORT
  is '收藏种类1为文本，2图片，3视频， 4网址，5附件';
comment on column XT_FRAVOURITES.FAVOURITEDATE
  is '收藏时间';
comment on column XT_FRAVOURITES.FAVOURITEAVATAR
  is '被收藏头像';
comment on column XT_FRAVOURITES.FAVOURITECUSTOM
  is '被收藏的人名';
comment on column XT_FRAVOURITES.FAVOURITETITLE
  is '收藏内容标题';
comment on column XT_FRAVOURITES.FAVOURITECONTENT
  is '收藏内容';
comment on column XT_FRAVOURITES.FAVOURITEIMAGE
  is '收藏图片';
comment on column XT_FRAVOURITES.FAVOURITEATTACHMENTSORT
  is '收藏附件种类这里单指收藏种类为5的时候';
comment on column XT_FRAVOURITES.FAVOURITEATTACHMENTSIZE
  is '收藏附件大小';
comment on column XT_FRAVOURITES.ATTACHMENTCON
  is '附件内容';
comment on column XT_FRAVOURITES.USERID
  is '用户';
-- Create/Recreate primary, unique and foreign key constraints 
alter table XT_FRAVOURITES
  add constraint PRIMARY_FRAVOURID primary key (FAVOURID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
-- Create table   积分商品表
-- Create table
create table M_SOURCE_GOODS
(
  GOODSID       VARCHAR2(40) not null,
  GOODSNAME     VARCHAR2(100),
  PRICE         NUMBER,
  DESCRIPTION   VARCHAR2(200),
  STORAGE       NUMBER,
  PICIDS        VARCHAR2(1000),
  PICPATHS      VARCHAR2(2000),
  ISACTIVE      VARCHAR2(2) default 1,
  CREATETIME    DATE,
  NUMBERICVALUE NUMBER
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SOURCE_GOODS
  is '积分商品表';
-- Add comments to the columns 
comment on column M_SOURCE_GOODS.GOODSID
  is '商品id';
comment on column M_SOURCE_GOODS.GOODSNAME
  is '商品名称';
comment on column M_SOURCE_GOODS.PRICE
  is '价格排序，1降序，2升序';
comment on column M_SOURCE_GOODS.DESCRIPTION
  is '商品介绍';
comment on column M_SOURCE_GOODS.STORAGE
  is '商品剩余库存';
comment on column M_SOURCE_GOODS.PICIDS
  is '商品图片id集合，逗号隔开';
comment on column M_SOURCE_GOODS.PICPATHS
  is '商品图片路径集合，逗号隔开';
comment on column M_SOURCE_GOODS.ISACTIVE
  is '是否启用此商品，1为启用中，0为停用';
comment on column M_SOURCE_GOODS.CREATETIME
  is '创建时间';
comment on column M_SOURCE_GOODS.NUMBERICVALUE
  is '价格';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_SOURCE_GOODS
  add constraint PKID_SG primary key (GOODSID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

  
-- Create table  签到历史记录表
create table M_SOURCE_SIGNINHIS
(
  USERID     VARCHAR2(40) not null,
  SOURCE     NUMBER,
  CREATETIME DATE,
  APPSOURCE  VARCHAR2(2)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SOURCE_SIGNINHIS
  is '用户积分签到历史记录表';
-- Add comments to the columns 
comment on column M_SOURCE_SIGNINHIS.USERID
  is '用户id';
comment on column M_SOURCE_SIGNINHIS.SOURCE
  is '每次的积分收入数值';
comment on column M_SOURCE_SIGNINHIS.CREATETIME
  is '创建时间';
comment on column M_SOURCE_SIGNINHIS.APPSOURCE
  is '签到来源--移动端1   web端2   其他设备3';
  
-- Create table 兑换历史记录表
create table M_SOURCE_CONSUMERHIS
(
  ID           VARCHAR2(40) not null,
  USERID       VARCHAR2(40),
  GOODSID      VARCHAR2(40),
  AMOUNT       NUMBER,
  FLAG         VARCHAR2(2),
  CREATETIME   DATE,
  EXCHANGETIME DATE
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SOURCE_CONSUMERHIS
  is '用户积分兑换历史记录表';
-- Add comments to the columns 
comment on column M_SOURCE_CONSUMERHIS.ID
  is '订单编号';
comment on column M_SOURCE_CONSUMERHIS.USERID
  is '用户id';
comment on column M_SOURCE_CONSUMERHIS.GOODSID
  is '商品id';
comment on column M_SOURCE_CONSUMERHIS.AMOUNT
  is '兑换数量';
comment on column M_SOURCE_CONSUMERHIS.FLAG
  is '是否兑换';
comment on column M_SOURCE_CONSUMERHIS.CREATETIME
  is '创建时间';
comment on column M_SOURCE_CONSUMERHIS.EXCHANGETIME
  is '兑换时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_SOURCE_CONSUMERHIS
  add constraint PKID_SCMER primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  -- Create table  用户表   增加source积分列
alter table ZFTAL_XTGL_YHB 
  add SOURCE  NUMBER default 0
  
  -- Create table   商家表
create table M_CANTEEN
(
  CANTEENID       VARCHAR2(40) not null,
  CANTEENNAME     VARCHAR2(50),
  DESCRIPTION     VARCHAR2(2000),
  PICID           VARCHAR2(40),
  PICPATH         VARCHAR2(200),
  LUNCHBOX        NUMBER default 0,
  FULLDISCOUNT    VARCHAR2(10),
  ISACTIVE        VARCHAR2(2),
  CREATETIME      DATE,
  TELEPHONE       VARCHAR2(20),
  LATITUDE2       NUMBER,
  LONGITUDE2      NUMBER,
  DISTANCE        NUMBER default 0,
  MINICONSUMPTION NUMBER default 0,
  TRAVELEXPENSES  NUMBER
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_CANTEEN
  is '商家表';
-- Add comments to the columns 
comment on column M_CANTEEN.CANTEENID
  is 'id';
comment on column M_CANTEEN.CANTEENNAME
  is '商家名称';
comment on column M_CANTEEN.DESCRIPTION
  is '商家介绍';
comment on column M_CANTEEN.PICID
  is '商家图片id';
comment on column M_CANTEEN.PICPATH
  is '商家图片路径';
comment on column M_CANTEEN.LUNCHBOX
  is '餐盒费，固定餐盒费';
comment on column M_CANTEEN.FULLDISCOUNT
  is '满减优惠';
comment on column M_CANTEEN.ISACTIVE
  is '状态';
comment on column M_CANTEEN.CREATETIME
  is '创建时间';
comment on column M_CANTEEN.TELEPHONE
  is '商家电话';
comment on column M_CANTEEN.LATITUDE2
  is '纬度2';
comment on column M_CANTEEN.LONGITUDE2
  is '经度2';
comment on column M_CANTEEN.DISTANCE
  is '范围';
comment on column M_CANTEEN.MINICONSUMPTION
  is '起送';
comment on column M_CANTEEN.TRAVELEXPENSES
  is '配送费';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_CANTEEN
  add constraint PKID_CANTEEN primary key (CANTEENID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  --执行Radian，获得弧度的函数
  CREATE OR REPLACE FUNCTION Radian(d number) RETURN NUMBER
is
PI number :=3.141592625;

begin
return  d* PI/180.0;
end ;
  
  --执行GetDistance，通过经纬度计算两地距离的函数
CREATE OR REPLACE FUNCTION GetDistance(lat1 number,
                                       lng1 number,
                                       lat2 number,
                                       lng2 number) RETURN NUMBER is
  earth_padius number := 6378.137;
  radLat1      number := Radian(lat1);
  radLat2      number := Radian(lat2);
  a            number := radLat1 - radLat2;
  b            number := Radian(lng1) - Radian(lng2);
  s            number := 0;
begin
  s := 2 *
       Asin(Sqrt(power(sin(a / 2), 2) +
                 cos(radLat1) * cos(radLat2) * power(sin(b / 2), 2)));
  s := s * earth_padius;
  s := Round(s * 10000) / 10000;
  return s;
end;

-- Create table 商家食品种类表
create table M_CANTEEN_FOODCATALOG
(
  FOODCATAID   VARCHAR2(40) not null,
  CANTEENID    VARCHAR2(40),
  FOODCATANAME VARCHAR2(20),
  ISACTIVE     VARCHAR2(2),
  CREATETIME   DATE
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_CANTEEN_FOODCATALOG
  is '商家食品种类表';
-- Add comments to the columns 
comment on column M_CANTEEN_FOODCATALOG.FOODCATAID
  is '食品种类id';
comment on column M_CANTEEN_FOODCATALOG.CANTEENID
  is '商家id';
comment on column M_CANTEEN_FOODCATALOG.FOODCATANAME
  is '食品种类名称';
comment on column M_CANTEEN_FOODCATALOG.ISACTIVE
  is '状态';
comment on column M_CANTEEN_FOODCATALOG.CREATETIME
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_CANTEEN_FOODCATALOG
  add constraint PKID_FOODCATA primary key (FOODCATAID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


-- Create table  商家食品表
create table M_CANTEEN_FOOD
(
  FOODID      VARCHAR2(40) not null,
  FOODNAME    VARCHAR2(100),
  FOODCATAID  VARCHAR2(40),
  PICID       VARCHAR2(40),
  PICPATH     VARCHAR2(200),
  ISACTIVE    VARCHAR2(2),
  STORAGE     NUMBER default 0,
  CREATETIME  DATE,
  PRICE       VARCHAR2(10),
  DESCRIPTION VARCHAR2(100)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_CANTEEN_FOOD
  is '食品表';
-- Add comments to the columns 
comment on column M_CANTEEN_FOOD.FOODID
  is '食品id';
comment on column M_CANTEEN_FOOD.FOODNAME
  is '食品名称';
comment on column M_CANTEEN_FOOD.FOODCATAID
  is '食品种类id';
comment on column M_CANTEEN_FOOD.PICID
  is '食品图片id';
comment on column M_CANTEEN_FOOD.PICPATH
  is '食品图片路径';
comment on column M_CANTEEN_FOOD.ISACTIVE
  is '状态';
comment on column M_CANTEEN_FOOD.STORAGE
  is '库存';
comment on column M_CANTEEN_FOOD.CREATETIME
  is '创建时间';
comment on column M_CANTEEN_FOOD.PRICE
  is '价格';
comment on column M_CANTEEN_FOOD.DESCRIPTION
  is '食品描述';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_CANTEEN_FOOD
  add constraint PKID_FOOD primary key (FOODID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  -- Create table 订单表
create table M_CANTEEN_ORDER
(
  ORDERID      VARCHAR2(40) not null,
  USERID       VARCHAR2(40),
  ADDRESSID    VARCHAR2(500),
  PERSONNUM    NUMBER default 0,
  CREATETIME   DATE,
  DELIVERYTIME DATE,
  DESCRIPTION  VARCHAR2(500),
  FLAG         VARCHAR2(2),
  CANTEENID    VARCHAR2(40),
  SUMMATION    VARCHAR2(20)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_CANTEEN_ORDER
  is '订单表';
-- Add comments to the columns 
comment on column M_CANTEEN_ORDER.ORDERID
  is '订单id';
comment on column M_CANTEEN_ORDER.USERID
  is '用户id';
comment on column M_CANTEEN_ORDER.ADDRESSID
  is '送餐地址id';
comment on column M_CANTEEN_ORDER.PERSONNUM
  is '用餐人数';
comment on column M_CANTEEN_ORDER.CREATETIME
  is '创建时间';
comment on column M_CANTEEN_ORDER.DELIVERYTIME
  is '送达时间';
comment on column M_CANTEEN_ORDER.DESCRIPTION
  is '备注';
comment on column M_CANTEEN_ORDER.FLAG
  is '订单状态：0商家未接单，1商家已接单，2商家拒单';
comment on column M_CANTEEN_ORDER.CANTEENID
  is '商家id';
comment on column M_CANTEEN_ORDER.SUMMATION
  is '总价';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_CANTEEN_ORDER
  add constraint PKID_ORDER primary key (ORDERID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table  订单食品表
create table M_ORDER_FOOD
(
  FOODID  VARCHAR2(40),
  ORDERID VARCHAR2(40),
  AMOUNT  NUMBER
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_ORDER_FOOD
  is '订单食品表';
-- Add comments to the columns 
comment on column M_ORDER_FOOD.FOODID
  is '食品id';
comment on column M_ORDER_FOOD.ORDERID
  is '订单id';
comment on column M_ORDER_FOOD.AMOUNT
  is '食品数量';
  
  -- Create table 个人送餐地址
create table M_CANTEEN_ADDRESS
(
  ADDRESSID       VARCHAR2(40) not null,
  USERID          VARCHAR2(40),
  NAME            VARCHAR2(32),
  MOBILEPHONE     VARCHAR2(16),
  SCHOOLNAME      VARCHAR2(300),
  SPECIFICADDRESS VARCHAR2(300)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_CANTEEN_ADDRESS
  is '个人送餐地址';
-- Add comments to the columns 
comment on column M_CANTEEN_ADDRESS.ADDRESSID
  is '地址id';
comment on column M_CANTEEN_ADDRESS.USERID
  is '用户id';
comment on column M_CANTEEN_ADDRESS.NAME
  is '姓名';
comment on column M_CANTEEN_ADDRESS.MOBILEPHONE
  is '手机号码';
comment on column M_CANTEEN_ADDRESS.SCHOOLNAME
  is '学校名称';
comment on column M_CANTEEN_ADDRESS.SPECIFICADDRESS
  is '具体地址';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_CANTEEN_ADDRESS
  add constraint PKID_ADDRESS primary key (ADDRESSID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


-- Create table 创建日累计访问统计表
create table M_VISIT_DAY
(
  VISITTIME      DATE not null,
  USERVISITCOUNT NUMBER not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_VISIT_DAY
  is '移动_每天访问统计';
-- Add comments to the columns 
comment on column M_VISIT_DAY.VISITTIME
  is '访问时间';
comment on column M_VISIT_DAY.USERVISITCOUNT
  is '访问累计数量';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_VISIT_DAY
  add constraint PK_M_VISIT_DAY primary key (VISITTIME)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

  -- Create table 创建在线人数统计表（以小时为单位）
create table M_VISIT_HOUR
(
  VISITTIME      DATE not null,
  USERVISITCOUNT NUMBER not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_VISIT_HOUR
  is '移动_每天每小时访问统计';
-- Add comments to the columns 
comment on column M_VISIT_HOUR.VISITTIME
  is '访问时间';
comment on column M_VISIT_HOUR.USERVISITCOUNT
  is '访问累计数量';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_VISIT_HOUR
  add constraint PK_M_VISIT_HOUR primary key (VISITTIME)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
 

--删除系统中不需要的菜单
delete from zftal_xtgl_xsgnmkdmb;

-- 增加app访问统计菜单
insert into zftal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM)
values ('admin', 'N010703', '#');

insert into zftal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, IS_AUTO, SYSCODE)
values ('N010703', 'App访问统计', 'N0107', '/visit/visit_visitForDayStatisticTop.html', '3', '', '');
  
   

  
  -- Create table M_WEBCASTS  移动直播间表
create table M_WEBCASTS
(
  WEBCASTID   VARCHAR2(40) not null,
  USERID      VARCHAR2(40),
  ANCHORNAME  VARCHAR2(32),
  ROOMNAME    VARCHAR2(50),
  PICPATH     VARCHAR2(200),
  ISACTIVE    VARCHAR2(2),
  STATUS      VARCHAR2(2),
  DROPNUM     NUMBER,
  DESCRIPTION VARCHAR2(20),
  CREATETIME  DATE,
  UPDATETIME  DATE,
  SCREENMODE  VARCHAR2(2),
  PICCON      BLOB
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_WEBCASTS
  is '移动直播间表';
-- Add comments to the columns 
comment on column M_WEBCASTS.WEBCASTID
  is 'id';
comment on column M_WEBCASTS.USERID
  is '用户id';
comment on column M_WEBCASTS.ANCHORNAME
  is '主播名称';
comment on column M_WEBCASTS.ROOMNAME
  is '直播间名称';
comment on column M_WEBCASTS.PICPATH
  is '直播封面图片路径';
comment on column M_WEBCASTS.ISACTIVE
  is '直播间状态  0禁用  1启用';
comment on column M_WEBCASTS.STATUS
  is '直播状态  0创建  1直播中  2结束';
comment on column M_WEBCASTS.DROPNUM
  is '点击量';
comment on column M_WEBCASTS.DESCRIPTION
  is '直播间描述';
comment on column M_WEBCASTS.CREATETIME
  is '创建时间';
comment on column M_WEBCASTS.UPDATETIME
  is '更新时间';
comment on column M_WEBCASTS.SCREENMODE
  is '直播间屏幕:0竖屏  1 横屏  2无';
comment on column M_WEBCASTS.PICCON
  is '图片';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_WEBCASTS
  add constraint PRI_KEY_WEBCASTID primary key (WEBCASTID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

  
  -- Create table 移动直播间申请审核表M_WEBCASTS_AUDIT
create table M_WEBCASTS_AUDIT
(
  APPID       VARCHAR2(40) not null,
  USERID      VARCHAR2(40),
  ISAUDIT     VARCHAR2(2),
  AUDITREASON VARCHAR2(100),
  AUDITTIME   DATE,
  CREATETIME  DATE,
  APPREASON   VARCHAR2(100)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_WEBCASTS_AUDIT
  is '移动直播间申请审核表';
-- Add comments to the columns 
comment on column M_WEBCASTS_AUDIT.APPID
  is 'id';
comment on column M_WEBCASTS_AUDIT.USERID
  is '用户id';
comment on column M_WEBCASTS_AUDIT.ISAUDIT
  is '审核状态:1通过  \0申请 2驳回';
comment on column M_WEBCASTS_AUDIT.AUDITREASON
  is '审核原因';
comment on column M_WEBCASTS_AUDIT.AUDITTIME
  is '审核时间';
comment on column M_WEBCASTS_AUDIT.CREATETIME
  is '申请时间';
comment on column M_WEBCASTS_AUDIT.APPREASON
  is '申请原因';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_WEBCASTS_AUDIT
  add constraint PK_APPLICATION_ID primary key (APPID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  --失物招领m_lossobject添加字段
alter table M_LOSSOBJECT add GOODSNAME VARCHAR(50);
alter table M_LOSSOBJECT add LOSEPLACE VARCHAR(50);
alter table M_LOSSOBJECT add PICKPLACE VARCHAR(50);
alter table M_LOSSOBJECT add QQ VARCHAR(11);


 --投票群组表
create table M_VOTEGROUP
(
  votemainid VARCHAR2(50),
  qzid       VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column M_VOTEGROUP.votemainid
  is '投票id';
comment on column M_VOTEGROUP.qzid
  is '群组id';
  
  --投票信息表
create table M_VOTEMAIN
(
  voteid            VARCHAR2(50) not null,
  votetitle         VARCHAR2(100),
  votetype          VARCHAR2(1) not null,
  voteismultiselect VARCHAR2(1),
  voteisanonymous   VARCHAR2(1),
  voteresultonlysee VARCHAR2(1),
  voteenddate       DATE,
  votestartdate     DATE,
  voteorganiserid   VARCHAR2(20),
  voteorganisername VARCHAR2(20),
  votemaxscore      VARCHAR2(3),
  voteisdraft       VARCHAR2(1),
  votestop          VARCHAR2(1),
  votedescription   VARCHAR2(200),
  votemaxchoice     VARCHAR2(2),
  votescoremethod   VARCHAR2(1)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column M_VOTEMAIN.voteid
  is '投票id';
comment on column M_VOTEMAIN.votetitle
  is '投票标题';
comment on column M_VOTEMAIN.votetype
  is '投票类型 0文字类型 1打分类型 2图片类型';
comment on column M_VOTEMAIN.voteismultiselect
  is '是否多选 1是0否';
comment on column M_VOTEMAIN.voteisanonymous
  is '是否匿名 1是0否';
comment on column M_VOTEMAIN.voteresultonlysee
  is '结果仅发起人可见 1是0否';
comment on column M_VOTEMAIN.voteenddate
  is '截止时间';
comment on column M_VOTEMAIN.votestartdate
  is '发起时间';
comment on column M_VOTEMAIN.voteorganiserid
  is '发起人账号';
comment on column M_VOTEMAIN.voteorganisername
  is '发起人姓名';
comment on column M_VOTEMAIN.votemaxscore
  is '打分类型最大分值';
comment on column M_VOTEMAIN.voteisdraft
  is '是否草稿 0不是 1是';
comment on column M_VOTEMAIN.votestop
  is '结束投票';
comment on column M_VOTEMAIN.votedescription
  is '描述';
comment on column M_VOTEMAIN.votemaxchoice
  is '多选时最多选几项';
comment on column M_VOTEMAIN.votescoremethod
  is '打分类型分数统计方式 0平均1总分';
alter table M_VOTEMAIN
  add constraint VOTEID primary key (VOTEID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
--投票选项表
create table M_VOTEOPTION
(
  voteoptionid          VARCHAR2(50) not null,
  voteoptiondescription VARCHAR2(200),
  votemainid            VARCHAR2(50),
  voteoptionsort        VARCHAR2(2),
  picturename           VARCHAR2(50),
  picturecontent        BLOB,
  picturepath           VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column M_VOTEOPTION.voteoptionid
  is '选项id';
comment on column M_VOTEOPTION.voteoptiondescription
  is '选项描述';
comment on column M_VOTEOPTION.votemainid
  is '投票id';
comment on column M_VOTEOPTION.voteoptionsort
  is '选项顺序';
comment on column M_VOTEOPTION.picturename
  is '图片名';
comment on column M_VOTEOPTION.picturecontent
  is '图片文件';
comment on column M_VOTEOPTION.picturepath
  is '文件路径';
alter table M_VOTEOPTION
  add constraint VOTEOPTIONID primary key (VOTEOPTIONID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  --投票结果表
create table M_VOTERESULT
(
  voteresultid    VARCHAR2(50) not null,
  voteuserid      VARCHAR2(20),
  voteoptionid    VARCHAR2(50),
  voteoptionscore VARCHAR2(3),
  votetime        DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column M_VOTERESULT.voteresultid
  is '结果id';
comment on column M_VOTERESULT.voteuserid
  is '投票用户id';
comment on column M_VOTERESULT.voteoptionid
  is '用户投票选项';
comment on column M_VOTERESULT.voteoptionscore
  is '打分类型分数';
comment on column M_VOTERESULT.votetime
  is '投票时间';
alter table M_VOTERESULT
  add constraint VOTERESULTID primary key (VOTERESULTID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
--报修信息表
create table M_REPORTFIX
(
  id         VARCHAR2(32) not null,
  problem    VARCHAR2(200),
  telephone  VARCHAR2(11),
  status     VARCHAR2(1),
  createtime DATE,
  evaluate   VARCHAR2(50),
  userid     VARCHAR2(20),
  username   VARCHAR2(20),
  score      VARCHAR2(3)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column M_REPORTFIX.id
  is '主键';
comment on column M_REPORTFIX.problem
  is '问题描述';
comment on column M_REPORTFIX.telephone
  is '联系方式';
comment on column M_REPORTFIX.status
  is '状态 0待报修1报修中2已报修';
comment on column M_REPORTFIX.createtime
  is '创建时间';
comment on column M_REPORTFIX.evaluate
  is '评价';
comment on column M_REPORTFIX.userid
  is '报修人id';
comment on column M_REPORTFIX.username
  is '报修人名称';
comment on column M_REPORTFIX.score
  is '评价打分';
alter table M_REPORTFIX
  add constraint FIXID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  --报修图片表
  create table M_REPORTFIX_PICTURE
(
  fixid      VARCHAR2(32),
  name       VARCHAR2(50),
  path       VARCHAR2(200),
  content    BLOB,
  createtime DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column M_REPORTFIX_PICTURE.fixid
  is '报修主键';
comment on column M_REPORTFIX_PICTURE.name
  is '图片名称';
comment on column M_REPORTFIX_PICTURE.path
  is '图片路径';
comment on column M_REPORTFIX_PICTURE.content
  is '图片文件';
comment on column M_REPORTFIX_PICTURE.createtime
  is '创建时间';
  
  --新增后台报修菜单
insert into ZFTAL_XTGL_JSGNMKDMB (gnmkdm, gnmkmc, fjgndm, dyym, xssx, is_auto, syscode)
values ('N910308', '报修', 'N9103', '/reportFix/reportFix_htReportFixList.html', '2', null, null);
insert into ZFTAL_XTGL_JSGNMKCZB (jsdm, gnmkdm, czdm)
values ('admin', 'N910308', '#');

  
  --表白墙主表
 create table M_EXPRESS
(
  expressid   VARCHAR2(32) default Sys_guid() not null,
  title       VARCHAR2(200) not null,
  textcontent VARCHAR2(2000) not null,
  thetype     VARCHAR2(2) not null,
  username    VARCHAR2(50) not null,
  createtime  DATE not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table M_EXPRESS
  is '表白墙主表';
comment on column M_EXPRESS.expressid
  is '主表id';
comment on column M_EXPRESS.title
  is '标题';
comment on column M_EXPRESS.textcontent
  is '文本内容';
comment on column M_EXPRESS.thetype
  is '0是帅哥，1是美女';
comment on column M_EXPRESS.username
  is '发布人姓名';
comment on column M_EXPRESS.createtime
  is '创建时间';
alter table M_EXPRESS
  add constraint PK_M_EXPRESS primary key (EXPRESSID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

--表白墙评论表
create table M_EXPRESS_COMMENT
(
  commentid      VARCHAR2(32) default sys_guid() not null,
  commentcontent VARCHAR2(2000) not null,
  expressid      VARCHAR2(32) not null,
  createtime     DATE default Sysdate not null,
  goodflag       VARCHAR2(2) default 0 not null,
  goodcount      NUMBER default 0,
  username       VARCHAR2(50),
  xm             VARCHAR2(50),
  anonymous      VARCHAR2(1)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table M_EXPRESS_COMMENT
  is '表白墙图片表';
comment on column M_EXPRESS_COMMENT.commentid
  is '主键id';
comment on column M_EXPRESS_COMMENT.commentcontent
  is '评论内容';
comment on column M_EXPRESS_COMMENT.expressid
  is '主表id';
comment on column M_EXPRESS_COMMENT.createtime
  is '创建时间';
comment on column M_EXPRESS_COMMENT.goodflag
  is '是否赞';
comment on column M_EXPRESS_COMMENT.goodcount
  is '被赞次数';
comment on column M_EXPRESS_COMMENT.username
  is '评论帐号';
comment on column M_EXPRESS_COMMENT.xm
  is '评论帐号姓名';
comment on column M_EXPRESS_COMMENT.anonymous
  is '是否匿名';  
  
alter table M_EXPRESS_COMMENT
  add constraint PK_M_EXPRESS_COMMENT primary key (COMMENTID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
  --表白墙点赞记录表
create table M_EXPRESS_GOODFLAG
(
  username  VARCHAR2(50),
  commentid VARCHAR2(50)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table M_EXPRESS_GOODFLAG
  is '某个用户是否赞过某个评论';
comment on column M_EXPRESS_GOODFLAG.username
  is '用户帐号';
comment on column M_EXPRESS_GOODFLAG.commentid
  is '评论id';
  
  --表白墙图片表
create table M_EXPRESS_PIC
(
  piccontent  BLOB not null,
  picname     VARCHAR2(50) not null,
  picpath     VARCHAR2(300) not null,
  ordernumber NUMBER not null,
  expressid   VARCHAR2(32) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table M_EXPRESS_PIC
  is '表白墙图片表';
comment on column M_EXPRESS_PIC.piccontent
  is '图片内容';
comment on column M_EXPRESS_PIC.picname
  is '图片名称';
comment on column M_EXPRESS_PIC.picpath
  is '文件路径';
comment on column M_EXPRESS_PIC.ordernumber
  is '排序码';
comment on column M_EXPRESS_PIC.expressid
  is '主表id';
  
  --新增表白墙菜单
insert into ZFTAL_XTGL_JSGNMKDMB (gnmkdm, gnmkmc, fjgndm, dyym, xssx, is_auto, syscode)
values ('N910304', '表白墙', 'N9103', '/express/express_list.html', '4', null, null);
insert into ZFTAL_XTGL_JSGNMKCZB (jsdm, gnmkdm, czdm)
values ('admin', 'N910304', '#');
  ---版本6.0.2---结束
  
  ---版本6.0.3---开始
  --新增门户服务特色类别表
create table M_WDMH_TSLB
(
  lbmc       VARCHAR2(50),
  lbms       VARCHAR2(200),
  createtime TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column M_WDMH_TSLB.lbmc
  is '类别名称';
comment on column M_WDMH_TSLB.lbms
  is '类别描述';
comment on column M_WDMH_TSLB.createtime
  is '创建时间';
  
  --修改我的门户表M_WDMH，增加特色类别和是否分配到发现界面字段
alter table M_WDMH add tsgn VARCHAR2(50);
alter table M_WDMH add isfx VARCHAR2(1);
comment on column M_WDMH.tsgn
  is '特色功能名称';
comment on column M_WDMH.isfx
  is '是否属于我的发现服务  0否，1是';
  
  --修改高德地图服务数据，增加描述和所属校区字段
alter table M_FWGL_MAP add description VARCHAR2(200);
alter table M_FWGL_MAP add affcampus VARCHAR2(200);
comment on column M_FWGL_MAP.description
  is '描述';
comment on column M_FWGL_MAP.affcampus
  is '所属校区';
  ---版本6.0.3---结束


---版本6.0.4--开始

  --服务未读数表增加用户账号字段
alter table M_SERVICE_NOTREADING add username VARCHAR2(32);
comment on column M_SERVICE_NOTREADING.username 
  is '描述';
  
  --appconfig表增加推送平台字段
alter table M_MOBILE_APPCONFIG add pushplatform VARCHAR2(32);
comment on column M_MOBILE_APPCONFIG.pushplatform 
  is '推送平台';
  
  --人员设备表增加设备系统类型字段  
alter table MOBILE_RYSBB add sbtype VARCHAR2(10);
comment on column MOBILE_RYSBB.sbtype 
  is '设备类型,MIUI|EMUI|JPUSH   分类推送';
