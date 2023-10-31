package com.example.eproject4.DTO.Response;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoHighlightDTO {
    @MapToDTO
    private Long id;
    @MapToDTO
    private String image;
    @MapToDTO
    private String video_link;
    @MapToDTO
    private String title;
    @MapToDTO
    private Integer status;
    @MapToDTO
    private Timestamp createdAt;
    @MapToDTO
    private Timestamp updatedAt;
}