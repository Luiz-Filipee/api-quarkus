package org.revisao.enums;

public enum DiaSemana {
    SEGUNDA(1, "Segunda-feira"),
    TERCA(2, "Terça-feira"),
    QUARTA(3, "Quarta-feira"),
    QUINTA(4, "Quinta-feira"),
    SEXTA(5, "Sexta-feira"),
    SABADO(6, "Sábado"),
    DOMINGO(7, "Domingo");

    private final Integer codigo;
    private final String descricao;

    DiaSemana(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
