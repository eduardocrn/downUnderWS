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

public class ServerImplementation implements DownUnderInterface {
	
	private static int MAX_PARTIDAS = 50;
	
	private int idCount = 100;
	
	private static final long serialVersionUID = 189L;
	
	private ArrayList<Partida> partidas = new ArrayList<>();
	
	public ServerImplementation() {
		timerPartida();
	}

	@Override
	public int registraJogador(String nomeJogador) {
		if (partidas.size() == MAX_PARTIDAS)
			return -2;
		
		Partida partidaExistente = buscaPartidaPorJogador(nomeJogador);
		
		if (partidaExistente != null)
			return -1;
		
		String id = ("" + Math.random()).substring(2, 9);
		id = idCount + id;
		
		Jogador novo = new Jogador(nomeJogador, Integer.parseInt(id)); 
		
		idCount++;
		
		if(!alocaJogador(novo))
			return -2;

		return novo.getId();
	}

	@Override
	public int encerraPartida(int idJogador) {
		
		Partida p = buscaPartidaPorJogador(idJogador);
				
		if (p == null)
			return -1;
		
		p.encerraPartida(idJogador);
		
		return 0;
	}

	@Override
	public int temPartida(int idJogador) {
		try {
			Partida partida = buscaPartidaPorJogador(idJogador);

			if(partida.statusPartida() == StatusPartida.AGUARDANDO)
				return 0;
			
			if (partida.statusPartida() == StatusPartida.TIMEOUT)
				return -2;
			
			if (partida.getJogador1().getId() == idJogador)
				return 1;
			else
				return 2;

		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int ehMinhaVez(int idjogador) {
		try {
			Partida partida = buscaPartidaPorJogador(idjogador);

			switch(partida.statusPartida()) {
				case AGUARDANDO :
					return -2;
				case ENCERRADA :
					int res = partida.getResultado(idjogador);
					
					return res;
				case INICIADA:
					if (partida.getJogadorAtual().getId() == idjogador)
						return 1;
					else
						return 0;
				default :
					return -1;
			}

		} catch (Exception e) {
			return 1;
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

		if (partida.statusPartida() == StatusPartida.AGUARDANDO)
			return -2;

		if (partida.getJogadorAtual().getId() != idJogador)
			return -3;
		
		if (partida.statusPartida() == StatusPartida.TIMEOUT)
			return 2;
		
		int resultado = partida.realizaJogada(idJogador, posicao);
		
		if (partida.statusPartida() == StatusPartida.ENCERRADA || partida.statusPartida() == StatusPartida.TIMEOUT)
			partida.removeJogadores();
		
		return resultado;
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
		for (Partida partida : partidas)
			if (partida.verificaJogador(id))
				return partida;
		
		return null;
	}
	
	private synchronized void timerPartida() {
		new Thread() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(60000);
						long now  = System.currentTimeMillis();
						for (int i=0; i<partidas.size(); i++) {
							if (partidas.get(i).statusPartida() == StatusPartida.ENCERRADA || partidas.get(i).statusPartida() == StatusPartida.TIMEOUT)
								if (now - partidas.get(i).getTempoEncerrada() > 60000)
									partidas.remove(i);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private synchronized Partida buscaPartidaPorJogador(String nomeJogador) {
		for (Partida partida : partidas)
			if (partida.verificaJogador(nomeJogador))
				return partida;
		
		return null;
	}

	private synchronized boolean alocaJogador(Jogador jogador) {
		Partida partidaDisponivel = null;
		
		for (Partida partida : partidas)
			if (partida.statusPartida() == StatusPartida.AGUARDANDO)
				partidaDisponivel = partida;
		
		if (partidaDisponivel != null)
			return partidaDisponivel.adicionaOponente(jogador);
		
		partidaDisponivel = new Partida(jogador);
		partidas.add(partidaDisponivel);
		
		return true;
			
	}

}

