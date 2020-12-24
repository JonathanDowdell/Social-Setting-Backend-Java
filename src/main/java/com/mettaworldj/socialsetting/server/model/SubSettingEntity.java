package com.mettaworldj.socialsetting.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SubSettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subSettingId;

    @ManyToOne
    private UserEntity userEntity;

    private String name;

    private String description;

    private boolean isProfile;

}
