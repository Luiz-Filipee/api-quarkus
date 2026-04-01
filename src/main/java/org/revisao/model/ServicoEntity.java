package org.revisao.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "servicos")
public class ServicoEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "descricao")
    public String descricao;

    @Column(name = "valor")
    public Double valor;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserEntity user;

    public ServicoEntity(String descricao, Double valor, UserEntity user) {
        this.descricao = descricao;
        this.valor = valor;
        this.user = user;
    }
}
