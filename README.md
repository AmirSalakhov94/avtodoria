- Сборка проекта посредством Maven 3+ (сделано)
- Оптимизация поискового запроса с помощью индексов (сделано)
- Защитить методы REST сервисов секретным ключом apikey, передаваемым в
заголовке запроса (сделано)
- Собирать метрику времени выполнения поискового метода сервиса событий с
помощью Spring Boot metrics (сделано)
- Покрытие сервиса модульными тестами JUnit (сделано)
- Наличие интеграционного теста, оформленного в виде bash скрипта, в котором
производится запуск сервисов, заполнение базы данных событиями, вызов
сервиса событий и отображение результата в читаемом формате. (не сделано)

Микросервисы:
Application - 1 сервис
GeoApplication - 2 сервис
