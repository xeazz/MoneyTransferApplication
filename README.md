# Сервис перевода денег
## Общая информация
**Сервис перевода денег** - REST-приложение, предоставляющее интерфейс для перевода денежных средств с одной карты на другую по заранее описанной [спецификации](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml).

Заранее подготовленное веб-приложение [(FRONT)](https://github.com/serp-ya/card-transfer) должно подключаться к разработанному сервису без доработок и использовать его функционал для перевода денег.

## Структура проекта

Подробная структурная [схема](/picture/Group.svg) проекта

Синтез произведен посредством REST-принципов на основе Spring mvc и Spring boot модулей:

- ### controller: 
 
  - `/controller/TransferController.java` - REST-контроллер для обработки сетевых запросов, помеченных аннотацией `@PostMapping` 
                   
- ### exceptions:
  
  - `/exceptions/IncorrectInputDataException.java` - Status Code : **400 - BAD REQUEST**.

  - `/exceptions/InternalServerErrorException.java` - Status Code : **500 - Internal Server Error**.
  
  - `/exceptions/GlobalExceptionHandler.java` - перехватчик исключений, создаваемых методами, аннотированными `@PostMapping`, обрабатывает исключения во всем приложении в одном глобальном компоненте обработки.

- ### model:
  - `/model/CardHolder.java` - сущность, содержащая параметры карты получателя и отправителя, имитирующая базу данных.
  
  - `/model/TransferAmount.java` - сущность, содержащая сумму перевода и валюту.
  
  - `/model/TransferMoney.java` - сущность, содержащая параметры карты отправителя и получателя, приходящая по адресу `localhost:5500/transfer`.
  
  - `/model/TransferOperation.java` - сущность, содержащая номер операции и код подтверждения транзации, приходящая по адресу `localhost:5500/confirmOperation`.
  
  - `/model/ErrorResponse.java` - сущность, содержащая код операции и сообщение об ошибки при некорректно введенных данных банковских карт.
  
  - `/model/SuccessResponse.java` - сущность, содержащая код операции при успешно проведенной транзакции.

- ### service:
   - `/service/TransferServiceImpl.java` - сервис для реализации бизнес-логики.


- ### repository:
  - `/repository/TransferRepositoryImpl.java` - абстракция доступа к базе данных, в качестве которой выступает `Map` для хранения `TransferMoney`, индекс в листе соответствует id операции типа `UUID`.

- ### validation:
  - `/repository/ValidationService.java` - класс отвечающий за валидацию запросов.

## Запуск приложения

1. Необходимо собрать `jar`-архив со Spring Boot приложением. Для этого в терминале, в корне проекта необходимо выполнить команду:

    - Для gradle: `./gradlew clean build` (если пишет Permission denied, тогда сначала выполните `chmod +x ./gradlew`).
  
    - Для maven: `./mvnw clean package` (если пишет Permission denied, тогда сначала выполните `chmod +x ./mvnw`).

2. С помощью Docker Compose необходимо запустить приложение с помощью команды `docker-compose up`.

3. Запускаем готовое демо-приложение по [адресу](https://serp-ya.github.io/card-transfer/).

## Виды запросов
- Запрос по адресу `http://localhost:5500/transfer` методом POST. Пример тела запроса:
```json
{
  "cardFromNumber": "4875130166305242",
  "cardFromValidTill": "05/26",
  "cardFromCVV": "123",
  "cardToNumber": "4832137002407281",
  "amount": {
      "value": 100,
      "currency": "RUB"
  }
}
```

- При успешном POST-запросе по endpoint `/transfer` для подтверждения транзакции передается код подтверждения методом POST-запросом. Пример тела запроса:
  

```json
{
    "operationId": "4fe32c4b-c747-4c2e-b608-b09680221e65",
    "code": "0000" 
}
```

## Логирование
В качестве логирования транзакций используется аннотация `@Slf4j`. На стороне сервера происходит сохранение информации о транзакциях, ошибках и успешных операциях в logs/application.log