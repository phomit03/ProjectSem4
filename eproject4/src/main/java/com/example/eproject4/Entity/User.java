package com.example.eproject4.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @Column(name = "address", nullable = true)
    private String address;

	@Column(name = "date_of_birth", nullable = true)
	private LocalDate dateOfBirth;


    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "status", columnDefinition = "INT DEFAULT 1")
    private Integer status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User(String username, String email,
                   String password, String address,
                    LocalDate dateOfBirth,
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