package com.arvind.reviewms.dto;

import lombok.Data;

@Data
public class ReviewMessage {
    private Long id;
    private String title;
    private String description;
    private double Rating;
    private Long companyId;
}
