package org.example.services;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class ImageService {
    private static ImageService instance;
    private static final String awsURL = "http://16.171.8.248:3000";

    private ImageService() {}

    public static ImageService getInstance() {
        if (instance == null) {
            instance = new ImageService();
        }
        return instance;
    }

    public static String uploadImageToAWS(File file) throws IOException {
        String boundary = Long.toHexString(System.currentTimeMillis());
        URL url = new URL("http://16.171.8.248:3000/upload");
        String CRLF = "\r\n";


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
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.lines().collect(Collectors.joining("\n"));
            in.close();
            return response; // this is the link to the uploaded image
        } else {
            throw new IOException("Upload failed with HTTP code " + responseCode);
        }
    }

    public static String createURL(String serverResponse) {
        String awsURL = "http://16.171.8.248:3000";
        String newURL = serverResponse.substring(29);
        return awsURL + newURL;
    }

    public String uploadImage(String imagePath) throws IOException {
        File file = new File(imagePath);
        String imageURL = uploadImageToAWS(file);
        return awsURL + createURL(imageURL);

    }

}
