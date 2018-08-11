# factlin sample project

this is sample project of [factlin](https://github.com/maeharin/factlin)

## test frameworks

- master branch: junit5
- kotlintest branch: kotlintest

## run test

start database. database are from [this](http://www.postgresqltutorial.com/postgresql-sample-database/)

```
docker-compose up
```

generate db test fixture classes by factlin

```
./gradlew factlin
```

run db tests

```
./gradlew test
```

done!

## run app

```
$ ./gradlew run
```

localhost:8080

```
$ curl -XPOST http://localhost:8080/user -d "name=maeharin&job=ENGINEER"
```

localhost:8080/users
