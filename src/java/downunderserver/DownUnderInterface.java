package downunderserver;

public interface DownUnderInterface {
	
	public int registraJogador(String nomeJogador);
	
	public int encerraPartida(int idJogador);
	
	public int temPartida(int idJogador);
	
	public int ehMinhaVez(int idjogador);
	
	public String obtemTabuleiro(int idJogador);
	
	public int soltaEsfera(int idJogador, int posicao);
	
	public String obtemOponente(int idJogador);
	
}
