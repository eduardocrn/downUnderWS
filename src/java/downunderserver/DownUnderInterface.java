package downunderserver;

public interface DownUnderInterface {
    
        public int preRegistro(String nomeJogador1, int idJogador1, String nomeJogador2, int idJogador2);
	
	public int registraJogador(String nomeJogador);
	
	public int encerraPartida(int idJogador);
	
	public int temPartida(int idJogador);
	
	public int ehMinhaVez(int idjogador);
	
	public String obtemTabuleiro(int idJogador);
	
	public int soltaEsfera(int idJogador, int posicao);
	
	public String obtemOponente(int idJogador);
	
}
