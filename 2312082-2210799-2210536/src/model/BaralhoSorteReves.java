package model;

import java.util.*;

class BaralhoSorteReves {
    private Map<String, Carta> cartas = new HashMap<>();
    private Random random = new Random();

    public BaralhoSorteReves() {
        inicializarCartas();
    }

    private void inicializarCartas() {
        cartas.put("chance1", new Carta("chance1", TipoEfeito.RECEBER_DO_BANCO, 25));
        cartas.put("chance2", new Carta("chance2", TipoEfeito.RECEBER_DO_BANCO, 150));
        cartas.put("chance3", new Carta("chance3", TipoEfeito.RECEBER_DO_BANCO, 80));
        cartas.put("chance4", new Carta("chance4", TipoEfeito.RECEBER_DO_BANCO, 200));
        cartas.put("chance5", new Carta("chance5", TipoEfeito.RECEBER_DO_BANCO, 50));
        cartas.put("chance6", new Carta("chance6", TipoEfeito.RECEBER_DO_BANCO, 50));
        cartas.put("chance7", new Carta("chance7", TipoEfeito.RECEBER_DO_BANCO, 100));
        cartas.put("chance8", new Carta("chance8", TipoEfeito.RECEBER_DO_BANCO, 100));
        cartas.put("chance9", new Carta("chance9", TipoEfeito.SAIR_DA_PRISAO, 0));
        cartas.put("chance10", new Carta("chance10", TipoEfeito.RECEBER_DO_BANCO, 200));
        cartas.put("chance11", new Carta("chance11", TipoEfeito.RECEBER_DOS_JOGADORES, 50));
        cartas.put("chance12", new Carta("chance12", TipoEfeito.RECEBER_DO_BANCO, 45));
        cartas.put("chance13", new Carta("chance13", TipoEfeito.RECEBER_DO_BANCO, 100));
        cartas.put("chance14", new Carta("chance14", TipoEfeito.RECEBER_DO_BANCO, 100));
        cartas.put("chance15", new Carta("chance15", TipoEfeito.RECEBER_DO_BANCO, 20));
        cartas.put("chance16", new Carta("chance16", TipoEfeito.PAGAR_AO_BANCO, 15));
        cartas.put("chance17", new Carta("chance17", TipoEfeito.PAGAR_AO_BANCO, 25));
        cartas.put("chance18", new Carta("chance18", TipoEfeito.PAGAR_AO_BANCO, 45));
        cartas.put("chance19", new Carta("chance19", TipoEfeito.PAGAR_AO_BANCO, 30));
        cartas.put("chance20", new Carta("chance20", TipoEfeito.PAGAR_AO_BANCO, 100));
        cartas.put("chance21", new Carta("chance21", TipoEfeito.PAGAR_AO_BANCO, 100));
        cartas.put("chance22", new Carta("chance22", TipoEfeito.PAGAR_AO_BANCO, 40));
        cartas.put("chance23", new Carta("chance23", TipoEfeito.IR_PARA_PRISAO, 0));
        cartas.put("chance24", new Carta("chance24", TipoEfeito.PAGAR_AO_BANCO, 30));
        cartas.put("chance25", new Carta("chance25", TipoEfeito.PAGAR_AO_BANCO, 50));
        cartas.put("chance26", new Carta("chance26", TipoEfeito.PAGAR_AO_BANCO, 25));
        cartas.put("chance27", new Carta("chance27", TipoEfeito.PAGAR_AO_BANCO, 30));
        cartas.put("chance28", new Carta("chance28", TipoEfeito.PAGAR_AO_BANCO, 45));
        cartas.put("chance29", new Carta("chance29", TipoEfeito.PAGAR_AO_BANCO, 50));
        cartas.put("chance30", new Carta("chance30", TipoEfeito.PAGAR_AO_BANCO, 50));
    }

    public Carta sortearCarta() {
        List<Carta> lista = new ArrayList<>(cartas.values());
        return lista.get(random.nextInt(lista.size()));
    }
}
