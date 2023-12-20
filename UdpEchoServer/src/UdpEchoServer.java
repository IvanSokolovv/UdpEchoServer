import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import static java.net.InetAddress.getLocalHost;
import static java.util.logging.Logger.getAnonymousLogger;

public class UdpEchoServer {

    public static void main(String[] args) throws IOException {
        Logger logger = getAnonymousLogger();

        InetSocketAddress socketAddress = new InetSocketAddress(getLocalHost(), 6666);
        DatagramSocket socket = new DatagramSocket(socketAddress);

        while (true) {
            byte[] bytesToReceive = new byte[1000];
            DatagramPacket receivedPacket =
                    new DatagramPacket(bytesToReceive, bytesToReceive.length);

            logger.info("Ждем клиента");
            socket.receive(receivedPacket);

            String receivedModified = "Принято: " + new String(receivedPacket.getData());
            logger.info(receivedModified);

            String responseString = "Отправляем: " + receivedModified;
            byte[] responseBytes = responseString.getBytes();
            InetAddress responseAddress = receivedPacket.getAddress();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseBytes,
                    responseBytes.length,
                    responseAddress,
                    receivedPacket.getPort()
            );

            logger.info(responseString);
            socket.send(responsePacket);
            logger.info("Сервер закончил свою работу");
        }
    }
}