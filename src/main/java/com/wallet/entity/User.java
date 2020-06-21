package com.wallet.entity;

import com.wallet.util.enums.RoleEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1693850165739564098L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}
