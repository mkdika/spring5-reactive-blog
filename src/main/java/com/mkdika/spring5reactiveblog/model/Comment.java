package com.mkdika.spring5reactiveblog.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Comment {

    @Id
    private Integer id;
    private String email;
    private Date commenttime;
    private String body;

    public Comment(Integer id, String email, Date commenttime, String body) {
        this.id = id;
        this.email = email;
        this.commenttime = commenttime;
        this.body = body;
    }
}
