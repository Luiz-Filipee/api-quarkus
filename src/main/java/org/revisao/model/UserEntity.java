package org.revisao.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.inject.Default;
import jakarta.persistence.*;
import org.revisao.dto.CreateUserRequest;
import org.revisao.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
public class UserEntity extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String username;
    public String email;
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    public Role role = Role.BARBEIRO;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<HorarioEntity> horarios;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<ServicoEntity> servicos;

    public UserEntity() { }

    public UserEntity(CreateUserRequest newUser) {
        this.username = newUser.username();
        this.email = newUser.email();
        this.password = newUser.password();
    }

    public static Optional<UserEntity> findByEmail(String email) {
        return find("email ", email).firstResultOptional();
    }
}
