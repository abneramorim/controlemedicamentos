package Controller;

import DAO.PersistenciaJPA;
import com.google.gson.JsonParser;
import java.util.List;

/**
 *
 * @author Abner Amorim
 */
public class CtrlFabricante extends ControllerBaseCtrl{
    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.Fabricante();
        DAO = new PersistenciaJPA(Model.Fabricante.class);
    }
    
    public String RecuperarFabCNPJ(String pCNPJ){
        String select = " WHERE U.cnpj_fab LIKE \"" + pCNPJ + "\"";

        List Insumos = this.Selecionar(Model.Fabricante.class, select);
        
        if(Insumos.size() == 1){
            //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString();
        }
        
        return null;
    }
}
