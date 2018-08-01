# factlin sample project

this is sample project of [factlin](https://github.com/maeharin/factlin)

## run

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
