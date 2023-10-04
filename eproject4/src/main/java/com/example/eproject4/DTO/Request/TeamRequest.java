package com.example.eproject4.DTO.Request;

import com.example.eproject4.utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest {
    @MapToDTO
    private String name;
    @MapToDTO
    private MultipartFile logo;
    @MapToDTO
    private String coach;
    @MapToDTO
    private String stadium;
    @MapToDTO
    private Float valuation;
    @MapToDTO
    private Integer status;
}
