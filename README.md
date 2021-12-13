# APIの概要

本APIはICWで利用されることを想定した商品検索兼在庫管理APIです。

このドキュメントでは、APIの役割やデータの説明、起動方法などを説明します。

## APIの役割

本APIが果たすべき役割について説明します。

### APIのインターフェース

APIのインターフェースについては、`docs/interface/api-spec-stocks.html` を参照してください。

### 役割

主に商品検索、在庫管理を行う際に利用されることを想定したAPIです。

### 利用想定

パブリックなAPIではありません。

利用する際は、本リポジトリをクローンし、自身のサーバーにコンテナ化してデプロイする必要があります。

#### データについて

本APIはDBとの一体型です。したがって、データ構造やデータはあらかじめ定められた形式に則っています。

テーブル作成用のDDLは `src/main/resources/schema.sql` , データ挿入用のDDLは `src/main/resources/data.sql` にあります。

このファイルの内容を変更しても、変更内容は反映されませんのでご注意ください。

どうしても好きなデータ構造にしたいという方がいらっしゃいましたら、[付録](#付録)を参照ください。

#### データ構造説明

* Products

```sql
create table Products (
    id varchar(10) not null,         -- 商品ID。プライマリーキー
    category varchar(10) not null,   -- カテゴリー。enumで表現される。FRUIT, VEGETABLE, JUICE
    name varchar(100) not null,      -- 商品名
    kana varchar(150) not null,      -- 商品カナ
    price int not null,              -- 金額
    comment varchar(300) not null,   -- コメント
    image_url varchar(100) not null, -- 商品の画像URL

    primary key (id)
);
```

* Stocks
  
```sql
create table Stocks (
    product_id varchar(10) not null,           -- 商品ID。プライマリーキー。外部キー
    amount int not null,                       -- 在庫数

    primary key (product_id),
    foreign key (product_id) references products(id)
        on update no action
        on delete cascade
);
```

なお、`Products.image_url` には `./img/products/tomato.jpg` などの文字列が入っていますので、適宜各自のリポジトリに同等の名前のイメージファイルを配置することで、画像を表示させることができます。

是非ご利用ください。

## APIの起動方法

ICW用の商品検索兼在庫管理APIを起動する方法を紹介します。

### Dockerコンテナ起動

[こちら](https://github.ibm.com/Riku-Hashiki/icw-api.git)からプロジェクトをクローン。

```bash
git clone https://github.ibm.com/Riku-Hashiki/icw-api.git
cd icw-api
```

Dockerコンテナを作成し、以下のように起動する。

```bash
docker build -t icw-api:1.0 .

docker run --name icw-api -d -p 8091:8091 icw-api:1.0
```

これで、`http://localhost:8091` にてコンテナが起動しています。

疎通確認は以下のように取ってください。

```bash
curl http://localhost:8091/api/healthcheck/app/status
```

ログは以下のコマンドで確認できます。

```bash
docker logs -f icw-api
```

### Kubernetesにデプロイ

作ったコンテナをKubernetesにデプロイします。

#### クラスタ作成

IBM CloudでKubernetesフリークラスターを作成してください。

#### コンテナレジストリ

作成している間に、コンテナレジストリのセットアップをします。

コンテナレジストリの名前空間を作成してください。

作成出来たら、以下のコマンドでコンテナレジストリにコンテナをプッシュします。

```bash
## IBMCloudにCLIからログイン
ibmcloud login --sso

## リソースグループをDefault、リージョンをjp-tokに設定
ibmcloud target -g Default -r jp-tok

## コンテナレジストリにログイン
ibmcloud cr login

## 先ほど作ったコンテナに別のタグを付ける
## 名前空間はさっき作成したものに置き換える
docker tag api-icw:1.0 jp.icr.io/名前空間/icw-api:1.0

## コンテナレジストリにプッシュ
docker push jp.icr.io/名前空間/icw-api:1.0
```

これでコンテナレジストリにプッシュが完了しました。

#### デプロイ

Kubernetesにコンテナをデプロイしていきます。

まずはIKSに作ったクラスタをCLIで利用できるように構成情報をダウンロードします。

```bash
## クラスタ名は自分で作成したクラスタの名前
ibmcloud ks cluster config --cluster クラスタ名
```

これでさっき作ったフリークラスタにアクセスできるようになっているので、デプロイします。

```bash
## deploymentを作成
kubectl apply -f k8s/deployment.yaml

## serviceを作成(※外部アクセスが必要な時のみ)
kubectl apply -f k8s/service.yaml
```

これでデプロイが完了しました。

#### 疎通確認

疎通確認をします。  
以下コマンドで疎通に必要な情報を取ってきましょう。

```bash
## 外部IPアドレスのどのポートでコンテナが開いているか確認(PORT(S)に該当する部分の3XXXX)
kubectl get svc/api-service

## クラスタの外部IPアドレスを確認(Public IPに該当する部分)
ibmcloud ks workers --cluster クラスタ名
```

これで、`http://{外部IPアドレス}:{3XXXX}` でアクセスできるので、ヘルスチェック等を打ってみてください。

#### ログ確認

クラスタ上のpodのログは以下のコマンドで確認できます。

```bash
## pod名取得(NAMEに該当する部分)
kubectl get pods

## ログ表示(pod名は上記コマンドで取得した名前)
kubectl logs -f pod名
```

`-f` オプションはフォローオプションです。(ターミナルにログを表示し続ける)  
もう見たくないって方は `Ctrl+C` で抜けましょう。

#### h2 console表示

このAPIは `Spring Boot` のインメモリDBを利用しています。  
インメモリDBとして `H2DB` を採用しているので、`H2 Console` を表示することができます。

ブラウザで、`http://{外部IPアドレス}:{3XXXX}/api/h2-console` と打つと表示できます。

直接SQLを実行させることも可能です。

DBが汚くなって綺麗にしたいと思ったら、再度コンテナをデプロイしなおすことで綺麗になります。  
その際は以下のように実行しましょう。

```bash
## deployment削除
kubectl delete -f k8s/deployment.yaml

## service削除
kubectl delete -f k8s/service.yaml

## deploymentを作成
kubectl apply -f k8s/deployment.yaml

## serviceを作成(※外部アクセスが必要な時のみ)
kubectl apply -f k8s/service.yaml
```

## 付録

本APIに修正を加えた際は、以下のようにコマンドを打ち、出来上がった `jarファイル` を `releaseフォルダ` に配置することで、修正後のコンテナを作成することができます。

しかし、以下コマンドはlinux用です。Powershellなどでは動きませんので、あらかじめご了承ください。

```bash
## APIのビルドコマンド
./gradlew build

## APIの起動コマンド
./gradlew bootRun
```
