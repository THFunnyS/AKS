<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>${formTitle}</title>
</head>

<body>

<h1>${formTitle}</h1>

<c:if test="${not empty error}">
    <p style="color: red;">
        ${error}
    </p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/orders">

    <input type="hidden"
           name="id"
           value="${order.id}">

    <p>
        <label>
            Дата заказа:
            <br>
            <input type="date"
                   name="orderDate"
                   value="${order.orderDate}"
                   required>

        </label>
    </p>

    <p>
        <label>
            Сумма:
            <br>
            <input type="number"
                   name="totalSum"
                   value="${order.totalSum}"
                   step="0.01"
                   min="0"
                   required>

        </label>
    </p>

    <p>
        <label>
            Статус:
            <br>
            <select name="status">

                <option value="not ready"
                    ${order.status == 'not ready' ? 'selected' : ''}>
                    Не готов
                </option>

                <option value="processing"
                    ${order.status == 'processing' ? 'selected' : ''}>
                    В обработке
                </option>

                <option value="ready"
                    ${order.status == 'ready' ? 'selected' : ''}>
                    Готов
                </option>

                <option value="completed"
                    ${order.status == 'completed' ? 'selected' : ''}>
                    Выполнен
                </option>
            </select>
        </label>
    </p>

    <p>
        <label>
            Покупатель:
            <br>
            <select name="customerId" required>
                <option value="">
                    -- Выберите покупателя --
                </option>
                <c:forEach var="customer"
                           items="${customers}">

                    <option value="${customer.id}"
                        ${order.customer != null &&
                          order.customer.id == customer.id
                          ? 'selected'
                          : ''}>

                        ${customer.name}
                    </option>
                </c:forEach>
            </select>
        </label>
    </p>
    <button type="submit">
        Сохранить
    </button>
</form>

<p>
    <a href="${pageContext.request.contextPath}/orders">
        Вернуться к списку
    </a>
</p>
</body>
</html>