/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Mensagens;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Heverton Pires
 */
public class Mensagem {

    public static void error(String mensagens, Component pai) {
        JOptionPane.showMessageDialog(pai, mensagens, "Falha", JOptionPane.ERROR_MESSAGE);
    }

    public static void warning(String mensagens, Component pai) {
        JOptionPane.showMessageDialog(pai, mensagens, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    public static void information(String mensagens, Component pai) {
        JOptionPane.showMessageDialog(pai, mensagens, "Informações", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean confirm(String mensagem, Component pai){
        return JOptionPane.showConfirmDialog(pai, mensagem, "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION;
    }
}
