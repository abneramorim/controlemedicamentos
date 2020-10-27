package Controller;

import DAO.PersistenciaJPA;
import com.google.gson.JsonParser;
import java.util.List;

/**
 *
 * @author Abner Amorim
 */
public class CtrlCliente extends ControllerBaseCtrl{
    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.Cliente();
        DAO = new PersistenciaJPA(Model.Cliente.class);
    }
    
    public String RecuperarCliCartao (String pCartao){
        String filtro = " WHERE U.cartao_cli LIKE \"" + pCartao + "\"";
        List retorno = this.Selecionar(Objeto.getClass(), filtro);        
        
        if(retorno != null && retorno.size() == 1){
            return new JsonParser().parse(retorno.get(0).toString()).getAsJsonObject().get("nome_cli").getAsString();
        }
        //System.out.println("Cliente recuperado: " + retorno);
        return null;
    }
    
    public String RecuperarCliCompCartao(String pCartao) {
        String filtro = " WHERE U.cartao_cli LIKE \"" + pCartao + "\"";
        List retorno = this.Selecionar(Objeto.getClass(), filtro);

        if (retorno.size() == 1) {
            //System.out.println("Cliente recuperado: " + new JsonParser().parse(retorno.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(retorno.get(0).toString()).getAsJsonObject().toString();
        }

        //System.out.println("Cliente recuperado: " + retorno);
        return "Erro ao recuperar o Cliente !!!";
    }
}
