# APIの概要

## dbの立ち上げ方

ルートディレクトリで以下を実行

```bash
$ docker-compose up -d

Starting postgresql ... done
```

## APIの立ち上げ方

ルートディレクトリで以下を実行

```bash
## APIのビルドコマンド
$ ./gradlew build

BUILD SUCCESSFUL in 2s

## APIの起動コマンド
$ ./gradlew bootRun

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.2.RELEASE)

2022/06/05 00:11:19.009  INFO [restartedMain] : Starting ApiApplication on PC with PID 21353 (/home/rikuhashiki/study/portfolio-api/build/classes/java/main started by rikuhashiki in /home/rikuhashiki/study/portfolio-api)
2022/06/05 00:11:19.011  INFO [restartedMain] : No active profile set, falling back to default profiles: default
2022/06/05 00:11:19.070  INFO [restartedMain] : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2022/06/05 00:11:19.070  INFO [restartedMain] : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2022/06/05 00:11:19.862  INFO [restartedMain] : Bootstrapping Spring Data JPA repositories in DEFERRED mode.
2022/06/05 00:11:19.904  INFO [restartedMain] : Finished Spring Data repository scanning in 25ms. Found 0 JPA repository interfaces.
2022/06/05 00:11:20.414  INFO [restartedMain] : Tomcat initialized with port(s): 8091 (http)
2022/06/05 00:11:20.422  INFO [restartedMain] : Starting service [Tomcat]
2022/06/05 00:11:20.422  INFO [restartedMain] : Starting Servlet engine: [Apache Tomcat/9.0.37]
2022/06/05 00:11:20.500  INFO [restartedMain] : Initializing Spring embedded WebApplicationContext
2022/06/05 00:11:20.500  INFO [restartedMain] : Root WebApplicationContext: initialization completed in 1429 ms
2022/06/05 00:11:20.732  INFO [restartedMain] : Initializing ExecutorService 'applicationTaskExecutor'
2022/06/05 00:11:20.765  INFO [task-1] : HHH000204: Processing PersistenceUnitInfo [name: default]
2022/06/05 00:11:20.801  INFO [task-1] : HHH000412: Hibernate ORM core version 5.4.18.Final
2022/06/05 00:11:20.910  INFO [task-1] : HCANN000001: Hibernate Commons Annotations {5.1.0.Final}
2022/06/05 00:11:20.995  INFO [task-1] : HikariPool-1 - Starting...
2022/06/05 00:11:21.176  INFO [task-1] : HikariPool-1 - Start completed.
2022/06/05 00:11:21.187  INFO [task-1] : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL95Dialect
2022/06/05 00:11:21.669  INFO [task-1] : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2022/06/05 00:11:21.678  INFO [task-1] : Initialized JPA EntityManagerFactory for persistence unit 'default'
2022/06/05 00:11:21.743  INFO [restartedMain] : LiveReload server is running on port 35729
2022/06/05 00:11:21.751  INFO [restartedMain] : Exposing 2 endpoint(s) beneath base path '/actuator'
2022/06/05 00:11:21.789  INFO [restartedMain] : Tomcat started on port(s): 8091 (http) with context path '/api'
2022/06/05 00:11:21.791  INFO [restartedMain] : Triggering deferred initialization of Spring Data repositories…
2022/06/05 00:11:21.792  INFO [restartedMain] : Spring Data repositories initialized!
```

## フロント(vue)の立ち上げ方

```bash
$ yarn serve
     ─╯
yarn run v1.22.4
$ vue-cli-service serve
 INFO  Starting development server...
Starting type checking service...
Using 1 worker with 2048MB memory limit
40% building 154/168 modules 14 active /home/rikuhashiki/study/portfolio/node_modules/vuetify/lib/util/mixins.jsBrowserslist: caniuse-lite is outdated. Please run:
  npx browserslist@latest --update-db
  Why you should do it regularly: https://github.com/browserslist/browserslist#browsers-data-updating
98% after emitting CopyPlugin

 DONE  Compiled successfully in 24372ms                                                                                                                                                                                                                      11:00:48 PM

No type errors found
Version: typescript 4.1.6
Time: 6265ms

  App running at:
  - Local:   http://localhost:8080/ 
  - Network: http://172.17.201.155:8080/

  Note that the development build is not optimized.
  To create a production build, run yarn build.
```
