<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
   <title>Hello</title>
</head>
<body>
   <h1>Current server time: <%= request.getAttribute("date") %></h1>
    <a href="index.jsp">Главная страница</a>

    <h2>Free Phone List:</h2>
        <p>
            <%= request.getAttribute("strokaJson")%>>
        </p>
</body>
</html>
