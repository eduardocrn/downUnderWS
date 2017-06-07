/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunderserver;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Eduardo
 */
@WebService(serviceName = "DownUnderWS")
public class DownUnderServer {

    private static ServerImplementation server;
    
    public DownUnderServer() {
        server =  new ServerImplementation();
    }
    
    @WebMethod(operationName = "preRegistro")
    public String preRegistro(@WebParam(name = "nomeJogador1") String nomeJogador1, @WebParam(name = "idJogador1") int idJogador1, 
            @WebParam(name = "nomeJogador2") String nomeJogador2, @WebParam(name = "idJogador2") int idJogador2) {
        return server.preRegistro(nomeJogador1, idJogador1, nomeJogador2, idJogador2) + "";
    }
    
    @WebMethod(operationName = "registraJogador")
    public String registra(@WebParam(name = "nome") String nomeJogador) {
        return server.registraJogador(nomeJogador) + "";
    }
    
    @WebMethod(operationName = "encerraPartida")
    public String encerraPartida(@WebParam(name = "id") int id) {
        return server.encerraPartida(id) + "";
    }

    @WebMethod(operationName = "temPartida")
    public String temPartida(@WebParam(name = "id") int id) {
        return server.temPartida(id) + "";
    }

    @WebMethod(operationName = "ehMinhaVez")
    public String ehMinhaVez(@WebParam(name = "id") int id) {
        return server.ehMinhaVez(id) + "";
    }

    @WebMethod(operationName = "obtemTabuleiro")
    public String obtemTabuleiro(@WebParam(name = "id") int id) {
        return server.obtemTabuleiro(id) + "";
    }

    @WebMethod(operationName = "soltaEsfera")
    public String soltaEsfera(@WebParam(name = "id") int id, @WebParam(name = "posicao") int pos) {
        return server.soltaEsfera(id, pos) + "";
    }

    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "id") int id) {
        return server.obtemOponente(id) + "";
    }
}
