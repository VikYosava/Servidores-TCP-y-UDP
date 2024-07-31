package tcp_client_eco;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class EchoClientTCP {
	static final int serverPort = 6543;
	public static void main(String[] args) throws IOException {
		if ((args.length < 1) || (args.length > 2)) {
			throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");
		}

		try {
			boolean salir = false;
			InetAddress serverAddr = InetAddress.getByName(args[0]);
			Socket sockfd = new Socket(serverAddr,serverPort);
			System.out.println("Conexión local"+serverAddr);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(sockfd.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter
					(new OutputStreamWriter(sockfd.getOutputStream())),
					true);
			System.out.println("Introduzca su nombre, apellidos y nº de habitacion");
			BufferedReader stdIn = new BufferedReader(
					new InputStreamReader(System.in));
			while (!salir) {

				String entrada = stdIn.readLine();
				//Comprobacion si el usuario ha introducido END
				boolean iguales = entrada.equals("END");
				//Si escribimos END o escribimos mas de 2 palabras salimos del bucle
				//        		int cnt = 1;
				//        		while (!iguales || cnt <= 4) {
				//        			stdIn = new BufferedReader(new InputStreamReader(System.in));
				//        			entrada += " " + stdIn.readLine();
				//        			System.out.println(entrada + " Entrada correcta. Introduzca el siguiente campo. ");
				//        			cnt++;
				//        		}

				//Si hemos escrito END se termina la aplicacion
				if(iguales) {
					out.println(entrada);
					System.out.println("echo: " + in.readLine());
					out.close();
					in.close();
					stdIn.close();
					sockfd.close();
					salir = true;
				}else {
					System.out.println("Los datos introducidos son validos");
					//Dividir por teclado
					String[] cadenas = entrada.split(" ");
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
					out.println(userInput);
					//leo socket
					System.out.println("echo: " + in.readLine());
				}

			}
			out.close();
			in.close();
			stdIn.close();
			sockfd.close();

		} catch (UnknownHostException e) {
			System.err.println("Unknown: " + args[0]);
			System.exit(1);


		}  catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("Superado el tamaño del array" + e);
			System.exit(1);

		}  catch (IOException e) {
			System.err.println("Error I/O for " + e);
			System.exit(1);

		}
	}
}
