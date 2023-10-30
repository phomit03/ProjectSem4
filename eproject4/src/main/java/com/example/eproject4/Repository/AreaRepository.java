package com.example.eproject4.Repository;

import com.example.eproject4.Entity.Area;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{
    @Query("SELECT e FROM Area e WHERE e.id = :matchIdValue and e.status = 1")
    Area findByAreaById(@Param("matchIdValue") int id);

    @Query("SELECT a FROM Area a where a.stadium_id = :stadiumId AND a.status = 1")
    List<Area> findByStadium (@Param("stadiumId") Long stadiumId);


    @Query(value = "SELECT a.* FROM area a " +
            "LEFT JOIN stadium s ON a.stadium_id = s.id " +
            "WHERE (:area_name is null or a.area_name like CONCAT('%', :area_name, '%')) " +
            "AND (:name_stadium is null or s.name like CONCAT('%', :name_stadium, '%')) " +
            "AND (:status is null or a.status like CONCAT('%', :status, '%'))", nativeQuery = true)
    List<Area> searchAreas(@Param("area_name") String area_name,
                              @Param("name_stadium") String name_stadium,
                              @Param("status") Integer status, Pageable pageable);

    @Query(value = "SELECT a.* FROM area a " +
            "LEFT JOIN stadium s ON a.stadium_id = s.id " +
            "WHERE (:area_name is null or a.area_name like CONCAT('%', :area_name, '%')) " +
            "AND (:name_stadium is null or s.name like CONCAT('%', :name_stadium, '%')) " +
            "AND (:status is null or a.status like CONCAT('%', :status, '%'))", nativeQuery = true)
        List<Area> searchAreas1(@Param("area_name") String area_name,
                              @Param("name_stadium") String name_stadium,
                              @Param("status") Integer status);

}