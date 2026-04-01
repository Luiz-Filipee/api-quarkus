package org.revisao.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
public class AgendamentoEntity extends PanacheEntityBase {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        public UserEntity usuario;

        @OneToOne
        @JoinColumn(name = "horario_id", nullable = false)
        public HorarioEntity horario;

        @Column(name = "servico", nullable = false)
        public ServicoEntity servico;

        @Column(name = "data_criacao")
        public LocalDateTime dataCriacao = LocalDateTime.now();

        public AgendamentoEntity() {}

        public AgendamentoEntity(UserEntity usuario, HorarioEntity horario, String servico) {
            this.usuario = usuario;
            this.horario = horario;
            this.servico = servico;
        }
}
