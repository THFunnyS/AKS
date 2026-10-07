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
      action="${pageContext.request.contextPath}/customers">

    <input type="hidden"
           name="id"
           value="${customer.id}">

    <p>
        <label>
            Имя:
            <br>
            <input type="text"
                   name="name"
                   value="${customer.name}"
                   maxlength="50"
                   required>
        </label>
    </p>
    <p>
        <label>
            Email:
            <br>
            <input type="email"
                   name="email"
                   value="${customer.email}"
                   maxlength="100">
        </label>
    </p>
    <p>
        <label>
            Телефон:
            <br>
            <input type="text"
                   name="phone"
                   value="${customer.phone}"
                   maxlength="20">
        </label>
    </p>

    <button type="submit">
        Сохранить
    </button>

</form>

<p>
    <a href="${pageContext.request.contextPath}/customers">
        Вернуться к списку
    </a>
</p>
</body>
</html>