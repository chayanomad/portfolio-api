--------------------------------------------------
-- ITEMS
--  商品情報テーブル
--------------------------------------------------
create table Items (
    id varchar(10) not null,
    category varchar(10) not null,
    name varchar(100) not null,
    kana varchar(150) not null,
    price int not null,
    comment varchar(300) not null,
    image_url varchar(100) not null,

    primary key (id)
);
create index items_ix1 on items(id);
create index items_ix2 on items(category);
create index items_ix3 on items(name, kana);
create index items_ix4 on items(kana);
create index items_ix5 on items(price);

--------------------------------------------------
-- STOCKS
--  在庫情報テーブル
--------------------------------------------------
create table Stocks (
    item_id varchar(10) not null,
    amount int not null,

    primary key (item_id),
    foreign key (item_id) references items(id)
        on update no action
        on delete cascade
);
create index stocks_ix1 on stocks(item_id);

--------------------------------------------------
-- ITEMS_SUMMARIES
--  商品情報一覧を取得するためのVIEW
--------------------------------------------------
create view item_summaries as
select
  i.id,
  i.category,
  i.name,
  i.kana,
  i.price,
  i.comment,
  i.image_url,
  s.amount,
from items i
LEFT JOIN stocks s
  on i.id = s.item_id
;