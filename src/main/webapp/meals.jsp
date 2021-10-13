<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<c:forEach items="${requestScope.mealsList}" var="meal">
    <h2>
        <c:set var="color" value="green"/>
        <c:if test="${meal.excess eq true}">
            <c:set var="color" value="red"/>
        </c:if>
        <span style="color: <c:out value="${color}"/>; ">
            <c:out value="${meal}"/>
        </span>
    </h2>
</c:forEach>
</body>
</html>