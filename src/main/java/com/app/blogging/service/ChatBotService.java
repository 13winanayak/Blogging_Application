package com.app.blogging.service;

import com.app.blogging.model.Blog;
import com.app.blogging.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatBotService {

    @Autowired
    private BlogRepository blogRepository;


    @Value("${gemini.api.key}")
    private String API_KEY;


    public String simpleChat(Long id) {
        Blog blog =  blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        String prompt = blog.getTitle();
        String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-pro:generateContent?key=" + API_KEY;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Correct JSON structure for Gemini API
        JSONObject requestBody = new JSONObject();
        JSONArray contentsArray = new JSONArray();
        JSONObject contentsObject = new JSONObject();
        JSONArray partsArray = new JSONArray();
        JSONObject textObject = new JSONObject();

        textObject.put("text", prompt + "explain in two lines and do not use");
        partsArray.put(textObject);
        contentsObject.put("parts", partsArray);
        contentsArray.put(contentsObject);
        requestBody.put("contents", contentsArray);

        // Create the HTTP entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // POST request
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_API_URL, requestEntity, String.class);

            // Extract the text from the response JSON
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray candidates = jsonResponse.getJSONArray("candidates");
            JSONObject firstCandidate = candidates.getJSONObject(0);
            JSONArray candidateContent = firstCandidate.getJSONObject("content").getJSONArray("parts");
            return candidateContent.getJSONObject(0).getString("text");

        } catch (HttpClientErrorException e) {
            System.err.println("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return "Error occurred: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            return "Unexpected error occurred.";
        }
    }


}
