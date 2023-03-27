<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
 <head>
   <title>onlinesim.ru</title>
 </head>
 <body>
   <h2>Первое задание:</h2>
    <p>
        ЗАДАЧА: собрать все доступные номера в удобную структуру используя два метода:
            <p>https://onlinesim.ru/api/getFreeCountryList</p>
            <p>https://onlinesim.ru/api/getFreePhoneList?country=?</p>

            Вывести все номера по всем странам в консоль, или файл, или графический интерфейс на Swing или JavaFx.
            <br>
            Можно использовать любые библиотеки, чем меньше, тем лучше.
            <br>
            Справка по api:
            https://onlinesim.ru/docs/api/ru/free/_info
    </p>
    <h3>
        <a href="<%=request.getContextPath()%>/api/tasks/1" id="task1">Результат задания номер 1</a>
    </h3>
   <h2>Второе задание:</h2>
    <p>
        ЗАДАЧА: Распарсить прайс лист со страницы http://onlinesim.ru/price-list, <br>
        ВАЖНО: json-ответ не использовать, именно парсинг html-страницы.

        <p>Привести к виду:<br>
        Map<Название страны, Map<Название сервиса, цена>>.
        </p>
        Привести в json строку. <br>
        Полученные данные вывести в консоль или файл, или визуализировать (по  своему усмотрению) в окне написанном на Swing (JavaFx)

    </p>
    <h3>
        <a href="<%=request.getContextPath()%>/api/tasks/2" id="task2">Результат задания номер 2</a>
    </h3>
 </body>
</html>