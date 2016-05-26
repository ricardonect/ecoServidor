/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricardo Marín
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EcoServidor implements Runnable {

    public static final int PORT = 1134;
    public String adios;

    public void procedimiento() throws IOException {

    }

    @Override
    public void run() {
        try {
            // Establece el puerto en el que escucha peticiones
            ServerSocket socketServidor = null;
            try {
                socketServidor = new ServerSocket(PORT);
            } catch (IOException e) {
                System.out.println("No puede escuchar en el puerto: " + PORT);
                System.exit(-1);
            }
            
            int cont = 0;
            
            Socket socketCliente = null;
            BufferedReader entrada = null;
            PrintWriter salida = null;
            
            System.out.println("Escuchando: " + socketServidor);
            
            try {
                // Se bloquea hasta que recibe alguna petición de un cliente
                // abriendo un socket para el cliente
                socketCliente = socketServidor.accept();
                System.out.println("Connexión acceptada: " + socketCliente);
                // Establece canal de entrada
                entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                // Establece canal de salida
                salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
                
                // Hace eco de lo que le proporciona el cliente, hasta que recibe "Adios"
                while (true) {
                    cont = cont + 5;
                    String str = entrada.readLine();
                    System.out.println("Cliente: " + str);
                    salida.println(cont);
                    if (cont == 60) {
                        cont = 0;
                    }
                    
                    if (adios.equals("adios")) {
                        break;
                    }
                }
                
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
            salida.close();
            entrada.close();
            socketCliente.close();
            socketServidor.close();
        } catch (IOException ex) {
            Logger.getLogger(EcoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
