package Controller;

import DAO.PersistenciaJPA;
import com.google.gson.JsonParser;
import java.util.List;

/**
 *
 * @author abner
 */
public class CtrlPedido_Produto extends ControllerBaseCtrl {

    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.Pedido_Produto();
        DAO = new PersistenciaJPA(Model.Pedido_Produto.class);
    }

    public String RecuperarPedido_Produto(int pCod) {
        String select = " WHERE U.pedido.objCod_objBase=" + pCod;

        List Insumos = this.Selecionar(Model.Pedido_Produto.class, select);
        
        //System.out.println("Tamanho array (RecuperarPEdido_Produto): " + Insumos.size());
        //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
        return new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString();
    }
    
    public String Recuperar_Lote_Pedido(int pCodigoPedido, String pIdLote){
        System.out.println("codigo pedido: " + pCodigoPedido + " pIdLote: " + pIdLote);
        String select = "SELECT R FROM Pedido AS P, Pedido_Produto AS R, Lote AS L"
                + " WHERE P.objCod_objBase = " + pCodigoPedido + " AND L.lote LIKE \'" + pIdLote + "\' AND P.produtos_pedido.objCod_objBase=R.objCod_objBase AND "
                + " R.lote_prod.objCod_objBase=L.objCod_objBase";
        
        String retorno = this.SelecionarPassaSQL(Model.Pedido.class, select);
        System.out.println("Recuperou Lote");
        if (retorno == null) {
            System.out.println("Retornou nulo");
            return null;
        }
        //System.out.println("Tamanho array (RecuperarPedido_Produto_Lote_Pedido): " + retorno);
        System.out.println("retornando: " + new JsonParser().parse(retorno).getAsJsonObject().toString());
        return new JsonParser().parse(retorno).getAsJsonObject().toString();
    }
}
