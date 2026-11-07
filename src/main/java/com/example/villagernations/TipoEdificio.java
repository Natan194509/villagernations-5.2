package com.example.villagernations;

/** Edifícios funcionais da versão 0.1. trabalho = unidades de obra necessárias para construir. */
public enum TipoEdificio {
    CASA("Casa", 20, 5, 6, 4, null, 0, 0),
    FAZENDA("Fazenda", 25, 5, 8, 0, Profissao.AGRICULTOR, 3, 0),
    SERRARIA("Posto de Lenhadores", 15, 5, 6, 0, Profissao.LENHADOR, 3, 0),
    PEDREIRA("Pedreira", 20, 0, 8, 0, Profissao.MINEIRO, 3, 0),
    DEPOSITO("Depósito", 30, 10, 8, 0, null, 0, 300);

    private final String nome;
    private final int madeira;
    private final int pedra;
    private final int trabalho;
    private final int moradia;
    private final Profissao profissao;
    private final int vagas;
    private final int armazenamento;

    TipoEdificio(String nome, int madeira, int pedra, int trabalho, int moradia,
                 Profissao profissao, int vagas, int armazenamento) {
        this.nome = nome;
        this.madeira = madeira;
        this.pedra = pedra;
        this.trabalho = trabalho;
        this.moradia = moradia;
        this.profissao = profissao;
        this.vagas = vagas;
        this.armazenamento = armazenamento;
    }

    public String nome() { return nome; }
    public int madeira() { return madeira; }
    public int pedra() { return pedra; }
    public int trabalho() { return trabalho; }
    public int moradia() { return moradia; }
    public Profissao profissao() { return profissao; }
    public int vagas() { return vagas; }
    public int armazenamento() { return armazenamento; }

    public String efeito() {
        if (moradia > 0) return "+" + moradia + " moradia";
        if (profissao != null) return vagas + " vagas de " + profissao.nome().toLowerCase();
        if (armazenamento > 0) return "+" + armazenamento + " de armazém";
        return "";
    }

    public static TipoEdificio porIndice(int i) {
        TipoEdificio[] todos = values();
        return todos[Math.max(0, Math.min(i, todos.length - 1))];
    }
}
