# Инструкция по сборке и настройке

### Настройки:
- spring.elasticsearch.uris - хост и порт для базы данных с таблицей результатов оценки в elasticsearch
- spring.elasticsearch.username - логин для базы данных с таблицей результатов оценки в elasticsearch
- spring.elasticsearch.password - пароль для базы данных с таблицей результатов оценки в elasticsearch
- camunda.bpm.db.schema-name - url для базы данных camunda
- camunda.bpm.db.username - логин для базы данных camunda
- camunda.bpm.db.password - пароль для базы данных camunda
- camunda.bpm.db.driver-class-name - драйвер для базы данных camunda
- camunda.bpm.database.schema-update - настройка которая определяет, как Hibernate будет управлять схемой базы данных при запуске приложения camunda
- camunda.base.url - http// + хост + порт для развертывания camunda
- camunda.bpm.database.type - тип базы данных camunda

### Сборка:
1) Указать нужные данные в настройках для подключения БД к приложению
2) Собрать проект используя maven (maven package)