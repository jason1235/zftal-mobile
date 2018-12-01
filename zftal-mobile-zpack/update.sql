-- 5.0.5需更新sql
-- Create table
create table ZFTAL_XTGL_SUGGEST
(
  USERID  VARCHAR2(32) not null,
  SUGGEST VARCHAR2(3000) not null,
  TIME    DATE not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table ZFTAL_XTGL_SUGGEST
  is '用户建议表（客户端提出）';
-- Add comments to the columns 
comment on column ZFTAL_XTGL_SUGGEST.USERID
  is '用户名';
comment on column ZFTAL_XTGL_SUGGEST.SUGGEST
  is '用户建议';
comment on column ZFTAL_XTGL_SUGGEST.TIME
  is '提出时间';
  
-- 删除消息推送主菜单 
delete  from zftal_xtgl_jsgnmkdmb t where t.gnmkmc = '消息推送'


-- Create table
create table M_SYSTEM_SETTINGS
(
  SK VARCHAR2(50),
  SV VARCHAR2(100),
  SC VARCHAR2(100)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_SYSTEM_SETTINGS
  is '移动_系统设置表';
-- Add comments to the columns 
comment on column M_SYSTEM_SETTINGS.SK
  is '键';
comment on column M_SYSTEM_SETTINGS.SV
  is '值';
comment on column M_SYSTEM_SETTINGS.SC
  is '描述';

insert into m_system_settings (SK, SV, SC)
values ('max_news_top', '5', '资讯置顶最多条数');

insert into m_system_settings (SK, SV, SC)
values ('max_news_headline', '5', '资讯头条最多条数');

insert into m_system_settings (SK, SV, SC)
values ('service_front', '302,306', '首页显示的服务（代办日程，邮件系统）');

insert into m_system_settings (SK, SV, SC)
values ('tt_name', '重要新闻', '头条');

-- 删除消息推送主菜单
delete  from zftal_xtgl_jsgnmkdmb t where t.gnmkmc = '消息推送'

  
-- 5.0.6需更新sql（无）

-- 5.0.7需更新sql
-- 存储过程更新
create or replace trigger sys_to_yhb
  after insert or update or delete on zftal_xtgl_yhb
  for each row
declare
  v_rylx varchar2(10);
  v_orylx varchar2(10);
begin

  if inserting then
    if :new.rylx = 'student' then
      v_rylx := 'student';
    else
      v_rylx := 'teacher';
    end if;

    insert into zftal_xtgl_yhjsb( zgh, jsdm ) values( :new.zgh, v_rylx );
	insert into m_fwgl_yhqx (yhid,fwbm) select :new.zgh,t.fwbm from m_fwgl_jsfpfwglb t where t.jsid=v_rylx;


  elsif updating then

    if (:old.rylx = 'student' and :new.rylx != 'student') or (:old.rylx is null and :new.rylx != 'student')
      or (:old.rylx is null and :new.rylx = 'student') or (:old.rylx != 'student' and :new.rylx = 'student') then
      if :new.rylx = 'student' then
        v_rylx := 'student';
        v_orylx := 'teacher';
      else
        v_rylx := 'teacher';
        v_orylx := 'student';
      end if;
      delete from M_FWGL_yhqx where yhid = :new.zgh;
      update zftal_xtgl_yhjsb set jsdm = v_rylx where zgh = :new.zgh and jsdm = v_orylx;
      insert into M_FWGL_yhqx select  :new.zgh, b.fwbm from M_FWGL_JSFPFWGLB b where b.jsid = v_rylx;
    end if;

  elsif deleting then
     delete from zftal_xtgl_yhjsb where zgh = :old.zgh;
     delete from M_FWGL_yhqx where yhid = :old.zgh;
  end if;

end sys_to_yhb;

-- 添加信息类图标地址
ALTER TABLE M_JCSZ_CCGCSZ
ADD DATADZ VARCHAR2(200);
-- 添加信息类图标id
ALTER TABLE M_JCSZ_CCGCSZ
ADD DATAID VARCHAR2(32);

-- 升级包的一张表忘记提交更新的，我的头像表
-- Create table
create table M_ZPB
(
  WJBT   VARCHAR2(255),
  WJNR   BLOB,
  WJLJ   VARCHAR2(255),
  SCRQ   DATE,
  USERID VARCHAR2(32) not null
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table M_ZPB
  is '移动端照片表(移动端头像)';
-- Add comments to the columns 
comment on column M_ZPB.WJBT
  is '文件标题';
comment on column M_ZPB.WJNR
  is '文件内容
';
comment on column M_ZPB.WJLJ
  is '文件路径';
comment on column M_ZPB.SCRQ
  is '上传日期';
comment on column M_ZPB.USERID
  is '用户id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table M_ZPB
  add constraint PK_ZPB primary key (USERID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


