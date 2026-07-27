### Hexlet tests and linter status:
[![Actions Status](https://github.com/spiri4/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/spiri4/java-project-72/actions)

## Page Analyzer

Веб-приложение для анализа веб-страниц.

### Demo

[Page Analyzer](https://java-project-72-vc4n.onrender.com)

### Запуск локально

```bash
cd app
./gradlew run
```

Приложение будет доступно по адресу: http://localhost:7070

### Сборка исполняемого JAR

```bash
cd app
./gradlew shadowJar
java -jar build/libs/app-1.0-SNAPSHOT-all.jar
```

### Переменные окружения

| Переменная | Описание | По умолчанию |
| --- | --- | --- |
| `PORT` | Порт HTTP-сервера | `7070` |
| `JDBC_DATABASE_URL` | URL подключения к БД | H2 in-memory `jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;` |
