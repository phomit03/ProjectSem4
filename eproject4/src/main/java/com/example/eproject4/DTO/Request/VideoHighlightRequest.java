package com.example.eproject4.DTO.Request;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoHighlightRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @MapToDTO
    private String image;
    @MapToDTO
    private String video_link;
    @MapToDTO
    private String title;
    @MapToDTO
    private Integer status;
}
