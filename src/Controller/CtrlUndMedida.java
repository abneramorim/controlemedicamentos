package Controller;

import DAO.PersistenciaJPA;
import com.google.gson.JsonParser;
import java.util.List;

/**
 *
 * @author Abner Amorim
 */
public class CtrlUndMedida extends ControllerBaseCtrl{
    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.UndMedida();
        DAO = new PersistenciaJPA(Model.UndMedida.class);
    }
    
    public String RecuperarUndMed(String pUndMed){
        String select = " WHERE U.unidade LIKE \"" + pUndMed + "\"";

        List Insumos = this.Selecionar(Model.UndMedida.class, select);
        
        if(Insumos.size() == 1){
            //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString();
        }
        
        return null;
    }
}
