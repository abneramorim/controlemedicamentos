/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ObjetoBase;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Abner Amorim
 * @param <T>
 */
public class PersistenciaJPA<T extends ObjetoBase> {

    private final Class<T> classePersistente;

    public PersistenciaJPA(Class<T> persistedClass) {
        this.classePersistente = persistedClass;
    }

    public int salvar(T obj) {
        if (obj.getObjCod_objBase() > 0) {
            //System.out.println("Atualizando");
            FuncoesJPA.Fundir(obj);
            //System.out.println("Fundiu");
            return obj.getObjCod_objBase();
        } else {
            //System.out.println("Salvando");
            return FuncoesJPA.Persistir(obj);
        }
    }
    
    public int atualizar(T obj) {
        if (obj.getObjCod_objBase() > 0) {
            //System.out.println("Atualizando");
            FuncoesJPA.Fundir(obj);
            return obj.getObjCod_objBase();
        }
        return -1;
    }
    
    public int update(Class classe, String pCampo, String pValor, String pCampoID,  String pCodigo){
        return FuncoesJPA.Update(classe, pCampo, pValor, pCampoID, pCodigo);
    }

    public void remover(int i) {
        FuncoesJPA.Excluir(i, classePersistente);
    }

    public T recuperar(int id) {
        Object obj = FuncoesJPA.recuperar(id, classePersistente);
        return (T) obj;
    }

    public List<T> RecuperarTodos() {
        return (List<T>) FuncoesJPA.Selecionar(classePersistente);
    }

    public List<T> Selecionar(Class classe, String whereJPQL) {
        return (List<T>) FuncoesJPA.Selecionar(classe, whereJPQL);
    }
    
    public List<T> Selecionar_Pesquisa(Class classe, String whereJPQL) {
        return (List<T>) FuncoesJPA.Selecionar_Pesquisa(classe, whereJPQL);
    }
    
    public List<T> SelecionarListSQL(Class classe, String pSQL) {
        return (List<T>) FuncoesJPA.SelecionarListPassaSQL(classe, pSQL);
    }

    public T RecuperarPrimeiroRegistro() {
        return (T) FuncoesJPA.RecuperarPrimeiroRegistro(classePersistente);
    }
    
    public T RecuperarUltimoRegistro() {
        return (T) FuncoesJPA.RecuperarUltimoRegistro(classePersistente);
    }
    
    public T RecuperarProximoRegistro(int pCodigo) {
        return (T) FuncoesJPA.RecuperarProximoRegistro(classePersistente, pCodigo);
    }
    
    public T RecuperarRegistroAnterior(int pCodigo) {
        return (T) FuncoesJPA.RecuperarRegistroAnterior(classePersistente, pCodigo);
    }
}
