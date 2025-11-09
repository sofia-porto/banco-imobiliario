package view;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import model.*;

class PainelInfoPropriedadeView extends Panel implements Observer {
    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private Image cartaImg;
    private String nomePropriedade;
    private int casas;
    private boolean hotel;
    private boolean temDono;
    private String nomeDono;
    private Color corDono;

    private final Color[] coresJogadores = {
        new Color(220, 0, 0),      // vermelho
        new Color(0, 80, 255),     // azul
        new Color(255, 140, 0),    // laranja
        new Color(255, 215, 0),    // amarelo
        new Color(128, 0, 128),    // roxo
        new Color(120, 120, 120)   // cinza
    };

    public PainelInfoPropriedadeView(JogoFacade jogo) {
        this.jogo = jogo;
        setPreferredSize(new Dimension(260, 400));
        setBackground(Color.WHITE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("🏠 Propriedade Atual", 20, 25);

        if (nomePropriedade == null || cartaImg == null) {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString("Nenhuma propriedade selecionada.", 20, 60);
            
            // Verifica se a casa atual é comprável para dar a msg correta
            int indiceJogador = jogo.getIndiceJogadorAtual();
            int posicao = jogo.getJogadorPosicao(indiceJogador);
            if (jogo.getPropriedadePreco(posicao) <= 0) {
                 g.drawString("(Casa atual não é comprável)", 20, 75);
            }
            return;
        }

        if (temDono && corDono != null) {
            g.setColor(corDono);
            g.fillRoundRect(20, 45, 210, 170, 15, 15);
        }

        g.drawImage(cartaImg, 25, 55, 200, 160, this);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int y = 235;
        g.drawString("Casas: " + casas, 25, y);
        g.drawString("Hotel: " + (hotel ? "Sim" : "Não"), 25, y + 20);
        g.drawString("Dono: " + (temDono ? nomeDono : "Disponível"), 25, y + 40);
    }

    @Override
    public void atualizar() {
        int indiceJogador = jogo.getIndiceJogadorAtual();
        int posicao = jogo.getJogadorPosicao(indiceJogador);

        nomePropriedade = jogo.getPropriedadeNome(posicao);
        
        if (nomePropriedade == null || jogo.getPropriedadePreco(posicao) <= 0) {
            cartaImg = null;
            this.nomePropriedade = null;
            repaint();
            return;
        }

        cartaImg = carregarCartaPropriedade(nomePropriedade);
        casas = jogo.getPropriedadeCasas(posicao);
        hotel = jogo.getPropriedadeTemHotel(posicao);
        temDono = jogo.propriedadeTemDono(posicao);
        nomeDono = jogo.getPropriedadeDonoNome(posicao);

        if (temDono) {
            int donoIndex = jogo.getIndiceJogadorPorNome(nomeDono); 
            if (donoIndex >= 0 && donoIndex < coresJogadores.length) {
                corDono = coresJogadores[donoIndex];
            }
        } else {
            corDono = null;
        }

        repaint();
    }

    private Image carregarCartaPropriedade(String nome) {
        try {
            String nomeArquivo = nome + ".png";
            
            URL url = getClass().getResource("/resources/" + nomeArquivo);
            if (url != null) {
                return ImageIO.read(url);
            } else {
                System.out.println("⚠️ Imagem da carta não encontrada: " + nomeArquivo);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem da carta: " + nome);
            return null;
        }
    }
}