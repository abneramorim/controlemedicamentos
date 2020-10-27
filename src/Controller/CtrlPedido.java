package Controller;

import DAO.FuncoesJPA;
import DAO.PersistenciaJPA;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abner Amorim
 */
public class CtrlPedido extends ControllerBaseCtrl {

    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.Pedido();
        DAO = new PersistenciaJPA(Model.Pedido.class);
    }

    public int Salvar(String pDados, JsonArray pItensAntigos, JsonArray pItensExcluidos, boolean isAtualiza) {
        //System.out.println("entrou salvar pedido");

        /*JsonArray produtos_pedido_antigo;
        if (new JsonParser().parse(pDados).getAsJsonObject().has("objCod_objBase")) {
        int codigo = new JsonParser().parse(pDados).getAsJsonObject().get("objCod_objBase").getAsInt();
        produtos_pedido_antigo = new JsonParser().parse(new CtrlPedido().Recuperar(codigo)).getAsJsonObject().getAsJsonArray("produtos_pedido");
        isAtualiza = true;
        }*/
        int codPed = DAO.salvar(Objeto.toObjeto(pDados));
        if (pItensAntigos == null) {
            pItensAntigos = new JsonArray();
        }
        if (pItensExcluidos == null) {
            pItensExcluidos = new JsonArray();
        }
        //System.out.println("Itens Excluidos qtd: " + pItensExcluidos.size());
        JsonArray produtos_pedido = new JsonParser().parse(pDados).getAsJsonObject().getAsJsonArray("produtos_pedido");
        //System.out.println("Tam. pItensAntigos: " + pItensAntigos.size() + " Tam. pItensExcluidos: " + pItensExcluidos.size() + " Tam. produtos_pedido: " + produtos_pedido.size());
        if (!isAtualiza) {
            for (int i = 0; i < produtos_pedido.size(); i++) {
                if (produtos_pedido.get(i).getAsJsonObject().get("produto").getAsString().split("-")[0].equals("MEDEXT")) {
                    if (i < produtos_pedido.size() - 1) {
                        i++;
                    } else {
                        break;
                    }
                }
                Double qtd_lote_antiga = produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("qtd_prod").getAsDouble();
                Double qtd_prod = produtos_pedido.get(i).getAsJsonObject().get("qtd_prod_ped").getAsDouble();
                produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().remove("qtd_prod");
                produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().add("qtd_prod", new JsonPrimitive(qtd_lote_antiga - qtd_prod));

                new CtrlLote().Salvar(produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().toString());
            }

            return codPed;
        } else {
            int auxTamArray = produtos_pedido.size();
            for (int i = 0; i < auxTamArray; i++) {
                boolean encontrou = false;
                if (produtos_pedido.get(i).getAsJsonObject().get("produto").getAsString().split("-")[0].equals("MEDEXT")) {
                    continue;
                }
                String loteProcurado = produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("lote").getAsString();
                Double qtd_lote = new JsonParser().parse(new CtrlLote().RecuperarLote(loteProcurado)).getAsJsonObject().get("qtd_prod").getAsDouble();
                Double qtd_atual = produtos_pedido.get(i).getAsJsonObject().get("qtd_prod_ped").getAsDouble();
                produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().remove("qtd_prod");
                for (int j = 0; j < pItensAntigos.size(); j++) {
                    if (pItensAntigos.get(j).getAsJsonObject().get("produto").getAsString().split("-")[0].equals("MEDEXT")) {
                        continue;
                    }
                    String lote = pItensAntigos.get(j).getAsJsonObject().get("lote_prod").getAsJsonObject().get("lote").getAsString();
                    if (lote.equals(loteProcurado)) {
                        encontrou = true;
                        Double qtd_antiga = pItensAntigos.get(j).getAsJsonObject().get("qtd_prod_ped").getAsDouble();
                        produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().add("qtd_prod", new JsonPrimitive(qtd_lote + qtd_antiga - qtd_atual));
                        //new CtrlLote().Salvar(produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().toString());
                        j = pItensAntigos.size();
                    }
                }
                if (!encontrou) {

                    //Double qtd_lote=  produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("qtd_prod").getAsDouble();
                    //Double qtd_atual = produtos_pedido.get(i).getAsJsonObject().get("qtd_prod_ped").getAsDouble();
                    //produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().remove("qtd_prod");
                    produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().add("qtd_prod", new JsonPrimitive(qtd_lote - qtd_atual));

                }
                new CtrlLote().Salvar(produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().toString());
            }
            auxTamArray = pItensExcluidos.size();
            //System.out.println("chegou nos itens excluidos");
            for (int i = 0; i < auxTamArray; i++) {
                //System.out.println("entrou no for");
                if (pItensExcluidos.get(i).getAsJsonObject().get("produto").getAsString().split("-")[0].equals("MEDEXT")) {
                    continue;
                }
                String lote = pItensExcluidos.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("lote").getAsString();
                //System.out.println("buscou o lote");
                Double qtd_lote = new JsonParser().parse(new CtrlLote().RecuperarLote(lote)).getAsJsonObject().get("qtd_prod").getAsDouble();
                //System.out.println("buscou a quantidade do lote");
                Double qtd_adicionar = pItensExcluidos.get(i).getAsJsonObject().get("qtd_prod_ped").getAsDouble();
                //System.out.println("buscou a quantidade de produtos a ser devolvida");
                pItensExcluidos.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().remove("qtd_prod");
                //System.out.println("removeu a quantidade dentro do lote");
                //System.out.println("qtd_lote: " + qtd_lote + " qtd_adicionar: " + qtd_adicionar + " total: " + (qtd_adicionar+qtd_lote));
                pItensExcluidos.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().add("qtd_prod", new JsonPrimitive(qtd_lote + qtd_adicionar));
                //System.out.println("incluiu a quantidade correta dentro do lote");
                new CtrlLote().Salvar(pItensExcluidos.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().toString());
                //System.out.println("salvou o lote");
                new CtrlPedido_Produto().Deletar(pItensExcluidos.get(i).getAsJsonObject().get("objCod_objBase").getAsInt());
            }
        }
        /*if (codPed > 0) {
        JsonArray produtos_pedido = new JsonParser().parse(pDados).getAsJsonObject().getAsJsonArray("produtos_pedido");
        int size = produtos_pedido.size();
        for (int i = 0; i < size; i++) {
        new CtrlLote().Update(Model.Lote.class, "qtd_prod", produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("qtd_prod").toString(),
        "objCod_objBase", produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("objCod_objBase").toString());
        }
        size = pItensExcluidos.size();
        //System.out.println("vai devolver os produtos");
        for (int i = 0; i < size; i++) {
        //System.out.println("Entrou no for");
        int qtd_antiga = new JsonParser().parse(new CtrlLote().RecuperarLote(pItensExcluidos.get(i).getAsJsonObject().get("lote").getAsString())).getAsJsonObject().get("qtd_prod").getAsInt();
        //System.out.println("qtd_antiga: " + qtd_antiga);
        int qtd_devolver = pItensExcluidos.get(i).getAsJsonObject().get("qtd").getAsInt();
        //System.out.println("qtd_devolver: " + qtd_devolver);
        //System.out.println("qtd att: " + String.valueOf(qtd_antiga + qtd_devolver));
        //System.out.println("Lote: " + pItensExcluidos.get(i).getAsJsonObject().get("lote").toString());
        new CtrlLote().Update(Model.Lote.class, "qtd_prod", String.valueOf(qtd_antiga + qtd_devolver),
        "lote", pItensExcluidos.get(i).getAsJsonObject().get("lote").toString());
        }
        return codPed;
        }*/
        return -1;
    }

    @Override
    public void Deletar(int pCodigo) {
        Objeto.setObjCod_objBase(pCodigo);

        JsonObject jsonPedido = new JsonParser().parse(new CtrlPedido().Recuperar(pCodigo)).getAsJsonObject();
        JsonArray produtos_pedido = jsonPedido.getAsJsonArray("produtos_pedido");

        int size = produtos_pedido.size();
        for (int i = 0; i < size; i++) {
            //System.out.println("Produto: " + produtos_pedido.get(i).getAsJsonObject().get("produto").getAsString());
            if (produtos_pedido.get(i).getAsJsonObject().get("produto").getAsString().split("-")[0].equals("MEDEXT")) {
                continue;
            }
            int qtd_atualizada = produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("qtd_prod").getAsInt()
                    + produtos_pedido.get(i).getAsJsonObject().get("qtd_prod_ped").getAsInt();
            new CtrlLote().Update(Model.Lote.class, "qtd_prod", String.valueOf(qtd_atualizada), "objCod_objBase",
                    produtos_pedido.get(i).getAsJsonObject().get("lote_prod").getAsJsonObject().get("objCod_objBase").toString());
        }

        DAO.remover(pCodigo);
    }

    public String Recuperar_Lote_Pedido(int pCodigoPedido, String pIdLote) {
        //System.out.println("codigo pedido: " + pCodigoPedido + " pIdLote: " + pIdLote);
        String select = "SELECT U.produtos_pedido.lote_prod FROM Pedido AS U WHERE U.objCod_objBase=" + pCodigoPedido + " AND U.produtos_pedido.lote_prod.lote LIKE \"" + pIdLote + "\"";

        String retorno = this.SelecionarPassaSQL(Model.Pedido.class, select);
        //System.out.println("Recuperou Lote");
        if (retorno == null) {
            //System.out.println("Retornou nulo");
            return null;
        }
        //System.out.println("Tamanho array (RecuperarPedido_Produto_Lote_Pedido): " + retorno);
        //System.out.println("retornando: " + new JsonParser().parse(retorno).getAsJsonObject().toString());
        return new JsonParser().parse(retorno).getAsJsonObject().toString();
    }

    public List SelecionarPorCampo_List(String pDataInicio, String pDataFim, String pIdProduto, String pSolicitante, String pLote, String pLoginResp, String pCliente, String pCuidados) {
        String JPQL = "select distinct P.objcod_objbase, P.data_pedido, P.responsavel_pedido,"
                + " C.nome_cli, S.nome_sol, P.cuidados "
                + " from Pedido P, Cliente C, Solicitante S, Pedido_Produto D,"
                + " Pedido_Pedido_Produto T, Lote L"
                + " where P.solicitante_pedido_OBJCOD_OBJBASE = S.objcod_objbase "
                + " and P.cliente_pedido_objcod_objbase = C.objcod_objbase"
                + " and P.objcod_objbase = T.pedido_objcod_objbase"
                + " and T.produtos_pedido_objcod_objbase = D.objcod_objbase"
                + " and D.LOTE_PROD_OBJCOD_OBJBASE = L.objcod_objbase";
        if (!pDataInicio.equals("") && !pDataFim.equals("")) {
            JPQL += " and P.DATA_PEDIDO between ('" + pDataInicio + "') and ('" + pDataFim + "')";
        }
        if (!pIdProduto.isEmpty()) {
            JPQL += " and D.PRODUTO like '" + pIdProduto + "'";
        }
        if (!pSolicitante.isEmpty()) {
            JPQL += " and S.crm_sol like '" + pSolicitante + "'";
        }
        if (!pLote.isEmpty()) {
            JPQL += " and L.lote like '" + pLote + "'";
        }
        if (!pLoginResp.isEmpty()) {
            JPQL += " and P.RESPONSAVEL_PEDIDO like '" + pLoginResp + "'";
        }
        if (!pCliente.equals("                      ")) {
            JPQL += " and C.cartao_cli like '" + pCliente + "'";
        }
        if (!pCuidados.equals("")) {
            JPQL += " and P.cuidados like '" + pCuidados + "'";
        }

        JPQL += " union "
                + "select distinct P.objcod_objbase, P.data_pedido, P.responsavel_pedido,"
                + " C.nome_cli, S.nome_sol, P.cuidados "
                + " from Pedido P, Cliente C, Solicitante S, Pedido_Produto D,"
                + " Pedido_Pedido_Produto T, Lote L"
                + " where P.solicitante_pedido_OBJCOD_OBJBASE = S.objcod_objbase "
                + " and P.cliente_pedido_objcod_objbase = C.objcod_objbase"
                + " and P.objcod_objbase = T.pedido_objcod_objbase"
                + " and T.produtos_pedido_objcod_objbase = D.objcod_objbase";
        if (!pDataInicio.equals("") && !pDataFim.equals("")) {
            JPQL += " and P.DATA_PEDIDO between ('" + pDataInicio + "') and ('" + pDataFim + "')";
        }
        if (!pIdProduto.isEmpty()) {
            JPQL += " and D.PRODUTO like 'MEDEXT-%" + pIdProduto + "%'";
        }
        if (!pSolicitante.isEmpty()) {
            JPQL += " and S.crm_sol like '" + pSolicitante + "'";
        }
        if (!pLote.isEmpty()) {
            JPQL += " and L.lote like '" + pLote + "'";
        }
        if (!pLoginResp.isEmpty()) {
            JPQL += " and P.RESPONSAVEL_PEDIDO like '" + pLoginResp + "'";
        }
        if (!pCliente.equals("                      ")) {
            JPQL += " and C.cartao_cli like '" + pCliente + "'";
        }
        if (!pCuidados.equals("")) {
            JPQL += " and P.cuidados like '" + pCuidados + "'";
        }
        //System.out.println("pIdProduto: " + pIdProduto + " pSolicitante: " + pSolicitante + " pLote: " + pLote + " pLoginResp: " + pLoginResp + " pCliente: " + pCliente);
        //System.out.println("SQL Recuperar Pedidos: " + JPQL);
        return  FuncoesJPA.SelecionarPedidos(JPQL);
        //return new ArrayList();
    }
    
    public void SelecionarInventario(ArrayList pProdutosUsados, ArrayList pLotesNaoUsados){
        FuncoesJPA.SelecionarInventário(pProdutosUsados, pLotesNaoUsados);
    }
}
