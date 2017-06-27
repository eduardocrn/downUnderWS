package downunderserver;

public class Tabuleiro {

    private final char[][] board;
    private final int[] ultimasJogadas = new int[]{-1, -1};

    public Tabuleiro() {
        board = new char[5][8];
    }

    public int colocaEsfera(char esfera, int posicao) {
        char[] linha = board[posicao];

        for (int i = 0; i <= linha.length; i++) {
            if (linha[i] == 0) {
                linha[i] = esfera;
                ultimasJogadas[0] = ultimasJogadas[1];
                ultimasJogadas[1] = posicao;
                return 1;
            }
        }

        return 0;
    }

    public boolean estaCompleto() {
        for (int i = 0; i < 5; i++) {
            if (board[i][7] == 0) {
                return false;
            }
        }

        return true;
    }

    public String estadoAtual() {
        StringBuilder estado = new StringBuilder("");

        if (estaCompleto()) {
            for (int j = 7; j >= 0; j--) {
                for (int i = 0; i < 5; i++) {
                    estado.append(board[i][j]);
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                if (board[i][7] == 0) {
                    estado.append('-');
                } else {
                    estado.append(board[i][7]);
                }
            }

            estado.append(".....");

            if (ultimasJogadas[0] > -1) {
                estado.setCharAt(5 + ultimasJogadas[0], '^');
            }

            if (ultimasJogadas[1] > -1) {
                estado.setCharAt(5 + ultimasJogadas[1], '^');
            }
        }

        return estado.toString();
    }

    public char[][] getTabuleiro() {
        return board;
    }
}
