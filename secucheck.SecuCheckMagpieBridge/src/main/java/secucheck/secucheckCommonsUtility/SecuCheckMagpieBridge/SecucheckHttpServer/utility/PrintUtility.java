package secucheck.secucheckCommonsUtility.SecuCheckMagpieBridge.SecucheckHttpServer.utility;

import secucheck.secucheckCommonsUtility.SecuCheckMagpieBridge.FluentTQLMagpieBridgeMainServer;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

public class PrintUtility {
    public static void printMessageInIDE(MessageType messageType, String message) {
        FluentTQLMagpieBridgeMainServer
                .fluentTQLMagpieServer
                .forwardMessageToClient(
                        new MessageParams(messageType, message)
                );
    }
}
