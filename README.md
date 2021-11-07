# API起動方法

ICW用の在庫管理APIを起動する方法を紹介します。

## APIのインターフェース

APIのインターフェースについては、`docs/interface/api-spec-stocks.html` を参照してください。

## Dockerコンテナ起動

[こちら](https://github.ibm.com/Riku-Hashiki/icw-api.git)からプロジェクトをクローン。

```bash
git clone https://github.ibm.com/Riku-Hashiki/icw-api.git
```

Dockerコンテナを作成し、以下のように起動する。

```bash
docker build -t icw-api:1.0 .

docker run --name icw-api -d -p 8091:8091 icw-api:1.0
```

これで、`http://localhost:8091` にてコンテナが起動しています。

疎通確認は以下のように取ってください。

```bash
curl -X GET http://localhost:8091/api/healthcheck/app/status
```

ログは以下のコマンドで確認できます。

```bash
docker logs -f icw-api
```

## Kubernetesにデプロイ

作ったコンテナをKubernetesにデプロイします。

### クラスタ作成

IBM CloudでKubernetesフリークラスターを作成してください。

### コンテナレジストリ

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

### デプロイ

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

### 疎通確認

疎通確認をします。  
以下コマンドで疎通に必要な情報を取ってきましょう。

```bash
## 外部IPアドレスのどのポートでコンテナが開いているか確認(PORT(S)に該当する部分の3XXXX)
kubectl get svc/api-service

## クラスタの外部IPアドレスを確認(Public IPに該当する部分)
ibmcloud ks workers --cluster クラスタ名
```

これで、`http://{外部IPアドレス}:{3XXXX}` でアクセスできるので、ヘルスチェック等を打ってみてください。

### ログ確認

クラスタ上のpodのログは以下のコマンドで確認できます。

```bash
## pod名取得(NAMEに該当する部分)
kubectl get pods

## ログ表示(pod名は上記コマンドで取得した名前)
kubectl logs -f pod名
```

`-f`　オプションはフォローオプションです。(ターミナルにログを表示し続ける)  
もう見たくないって方は `Ctrl+C` で抜けましょう。

### h2 console表示

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
