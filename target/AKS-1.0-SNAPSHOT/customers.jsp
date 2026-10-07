<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Покупатели</title>
</head>

<body>

<h1>Список покупателей</h1>

<nav>
    <a href="${pageContext.request.contextPath}/customers">
        Покупатели
    </a>
    |
    <a href="${pageContext.request.contextPath}/orders">
        Заказы
    </a>
</nav>
<hr>

<p>
    <a href="${pageContext.request.contextPath}/customers?action=new">
        Добавить покупателя
    </a>
</p>

<table border="1" cellpadding="5" cellspacing="0">

    <thead>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Email</th>
        <th>Телефон</th>
        <th>Действия</th>
    </tr>
    </thead>

    <tbody>

    <c:forEach var="customer" items="${customers}">
        <tr>
            <td>
                ${customer.id}
            </td>
            <td>
                ${customer.name}
            </td>
            <td>
                ${customer.email}
            </td>
            <td>
                ${customer.phone}
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/customers?action=edit&id=${customer.id}">
                    Редактировать
                </a>
                |
                <a href="${pageContext.request.contextPath}/customers?action=delete&id=${customer.id}"
                   onclick="return confirm('Удалить этого покупателя?');">
                    Удалить
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>