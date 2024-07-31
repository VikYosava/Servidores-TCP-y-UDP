package tcp_server_eco;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
class EchoServerTCP {
    public static void main(String args[]) {
        String line;
        int numero_puerto=6543;
        try {
            ServerSocket sockfd = new ServerSocket(numero_puerto);
            System.out.println("Inicio servidor "+sockfd);

            while (true) {
                Socket newsockfd = sockfd.accept();
                System.out.println("Nuevo cliente, socket "+newsockfd);

                BufferedReader in = new BufferedReader(new InputStreamReader
                        (  newsockfd.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter
                        (new OutputStreamWriter
                                (newsockfd.getOutputStream())),true);
                boolean salir = false;
                while(!salir) {
                    line = in.readLine(); //lectura socket cliente
                    if (line!=null) {
                    	if(line.equals("END")) {
                    		System.out.println(line);
                    		out.println("OK");
                    		salir = true;
                    	}
                    	else {
                    		System.out.println(line);
                    		String[] cadenas = line.split(" ");
                    		String nombre = cadenas[0];
                    		String apellido1 = cadenas[1];
                    		String apellido2 = cadenas[2];
                    		String cadena = "aceptado" + " " + nombre + " " + apellido1 + " " + apellido2;
                    		out.println(cadena);
                    	} //escritura socket cliente
                    }
                    else {
                        salir = true;
                    } // cierre socket cliente
                }
                newsockfd.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}