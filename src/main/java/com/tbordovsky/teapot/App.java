package com.tbordovsky.teapot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        new App().start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(3128)) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                new TeapotHandler(socket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class TeapotHandler extends Thread {
        public static final Pattern HTTP_PATTERN = Pattern.compile(".*HTTP/(1\\.[01])", Pattern.CASE_INSENSITIVE);
        private final Socket clientSocket;

        private TeapotHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.ISO_8859_1)) {
                String request = in.readLine();
                LOG.info(request);

                Matcher matcher = HTTP_PATTERN.matcher(request);
                String httpVersion = matcher.matches() ? matcher.group(1) : "1.1";

                outputStreamWriter.write("HTTP/" + httpVersion + " 418 I'm a teapot\r\n");
                outputStreamWriter.write("Proxy-agent: Teapot/0.1\r\n");
                outputStreamWriter.write("\r\n");
                outputStreamWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
