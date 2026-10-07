<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Заказы</title>
</head>

<body>
<h1>Список заказов</h1>
<p>
    <a href="${pageContext.request.contextPath}/orders?action=new">
        Добавить заказ
    </a>
</p>

<table border="1" cellpadding="5" cellspacing="0">

    <thead>
    <tr>
        <th>ID</th>
        <th>Дата</th>
        <th>Сумма</th>
        <th>Статус</th>
        <th>Покупатель</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                ${order.id}
            </td>
            <td>
                ${order.orderDate}
            </td>
            <td>
                ${order.totalSum}
            </td>
            <td>
                ${order.status}
            </td>
            <td>
                ${order.customer.name}
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders?action=edit&id=${order.id}">
                    Редактировать
                </a>
                |
                <a href="${pageContext.request.contextPath}/orders?action=delete&id=${order.id}"
                   onclick="return confirm('Удалить этот заказ?');">
                    Удалить
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p>
    <a href="${pageContext.request.contextPath}/customers">
        Перейти к покупателям
    </a>
    |
    <a href="${pageContext.request.contextPath}/orders">
        Заказы
    </a>
</p>
</body>
</html>