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
        casas.add(new Propriedade("Partida", 0, 0));
        casas.add(new Propriedade("Leblon", 100, 10));
        casas.add(new Propriedade("Av. Presidente Vargas", 60, 6));
        casas.add(new Propriedade("Av. Nossa Sra. de Copacabana", 60, 6));
        casas.add(new Propriedade("Companhia Ferroviária", 200, 25));
        casas.add(new Propriedade("Av. Brigadeiro Faria Lima", 240, 24));
        casas.add(new Propriedade("Companhia de Viação", 200, 25));
        casas.add(new Propriedade("Av. Rebouças", 220, 22));
        casas.add(new Propriedade("Av. 9 de Julho", 220, 22));
        casas.add(new Propriedade("Prisão", 0, 0));
        casas.add(new Propriedade("Av. Europa", 200, 20));
        casas.add(new Propriedade("Rua Augusta", 180, 18));
        casas.add(new Propriedade("Av. Pacaembu", 180, 18));
        casas.add(new Propriedade("Companhia de Táxi", 150, 15));
        casas.add(new Propriedade("Interlagos", 350, 35));
        casas.add(new Propriedade("Lucros ou Dividendos", 0, 0));
        casas.add(new Propriedade("Morumbi", 400, 40));
        casas.add(new Propriedade("Parada Livre", 0, 0));
        casas.add(new Propriedade("Flamengo", 120, 12));
        casas.add(new Propriedade("Botafogo", 100, 10));
        casas.add(new Propriedade("Imposto de Renda", 0, 0));
        casas.add(new Propriedade("Companhia de Navegação", 150, 15));
        casas.add(new Propriedade("Av. Brasil", 160, 16));
        casas.add(new Propriedade("Av. Paulista", 140, 14));
        casas.add(new Propriedade("Jardim Europa", 140, 14));
        casas.add(new Propriedade("Vá para a Prisão", 0, 0));
        casas.add(new Propriedade("Copacabana", 260, 26));
        casas.add(new Propriedade("Companhia de Aviação", 200, 25));
        casas.add(new Propriedade("Av. Vieira Souto", 320, 32));
        casas.add(new Propriedade("Av. Atlântica", 300, 30));
        casas.add(new Propriedade("Companhia de Taxi Aereo", 200, 20));
        casas.add(new Propriedade("Ipanema", 300, 30));
        casas.add(new Propriedade("Jardim Paulista", 280, 28));
        casas.add(new Propriedade("Brooklin", 260, 26));
    }

    int tamanho() { return casas.size(); }

    Propriedade getCasa(int posicao) {
        return casas.get(posicao);
    }
    
    void verificarCasaAtual(Jogador jogador) {
        Propriedade casa = casas.get(jogador.getPosicao());
        String nome = casa.getNome();

        switch (nome) {
        	case "Partida":
        		jogador.receber(200);
        		break;
        		
            case "Vá para a Prisão":
                jogador.prender();
                break;

            case "Imposto de Renda":
                jogador.pagar(200);
                break;

            case "Lucros ou Dividendos":
                jogador.receber(200);
                break;

            case "Prisão":
            	
            case "Parada Livre":
                break;

            default:
                break;
        }
    }
}
