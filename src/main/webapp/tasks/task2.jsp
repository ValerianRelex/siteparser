<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
   <title>Task 2</title>
</head>
<body>
    <a href="<%=request.getContextPath()%>">Главная страница</a>

    <h2>Task 2 result:</h2>
        <p>
            <c:forEach items="${price}" var="string">
                            ${entry.key}, услуга: ${entry.value}<br>
            </c:forEach>
        </p>
</body>
</html>
