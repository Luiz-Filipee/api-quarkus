package org.revisao.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.revisao.enums.DiaSemana;

import java.time.LocalDateTime;

@Entity
@Table(name = "horarios")
public class HorarioEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "horario_inicio")
    public LocalDateTime horarioInicio;

    @Column(name = "horario_fim")
    public LocalDateTime horarioFim;

    @Column(name = "dia_semana")
    @Enumerated(EnumType.STRING)
    public DiaSemana diaSemana;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserEntity user;

    public HorarioEntity() { }

    public HorarioEntity(LocalDateTime horarioInicio, LocalDateTime horarioFim, DiaSemana diaSemana, UserEntity user) {
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.diaSemana = diaSemana;
        this.user = user;
    }
}
