package com.testapi.leem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Entity
@Data
public class Db {
    @Id
    @GeneratedValue
    private Integer id;

    private String subject;
    @Column(length = 1000000000)
    private String content;

    private Date regDate;

    private String url;
}
