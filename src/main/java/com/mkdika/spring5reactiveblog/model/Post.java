package com.mkdika.spring5reactiveblog.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Post {

    @Id
    private Integer id;
    private String title;
    private String body;
    private Date posttime;
    private List<Comment> comments;

}
