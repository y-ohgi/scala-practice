http-server
---

# 概要
httpリクエストを受け、htmlを返すだけのサーバー

# How To Use
## 環境
* scala `2.12.3`
* sbt `1.0.4`

## 起動
```console
$ sbt run
```

## jarの生成
```console
$ sbt assembly
```

## Dockerの起動
```console
$ docker build -t http-server .
$ docker run -p 8080:8080 http-server
```

# TODO
* [ ] GET以外のリクエストへの対応
* [ ] 脆弱性の対応
* [ ] リファクタリング
