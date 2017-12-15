create table rank (
id int(11) NOT NULL AUTO_INCREMENT,
name char(20) NOT NULL DEFAULT ' ' COMMENT '姓名',
score int(32) NOT NULL DEFAULT '0' COMMENT '得分',
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;