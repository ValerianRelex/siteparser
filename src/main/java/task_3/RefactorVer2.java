//package task_3;
//
//import java.net.InetSocketAddress;
//
//public class RefactorVer2 {
//    void processTask(ChannelHandlerContext ctx) {
//	InetSocketAddress lineAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
//	CommandType typeToRemove;
//	for (Command currentCommand : getAllCommands()) {
//	    if (currentCommand.getCommandType() == CommandType.REBOOT_CHANNEL) {
//		if (!currentCommand.isAttemptsNumberExhausted() && currentCommand.isTimeToSend()) {
//		    sendCommand(ctx, lineAddress, currentCommand);
//		} else {
//		    typeToRemove = currentCommand.getCommandType();
//		    deleteCommand(typeToRemove);
//		}
//	    } else {
//		if (!currentCommand.isAttemptsNumberExhausted()) {
//		    sendCommand(ctx, lineAddress, currentCommand);
//		} else {
//		    typeToRemove = currentCommand.getCommandType();
//		    deleteCommand(typeToRemove);
//		}
//	    }
//	}
//	sendKeepAliveOkAndFlush(ctx);
//    }
//
//    private void sendCommand(ChannelHandlerContext ctx, InetSocketAddress lineAddress, Command command) {
//	sendCommandToContext(ctx, lineAddress, command.getCommandText());
//	try {
//	    AdminController.getInstance().processUssdMessage(
//			    new DblIncomeUssdMessage(lineAddress.getHostName(), lineAddress.getPort(), 0,
//					    EnumGoip.getByModel(getGoipModel()), command.getCommandText()), false);
//	} catch (Exception ignored) {
//	}
//	command.setSendDate(new Date());
//	Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s", lineAddress.getHostString(),
//			lineAddress.getPort(), command.getCommandText()));
//	command.incSendCounter();
//    }
//}
