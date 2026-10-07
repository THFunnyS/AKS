# Дисциплина: Архитектура корпоративных систем
# Студент: Карев Федор Сергеевич
# Группа: 6132-010402D

# AKS — Jakarta EE приложение для учёта покупателей и заказов

Приложение предназначено для учёта покупателей и их заказов. Реализована многоуровневая архитектура с использованием GlassFish, Jakarta EE, JPA, Stateless EJB, Servlet, JSP и PostgreSQL.

## 1. Используемые технологии

* **Java 17**
* **Jakarta EE 10**
* **GlassFish 7**
* **PostgreSQL**
* **JPA (Jakarta Persistence)**
* **Stateless Session EJB**
* **Servlet**
* **JSP**
* **JSTL**
* **Maven**
* **JDBC**

## 2. Предметная область

В приложении реализована система учёта покупателей и заказов интернет-магазина.

Основные сущности:

### Customer — покупатель

Содержит следующие свойства:

* `id` — идентификатор;
* `name` — имя покупателя;
* `email` — адрес электронной почты;
* `phone` — номер телефона.

### CustomOrder — заказ

Содержит:

* `id` — идентификатор;
* `orderDate` — дата заказа;
* `totalSum` — сумма заказа;
* `status` — статус заказа;
* `customer` — покупатель, которому принадлежит заказ.

Между сущностями установлена связь **один ко многим**:

```text
Customer 1 ───────── * CustomerOrder
```

Один покупатель может иметь несколько заказов.

## 3. Архитектура приложения

Приложение построено по многоуровневой архитектуре:

```text
                    Браузер
                       │
                       ▼
                  JSP / HTML
                       │
                       ▼
                   Servlet
                       │
                       ▼
              Stateless Session EJB
                 ┌─────┴─────┐
                 │           │
        CustomerService  OrderService
                 │           │
                 └─────┬─────┘
                       ▼
                  EntityManager
                       │
                       ▼
                      JPA
                       │
                       ▼
                 JDBC Resource
                  jdbc/ShopDB
                       │
                       ▼
                  PostgreSQL
```

### Слой представления

Используются Servlet и JSP.

Основные компоненты:

* `CustomerServlet`
* `OrderServlet`
* `customers.jsp`
* `customer-form.jsp`
* `orders.jsp`
* `order-form.jsp`

### Бизнес-слой

Реализован с помощью Stateless Session EJB:

* `CustomerService`
* `OrderService`

Компоненты предоставляют операции создания, получения, изменения и удаления данных.

### Слой данных

Для работы с базой данных используются JPA Entity:

* `Customer`
* `CustomerOrder`

Доступ к данным осуществляется через `EntityManager`.

## 4. Структура проекта

Основная структура проекта:

```text
AKS/
├── database/
│   └── init.sql
│
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── example/
│       │           ├── model/
│       │           │   ├── Customer.java
│       │           │   └── CustomerOrder.java
│       │           │
│       │           ├── service/
│       │           │   ├── CustomerService.java
│       │           │   └── OrderService.java
│       │           │
│       │           └── web/
│       │               ├── CustomerServlet.java
│       │               └── OrderServlet.java
│       │
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml
│       │
│       └── webapp/
│           ├── customers.jsp
│           ├── customer-form.jsp
│           ├── orders.jsp
│           └── order-form.jsp
│
├── pom.xml
└── README.md
```

## 5. База данных

Для приложения используется PostgreSQL.

Название базы данных:

```text
AKS
```

Параметры подключения:

```text
Host: localhost
Port: 5432
Database: AKS
User: postgres
```

SQL-скрипт для создания структуры базы данных и её начального заполнения находится в:

```text
database/init.sql
```

Основные таблицы:

```text
customer
orders
```

Связь между таблицами реализована с помощью внешнего ключа:

```text
orders.customer_id → customer.id
```

Схема базы данных создаётся SQL-скриптом. Hibernate/EclipseLink не используется для автоматического создания таблиц.

В `persistence.xml` генерация схемы отключена:

```xml
<property name="jakarta.persistence.schema-generation.database.action"
          value="none"/>
```

## 6. Настройка GlassFish

В GlassFish настроен JDBC Connection Pool:

```text
Name: ShopDBPool
DataSource Classname:
org.postgresql.ds.PGSimpleDataSource
```

Для подключения используются параметры PostgreSQL:

```text
serverName=localhost
portNumber=5432
databaseName=AKS
user=postgres
password=<пароль PostgreSQL>
```

Также создан JDBC Resource:

```text
JNDI Name: jdbc/ShopDB
Pool Name: ShopDBPool
```

Файл драйвера PostgreSQL добавлен в библиотеку домена GlassFish.

## 7. Persistence Unit

JPA настроена с помощью файла:

```text
src/main/resources/META-INF/persistence.xml
```

Persistence Unit:

```text
ShopPU
```

Тип транзакций:

```text
JTA
```

Источник данных:

```text
jdbc/ShopDB
```

## 8. Функциональность приложения

### Работа с покупателями

В разделе «Покупатели» доступны:

* просмотр списка покупателей;
* добавление покупателя;
* редактирование покупателя;
* удаление покупателя.

### Работа с заказами

В разделе «Заказы» доступны:

* просмотр списка заказов;
* добавление заказа;
* редактирование заказа;
* удаление заказа;
* выбор покупателя для заказа;
* просмотр связанного с заказом покупателя.

### Бизнес-правило

Реализовано ограничение:

> Покупателя нельзя удалить, если в базе данных существуют связанные с ним заказы.

После удаления всех заказов покупателя его удаление становится возможным.

## 9. Сборка проекта

Проект использует Maven.

Для создания WAR-файла необходимо выполнить:

```text
clean
package
```

в Maven.

После успешной сборки файл приложения находится в:

```text
target/AKS-1.0-SNAPSHOT.war
```

## 10. Развёртывание

Приложение разворачивается на GlassFish.

Развёртывание выполняется отдельно от IDE через `asadmin`.

Пример:

```text
asadmin deploy D:\python\AKS\target\AKS-1.0-SNAPSHOT.war
```

При повторном развёртывании:

```text
asadmin redeploy D:\python\AKS\target\AKS-1.0-SNAPSHOT.war
```

После успешного развёртывания приложение доступно по адресу:

```text
http://localhost:8080/AKS-1.0-SNAPSHOT/
```

Основные страницы:

```text
/customers
/orders
```

## 11. Запуск GlassFish

Для запуска сервера используется `asadmin`.

```text
asadmin start-domain
```

Административная консоль GlassFish:

```text
http://localhost:4848
```
