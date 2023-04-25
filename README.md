**Задание №1**

ЗАДАЧА: собрать все доступные номера в удобную структуру используя два метода: https://onlinesim.ru/api/getFreeCountryList https://onlinesim.ru/api/getFreePhoneList?country=?

Вывести все номера по всем странам в консоль, или файл, или графический интерфейс на Swing или JavaFx.

Можно использовать любые библиотеки, чем меньше, тем лучше.

Справка по api: https://onlinesim.ru/docs/api/ru/free/_info

**Задание №2**

ЗАДАЧА: Распарсить прайс лист со страницы http://onlinesim.ru/price-list, ВАЖНО: json-ответ не использовать, именно парсинг html-страницы.

Привести к виду: Map<Название страны, Map<Название сервиса, цена>>.

Привести в json строку. Полученные данные вывести в консоль или файл, или визуализировать (по своему усмотрению) в окне написанном на Swing (JavaFx)

Примерный результат: { "Россия" : { "Вконтакте" : 2, "Mail.ru" : 1 ... }, "Германия" : { "Вконтакте" : 2, "Mail.ru" : 1 ... } ... }

**Задание №3**

ЗАДАЧА: Упростить (оптимизировать, улучшить читаемость)

void processTask(ChannelHandlerContext ctx) { InetSocketAddress lineAddress = new InetSocketAddress(getIpAddress(), getUdpPort()); CommandType typeToRemove; for (Command currentCommand : getAllCommands()) { if (currentCommand.getCommandType() == CommandType.REBOOT_CHANNEL) { if (!currentCommand.isAttemptsNumberExhausted()) { if (currentCommand.isTimeToSend()) { sendCommandToContext(ctx, lineAddress, currentCommand.getCommandText());

try { AdminController.getInstance().processUssdMessage( new DblIncomeUssdMessage(lineAddress.getHostName(), lineAddress.getPort(), 0, EnumGoip.getByModel(getGoipModel()), currentCommand.getCommandText()), false); } catch (Exception ignored) { } currentCommand.setSendDate(new Date()); Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s", lineAddress.getHostString(), lineAddress.getPort(), currentCommand.getCommandText())); currentCommand.incSendCounter(); } } else { typeToRemove = currentCommand.getCommandType(); deleteCommand(typeToRemove); } } else { if (!currentCommand.isAttemptsNumberExhausted()) { sendCommandToContext(ctx, lineAddress, currentCommand.getCommandText());

try { AdminController.getInstance().processUssdMessage( new DblIncomeUssdMessage(lineAddress.getHostName(), lineAddress.getPort(), 0, EnumGoip.getByModel(getGoipModel()), currentCommand.getCommandText()), false); } catch (Exception ignored) { } Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s", lineAddress.getHostString(), lineAddress.getPort(), currentCommand.getCommandText())); currentCommand.setSendDate(new Date()); currentCommand.incSendCounter(); } else { typeToRemove = currentCommand.getCommandType(); deleteCommand(typeToRemove); } } } sendKeepAliveOkAndFlush(ctx); }
