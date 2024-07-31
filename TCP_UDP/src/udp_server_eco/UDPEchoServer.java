package udp_server_eco;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPEchoServer {

    private static final int ECHOMAX = 255; // Tamagno maximo de los mensajes

    public static void main(String[] args) throws IOException {
    	System.out.println("Iniciando servidor...");
        DatagramSocket socket = new DatagramSocket(6789);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

        while (true) {
            socket.receive(packet); // Recibe un datagrama del cliente
            System.out.println("IP cliente: " + packet.getAddress().getHostAddress() +
                    " Puerto cliente: " + packet.getPort());
            packet.getData(); //hago lo que quiera con los datos
            byte[] bytesToSend = packet.getData();
            DatagramPacket sendPacket = new DatagramPacket(bytesToSend, bytesToSend.length, packet.getAddress(), packet.getPort());
            String data=new String(sendPacket.getData(), StandardCharsets.UTF_8);
            System.out.println(data);
            socket.send(sendPacket); // Enviar el mismo datagrama al cliente
            packet.setLength(ECHOMAX); // Limpiar buffer
        }
    }
}