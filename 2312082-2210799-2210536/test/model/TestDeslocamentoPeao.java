package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDeslocamentoPeao {

	@Test
	public void testMoverPeao() {
	    Tabuleiro tabuleiro = new Tabuleiro();
	    Jogador j = new Jogador("Ana", 4000, null);

	    // Simula que tirou 7 nos dados
	    j.mover(7, tabuleiro.tamanho());

	    assertEquals(7, j.getPosicao());
	}
}
