package Controller;

import DAO.FuncoesJPA;
import DAO.PersistenciaJPA;
import Model.Produto;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.util.List;

/**
 *
 * @author Abner Amorim
 */
public class CtrlProduto extends ControllerBaseCtrl {

    @Override
    protected void InicializarCtrl() {
        Objeto = new Produto();
        DAO = new PersistenciaJPA(Model.Produto.class);
    }
    
    public String RecuperarProdIdentificador(String pIdentificador){
        String select = " WHERE U.identificador_prod LIKE \"" + pIdentificador + "\"";

        List Insumos = this.Selecionar(Model.Produto.class, select);
        
        if(Insumos.size() == 1){
            //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString();
        }
        
        return null;
    }
    
    /*public Object[] RecuperaProdutosQtdMin(){
    //String select = "SELECT P FROM Produto as P, Lote as L WHERE P.descricao_prod = L.produtoIdent ";
    
    Object[] retorno = this.SelecionarListPassaSQL(Produto.class, select).toArray();
    
    for(int i = 0; i < retorno.length; i++){
    ObjetoBase obj = (ObjetoBase) retorno[i];
    
    System.out.println(obj.toJson());
    }
    
    return retorno;
    }*/
    
    public List RecuperaProdutosQtdMin(){
        
        String whereJPQL = "select sum(L.qtd_prod), P.identificador_prod, P.qtd_min, P.descricao_prod  from Produto P, Lote L where P.identificador_prod LIKE L.produtoIdent GROUP BY L.produtoIdent HAVING sum(L.qtd_prod)<P.qtd_min";
        
        return FuncoesJPA.SelecionarProdutos(whereJPQL);
    }
}
