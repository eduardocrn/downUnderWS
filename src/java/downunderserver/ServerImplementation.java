/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunderserver;

/**
 *
 * @author Eduardo
 */
import java.util.ArrayList;
import java.util.HashMap;

public class ServerImplementation implements DownUnderInterface {

    private static final int MAX_PARTIDAS = 500;

    private int idCount = 100;

    private final ArrayList<Partida> partidas = new ArrayList<>();

    private final HashMap<String, String> preRegister = new HashMap<>();

    public ServerImplementation() {

    }

    @Override
    public int preRegistro(String nomeJogador1, int idJogador1, String nomeJogador2, int idJogador2) {

        preRegister.put(nomeJogador1, idJogador1 + ";" + idJogador2);
        preRegister.put(nomeJogador2, idJogador2 + ";" + idJogador1);

        return 0;
    }

    @Override
    public synchronized int registraJogador(String nomeJogador) {
        String id = "";
        Partida partidaExistente = null;

        if (preRegister.containsKey(nomeJogador)) {
            id = preRegister.get(nomeJogador);

            preRegister.remove(nomeJogador);

            partidaExistente = buscaPartidaPorJogador(Integer.parseInt(id.split(";")[1]));
            if (partidaExistente == null) {
                partidas.add(new Partida(new Jogador(nomeJogador, Integer.parseInt(id.split(";")[0]))));
            } else {
                partidaExistente.adicionaOponente(new Jogador(nomeJogador, Integer.parseInt(id.split(";")[0])));
            }

            return Integer.parseInt(id.split(";")[0]);
        } else {
            partidaExistente = buscaPartidaPorJogador(nomeJogador);
        }
        
        if (partidas.size() == MAX_PARTIDAS) {
            if (partidas.get(partidas.size()).statusPartida() == StatusPartida.INICIADA)
                return -2;
        }

        if (partidaExistente != null) {
            return -1;
        }

        if (id == "") {
            id = ("" + Math.random()).substring(2, 9);
            synchronized (this) {
                id = idCount + id;
                idCount++;
            }
        }

        Jogador novo = new Jogador(nomeJogador, Integer.parseInt(id));

        if (!alocaJogador(novo)) {
            return -2;
        }

        return novo.getId();
    }

    @Override
    public int encerraPartida(int idJogador) {

        Partida p = buscaPartidaPorJogador(idJogador);

        if (p == null) {
            return -1;
        }

        p.encerraPartida(idJogador);
        
        if (!p.temJogador())
            removePartidaPorJogador(idJogador);

        return 0;
    }

    @Override
    public int temPartida(int idJogador) {
        try {
            Partida partida = buscaPartidaPorJogador(idJogador);

            if (partida.statusPartida() == StatusPartida.AGUARDANDO) {
                return 0;
            }

            if (partida.statusPartida() == StatusPartida.TIMEOUT) {
                return -2;
            }

            if (partida.getJogador1().getId() == idJogador) {
                return 1;
            } else {
                return 2;
            }

        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int ehMinhaVez(int idjogador) {
        try {
            Partida partida = buscaPartidaPorJogador(idjogador);

            switch (partida.statusPartida()) {
                case AGUARDANDO:
                    return -2;
                case ENCERRADA:
                    int res = partida.getResultado(idjogador);

                    return res;
                case INICIADA:
                    if (partida.getJogadorAtual().getId() == idjogador) {
                        return 1;
                    } else {
                        return 0;
                    }
                default:
                    return -1;
            }

        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String obtemTabuleiro(int idJogador) {
        try {
            Partida partida = buscaPartidaPorJogador(idJogador);

            return partida.getTabuleiro();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public int soltaEsfera(int idJogador, int posicao) {
        Partida partida = buscaPartidaPorJogador(idJogador);

        try {
            if (partida.statusPartida() == StatusPartida.AGUARDANDO) {
                return -2;
            }

            if (partida.getJogadorAtual().getId() != idJogador) {
                return -3;
            }

            if (partida.statusPartida() == StatusPartida.TIMEOUT) {
                return 2;
            }

            int resultado = partida.realizaJogada(idJogador, posicao);

            if (partida.statusPartida() == StatusPartida.ENCERRADA || partida.statusPartida() == StatusPartida.TIMEOUT) {
                int i = 0;
            }

            return resultado;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String obtemOponente(int idJogador) {
        try {
            Partida partida = buscaPartidaPorJogador(idJogador);

            return partida.getOponente(idJogador).getNome();
        } catch (Exception e) {
            return "";
        }
    }

    private synchronized Partida buscaPartidaPorJogador(int id) {
        for (Partida partida : partidas) {
            if (partida.verificaJogador(id)) {
                return partida;
            }
        }

        return null;
    }

    private synchronized boolean removePartidaPorJogador(int id) {
        Partida p = null;
        for (int i = 0; i < partidas.size(); i++) {
            if (partidas.get(i).verificaJogador(id)) {
                partidas.remove(i);
                return true;
            }
        }

        return false;
    }

    private synchronized Partida buscaPartidaPorJogador(String nomeJogador) {
        for (Partida partida : partidas) {
            if (partida.verificaJogador(nomeJogador)) {
                return partida;
            }
        }

        return null;
    }

    private synchronized boolean alocaJogador(Jogador jogador) {
        Partida partidaDisponivel = null;

        for (Partida partida : partidas) {
            if (partida.statusPartida() == StatusPartida.AGUARDANDO) {
                partidaDisponivel = partida;
            }
        }

        if (partidaDisponivel != null) {
            return partidaDisponivel.adicionaOponente(jogador);
        }

        partidaDisponivel = new Partida(jogador);
        partidas.add(partidaDisponivel);

        return true;

    }

}
