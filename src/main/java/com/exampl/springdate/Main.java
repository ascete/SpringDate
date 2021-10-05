package com.exampl.springdate;

import com.exampl.springdate.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class Main {
    final static String URL = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        RestTemplate template = new RestTemplate();

        User user = new User(3L, "James", "Brown", (byte) 30);

        ResponseEntity<String> forEntity = template.getForEntity(URL, String.class);

        List<String> cookies = forEntity.getHeaders().get("Set-Cookie");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", String.join(";", Objects.requireNonNull(cookies)));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        forEntity = template.exchange(URL, HttpMethod.POST, entity, String.class);
        builder.append(forEntity.getBody());

        user.setName("Thomas");
        user.setLastName("Shelby");

        entity = new HttpEntity<>(user, headers);
        forEntity = template.exchange(URL, HttpMethod.PUT, entity, String.class);
        builder.append(forEntity.getBody());

        forEntity = template.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class);
        builder.append(forEntity.getBody());

        System.out.println(builder);
    }
}