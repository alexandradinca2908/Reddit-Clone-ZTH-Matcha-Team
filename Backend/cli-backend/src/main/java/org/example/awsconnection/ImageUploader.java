package org.example.awsconnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ImageUploader {

    public static void uploadImage(File file) throws IOException {
        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";

        URL url = new URL("http://16.171.8.248:3000/upload");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream output = conn.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true)) {

            // Send file
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"").append(file.getName()).append("\"").append(CRLF);
            writer.append("Content-Type: image/png").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(file.toPath(), output);
            output.flush();
            writer.append(CRLF).flush();

            writer.append("--").append(boundary).append("--").append(CRLF).flush();
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Server responded with: " + responseCode);
    }
}
