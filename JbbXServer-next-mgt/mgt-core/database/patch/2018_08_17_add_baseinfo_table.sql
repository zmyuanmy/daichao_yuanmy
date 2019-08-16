CREATE TABLE mgt_education_base
(
  id int not null ,
  des varchar(10),
  PRIMARY KEY (id)
)
ENGINE InnoDB;

CREATE TABLE mgt_marital_status_base
(
  id int not null ,
  des varchar(10),
  PRIMARY KEY (id)
)
ENGINE InnoDB;

CREATE TABLE mgt_relation_base
(
  id int not null ,
  relation_level int not null,
  des varchar(10),
  relation varchar(10),
  queue varchar(10),
  PRIMARY KEY (id,relation_level)
)
ENGINE InnoDB;

CREATE TABLE mgt_loan_purpose_base
(
  id int not null ,
  des varchar(10),
  PRIMARY KEY (id)
)
ENGINE InnoDB;

CREATE TABLE mgt_profession_type_base
(
  id int not null ,
  des varchar(10),
  PRIMARY KEY (id)
)
ENGINE InnoDB;


INSERT INTO mgt_education_base (id,des) values (1,'中专/高中以下');
INSERT INTO mgt_education_base (id,des) values (2,'大专');
INSERT INTO mgt_education_base (id,des) values (3,'本科');
INSERT INTO mgt_education_base (id,des) values (4,'硕士');
INSERT INTO mgt_education_base (id,des) values (5,'博士及以上');

INSERT INTO mgt_marital_status_base (id,des) values (1,'未婚');
INSERT INTO mgt_marital_status_base (id,des) values (2,'已婚已育');
INSERT INTO mgt_marital_status_base (id,des) values (3,'离异');
INSERT INTO mgt_marital_status_base (id,des) values (4,'已婚未育');
INSERT INTO mgt_marital_status_base (id,des) values (5,'丧偶');
INSERT INTO mgt_marital_status_base (id,des) values (6,'其他');

INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (1,1,'父亲','1','2');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (2,1,'配偶','1','1');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (3,1,'母亲','1','3');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (9,1,'儿子','1','4');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (10,1,'女儿','1','5');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (11,1,'兄弟','2','1');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (12,1,'姐妹','2','2');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (5,2,'同学','2','4');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (8,3,'亲戚','2','3');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (7,4,'同事','2','6');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (6,5,'朋友','2','5');
INSERT INTO mgt_relation_base (id,relation_level,des,relation,queue) values (100,6,'其他','2','7');

INSERT INTO mgt_loan_purpose_base   (id,des) values (1,'装修');
INSERT INTO mgt_loan_purpose_base   (id,des) values (2,'婚庆');
INSERT INTO mgt_loan_purpose_base   (id,des) values (3,'旅游');
INSERT INTO mgt_loan_purpose_base   (id,des) values (4,'教育');
INSERT INTO mgt_loan_purpose_base   (id,des) values (5,'租房');
INSERT INTO mgt_loan_purpose_base   (id,des) values (6,'汽车周边');
INSERT INTO mgt_loan_purpose_base   (id,des) values (7,'电子数码产品');
INSERT INTO mgt_loan_purpose_base   (id,des) values (8,'医疗');
INSERT INTO mgt_loan_purpose_base   (id,des) values (9,'其他');
INSERT INTO mgt_loan_purpose_base   (id,des) values (10,'家用电器');
INSERT INTO mgt_loan_purpose_base   (id,des) values (11,'家具家居');

INSERT INTO mgt_profession_type_base   (id,des) values (1,'上班族');
INSERT INTO mgt_profession_type_base   (id,des) values (2,'企业主');
INSERT INTO mgt_profession_type_base   (id,des) values (3,'个体户');
INSERT INTO mgt_profession_type_base   (id,des) values (4,'学生');
INSERT INTO mgt_profession_type_base   (id,des) values (5,'自由职业');









