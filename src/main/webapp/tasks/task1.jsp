<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
   <title>Task 1</title>
</head>
<body>
    <a href="<%=request.getContextPath()%>">Главная страница</a>

    <h2>Free Phone List:</h2>
        <p>
            <c:forEach items="${countryNumbers}" var="entry">
                ${entry.key}, телефон: ${entry.value}<br>
            </c:forEach>
        </p>
</body>
</html>
