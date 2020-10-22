/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PersistenciaJPA;
import Model.ObjetoBase;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author abner
 */
public class CtrlLote extends ControllerBaseCtrl {

    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.Lote();
        DAO = new PersistenciaJPA(Model.Lote.class);
    }
    
    public String RecuperarLote(String pLote, String pProduto){
        String select = " WHERE U.lote LIKE \"" + pLote + "\" AND U.produtoIdent LIKE \"" + pProduto + "\"";
        List Lotes = this.Selecionar(Model.Produto.class, select);
        if(Lotes.size() == 1){
            return new JsonParser().parse(Lotes.get(0).toString()).getAsJsonObject().toString();
        }
        return null;
    }
    
    public String RecuperarLote(String pLote){
        String select = " WHERE U.lote LIKE \"" + pLote + "\"";
        List Lotes = this.Selecionar(Model.Produto.class, select);
        if(Lotes.size() == 1){
            return new JsonParser().parse(Lotes.get(0).toString()).getAsJsonObject().toString();
        }
        return null;
    }
    
    public JsonArray LotesVencimento(String pDataFim){
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        
        String whereJPQL = " WHERE U.validade_prod BETWEEN ('" + formatador.format(data) + "') AND (\'" + pDataFim + "\') AND U.qtd_prod>0 order by U.validade_prod ASC";
        JsonArray Vetores = new JsonArray();

        List<ObjetoBase> Objetos = DAO.Selecionar(Model.Lote.class, whereJPQL);
        //System.out.println("Lotes Recuperados: " + Objetos.size());

        for (int i = 0; i < Objetos.size(); i++) {
            Vetores.add(Objetos.get(i).toJson());
        }
        
        return Vetores;
    }
    
    public JsonArray LotesVencimento(int pQtdDias){
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        data.setDate(data.getDate()+pQtdDias);
        //System.out.println("Data: " + data.toString());
        
        //return new JsonArray();
        return LotesVencimento(formatador.format(data));
    }
    
}
