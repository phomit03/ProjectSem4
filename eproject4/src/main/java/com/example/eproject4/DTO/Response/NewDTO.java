package com.example.eproject4.DTO.Response;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewDTO {
    @MapToDTO
    private Long id;
    @MapToDTO
    private String new_img;
    @MapToDTO
    private String title;
    @MapToDTO
    private String sub_title;
    @MapToDTO
    private String content;
    @MapToDTO
    private Integer status;
    @MapToDTO
    private Timestamp createdAt;
    @MapToDTO
    private Timestamp updatedAt;
}