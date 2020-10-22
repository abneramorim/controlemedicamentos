/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

//import Controller.CtrlCidade;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Heverton Pires
 */
public class Util {

    private static final String URL_WEBSERVICE_CORREIOS = "https://viacep.com.br/ws/";
    private static final String URL_WEBSERVICE_IBGE = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/";

    public static String inputStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int numChar;
                while ((numChar = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, numChar);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static String buscaEnderecoAPICorreios(String CEP) {
        return consumirAPI(URL_WEBSERVICE_CORREIOS + CEP + "/json/");
    }

    public static String buscaEnderecoAPIIBGE(String codIBGE) {
        return consumirAPI(URL_WEBSERVICE_IBGE + codIBGE);
    }

    public static String consumirAPI(String URL) {

        try {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.connect();
            if (connection.getResponseMessage().equals("OK")) {
                String responseJson = inputStreamToString(connection.getInputStream());
                connection.disconnect();
                return responseJson;
            } else {
                return "ERROR";
            }
        } catch (MalformedURLException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    public static String geraMD5(String pTexto) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(pTexto.getBytes(), 0, pTexto.length());

            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "erro";
    }

    public static void temaVisualizacao(Component comp) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(comp);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {

        }
    }

    public static JsonObject toJsonCidade(String cidade, String Estado) {
        JsonObject json = new JsonObject();
        JsonObject jsonEstado = new JsonObject();
        jsonEstado.addProperty("nome_est", Estado);

        json.addProperty("nome_cid", cidade);
        json.add("estado_cid", jsonEstado);
        json.add("regiao_cid", null);
        JsonParser parser = new JsonParser();
        //JsonElement element = new CtrlCidade().selecionarCidade(json.toString());
        JsonElement element = null;
        System.out.println(element.getAsJsonArray().toString());
        return element.getAsJsonArray().get(0).getAsJsonObject();
    }

    public static void addRegistroTab(DefaultTableModel tabela, String[] dados) {
        tabela.addRow(dados);
    }

    public static void setRegistroTab(DefaultTableModel tabela, String[] dados, int linha) {
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.setValueAt(dados[i], linha, i);
        }
    }

    public static String gerarCodigoEncomenda() {
        Random random = new Random();
        String codigoIdent_enc = "SL";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundo = calendar.get(Calendar.SECOND);

        codigoIdent_enc += (hora > 9 ? hora : "0" + hora) + "" + (minuto > 9 ? minuto : "0" + minuto) + ""
                + (segundo > 9 ? segundo : "0" + segundo) + "" + random.nextInt(10) + "" + random.nextInt(10) + "" + random.nextInt(10);

        codigoIdent_enc += "BR";
        System.out.println(codigoIdent_enc);
        return codigoIdent_enc;
    }
}
