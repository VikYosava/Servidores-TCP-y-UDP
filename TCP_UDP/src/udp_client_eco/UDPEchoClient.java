package udp_client_eco;


import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UDPEchoClient {

    public static void main(String[] args) throws IOException {

        if ((args.length < 1) || (args.length > 2)) {
            throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");
        }
        InetAddress serverAddress = InetAddress.getByName(args[0]);  // IP Servidor

        int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 6789;
//        boolean salir = false;
//        while(!salir) {
        	System.out.println("Introduzca el nombre, los apellidos y el número de habitación del paciente: ");
        	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        	String mensaje=stdIn.readLine();
			String[] cadenas = mensaje.split(" ");
			String nombre = cadenas[0];
			String apellido1 = cadenas[1];
			String apellido2 = cadenas[2];
			String numHabitacion = cadenas[3];

			//fecha
			LocalDate fechaActual = LocalDate.now();
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String fecha = fechaActual.format(formato);
			String cadenaCompleta = nombre + " | " + apellido1 + " " + apellido2 + " | " + numHabitacion + " | " + fecha;
			System.out.println("La cadena completa es: " + cadenaCompleta);
			//escribo socket
			String userInput = nombre + " " + apellido1 + " " + apellido2 + " " + numHabitacion;
        	byte[] bytesToSend = userInput.getBytes();
        	DatagramSocket socket = new DatagramSocket();
        	DatagramPacket sendPacket = new DatagramPacket(bytesToSend, //Datagrama a enviar
        			bytesToSend.length, serverAddress, servPort);
        	boolean iguales = mensaje.equals("CLOSE");
        	if(iguales) {
        		System.out.println("La conexión ha finalizado");
        		socket.close();
//        		salir = true;
        	}else {
        		System.out.println("Los datos introducidos son válidos");
        		socket.send(sendPacket);
        		DatagramPacket receivePacket =                             //Datagrama a recibir
        				new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);
        		socket.receive(receivePacket); // Podria no llegar nunca el datagrama de ECO
        		System.out.println("ECO: "+ new String(receivePacket.getData()));
        		socket.close();
        	}

//        }
    }

}