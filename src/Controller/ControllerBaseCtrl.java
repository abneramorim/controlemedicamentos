package Controller;

import DAO.FuncoesJPA;
import DAO.PersistenciaJPA;
import Model.ObjetoBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abner Amorim
 */
public abstract class ControllerBaseCtrl {

    protected ObjetoBase Objeto;

    protected PersistenciaJPA DAO;

    protected abstract void InicializarCtrl();

    public ControllerBaseCtrl() {
        InicializarCtrl();
        //System.out.println(Objeto.getClass());
    }

    public int Salvar(String pDados) {
        //System.out.println("Json Recebido pelo CtrlBase: " + pDados + "\nObjeto: " + Objeto.toObjeto(pDados)); 
        return DAO.salvar(Objeto.toObjeto(pDados));
    }
    
    public void Salvar(String[] pDados){
        Gson gson = new GsonBuilder().create();
    }
    
    public int Atualizar(String pDados){
        return DAO.atualizar(Objeto.toObjeto(pDados));
    }
    
    public int Update(Class classe, String pCampo, String pValor, String pCampoID, String pCodigo){
        return DAO.update(classe, pCampo, pValor, pCampoID, pCodigo);
    }

    public void Deletar(int pCodigo) {
        Objeto.setObjCod_objBase(pCodigo);

        DAO.remover(pCodigo);
        //System.out.println("pedido_produto " + pCodigo + " removido com sucesso");
    }

    public String Recuperar(int pCodigo) {
        Objeto = DAO.recuperar(pCodigo);
        return Objeto != null ? Objeto.toJson() : "";
    }

    public List<String> RecuperarTodos() {
        ArrayList<String> Vetores = new ArrayList<>();

        List<ObjetoBase> Objetos = DAO.RecuperarTodos();
        
        if(Objetos == null){
            return null;
        }
        
        for (int i = 0; i < Objetos.size(); i++) {
            Vetores.add(Objetos.get(i).toJson());
        }
        
        return Vetores;
    }
    
    public List<String> Selecionar(Class pClasse, String whereJPQL){
        ArrayList<String> Vetores = new ArrayList<>();

        List<ObjetoBase> Objetos = DAO.Selecionar(Objeto.getClass(), whereJPQL);

        for (int i = 0; i < Objetos.size(); i++) {
            Vetores.add(Objetos.get(i).toJson());
        }
        
        return Vetores;
        
    }
    
    public JsonArray Selecionar(String whereJPQL){
        JsonArray Vetores = new JsonArray();

        List<ObjetoBase> Objetos = DAO.Selecionar(Objeto.getClass(), whereJPQL);
        JsonParser parser = new JsonParser();
        for (int i = 0; i < Objetos.size(); i++) {
            Vetores.add(parser.parse(Objetos.get(i).toJson()).getAsJsonObject());
        }
        
        return Vetores;
    }
    
    public String SelecionarPorCampo(Class pClasse, String pCampo, String pValor){
        String select = " WHERE U." + pCampo + " LIKE \"" + pValor + "\"";
        
        List Insumos = this.Selecionar(pClasse, select);
        
        if(Insumos.size() == 1){
            //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString();
        }

        return null;
    }
    
    public JsonArray SelecionarPorCampo_JsonArray(String pCampo, String pValor){
        String select = " WHERE U." + pCampo + " LIKE \"" + pValor + "\"";
        
        JsonArray Vetores = new JsonArray();
        
        List<ObjetoBase> Objetos = DAO.Selecionar_Pesquisa(Objeto.getClass(), select);
        JsonParser parser = new JsonParser();

        for (int i = 0; i < Objetos.size(); i++) {
            Vetores.add(parser.parse(Objetos.get(i).toJson()).getAsJsonObject());
        }
        
        return Vetores;
    }
    
    protected String SelecionarPassaSQL(Class pClasse, String pSQL){
        //String select = " WHERE U." + pCampo + " LIKE \"" + pValor + "\"";
        
        String retorno = FuncoesJPA.SelecionarPassaSQL(pClasse, pSQL);
        
        if(retorno != null){
            //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(retorno).getAsJsonObject().toString();
        }

        return null;
    }
    
    protected List SelecionarListPassaSQL(Class pClasse, String pSQL){
        ArrayList<String> Vetores = new ArrayList<>();
        List<ObjetoBase> retorno = DAO.SelecionarListSQL(pClasse, pSQL);

        for (int i = 0; i < retorno.size(); i++) {
            Vetores.add(retorno.get(i).toJson());
            System.out.println("Produto -> " + retorno.get(i).toJson());
        }
        
        return retorno;
    }
    
    public String RecuperaPrimeiroRegistro(){
        Objeto = DAO.RecuperarPrimeiroRegistro();
        if(Objeto == null){
            return null;
        }
        return Objeto.toJson();
    }
    
    public String RecuperaUltimoRegistro(){
        Objeto = DAO.RecuperarUltimoRegistro();
        if(Objeto == null){
            return null;
        }
        return Objeto.toJson();
    }
    
    public String RecuperaProximoRegistro(int pCodigo){
        Objeto = DAO.RecuperarProximoRegistro(pCodigo);
        if(Objeto == null){
            return null;
        }
        return Objeto.toJson();
    }
    
    public String RecuperaRegistroAnterior(int pCodigo){
        Objeto = DAO.RecuperarRegistroAnterior(pCodigo);
        if(Objeto == null){
            return null;
        }
        return Objeto.toJson();
    }
    
    public int proximoCodigo(){
        Objeto = DAO.RecuperarUltimoRegistro();
        if(Objeto == null){
            return -1;
        }
        return Objeto.getObjCod_objBase()+1;
    }
}
