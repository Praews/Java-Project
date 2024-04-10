package src.puem;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SentimentAnalysisClient {

    private static final String API_KEY = "aGn4OPROAF5PxtORAoA1lpPT8PJAxFlK";
    private static final String API_ENDPOINT = "https://api.iapp.co.th/sentimental-analysis/predict";

    public String analyzeSentiment(String text) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        
        // Encode the text parameter to ensure it's safe for the URL
        // as dasd -> as%20dasd
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);

        // Build the URL with the encoded text
        String url = API_ENDPOINT + "?text=" + encodedText;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", API_KEY)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            
            return response.body();
        } else {
            throw new IOException("Post request failed with status code: " + response.statusCode());
        }
    }

    public static Map<String, String> getSentiment(String text) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Encode the text parameter to ensure it's safe for the URL
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);

        // Build the URL with the encoded text
        String url = API_ENDPOINT + "?text=" + encodedText;

        // Create a POST request with the URL and API key
        HttpRequest request = HttpRequest.newBuilder()
                // Build the URL
                .uri(URI.create(url))
                // Add the API key to the header
                .header("apikey", API_KEY)
                // The API only accepts POST requests, so we don't need to send any data
                .POST(HttpRequest.BodyPublishers.noBody())
                // Build the request
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the request was successful
        if (response.statusCode() == 200) {
            // Parse the response and create a HashMap to store sentiment information
            Map<String, String> sentiment = new HashMap<>();
            sentiment.put("text", text);
            sentiment.put("label", response.body().substring(10, 13));
            sentiment.put("score", response.body().substring(23, 30));
            sentiment.put("analysis", response.body());
            return sentiment;
        } else {
            // If the request was not successful, throw an exception
            throw new IOException("Post request failed with status code: " + response.statusCode());
        }

    }

    public void printSentiment(Map<String, String> sentiment) {
        System.out.println("Text: " + sentiment.get("text"));
        
        if (sentiment.get("label").equals("pos")) {
            System.out.println("Label: Positive");
        } else if (sentiment.get("label").equals("neg")) {
            System.out.println("Label: Negative");
        } else if (sentiment.get("label").equals("neu")) {
            System.out.println("Label: Neutral");
        }
        
        System.out.println("Score: " + sentiment.get("score"));
        System.out.println("Analysis: " + sentiment.get("analysis"));
    }
}
