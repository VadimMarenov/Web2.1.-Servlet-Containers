package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    final Gson gson;
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
        this.gson = new Gson();
    }

    public void all(HttpServletResponse response) throws IOException {
        setContentTypeAndPrintReply(response, APPLICATION_JSON, service.all());

    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        setContentTypeAndPrintReply(response, APPLICATION_JSON, service.getById(id));

    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        final Post post = gson.fromJson(body, Post.class);
        setContentTypeAndPrintReply(response, APPLICATION_JSON, service.save(post));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        if (service.getById(id) != null) {
            service.removeById(id);
            setContentTypeAndPrintReply(response, APPLICATION_JSON, "Post with id: " + id + " deleted successfully");
        }


    }

    private <T> void setContentTypeAndPrintReply(HttpServletResponse response, String contentType, T data) throws IOException {
        response.setContentType(contentType);
        String json = gson.toJson(data);
        response.getWriter().print(json);
    }
}
