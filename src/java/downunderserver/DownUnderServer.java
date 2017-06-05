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
 * @author 12205139
 */
@WebService(serviceName = "DownUnderServer")
public class DownUnderServer {
    
    private static ServerImplementation server;
    
    public DownUnderServer() {
        server =  new ServerImplementation();
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "registraJogador")
    public String registra(@WebParam(name = "nome") String txt) {
        return server.registraJogador(txt) + "";
    }
    
    @WebMethod(operationName = "encerraPartida")
    public String encerraPartida(@WebParam(name = "id") String id) {
        return server.encerraPartida(Integer.parseInt(id)) + "";
    }

    @WebMethod(operationName = "temPartida")
    public String temPartida(@WebParam(name = "id") String id) {
        return server.temPartida(Integer.parseInt(id)) + "";
    }

    @WebMethod(operationName = "ehMinhaVez")
    public String ehMinhaVez(@WebParam(name = "id") String id) {
        return server.ehMinhaVez(Integer.parseInt(id)) + "";
    }

    @WebMethod(operationName = "obtemTabuleiro")
    public String obtemTabuleiro(@WebParam(name = "id") String id) {
        return server.obtemTabuleiro(Integer.parseInt(id)) + "";
    }

    @WebMethod(operationName = "soltaEsfera")
    public String soltaEsfera(@WebParam(name = "id") String id, @WebParam(name = "posicao") String pos) {
        return server.soltaEsfera(Integer.parseInt(id), Integer.parseInt(pos)) + "";
    }

    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "id") String id) {
        return server.obtemOponente(Integer.parseInt(id)) + "";
    }
}
