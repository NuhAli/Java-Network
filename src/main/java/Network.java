import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class Network {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        // getMethod();
        // postMethod(new User(1,"Post test", "This is the body blah blah"));
        deletePost("1");
    }

    public static void getMethod() throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest getRequest = HttpRequest.newBuilder(new URI("https://jsonplaceholder.typicode.com/users/1/posts"))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        String response = getResponse.body();
        User[] allUsers = gson.fromJson(response,User[].class);
        System.out.println(List.of(allUsers));
    }

    public static void postMethod(User user) throws URISyntaxException, IOException, InterruptedException {

        Gson gson = new Gson();
        String body = gson.toJson(user);
        HttpRequest postResponse = HttpRequest.newBuilder(new URI("https://jsonplaceholder.typicode.com/posts/"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(postResponse, HttpResponse.BodyHandlers.ofString());
        User newId = gson.fromJson(response.body(), User.class);
        System.out.println(getPost(Integer.toString(newId.getId())));
    }

    public static User getPost(String id) throws IOException, InterruptedException, URISyntaxException {
        Gson gson = new Gson();
        HttpRequest getRequest = HttpRequest.newBuilder(new URI("https://jsonplaceholder.typicode.com/posts/" + id)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(),User.class);
    }

    public static void deletePost(String id) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder(new URI("https://jsonplaceholder.typicode.com/posts/" + id))
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
