/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import View.Configuracao;
import View.Mensagens.Mensagem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author abner
 */
public class FabricaJPA {
    
    private static EntityManagerFactory Fabrica = null;
    private static final String nomeUndPersistencia = "ConexaoBD";
    
    private FabricaJPA() {
        Fabrica = Persistence.createEntityManagerFactory(nomeUndPersistencia);
    }
    
    public static EntityManager getManager() {
        try{
            if(Fabrica == null) {
                FabricaJPA  CONEXAOABERTA = new FabricaJPA();
                System.out.println("Fabrica JPA aberta!");
            }
            
            return Fabrica.createEntityManager();
        } catch (Exception e) {
            if(Mensagem.confirm("Erro ao iniciar o sistema !!!\nDeseja alterar a conexão com o Banco de Dados?", null)){
                new Configuracao().setVisible(true);
            }
            System.out.println("Erro ao abrir conexão JPA ou criar gerenciador: " + e.getMessage());
//            e.printStackTrace();
            return null;
        }
    }
    
    public static void FecharFabrica(){
        Fabrica.close();
    }
}
