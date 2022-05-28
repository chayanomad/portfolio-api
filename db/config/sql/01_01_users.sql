-----------------------------------------------------
--  ユーザー情報に関わるテーブルを生成するためのDDL
-----------------------------------------------------

-----------------------------------------------------
-- USERS
--  ユーザー情報に関する情報を格納するテーブル
-----------------------------------------------------
create table users (
  -- ユーザーID
  id varchar(20) not null,
  -- 名前
  first_name varchar(30) not null,
  -- 名字
  last_name varchar(30) not null,
  -- 名前カナ
  first_name_kana varchar(30) not null,
  -- 名字カナ
  last_name_kana varchar(30),
  -- プロフィールのS3URL
  profile_pic_url varchar(150),
  -- 自己紹介文
  introduction varchar(500) not null,
  -- メールアドレス
  email varchar(100),
  -- LinkedInのURL
  linked_in varchar(150),
  -- TwitterのURL
  twitter varchar(150),
  -- FaceBookのURL
  facebook varchar(150),

  primary key (id)
);