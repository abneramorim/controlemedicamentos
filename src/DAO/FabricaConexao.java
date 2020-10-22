package DAO;

import View.Mensagens.Mensagem;
import java.awt.Frame;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FabricaConexao {
    
    private static final String STR_DRIVER = "org.gjt.mm.mysql.Driver";  // definição de qual banco será utilizado
    private static String DATABASE; // Nome do banco de dados         
    private static String IP;  // ip de conexao
    private static String STR_CON; // string de conexao com o banco de dados
    private static String USER; // Nome do usuário
    private static String PASSWORD; // senha
    private static String PORTA; // senha
    private static Connection objConexaoSin = null;
    private static Document doc;

    public FabricaConexao() {
        objConexaoSin = criarConexao();
    }
    
    private static void montaSTR_DRIVER(){
        
        SAXReader reader = new SAXReader();
        try {
            String caminhoValido = "..\\build\\classes\\META-INF\\persistence.xml"; 
            File validaPersistence = new File(caminhoValido);
            if(!validaPersistence.exists()){
                caminhoValido = ".\\build\\classes\\META-INF\\persistence.xml";
            }
            doc = reader.read(caminhoValido);
            if(doc == null){
                Mensagem.error("Erro ao conectar no banco de dados\nVerifique o arquivo de configuração", new Frame());
            }
            Element properties = doc.getRootElement().element("persistence-unit").element("properties");
            for (Object propertie : properties.elements()) {
                Element prop = (Element) propertie;
                String name = prop.attributeValue("name");
                switch (name) {
                    case "javax.persistence.jdbc.user":
                        USER = prop.attributeValue("value");
                        break;
                    case "javax.persistence.jdbc.password":
                        PASSWORD = prop.attributeValue("value");
                        break;
                    case "javax.persistence.jdbc.url":
                        DATABASE = prop.attributeValue("value").split("/")[3].split("\\?")[0];
                        IP = prop.attributeValue("value").split(":")[2].substring(2);
                        PORTA = prop.attributeValue("value").split(":")[3].split("/")[0];
                        break;
                    default:
                        break;
                }
            }
        } catch (DocumentException ex) {
            Logger.getLogger(FabricaConexao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        STR_CON = "jdbc:mysql://" + IP + ":" + PORTA + "/" + DATABASE;
        //System.out.println("STR_CON: " + STR_CON);
    }

    private static Connection criarConexao() {
        Connection objConexao = null;
        try {
            montaSTR_DRIVER();
            Class.forName(STR_DRIVER);
            objConexao = DriverManager.getConnection(STR_CON, USER, PASSWORD);
            //System.out.println("Conectado ao banco");
        } catch (ClassNotFoundException e) {
            String errorMsg = "Driver nao encontrado: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errorMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            String errorMsg = "Erro ao obter a conexao: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errorMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }
        return objConexao;
    }

    public synchronized static Connection getConexaoSingleton() {
        if (objConexaoSin == null) {
            FabricaConexao objGlobal = new FabricaConexao();
        }
        System.out.println("Conectado ao banco");
        return objConexaoSin;
    }

    public static Connection getNovaConexao() {
        Connection objConexaoNov = criarConexao();
        try {
            objConexaoNov.setAutoCommit(true);
            objConexaoNov.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException ex) {
            System.out.println("Erro ao criar conexão: "+ ex.getMessage());
        }
        return objConexaoNov;
    }

}
