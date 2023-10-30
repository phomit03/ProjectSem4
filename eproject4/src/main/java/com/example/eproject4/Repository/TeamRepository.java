package com.example.eproject4.Repository;

import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(value = "SELECT * FROM team WHERE (:name is null or name like CONCAT('%',:name,'%')) "+
            " AND (:coach is null or coach like CONCAT('%',:coach,'%')) " +
            " AND (:home_stadium is null or home_stadium like CONCAT('%',:home_stadium,'%')) " +
            " AND (:status is null or status like CONCAT('%',:status,'%')) ", nativeQuery = true)
    List<Team> searchTeams(@Param("name") String name, @Param("coach") String coach,
                          @Param("home_stadium") String home_stadium,@Param("status") Integer status, Pageable pageable);

    @Query(value = "SELECT * FROM team WHERE (:name is null or name like CONCAT('%',:name,'%')) "+
            " AND (:coach is null or coach like CONCAT('%',:coach,'%')) " +
            " AND (:home_stadium is null or home_stadium like CONCAT('%',:home_stadium,'%')) " +
            " AND (:status is null or status like CONCAT('%',:status,'%')) ", nativeQuery = true)
    List<Team> searchTeams1(@Param("name") String name, @Param("coach") String coach,
                          @Param("home_stadium") String home_stadium,@Param("status") Integer status);
}
