package edu.eci.arep;



import edu.eci.arep.exceptions.WeatherServiceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clima {
    public static void main(String[] args) throws IOException, WeatherServiceException {
        ServerSocket serv;
        Socket sc;
        final int PUETO = 5000;
        WeatherService ws = new WeatherService();

 
        
        try {
            serv = new ServerSocket(PUETO);
            while(true){
                 sc = serv.accept();
                PrintWriter out = new PrintWriter(sc.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
                String inputLine, outputLine;
                String[] arch;
                String[] elem = null;

                while ((inputLine = in.readLine()) != null) {
                        if (inputLine.contains("GET")) {
                                arch = inputLine.split("/");
                                elem = arch[1].split(" ");
                                System.out.print(elem[0]);
                        }
                
                }
                if(!"clima".equals(elem[0])) {
                    file(ws.getCityWeather(elem[0]).toString(), sc.getOutputStream(), out);
                }else {
                     file("clima", sc.getOutputStream(), out);
                }
                
        
        
             }
        } catch (IOException ex) {
            Logger.getLogger(Clima.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void file(String contenido , OutputStream clientOutput, PrintWriter out ) throws FileNotFoundException, IOException{  
            System.out.println(contenido); 
            if(!contenido.equals("clima")){
                String outputLine = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <title>index file</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<div class=\"container\">\n"
                        + "  <div class=\"title\">\n"
                        + "    <h1 class=\"text-title\">SERVER</h1>\n"
                        + "  </div>\n"
                        + "  <div class=\"container-input\">\n"
                        + contenido
                        + "  </div>\n"
                        + "</div>\n"
                        + "</body>\n"
                        + "</html>";
                out.write(("HTTP/1.1 404 Not Found \r\n" + "Content-Type: text/html; charset=\"utf-8\" \r\n" + "\r\n"
						+ outputLine));
            }else{
                String outputLine = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <title>index file</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<div class=\"container\">\n"
                        + "  <div class=\"title\">\n"
                        + "    <h1 class=\"text-title\">SERVER</h1>\n"
                        + "  </div>\n"
                        + "  <div class=\"container-input\">\n"
                        + "    <p>para buscar la informacion de una ciudad en especifico grega al path : /'nombre de la ciudad' </b></p>\n"
                        + "  </div>\n"
                        + "</div>\n"
                        + "</body>\n"
                        + "</html>";
                out.write(("HTTP/1.1 404 Not Found \r\n" + "Content-Type: text/html; charset=\"utf-8\" \r\n" + "\r\n"
						+ outputLine));
            }
    }
}
