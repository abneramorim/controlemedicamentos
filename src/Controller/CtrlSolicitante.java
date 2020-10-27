package Controller;

import DAO.PersistenciaJPA;
import com.google.gson.JsonParser;
import java.util.List;

/**
 *
 * @author Abner Amorim
 */
public class CtrlSolicitante extends ControllerBaseCtrl{
    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.Solicitante();
        DAO = new PersistenciaJPA(Model.Solicitante.class);
    }
    
    public String RecuperarSolCRM(String pCRM){
        String select = " WHERE U.crm_sol LIKE \"" + pCRM + "\"";

        List Insumos = this.Selecionar(Model.Solicitante.class, select);
        
        if(Insumos.size() == 1){
            //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString();
        }
        
        return null;
    }
}
