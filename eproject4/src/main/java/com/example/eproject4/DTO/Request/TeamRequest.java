package com.example.eproject4.DTO.Request;

import com.example.eproject4.Utils.MapToDTO;
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
    private String logo;
    @MapToDTO
    private String coach;
    @MapToDTO
    private String home_stadium;
    @MapToDTO
    private Float club_valuation;
    @MapToDTO
    private Integer status;
}
