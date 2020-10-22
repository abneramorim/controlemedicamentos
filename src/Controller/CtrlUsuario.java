/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.FuncoesJPA;
import DAO.PersistenciaJPA;
import Model.Usuario;
import com.google.gson.JsonParser;
import java.util.List;

/**
 *
 * @author abner
 */
public class CtrlUsuario extends ControllerBaseCtrl {

    @Override
    protected void InicializarCtrl() {
        Objeto = new Model.Usuario();
        DAO = new PersistenciaJPA(Model.Usuario.class);
    }

    public String realizaLogin(String pLogin, String pSenha) throws Exception {
        Usuario usuario = null;

        usuario = (Usuario) FuncoesJPA.Selecionar(Usuario.class, "WHERE U.login_usu LIKE \'" + pLogin + "\'").get(0);

        if (usuario != null) {
            return (usuario.getSenha_usu().equals(pSenha)) ? usuario.getNome_usu() : null;
        } else {
            throw new Exception("Usuario ou senha incorreto");
        }

    }

    public String SelecionaPorLogin(String pLogin) {
        String select = " WHERE U.login_usu LIKE \"" + pLogin + "\"";

        List Usuario = this.Selecionar(Model.Usuario.class, select);

        if (Usuario.size() == 1) {
            //System.out.println("retornando: " + new JsonParser().parse(Insumos.get(0).toString()).getAsJsonObject().toString());
            return new JsonParser().parse(Usuario.get(0).toString()).getAsJsonObject().toString();
        }

        return null;
    }

    public String SelecionarPorCampo(String pCampo, String pValor) {
        return this.SelecionarPorCampo(Model.Usuario.class, pCampo, pValor);
    }
}
