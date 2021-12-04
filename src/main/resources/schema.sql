--------------------------------------------------
-- ITEMS
--  商品情報テーブル
--------------------------------------------------
create table Products (
    id varchar(10) not null,
    category varchar(10) not null,
    name varchar(100) not null,
    kana varchar(150) not null,
    price int not null,
    comment varchar(300) not null,
    image_url varchar(100) not null,

    primary key (id)
);
create index products_ix1 on products(id);
create index products_ix2 on products(category);
create index products_ix3 on products(name, kana);
create index products_ix4 on products(kana);
create index products_ix5 on products(price);

--------------------------------------------------
-- STOCKS
--  在庫情報テーブル
--------------------------------------------------
create table Stocks (
    product_id varchar(10) not null,
    amount int not null,

    primary key (product_id),
    foreign key (product_id) references products(id)
        on update no action
        on delete cascade
);
create index stocks_ix1 on stocks(product_id);

--------------------------------------------------
-- ITEMS_SUMMARIES
--  商品情報一覧を取得するためのVIEW
--------------------------------------------------
create view product_summaries as
select
  p.id,
  p.category,
  p.name,
  p.kana,
  p.price,
  p.comment,
  p.image_url,
  s.amount,
from products p
LEFT JOIN stocks s
  on p.id = s.product_id
;