package model;

import java.util.HashMap;
import java.util.Map;

class Tabuleiro {
    private Map<Integer, Propriedade> casas;
    private BaralhoSorteReves baralho = new BaralhoSorteReves();

    Tabuleiro() {
        casas = new HashMap<>();
        inicializarCasas();
    }

    private void inicializarCasas() {
        casas.put(0,  new Propriedade("Partida", 0, 0));
        casas.put(1,  new Propriedade("Leblon", 100, 10));
        casas.put(2,  new Propriedade("Sorte ou Revés", 0, 0));
        casas.put(3,  new Propriedade("Av. Presidente Vargas", 60, 6));
        casas.put(4,  new Propriedade("Av. Nossa S. de Copacabana", 60, 6));
        casas.put(5,  new Propriedade("Companhia Ferroviaria", 200, 25)); 
        casas.put(6,  new Propriedade("Av. Brigadero Faria Lima", 240, 24)); 
        casas.put(7,  new Propriedade("Companhia de Viacao", 200, 25)); 
        casas.put(8,  new Propriedade("Av. Reboucas", 220, 22)); 
        casas.put(9,  new Propriedade("Av. 9 de Julho", 220, 22));
        casas.put(10, new Propriedade("Prisão", 0, 0));
        casas.put(11, new Propriedade("Av. Europa", 200, 20));
        casas.put(12, new Propriedade("Sorte ou Revés", 0, 0));
        casas.put(13, new Propriedade("Rua Augusta", 180, 18));
        casas.put(14, new Propriedade("Av. Pacaembu", 180, 18));
        casas.put(15, new Propriedade("Companhia de Taxi", 150, 15)); 
        casas.put(16, new Propriedade("Sorte ou Revés", 0, 0));
        casas.put(17, new Propriedade("Interlagos", 350, 35));
        casas.put(18, new Propriedade("Lucros e Dividendos", 0, 0));
        casas.put(19, new Propriedade("Morumbi", 400, 40));
        casas.put(20, new Propriedade("Parada Livre", 0, 0));
        casas.put(21, new Propriedade("Flamengo", 120, 12));
        casas.put(22, new Propriedade("Sorte ou Revés", 0, 0)); 
        casas.put(23, new Propriedade("Botafogo", 100, 10));
        casas.put(24, new Propriedade("Imposto de Renda", 0, 0));
        casas.put(25, new Propriedade("Companhia de Navegacao", 150, 15)); 
        casas.put(26, new Propriedade("Av. Brasil", 160, 16));
        casas.put(27, new Propriedade("Sorte ou Revés", 0, 0));
        casas.put(28, new Propriedade("Av. Paulista", 140, 14));
        casas.put(29, new Propriedade("Jardim Europa", 140, 14));
        casas.put(30, new Propriedade("Vá para a Prisão", 0, 0));
        casas.put(31, new Propriedade("Copacabana", 260, 26));
        casas.put(32, new Propriedade("Companhia de Aviacao", 200, 25)); 
        casas.put(33, new Propriedade("Av. Vieira Souto", 320, 32));
        casas.put(34, new Propriedade("Av. Atlantica", 300, 30)); 
        casas.put(35, new Propriedade("Companhia de Taxi Aereo", 200, 15)); 
        casas.put(36, new Propriedade("Ipanema", 300, 30));
        casas.put(37, new Propriedade("Sorte ou Revés", 0, 0));
        casas.put(38, new Propriedade("Jardim Paulista", 280, 28));
        casas.put(39, new Propriedade("Brooklin", 260, 26));
    }


    int tamanho() { return casas.size(); }

    Propriedade getCasa(int posicao) {
        return casas.get(posicao);
    }

    void verificarCasaAtual(Jogador jogador) {
        Propriedade casa = casas.get(jogador.getPosicao());
        String nome = casa.getNome();
        Jogo jogo = Jogo.getInstancia(); 

        switch (nome) {
            case "Partida":
                jogador.receber(200, "Banco"); 
                break;
            case "Vá para a Prisão":
                jogador.prender(); 
                break;
            case "Imposto de Renda":
                jogador.pagar(200, "Banco"); 
                break;
            case "Lucros e Dividendos": 
                jogador.receber(200, "Banco"); 
                break;
            case "Sorte ou Revés":
                Carta carta = baralho.sortearCarta();
                carta.aplicar(jogador, jogo); 
                jogador.setUltimaCarta(carta.getId());
                jogo.setUltimaCartaGlobal(carta.getId());
                break;      	
            case "Prisão":
            case "Parada Livre":
                break;
            default:
                Propriedade prop = casa;
                jogo.setUltimaCartaGlobal(prop.getNome());
                
                if (!prop.temDono() && prop.getPreco() > 0) {                
                } else if (prop.getDono() == jogador) {                
                } else if (prop.temDono() && prop.getDono() != jogador) {
                    Jogador dono = prop.getDono();
                    double aluguel = prop.calcularAluguel();
                    jogador.pagar(aluguel);
                    dono.receber(aluguel);

                    String msg = String.format("💸 %s pagou R$%.2f para %s.\n(Saldo %s: R$%.2f)",
                        jogador.getNome(), aluguel, dono.getNome(),
                        dono.getNome(), dono.getSaldo()
                    );
                    jogo.setUltimoEventoLog(msg);
                }
                break;
        }
    }
    
    void processaAluguel(Jogador jogador) { }

    Propriedade getPropriedadePorNome(String nome) {
        if (nome == null) return null;
        for (Propriedade p : casas.values()) {
            if (nome.equals(p.getNome())) {
                return p;
            }
        }
        return null;
    }
}