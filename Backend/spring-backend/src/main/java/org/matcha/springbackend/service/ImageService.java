package org.matcha.springbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final RestTemplate restTemplate;
    private final String csharpServiceUrl;

    public ImageService(RestTemplate restTemplate, @Value("http://51.21.228.104") String baseUrl) {
        this.restTemplate = restTemplate;
        this.csharpServiceUrl = baseUrl + "/filter";
    }

    public byte[] applyFilterToImage(MultipartFile imageFile, String filter) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("filter", filter);

        try {
            ByteArrayResource fileResource = new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            };
            body.add("imageFile", fileResource);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file data.", e);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            System.out.println("Sending request to image processor service...");
            ResponseEntity<byte[]> response = restTemplate.postForEntity(csharpServiceUrl, requestEntity, byte[].class);

            return response.getBody();

        } catch (RestClientException e) {
            System.err.println("Error calling Image Processor Service: " + e.getMessage());
            throw new RuntimeException("Failed to apply filter due to an error in the image processing service.", e);
        }
    }
}