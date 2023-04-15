//package task_3;
//
//import java.net.InetSocketAddress;
//
//public class RefactorComplete {
//    void processTask(ChannelHandlerContext context) {
//	InetSocketAddress lineAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
//
//	for (Command currentCommand : getAllCommands()) {
//	    if (currentCommand.getCommandType() == CommandType.REBOOT_CHANNEL) {
//		if (!currentCommand.isAttemptsNumberExhausted() && currentCommand.isTimeToSend()) {
//			// Тип переменной и версия Java неизвестны. Предположу что версия Java 10 или выше.
//			// Иначе нужно точно знать возвращаемый тип, судя по методу - это может быть String.
//			var commandText = currentCommand.getCommandText();
//			sendCommandToContext(context, lineAddress, commandText);
//
//			try {
//			    DblIncomeUssdMessage dblIncomeUssdMessage = new DblIncomeUssdMessage(
//					    lineAddress.getHostName(), lineAddress.getPort(), 0,
//					    EnumGoIp.getByModel(getGoIpModel()), commandText);
//
//			    AdminController.getInstance().processUssdMessage(dblIncomeUssdMessage, false);
//			} catch (Exception ignored) {
//			}
//			currentCommand.setSendDate(new Date());
//			currentCommand.incSendCounter();
//			Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s", lineAddress.getHostString(),
//					lineAddress.getPort(), commandText));
//		    }
//		} else {
//		    deleteCommand(currentCommand.getCommandType());
//		}
//	    } else {
//		if (!currentCommand.isAttemptsNumberExhausted()) {
//		    var commandText = currentCommand.getCommandText();
//		    sendCommandToContext(context, lineAddress, commandText);
//
//		    try {
//			DblIncomeUssdMessage dblIncomeUssdMessage = new DblIncomeUssdMessage(lineAddress.getHostName(),
//					lineAddress.getPort(), 0, EnumGoIp.getByModel(getGoIpModel()), commandText);
//
//			AdminController.getInstance().processUssdMessage(dblIncomeUssdMessage, false);
//		    } catch (Exception ignored) {
//		    }
//		    currentCommand.setSendDate(new Date());
//		    currentCommand.incSendCounter();
//		    Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s", lineAddress.getHostString(),
//				    lineAddress.getPort(), commandText));
//		} else {
//		    deleteCommand(currentCommand.getCommandType());
//		}
//	    }
//	}
//	sendKeepAliveOkAndFlush(context);
//    }
//}
