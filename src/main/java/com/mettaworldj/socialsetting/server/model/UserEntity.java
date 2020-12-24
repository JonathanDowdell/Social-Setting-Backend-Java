package com.mettaworldj.socialsetting.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Profile name is required")
    @Column(length = 50)
    private String profileName;

    @NotBlank(message = "Username is required")
    private String encryptedPassword;

    @NotBlank(message = "Public ID is required for cache")
    private String publicId;

    @NotBlank(message = "Creation Date is required")
    private Instant created;

    @NotBlank(message = "Enabled status is required")
    private Boolean enabled;

    private String description;

    private String url;

}
