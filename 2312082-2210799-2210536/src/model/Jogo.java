package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Jogo {
    private static Jogo instancia; 
    private Jogo() {
        jogadores = new ArrayList<>();
        tabuleiro = new Tabuleiro();
        jogadorAtual = 0;
    }

    public static Jogo getInstancia() {
        if (instancia == null) {
            instancia = new Jogo();
        }
        return instancia;
    }

    private List<Jogador> jogadores;
    private Tabuleiro tabuleiro;
    private int jogadorAtual; 
    private List<Observer> observadores = new ArrayList<>();
    
    private String ultimaCartaGlobal; 
    
    private String ultimoEventoLog; 

    public void adicionarJogador(Jogador j) {
        jogadores.add(j);
        notificarObservadores();
    }

    public void iniciarPartida() {
        jogadorAtual = 0;
        notificarObservadores();
    }

    public Jogador getJogadorAtual() {
        return jogadores.get(jogadorAtual);
    }
    
    public int getIndiceJogadorAtual() {
        return this.jogadorAtual;
    }

    public int getIndicePorNome(String nome) {
        if (nome == null) return -1;
        for (int i = 0; i < jogadores.size(); i++) {
            if (nome.equals(jogadores.get(i).getNome())) {
                return i;
            }
        }
        return -1;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }
    
    public double getJogadorSaldo(int indice) {
        if (indice >= 0 && indice < jogadores.size()) {
            return jogadores.get(indice).getSaldo();
        }
        return 0.0;
    }

    public Set<String> getPropriedadesDoJogador(int indice) {
        if (indice >= 0 && indice < jogadores.size()) {
            return jogadores.get(indice).getPropriedades().stream()
                .map(Propriedade::getNome)
                .collect(Collectors.toSet());
        }
        return null;
    }

    public void proximaJogada(int valorDado) {
        Jogador j = getJogadorAtual();
        j.mover(valorDado, tabuleiro.tamanho());
        tabuleiro.verificarCasaAtual(j);
        avancarJogadorDaVez();
        notificarObservadores();
    }

    private void avancarJogadorDaVez() {
        jogadorAtual = (jogadorAtual + 1) % jogadores.size();
    }
    
    void moverJogadorAtual(int casas) {
        this.ultimaCartaGlobal = null;
        this.ultimoEventoLog = null;
        
        Jogador atual = getJogadorAtual();
        if (atual != null && !atual.estaPreso()) {
            atual.mover(casas, tabuleiro.tamanho());
            tabuleiro.verificarCasaAtual(atual); 
            
            notificarObservadores();
        }
    }
    
    public void encerrarTurno() {
        avancarJogadorDaVez();
        notificarObservadores();
    }
    
    public void setUltimaCartaGlobal(String id) {
        ultimaCartaGlobal = id;
    }

    public String getUltimaCartaGlobal() {
        return ultimaCartaGlobal;
    }

    public void setUltimoEventoLog(String msg) {
        System.out.println(msg); 
        this.ultimoEventoLog = msg;
    }

    public String getUltimoEventoLog() {
        return this.ultimoEventoLog;
    }
    
    public boolean comprarPropriedadeAtual() {
        Jogador j = getJogadorAtual();
        Propriedade p = tabuleiro.getCasa(j.getPosicao());
        j.comprarPropriedade(p); 
        notificarObservadores(); 
        return p.getDono() == j; 
    }
    
    public boolean construirCasa(String nomePropriedade) {
        Jogador j = getJogadorAtual();
        Propriedade p = tabuleiro.getPropriedadePorNome(nomePropriedade);

        if (p != null && p.getDono() == j) {
            double custoConstrucao = p.getPreco() * 0.5;
            if (j.getSaldo() >= custoConstrucao && p.getCasas() < 4) {
                p.construirCasa();
                j.pagar(custoConstrucao, "Banco"); 
                setUltimoEventoLog("🏠 " + j.getNome() + " construiu 1 casa em " + p.getNome()); 
                notificarObservadores();
                return true;
            }
        }
        setUltimoEventoLog("❌ Não foi possível construir casa em " + nomePropriedade);
        notificarObservadores();
        return false;
    }
    
    public boolean construirHotel(String nomePropriedade) {
        Jogador j = getJogadorAtual();
        Propriedade p = tabuleiro.getPropriedadePorNome(nomePropriedade);

        if (p != null && p.getDono() == j) {
            double custoConstrucao = p.getPreco() * 0.5; 
            if (j.getSaldo() >= custoConstrucao && p.getCasas() == 4 && !p.temHotel()) {
                p.construirHotel();
                j.pagar(custoConstrucao, "Banco"); 
                setUltimoEventoLog("🏨 " + j.getNome() + " construiu 1 hotel em " + p.getNome());
                notificarObservadores();
                return true;
            }
        }
        setUltimoEventoLog("❌ Não foi possível construir hotel em " + nomePropriedade);
        notificarObservadores();
        return false;
    }
    
    public boolean venderPropriedade(String nomePropriedade) {
        Jogador j = getJogadorAtual();
        Propriedade p = tabuleiro.getPropriedadePorNome(nomePropriedade);

        if (p != null && p.getDono() == j) {
            double valorVenda = p.getPreco() * 0.5;
            j.receber(valorVenda, "Banco"); 
            j.removerPropriedade(p); 
            p.setDono(null); 
            setUltimoEventoLog("🏦 " + j.getNome() + " vendeu " + p.getNome() + " ao banco.");
            notificarObservadores();
            return true;
        }
        setUltimoEventoLog("❌ Não foi possível vender " + nomePropriedade);
        notificarObservadores();
        return false;
    }

    public void adicionarObservador(Observer o) {
        observadores.add((Observer) o);
    }

    public void removerObservador(Observer o) {
        observadores.remove(o);
    }

    public void notificarObservadores() {
        for (Observer o : observadores) {
            o.atualizar();
        }
    }
    
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}