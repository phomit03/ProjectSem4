package com.example.eproject4.Entity;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @MapToDTO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapToDTO
    private String email;

    @MapToDTO
    private String username;

    @MapToDTO
    private String password;

    @MapToDTO
    @Column(name = "full_name", nullable = true)
    private String fullName;

    @MapToDTO
    @Column(name = "date_of_birth", nullable = true)
    private String dateOfBirth;

    @MapToDTO
    @Column(name = "phone", nullable = true)
    private String phone;

    @MapToDTO
    @Column(name = "address", nullable = true)
    private String address;

    @MapToDTO
    @Column(name = "status", columnDefinition = "INT DEFAULT 1")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @MapToDTO
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @MapToDTO
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User(String username, String email,
                   String password, String address,
                    String dateOfBirth,
                   String fullName,
                   String phone,
                   Collection<Role> roles) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        	this.dateOfBirth = dateOfBirth;
        this.fullName = fullName;
        this.phone = phone;
        this.roles = roles;
    }
}