/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Usuario;
import java.util.List;

/**
 *
 * @author abner
 */
public class UsuarioDAO extends PersistenciaJPA<Model.Usuario> {

    public UsuarioDAO(Class<Usuario> persistedClass) {
        super(persistedClass);
    }
}
