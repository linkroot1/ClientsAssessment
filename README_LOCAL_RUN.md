# Инструкция по локальному запуску
- Создайте пустую БД для камунды в PostgreSQL используя имя из настройки camunda.bpm.db.schema-name
- Запустите совместимую БД Elasticsearch (совместимая версия 7.15.2 и меньше)
- Выполните для БД с таблицей результатов оценки в Elasticsearch следующий  curl
`curl --location --request PUT 'http://localhost:9200/clientassessment' \
--header 'Content-Type: application/json' \
--data '{
       "mappings": {
         "properties": {
           "inn": { "type": "text" },
           "details": { "type": "text" },
           "success": { "type": "boolean" }
         }
       }
     }'`
  (подставьте свой порт и хост от бд Elasticsearch)
- Выполните шаги из README_BUILD_AND_SETTINGS.md по сборке проекта
- Запустите приложение