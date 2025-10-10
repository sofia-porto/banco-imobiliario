package model;

import java.util.ArrayList;
import java.util.List;

class Tabuleiro {
    private List<Propriedade> casas;

    Tabuleiro() {
        casas = new ArrayList<>();
        inicializarCasas();
    }

    private void inicializarCasas() {
        casas.add(new Propriedade("Início", 0, 0));
        casas.add(new Propriedade("Leblon", 100, 20));
        casas.add(new Propriedade("Avenida Atlântica", 120, 25));
        casas.add(new Propriedade("Prisão", 0, 0));
        casas.add(new Propriedade("Copacabana", 150, 30));
    }

    int tamanho() { return casas.size(); }

    Propriedade getCasa(int posicao) {
        return casas.get(posicao);
    }
}
