package model;

enum TipoEfeito {
    RECEBER_DO_BANCO,
    PAGAR_AO_BANCO,
    RECEBER_DOS_JOGADORES,
    SAIR_DA_PRISAO,
    IR_PARA_PRISAO
}

class Carta {
    private String id;
    private TipoEfeito tipo;
    private int valor;

    public Carta(String id, TipoEfeito tipo, int valor) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void aplicar(Jogador jogador, Jogo jogo) {
        switch (tipo) {
            case RECEBER_DO_BANCO:
                jogador.receber(valor, "Banco");
                break;

            case PAGAR_AO_BANCO:
                jogador.pagar(valor, "Banco");
                break;

            case RECEBER_DOS_JOGADORES:
                for (Jogador outro : jogo.getJogadores()) {
                    if (outro != jogador) {
                        outro.pagar(valor, jogador);
                        jogador.receber(valor, outro);
                    }
                }
                break;

            case SAIR_DA_PRISAO:
                jogador.receberCartaoSaidaLivre();
                break;

            case IR_PARA_PRISAO:
                jogador.prender();
                break;
        }
    }

    public String getId() { return id; }
}