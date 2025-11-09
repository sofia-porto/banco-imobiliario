package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import model.JogoFacade;
import model.Observer;
import controller.JogoController;

public class TabuleiroView extends Canvas implements KeyListener, MouseListener, Observer {

    private static final long serialVersionUID = 1L;

    private JogoFacade jogo;
    private JogoController controller; 
    
    private Image imagemTabuleiro;
    private Image[] imagensPioes = new Image[6];
    
    private Map<String, Image> imagensCartas = new HashMap<>();

    private static final int TOTAL_CASAS = 40;
    private static final int TAMANHO = 700; 

    public TabuleiroView(JogoFacade jogo, JogoController controller) {
        this.jogo = jogo;
        this.controller = controller; 
        
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        carregarImagens();
                
        this.setPreferredSize(new Dimension(800, 800)); 
    }

    private void carregarImagens() {
        try {
            URL urlTabuleiro = getClass().getResource("/resources/tabuleiro.png");
            imagemTabuleiro = ImageIO.read(urlTabuleiro);

            for (int i = 0; i < imagensPioes.length; i++) {
                URL u = getClass().getResource("/resources/pin" + i + ".png");
                if (u != null) {
                    imagensPioes[i] = ImageIO.read(u);
                } else {
                    System.out.println("⚠️ Pião " + i + " não encontrado");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Imagem do tabuleiro ou piões não encontrada.");
            imagemTabuleiro = null;
        }
    }
 
    private Image getImagemCarta(String idCarta) {
        if (imagensCartas.containsKey(idCarta)) {
            return imagensCartas.get(idCarta);
        }

        try {
            String nomeArquivo = idCarta + ".png";
            URL u = getClass().getResource("/resources/" + nomeArquivo);
            
            if (u != null) {
                Image img = ImageIO.read(u);
                imagensCartas.put(idCarta, img);
                return img;
            } else {
                System.out.println("⚠️ Imagem da carta não encontrada: " + nomeArquivo);
                imagensCartas.put(idCarta, null); 
                return null;
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem da carta: " + idCarta);
            imagensCartas.put(idCarta, null);
            return null;
        }
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (imagemTabuleiro != null) {
            g2.drawImage(imagemTabuleiro, 40, 50, TAMANHO, TAMANHO, this);
        } else {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(40, 50, TAMANHO, TAMANHO);
        }

        int numJogadores = jogo.getNumeroJogadores();
        if (numJogadores == 0) return; 

        int indiceJogadorAtual = jogo.getIndiceJogadorAtual();
        
        for (int i = 0; i < numJogadores; i++) {
            int pos = jogo.getJogadorPosicao(i);
            Point p = getCoordenadaDaCasa(pos, i);
            Image img = imagensPioes[i];

            if (img != null) {
                g2.drawImage(img, p.x + 32, p.y + 32, 24, 24, this);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillOval(p.x, p.y, 20, 20);
            }
        }

        String idCarta = jogo.getUltimaCartaGlobal();
        if (idCarta != null) {
            Image cartaImg = getImagemCarta(idCarta);
            
            if (cartaImg != null) {
                int x = 290;
                int y = 250; 
                int w = 200;
                int h = 220; 
                
                g2.drawImage(cartaImg, x, y, w, h, this);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, w, h);

                if (!idCarta.startsWith("chance")) {
                    int pos = jogo.getJogadorPosicao(indiceJogadorAtual);
                    
                    String dono = jogo.getPropriedadeDonoNome(pos);
                    int casas = jogo.getPropriedadeCasas(pos);
                    boolean hotel = jogo.getPropriedadeTemHotel(pos);

                    g2.setFont(new Font("Arial", Font.BOLD, 14));
                    g2.setColor(Color.BLACK);
                    
                    int yTexto = y + h + 20; 
                    g2.drawString("Dono: " + (dono != null ? dono : "Disponível"), x, yTexto);
                    g2.drawString("Casas: " + casas, x, yTexto + 20);
                    g2.drawString("Hotel: " + (hotel ? "Sim" : "Não"), x, yTexto + 40);
                }
            }
        }
    }

    private Point getCoordenadaDaCasa(int idx, int pista) {
        final int margem = 7;
        final int quina = 92;
        final int casa = 55;
        final int espaco = 2;
        final int tamanho = 700;
        int deslocamento = pista * 8; 
        final int xIni = margem + deslocamento;
        final int yIni = margem + deslocamento;
        final int lado = tamanho - deslocamento * 2;
        final int passo = casa + espaco;
        int x = 0, y = 0;
        if (idx <= 10) { 
            x = xIni + lado - quina - (idx * passo);
            y = yIni + lado - quina;
        } else if (idx <= 20) { 
            x = xIni;
            y = yIni + lado - quina - ((idx - 10) * passo);
        } else if (idx <= 30) { 
            x = xIni + ((idx - 20) * passo);
            y = yIni;
        } else if (idx <= 39) { 
            x = xIni + lado - quina;
            y = yIni + ((idx - 30) * passo);
        }
        
        return new Point(40 + x, 50 + y);
    }

    @Override public void atualizar() { repaint(); }
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}