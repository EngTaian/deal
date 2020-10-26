package br.com.taian.deal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DealDto {

    @JsonProperty
    private String  make;

    @JsonProperty
    private String model;

    @JsonProperty
    private Double price;

    @JsonProperty
    private Long dealerId;

    @JsonProperty
    private Date createdAt;

}
