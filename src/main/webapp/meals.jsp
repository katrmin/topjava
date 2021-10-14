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
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th/>
        <th/>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.mealsList}" var="meal">
        <tr>
            <td style="color: ${meal.color}; ">${meal.dateTime}</td>
            <td style="color: ${meal.color}; ">${meal.description}</td>
            <td style="color: ${meal.color}; ">${meal.calories}</td>
            <td><a href="MealController?action=edit&userId=${meal.id}">Update</a></td>
            <td><a href="MealController?action=delete&userId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="MealController?action=insert">Add Meal</a></p>
</body>
</html>