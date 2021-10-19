package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {

    private String mailFrom;

    private String mailTo;

    private String mailType;

    private String mailContent;

    private String mailSubject;
}
