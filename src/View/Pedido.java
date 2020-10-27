/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CtrlCliente;
import Controller.CtrlLote;
import Controller.CtrlPedido;
import Controller.CtrlProduto;
import Controller.CtrlSolicitante;
import View.Mensagens.Mensagem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Abner Amorim
 */
public class Pedido extends javax.swing.JDialog {

    /**
     * Creates new form Pedido
     */
    private String loginResp;
    boolean isAlterar = false;
    private JsonParser objParser;
    private JsonObject objJsonObject;
    private JsonObject pedido_produto_bkp = null;
    private DefaultTableModel ModeloTabelaMatMed;
    private int codBackup = -1;
    private String jsonInsumo = null;
    private boolean isAlterarTabInsumo;
    private JsonArray pedido_produtos_antigo;
    private JsonArray itensExcluidos;
    private DecimalFormat df;
    private boolean exibeMsgMedExt = true;

    public Pedido(java.awt.Frame parent, boolean modal, String pLoginResp, int pCodigoInicial) {
        super(parent, modal);
        initComponents();
        loginResp = pLoginResp;
        Util.Util.temaVisualizacao(this);
        df = new DecimalFormat("#.00");
        objParser = new JsonParser();
        isAlterarTabInsumo = false;
        ModeloTabelaMatMed = (DefaultTableModel) tabMedicamentos.getModel();
        itensExcluidos = new JsonArray();
        txtDescInsumo.setEditable(false);
        txtNomeCliente.setEditable(false);
        txtResponsavel.setEditable(false);
        txtData.setEditable(false);
        carregaSolicitantes();
        limpaPedido();
        limpaTabelaMatMed();
        controlaAcessoPedido(true);
        habilitaMenuInsumo(false);
        jsonToTela(new CtrlPedido().RecuperaUltimoRegistro());
        if (pCodigoInicial > 0) {
            jsonToTela(new CtrlPedido().Recuperar(pCodigoInicial));
        } else {
            jsonToTela(new CtrlPedido().RecuperaUltimoRegistro());
        }
    }

    public Pedido() {

    }

    private void carregaInsumoTabela(String pInsumo) {
        objJsonObject = objParser.parse(pInsumo).getAsJsonObject();
        //System.out.println(objJsonObject.toString());
        ModeloTabelaMatMed.addRow(new String[]{objJsonObject.get("identificador_prod").getAsString(),
            objJsonObject.get("descricao_prod").getAsString(), txtQuant.getText(), objJsonObject.get("lote_prod").getAsString(),
            objJsonObject.get("validade_prod").getAsString()});
    }

    private void controlaAcessoPedido(boolean status) {
        btnIncluir.setEnabled(status);
        btnSalvar.setEnabled(!status);
        btnExcluir.setEnabled(status);
        btnAlterar.setEnabled(status);
        btnCancelar.setEnabled(!status);
        txtCodigo.setEnabled(status);
        btnBuscarCliente.setEnabled(!status);
        txtCliente.setEditable(!status);
        txtCuidados.setEditable(!status);
        jcbSolicitante.setEnabled(!status);
        btnPrimeiroPedido.setEnabled(status);
        btnAnteriorPedido.setEnabled(status);
        btnProximoPedido.setEnabled(status);
        btnUltimoPedido.setEnabled(status);
    }

    private void controlaAcessoInsumo(boolean status) {
        btnIncluirInsumo.setEnabled(status);
        btnSalvarInsumo.setEnabled(!status);
        btnRemoverInsumo.setEnabled(status);
        btnAlterarInsumo.setEnabled(status);
        btnCancelarInsumo.setEnabled(!status);
        btnBuscarInsumo.setEnabled(!status);
        jcbLotes.setEnabled(!status);
        jckMedExt.setEnabled(!status);
        txtIdentificador.setEditable(!status);
        txtQuant.setEditable(!status);
        txtDescInsumo.setEditable(false);
        txtUnidade.setEditable(false);
    }

    private void habilitaMenuInsumo(boolean status) {
        btnIncluirInsumo.setEnabled(status);
        btnSalvarInsumo.setEnabled(status);
        btnRemoverInsumo.setEnabled(status);
        btnAlterarInsumo.setEnabled(status);
        btnCancelarInsumo.setEnabled(status);
        btnBuscarInsumo.setEnabled(status);
        jcbLotes.setEnabled(status);
        jckMedExt.setEnabled(status);
        txtIdentificador.setEditable(status);
        txtQuant.setEditable(status);
    }

    private void limpaCamposInsumo() {
        txtIdentificador.setText("");
        txtQuant.setText("");
        txtDescInsumo.setText("");
        txtUnidade.setText("");
        jcbLotes.removeAllItems();
        jckMedExt.setSelected(false);
        controlaAcessoInsumo(false);
    }

    private void limpaTabelaMatMed() {
        for (int i = ModeloTabelaMatMed.getRowCount(); i > 0; i--) {
            ModeloTabelaMatMed.removeRow(0);
        }
    }

    private void limpaPedido() {
        txtCodigo.setText("");
        txtData.setText("");
        txtResponsavel.setText("");
        txtCliente.setText("");
        txtNomeCliente.setText("");
        txtCuidados.setText("");
        carregaSolicitantes();
    }

    private void preencheDataRespPedido() {
        txtResponsavel.setText(loginResp);
        txtResponsavel.setEditable(false);

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        txtData.setText(formatador.format(data));
        txtData.setEditable(false);
    }

    private void carregaSolicitantes() {
        List solicitantes = new CtrlSolicitante().RecuperarTodos();

        if (solicitantes == null) {
            return;
        }

        jcbSolicitante.removeAllItems();
        for (int i = 0; i < solicitantes.size(); i++) {
            objJsonObject = objParser.parse(solicitantes.get(i).toString()).getAsJsonObject();
            jcbSolicitante.addItem(objJsonObject.get("crm_sol").getAsString() + " - " + objJsonObject.get("nome_sol").getAsString());
        }
    }

    private String telaToJson() {
        //TRATAR RETORNO NULO EM TODOS OS CASOS ABAIXO

        JsonObject pedido = new JsonObject();
        JsonArray objArray = new JsonArray();
        String json;

        pedido.add("data_pedido", new JsonPrimitive(txtData.getText().split("/")[2] + "-" + txtData.getText().split("/")[1] + "-" + txtData.getText().split("/")[0]));
        //System.out.println("data pedido ok");
        pedido.add("cliente_pedido", new JsonParser().parse(new CtrlCliente().RecuperarCliCompCartao(txtCliente.getText())).getAsJsonObject());
        //System.out.println("cliente pedido ok");

        //json = new JsonParser().parse(new CtrlUsuario().SelecionarPorCampo("login_usu", txtResponsavel.getText())).toString();
        //System.out.println("buscou responsavel pedido");
        pedido.add("responsavel_pedido", new JsonPrimitive(txtResponsavel.getText()));
        //System.out.println("responsavel pedido ok");
        json = new JsonParser().parse(new CtrlSolicitante().RecuperarSolCRM(jcbSolicitante.getSelectedItem().toString().split(" ")[0])).toString();
        //System.out.println("buscou solicitante pedido");
        pedido.add("solicitante_pedido", new JsonParser().parse(json).getAsJsonObject());
        pedido.add("cuidados", new JsonPrimitive(txtCuidados.getText()));
        //System.out.println("Solicitante pedido ok");
        if (!txtCodigo.getText().isEmpty()) {
            pedido.add("objCod_objBase", new JsonPrimitive(Integer.parseInt(txtCodigo.getText())));
            //System.out.println("add pedido cod");
        }

        for (int i = 0; i < tabMedicamentos.getRowCount(); i++) {

            JsonObject ped_prod = new JsonObject();
            //System.out.println("vai inserir qtd");
            ped_prod.add("qtd_prod_ped", new JsonPrimitive(ModeloTabelaMatMed.getValueAt(i, 2).toString().split(" ")[0].replace(".", "").replace(',', '.')).getAsJsonPrimitive());

            if (!ModeloTabelaMatMed.getValueAt(i, 5).toString().isEmpty()) {
                ped_prod.add("objCod_objBase", new JsonPrimitive(ModeloTabelaMatMed.getValueAt(i, 5).toString()));
            }

            if (ModeloTabelaMatMed.getValueAt(i, 0).toString().equals("MEDEXT")) {
                ped_prod.add("produto", new JsonPrimitive("MEDEXT-" + ModeloTabelaMatMed.getValueAt(i, 1).toString() + "-"
                        + ModeloTabelaMatMed.getValueAt(i, 2).toString().split(" ")[1]));
                //System.out.println("medext");
            } else {
                ped_prod.add("produto", new JsonPrimitive(ModeloTabelaMatMed.getValueAt(i, 0).toString()));
                JsonObject lote = new JsonParser().parse(new CtrlLote().RecuperarLote(ModeloTabelaMatMed.getValueAt(i, 3).toString(), ModeloTabelaMatMed.getValueAt(i, 0).toString())).getAsJsonObject();
                ped_prod.add("lote_prod", lote);
            }
            //System.out.println("vai adicionar ao array");
            //DESCONTAR QUANTIDADE DE PRODUTOS NO SALDO DO LOTE
            objArray.add(ped_prod);
            /*if (Acao != null || !Acao.isEmpty()) {
            Double qtd_total = lote.get("qtd_prod").getAsDouble();
            Double qtd_ped = ped_prod.get("qtd_prod_ped").getAsDouble();
            
            if (Acao.equals("Salvar")) {
            System.out.println("Salvar - Inserindo quantidade atual");
            System.out.println("Quantidade Antiga: " + qtd_total + " Quantidade Pedido: " + qtd_ped);
            lote.remove("qtd_prod");
            lote.add("qtd_prod", new JsonPrimitive(qtd_total - qtd_ped));
            } else if (Acao.equals("Atualizar")) {
            boolean alterado = false;
            pedido_produtos_antigo = new JsonParser().parse(new CtrlPedido().Recuperar(pedido.get("objCod_objBase").getAsInt())).getAsJsonObject().getAsJsonArray("produtos_pedido");
            for (int j = 0; j < pedido_produtos_antigo.size(); j++) {
            int qtd_antiga = pedido_produtos_antigo.get(j).getAsJsonObject().get("qtd_prod_ped").getAsInt();
            if (pedido_produtos_antigo.get(j).getAsJsonObject().get("produto").getAsJsonObject().get("identificador_prod").getAsString().equals(ModeloTabelaMatMed.getValueAt(i, 0).toString())
            && pedido_produtos_antigo.get(j).getAsJsonObject().get("lote_prod").getAsJsonObject().get("lote").getAsString().equals(ModeloTabelaMatMed.getValueAt(i, 3).toString())) {
            ped_prod.add("objCod_objBase", pedido_produtos_antigo.get(j).getAsJsonObject().get("objCod_objBase").getAsJsonPrimitive());
            
            lote.add("objCod_objBase", pedido_produtos_antigo.get(j).getAsJsonObject().get("lote_prod").getAsJsonObject().get("objCod_objBase").getAsJsonPrimitive());
            lote.remove("qtd_prod");
            lote.add("qtd_prod", new JsonPrimitive(qtd_total + qtd_antiga - qtd_ped));
            alterado = true;
            j = pedido_produtos_antigo.size() + 1;
            }
            }
            if (!alterado) {
            lote.remove("qtd_prod");
            lote.add("qtd_prod", new JsonPrimitive(qtd_total - qtd_ped));
            }
            }
            }*/

            //JsonObject produto = new JsonParser().parse(new CtrlProduto().RecuperarProdIdentificador(ModeloTabelaMatMed.getValueAt(i, 0).toString())).getAsJsonObject();
        }
        pedido.add("produtos_pedido", new Gson().toJsonTree(objArray));
        //System.out.println("String pedido: " + pedido.toString());
        return pedido.toString();
    }

    private void jsonToTela(String pJson) {
        if (pJson == null) {
            return;
        }
        JsonObject objPedido = new JsonParser().parse(pJson).getAsJsonObject();
        JsonArray produtos_pedido;

        try {
            txtCodigo.setText(objPedido.get("objCod_objBase").getAsString());
        } catch (Exception e) {
        }

        txtCliente.setText(objPedido.get("cliente_pedido").getAsJsonObject().get("cartao_cli").getAsString());
        txtNomeCliente.setText(objPedido.get("cliente_pedido").getAsJsonObject().get("nome_cli").getAsString());
        txtData.setText(dateFormat(objPedido.get("data_pedido").getAsString()));
        if(objPedido.has("cuidados")){
            txtCuidados.setText(objPedido.get("cuidados").getAsString());
        } else {
            txtCuidados.setText("");
        }
        //jcbSolicitante.removeAllItems();
        carregaSolicitantes();
        jcbSolicitante.setSelectedItem(objPedido.get("solicitante_pedido").getAsJsonObject().get("crm_sol").getAsString() + " - " + objPedido.get("solicitante_pedido").getAsJsonObject().get("nome_sol").getAsString());
        txtResponsavel.setText(objPedido.get("responsavel_pedido").getAsString());

        limpaTabelaMatMed();
        pedido_produtos_antigo = objPedido.get("produtos_pedido").getAsJsonArray();
        for (int i = 0; i < pedido_produtos_antigo.size(); i++) {
            JsonObject produto_pedido = pedido_produtos_antigo.get(i).getAsJsonObject();
            //System.out.println("Produto: " + produto_pedido.toString());
            //System.out.println("Validade: " + validade.split("-")[2] + "/"+validade.split("-")[1] + "/" + validade.split("-")[0]); 
            if (produto_pedido.get("produto").getAsString().split("-")[0].equals("MEDEXT")) {
                //System.out.println("med ext");
                ModeloTabelaMatMed.addRow(new String[]{"MEDEXT",
                    produto_pedido.get("produto").getAsString().split("-")[1],
                    df.format(Double.valueOf(produto_pedido.get("qtd_prod_ped").getAsString())).replace('.', ',') + " " + produto_pedido.get("produto").getAsString().split("-")[2],
                    "-", "-", produto_pedido.get("objCod_objBase").getAsString()});
            } else {
                String validade = produto_pedido.get("lote_prod").getAsJsonObject().get("validade_prod").getAsString();
                JsonObject produto = new JsonParser().parse(new CtrlProduto().RecuperarProdIdentificador(produto_pedido.get("produto").getAsString())).getAsJsonObject();
                ModeloTabelaMatMed.addRow(new String[]{produto_pedido.get("produto").getAsString(),
                    produto.get("descricao_prod").getAsString(),
                    df.format(Double.valueOf(produto_pedido.get("qtd_prod_ped").getAsString())).replace('.', ',') + " " + produto.get("undMedida").getAsJsonObject().get("unidade").getAsString(),
                    produto_pedido.get("lote_prod").getAsJsonObject().get("lote").getAsString(),
                    dateFormat(validade), produto_pedido.get("objCod_objBase").getAsString()});
            }
            //System.out.println("Produto: " + produto_pedido.get("produto").getAsString() + "  ---  " + produto_pedido.get("produto").getAsString().split("-")[0].equals("MEDEXT"));
        }
        //jsonAux = json
    }

    private String dateFormat(String pDate) {
        Date data = new Date();
        data.setTime(Date.parse(pDate));
        //System.out.println("data: " + data.toString());
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        //System.out.println("data: " + formatador.format(data));
        return formatador.format(data);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCabecalho = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        buscar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JFormattedTextField();
        btnPrimeiroPedido = new javax.swing.JButton();
        btnAnteriorPedido = new javax.swing.JButton();
        btnProximoPedido = new javax.swing.JButton();
        btnUltimoPedido = new javax.swing.JButton();
        jPanelRodape = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnIncluir = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        labelData = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        labelCliente = new javax.swing.JLabel();
        labelResp = new javax.swing.JLabel();
        txtResponsavel = new javax.swing.JTextField();
        labelSolicitante = new javax.swing.JLabel();
        jPanelMatMEd = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabMedicamentos = new javax.swing.JTable();
        txtIdentificador = new javax.swing.JTextField();
        lbIdentificador = new javax.swing.JLabel();
        labelQuant = new javax.swing.JLabel();
        btnSalvarInsumo = new javax.swing.JButton();
        btnRemoverInsumo = new javax.swing.JButton();
        txtDescInsumo = new javax.swing.JTextField();
        btnBuscarInsumo = new javax.swing.JButton();
        btnCancelarInsumo = new javax.swing.JButton();
        btnAlterarInsumo = new javax.swing.JButton();
        btnIncluirInsumo = new javax.swing.JButton();
        txtQuant = new javax.swing.JFormattedTextField();
        jlLote = new javax.swing.JLabel();
        jcbLotes = new javax.swing.JComboBox<>();
        jckMedExt = new javax.swing.JCheckBox();
        txtUnidade = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lbIdentificador1 = new javax.swing.JLabel();
        labelQuant1 = new javax.swing.JLabel();
        jcbSolicitante = new javax.swing.JComboBox<>();
        btnBuscarCliente = new javax.swing.JButton();
        txtNomeCliente = new javax.swing.JTextField();
        txtCliente = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCuidados = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Atendimento");

        jPanelCabecalho.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/logo_mini.png"))); // NOI18N

        jLabel4.setText("Codigo:");

        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/search.png"))); // NOI18N
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        txtCodigo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        btnPrimeiroPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/first_mini.png"))); // NOI18N
        btnPrimeiroPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeiroPedidoActionPerformed(evt);
            }
        });

        btnAnteriorPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/previous_mini.png"))); // NOI18N
        btnAnteriorPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorPedidoActionPerformed(evt);
            }
        });

        btnProximoPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/next_mini.png"))); // NOI18N
        btnProximoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProximoPedidoActionPerformed(evt);
            }
        });

        btnUltimoPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/last_mini.png"))); // NOI18N
        btnUltimoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCabecalhoLayout = new javax.swing.GroupLayout(jPanelCabecalho);
        jPanelCabecalho.setLayout(jPanelCabecalhoLayout);
        jPanelCabecalhoLayout.setHorizontalGroup(
            jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCabecalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrimeiroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelCabecalhoLayout.createSequentialGroup()
                        .addComponent(btnAnteriorPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProximoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnUltimoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCabecalhoLayout.createSequentialGroup()
                        .addComponent(txtCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelCabecalhoLayout.setVerticalGroup(
            jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCabecalhoLayout.createSequentialGroup()
                .addGroup(jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnUltimoPedido)
                    .addComponent(btnProximoPedido)
                    .addGroup(jPanelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAnteriorPedido)
                        .addComponent(btnPrimeiroPedido)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCabecalhoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(23, 23, 23))
        );

        jPanelCabecalhoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAnteriorPedido, btnPrimeiroPedido, btnProximoPedido, btnUltimoPedido, buscar, jLabel4, txtCodigo});

        jPanelRodape.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/check.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/create.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/trash.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/close.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/add.png"))); // NOI18N
        btnIncluir.setText("Incluir");
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/back.png"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRodapeLayout = new javax.swing.GroupLayout(jPanelRodape);
        jPanelRodape.setLayout(jPanelRodapeLayout);
        jPanelRodapeLayout.setHorizontalGroup(
            jPanelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRodapeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVoltar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelRodapeLayout.setVerticalGroup(
            jPanelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRodapeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnIncluir)
                    .addComponent(btnSalvar)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir)
                    .addComponent(btnCancelar)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelData.setText("Data:");

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelCliente.setText("Cliente:");

        labelResp.setText("Resp:");

        labelSolicitante.setText("Solicitante:");

        jPanelMatMEd.setBorder(javax.swing.BorderFactory.createTitledBorder("Insumos"));

        tabMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ident.", "Descrição Insumo", "Quant.", "Lote", "Validade", "Oculta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabMedicamentos.getTableHeader().setReorderingAllowed(false);
        tabMedicamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMedicamentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabMedicamentos);
        if (tabMedicamentos.getColumnModel().getColumnCount() > 0) {
            tabMedicamentos.getColumnModel().getColumn(0).setResizable(false);
            tabMedicamentos.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabMedicamentos.getColumnModel().getColumn(1).setResizable(false);
            tabMedicamentos.getColumnModel().getColumn(1).setPreferredWidth(350);
            tabMedicamentos.getColumnModel().getColumn(2).setResizable(false);
            tabMedicamentos.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabMedicamentos.getColumnModel().getColumn(3).setResizable(false);
            tabMedicamentos.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabMedicamentos.getColumnModel().getColumn(4).setResizable(false);
            tabMedicamentos.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabMedicamentos.getColumnModel().getColumn(5).setMinWidth(0);
            tabMedicamentos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tabMedicamentos.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        txtIdentificador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdentificadorKeyPressed(evt);
            }
        });

        lbIdentificador.setText("Identificador:");

        labelQuant.setText("Quantidade:");

        btnSalvarInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/check-mini.png"))); // NOI18N
        btnSalvarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarInsumoActionPerformed(evt);
            }
        });

        btnRemoverInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/trash.png"))); // NOI18N
        btnRemoverInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverInsumoActionPerformed(evt);
            }
        });

        txtDescInsumo.setBackground(new java.awt.Color(240, 240, 240));
        txtDescInsumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescInsumoKeyReleased(evt);
            }
        });

        btnBuscarInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/search.png"))); // NOI18N
        btnBuscarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarInsumoActionPerformed(evt);
            }
        });

        btnCancelarInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/close-mini.png"))); // NOI18N
        btnCancelarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarInsumoActionPerformed(evt);
            }
        });

        btnAlterarInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/create-mini.png"))); // NOI18N
        btnAlterarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarInsumoActionPerformed(evt);
            }
        });

        btnIncluirInsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/add-mini.png"))); // NOI18N
        btnIncluirInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirInsumoActionPerformed(evt);
            }
        });

        txtQuant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtQuant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantFocusLost(evt);
            }
        });
        txtQuant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuantKeyPressed(evt);
            }
        });

        jlLote.setText("Lote:");

        jckMedExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jckMedExtActionPerformed(evt);
            }
        });

        txtUnidade.setBackground(new java.awt.Color(240, 240, 240));
        txtUnidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnidadeKeyPressed(evt);
            }
        });

        jLabel1.setText("Medicamento Externo");

        lbIdentificador1.setText("Descrição:");

        labelQuant1.setText("Unidade:");

        javax.swing.GroupLayout jPanelMatMEdLayout = new javax.swing.GroupLayout(jPanelMatMEd);
        jPanelMatMEd.setLayout(jPanelMatMEdLayout);
        jPanelMatMEdLayout.setHorizontalGroup(
            jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanelMatMEdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMatMEdLayout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(btnIncluirInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelMatMEdLayout.createSequentialGroup()
                        .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMatMEdLayout.createSequentialGroup()
                                .addComponent(jckMedExt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlLote)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbLotes, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelQuant)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtQuant)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelQuant1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelMatMEdLayout.createSequentialGroup()
                                .addComponent(lbIdentificador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbIdentificador1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescInsumo)))
                        .addContainerGap())))
        );
        jPanelMatMEdLayout.setVerticalGroup(
            jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMatMEdLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdentificador))
                    .addComponent(btnBuscarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbIdentificador1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDescInsumo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelQuant1))
                    .addComponent(jckMedExt)
                    .addComponent(jLabel1)
                    .addComponent(jlLote)
                    .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbLotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelQuant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelMatMEdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoverInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIncluirInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelMatMEdLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAlterarInsumo, btnBuscarInsumo, btnCancelarInsumo, btnIncluirInsumo, btnRemoverInsumo, btnSalvarInsumo, jLabel1, jcbLotes, jckMedExt, jlLote, labelQuant, labelQuant1, lbIdentificador, txtDescInsumo, txtIdentificador, txtQuant, txtUnidade});

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/search.png"))); // NOI18N
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        try {
            txtCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("# ### #### ###### ## #")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCliente.setToolTipText("");
        txtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClienteKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuidados"));

        txtCuidados.setColumns(20);
        txtCuidados.setFont(new java.awt.Font("Trebuchet MS", 0, 11)); // NOI18N
        txtCuidados.setRows(5);
        txtCuidados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCuidadosKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtCuidados);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelRodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelMatMEd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelData)
                    .addComponent(labelCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeCliente))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelResp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelSolicitante)
                        .addGap(2, 2, 2)
                        .addComponent(jcbSolicitante, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelResp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelData))))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelMatMEd, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanelRodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBuscarCliente, jcbSolicitante, labelCliente, labelData, labelResp, labelSolicitante, txtCliente, txtData, txtNomeCliente, txtResponsavel});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        if (txtCodigo.getText().isEmpty()) {
            Mensagem.information("Informe um código a ser pesquisado !!!", this);
            return;
        }

        try {
            int codigoProd = Integer.parseInt(txtCodigo.getText());
            String json;
            if (codigoProd > 0) { //Pesquisa direta
                json = new CtrlPedido().Recuperar(codigoProd);

                if (json.isEmpty()) {
                    if (Mensagem.confirm("Pedido não encontrado\nDeseja abrir a tela de pesquisa?", this)) {
                        Pesquisa_Atendimento pesq = new Pesquisa_Atendimento(new javax.swing.JFrame(), true, "REQUER RETORNO", loginResp);
                        pesq.setVisible(true);
                        txtCodigo.setText(String.valueOf(pesq.getCodigo()));
                        buscarActionPerformed(new java.awt.event.ActionEvent(this, 0, "Preencher"));
                    }
                    return;
                } else {
                    jsonToTela(json);
                    controlaAcessoPedido(true);
                }
            } else { //Abre a tela de pesquisa
                jsonToTela(new CtrlPedido().RecuperaUltimoRegistro());
            }
        } catch (NumberFormatException ex) {
            txtCodigo.requestFocus();

        } catch (Exception ex) {
            Mensagem.warning(ex.getMessage(), this);
        } finally {
            txtCodigo.selectAll();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarActionPerformed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (evt.getKeyCode() == 10) {
            buscarActionPerformed(new java.awt.event.ActionEvent(this, WIDTH, "Salvar"));
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        //System.out.println("Entrou no salvar");
        if (txtNomeCliente.getText().isEmpty() || (txtDescInsumo.getText().isEmpty() && tabMedicamentos.getRowCount() == 0)) {
            Mensagem.information("Informe Cliente e Medicamentos para gravar !!!", this);
            return;
        }
        try {
            int codigoPed = new CtrlPedido().Salvar(telaToJson(), pedido_produtos_antigo, itensExcluidos, isAlterar);
            //System.out.println(telaToJson());
            //System.out.println("Passou do salvar");
            if (isAlterar) {
                Mensagem.information("Pedido atualizado com sucesso", this);
            } else {
                txtCodigo.setText(String.valueOf(codigoPed));
                Mensagem.information("Pedido cadastrado com sucesso", this);
            }
            isAlterar = false;
            controlaAcessoPedido(true);
            habilitaMenuInsumo(false);
            pedido_produtos_antigo = new JsonParser().parse(new CtrlPedido().Recuperar(Integer.parseInt(txtCodigo.getText()))).getAsJsonObject().getAsJsonArray("produtos_pedido");
            buscarActionPerformed(new ActionEvent(this, 0, "Atualiza"));
            //System.out.println("tamanho array: " + pedido_produtos_antigo.size());
        } catch (Exception ex) {
            Mensagem.error("Não foi possivel cadastrar o Pedido", this);
            System.out.println(ex.getMessage());
        } finally {
            txtCodigo.selectAll();
        }
        //System.out.println(telaToJson());
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        controlaAcessoPedido(false);
        controlaAcessoInsumo(true);
        isAlterar = true;
        itensExcluidos = new JsonArray();
        if (!txtCodigo.getText().isEmpty()) {
            codBackup = Integer.parseInt(txtCodigo.getText());
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (!Mensagem.confirm("Deseja excluir este pedido?", this)) {
            return;
        }
        int codigo = txtCodigo.getText().isEmpty() ? 0 : Integer.parseInt(txtCodigo.getText());
        if (codigo > 0) {
            new CtrlPedido().Deletar(codigo);
            Mensagem.information("Pedido excluido com sucesso", this);
            limpaCamposInsumo();
            limpaPedido();
            limpaTabelaMatMed();
            //limpaCamposInsumo();
            jsonToTela(new CtrlPedido().RecuperaRegistroAnterior(codigo));
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if (!Mensagem.confirm("Deseja descartar as alterações?", this)) {
            return;
        }
        limpaCamposInsumo();
        isAlterarTabInsumo = false;
        isAlterar = false;
        habilitaMenuInsumo(false);
        //btnCancelarInsumoActionPerformed(new java.awt.event.ActionEvent(this, 0, "Cancelar"));
        controlaAcessoPedido(true);
        if (codBackup > 0) {
            jsonToTela(new CtrlPedido().Recuperar(codBackup));
            return;
        }
        if (!isAlterar) {
            limpaPedido();
            limpaTabelaMatMed();
            codBackup = -1;
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        controlaAcessoPedido(false);
        if (!txtCodigo.getText().isEmpty()) {
            codBackup = Integer.parseInt(txtCodigo.getText());
        }
        limpaTabelaMatMed();
        limpaCamposInsumo();
        limpaPedido();
        preencheDataRespPedido();
        controlaAcessoInsumo(true);
        txtCliente.requestFocus();
        isAlterar = false;
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnRemoverInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverInsumoActionPerformed
        if (txtIdentificador.getText().isEmpty()) {
            return;
        }
        if (!isAlterar) {
            return;
        }
        //JsonObject itemExc = new JsonObject();
        //itemExc.add("lote", new JsonPrimitive(ModeloTabelaMatMed.getValueAt(tabMedicamentos.getSelectedRow(), 3).toString()).getAsJsonPrimitive());
        //itemExc.add("qtd", new JsonPrimitive(ModeloTabelaMatMed.getValueAt(tabMedicamentos.getSelectedRow(), 2).toString().replace(',', '.')).getAsJsonPrimitive());
        int linhaAtiva = tabMedicamentos.getSelectedRow();
        if(!ModeloTabelaMatMed.getValueAt(linhaAtiva, 5).toString().isEmpty()){
            itensExcluidos.add(pedido_produtos_antigo.get(linhaAtiva));
            pedido_produtos_antigo.remove(linhaAtiva);
        }
        //System.out.println("Pedido_Produto excluido e acrescentado ao itens excluidos, tam: " + itensExcluidos.size());
        ModeloTabelaMatMed.removeRow(linhaAtiva);
        limpaCamposInsumo();
        controlaAcessoInsumo(true);
    }//GEN-LAST:event_btnRemoverInsumoActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        CtrlCliente CTRL_Cliente = new CtrlCliente();
        if (txtCliente.getText().length() == 22) {
            String nomeCli = CTRL_Cliente.RecuperarCliCartao(txtCliente.getText());
            if (nomeCli == null) {
                if (Mensagem.confirm("Cliente não encontrado\nDeseja abrir a tela de pesquisa?", this)) {
                        Pesquisa pesq = new Pesquisa(new javax.swing.JFrame(), true, "CLIENTE", new String());
                        pesq.setVisible(true);
                        //System.out.println("Identificador retornado: " + pesq.getResultPesq());
                        txtCliente.setText(pesq.getResultPesq());
                        btnBuscarClienteActionPerformed(new java.awt.event.ActionEvent(this, 0, "Preencher"));
                    }
                return;
            }
            if (!nomeCli.isEmpty()) {
                txtNomeCliente.setText(nomeCli);
            }
        } else {
            Mensagem.warning("Preencha o campo Cliente com o código da Carteira de 17 digitos !!!", this);
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnBuscarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarInsumoActionPerformed
        if(txtIdentificador.getText().isEmpty()){
            return;
        }
        String insumo = new CtrlProduto().RecuperarProdIdentificador(txtIdentificador.getText());
        JsonArray lotes;
        if (insumo == null) {
            if(Mensagem.confirm("Insumo não encontrado\nDeseja abrir a tela de pesquisa?", this)){
                String retorno = new String();
                Pesquisa pesq = new Pesquisa(new javax.swing.JFrame(), true, "PRODUTO", retorno);
                pesq.setVisible(true);
                //System.out.println("Identificador retornado: " + pesq.getResultPesq());
                txtIdentificador.setText(pesq.getResultPesq());
                btnBuscarInsumoActionPerformed(new java.awt.event.ActionEvent(this, 0, "Preencher"));
            }
            return;
        }

        jsonInsumo = objParser.parse(insumo).toString();
        //System.out.println("json retornado: " + jsonInsumo);

        if (jsonInsumo != null) {
            objJsonObject = objParser.parse(jsonInsumo).getAsJsonObject();
            txtDescInsumo.setText(objJsonObject.get("descricao_prod").getAsString());
            txtUnidade.setText(objJsonObject.get("undMedida").getAsJsonObject().get("unidade").getAsString());

            lotes = objJsonObject.get("lotes_prod").getAsJsonArray();

            jcbLotes.removeAllItems();
            //JsonObject fabricante = lotes.get(0).getAsJsonObject().get("fabricante_prod").getAsJsonObject();
            for (int i = 0; i < lotes.size(); i++) {
                if (lotes.get(i).getAsJsonObject().get("qtd_prod").getAsInt() > 1) {
                    jcbLotes.addItem(lotes.get(i).getAsJsonObject().get("lote").getAsString());
                }
            }
            //jsonInsumo = objJsonObject.toString();
            txtQuant.requestFocus();
            txtIdentificador.setText(txtIdentificador.getText().toUpperCase());
        } else {
            Mensagem.information("Erro ao recuperar o Insumo\nVerifique o Identificador e tente novamente !!!", this);
            txtIdentificador.selectAll();
        }
    }//GEN-LAST:event_btnBuscarInsumoActionPerformed

    private void txtIdentificadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdentificadorKeyPressed
        if (evt.getKeyCode() == 10) {
            btnBuscarInsumoActionPerformed(new java.awt.event.ActionEvent(this, 0, "Preencher"));
        }
    }//GEN-LAST:event_txtIdentificadorKeyPressed

    private void btnSalvarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarInsumoActionPerformed
        //System.out.println("Lote: " + jcbLotes.getSelectedItem().toString() + " Identificador: " + txtIdentificador.getText());
        //System.out.println("Retorno: " + new JsonParser().parse(new CtrlLote().RecuperarLote(jcbLotes.getSelectedItem().toString(), txtIdentificador.getText())).getAsJsonObject().toString());
        if (txtQuant.getText().isEmpty()) {
            Mensagem.error("Informe a quantidade para prosseguir !!!", this);
            return;
        }

        if (jckMedExt.isSelected()) {
            if(txtDescInsumo.getText().isEmpty() || txtUnidade.getText().isEmpty()){
                Mensagem.error("Os campos Descrição e Unidade de Medida\n devem estar preenchidos para prosseguir !!!", this);
                return;
            }
            if (!isAlterarTabInsumo) {
                ModeloTabelaMatMed.addRow(new String[]{txtIdentificador.getText(),
                    txtDescInsumo.getText(), txtQuant.getText() + " " + txtUnidade.getText(), "-",
                    "-", ""});
            } else if (isAlterarTabInsumo) {
                ModeloTabelaMatMed.setValueAt(txtQuant.getText() + " " + txtUnidade.getText(), tabMedicamentos.getSelectedRow(), 2);
            }
            isAlterarTabInsumo = false;
            txtDescInsumo.requestFocus();
            limpaCamposInsumo();
            controlaAcessoInsumo(true);
            btnSalvar.setEnabled(true);
            pedido_produto_bkp = new JsonObject();
            return;
        }

        Double qtd_total_lote = new JsonParser().parse(new CtrlLote().RecuperarLote(jcbLotes.getSelectedItem().toString(), txtIdentificador.getText())).getAsJsonObject().get("qtd_prod").getAsDouble();

        if (Double.valueOf(txtQuant.getText().replace(".", "").replace(',', '.')) > qtd_total_lote && !isAlterarTabInsumo) {
            Mensagem.error("Quantidade Insuficiente em Estoque !!!\nInforme um valor menor ou igual a " + qtd_total_lote, this);
            return;
        }

        //System.out.println("qtd_total_lote: " + qtd_total_lote + " qtd_ped_prod_ant: " + pedido_produtos_antigo.get(tabMedicamentos.getSelectedRow()).getAsJsonObject().get("qtd_prod_ped").getAsDouble() + " txtQuant: " + Double.valueOf(txtQuant.getText().replace(',', '.')));
        //System.out.println("isAlterarTabInsumo: " + isAlterarTabInsumo + " isAlterar: " + isAlterar + " ");
        if (isAlterarTabInsumo && isAlterar && (Double.valueOf(txtQuant.getText().replace(',', '.')) > (qtd_total_lote + pedido_produtos_antigo.get(tabMedicamentos.getSelectedRow()).getAsJsonObject().get("qtd_prod_ped").getAsDouble()))) {
            Mensagem.error("Quantidade Insuficiente em Estoque !!!\nInforme um valor menor ou igual a " + (qtd_total_lote + pedido_produtos_antigo.get(tabMedicamentos.getSelectedRow()).getAsJsonObject().get("qtd_prod_ped").getAsDouble()), this);
            txtQuant.setText(String.valueOf(qtd_total_lote + pedido_produtos_antigo.get(tabMedicamentos.getSelectedRow()).getAsJsonObject().get("qtd_prod_ped").getAsDouble()).replace('.', ','));
            return;
        }

        boolean isOnTable = false;
        for (int i = 0; i < tabMedicamentos.getRowCount(); i++) {
            if (txtIdentificador.getText().equals(ModeloTabelaMatMed.getValueAt(i, 0)) && jcbLotes.getSelectedItem().toString().equals(ModeloTabelaMatMed.getValueAt(i, 3))) {
                isOnTable = true;
            }
        }

        if (!isAlterarTabInsumo && isOnTable) {
            Mensagem.information("Produto já está na tabela !!!", this);
            return;
        }

        if (isAlterarTabInsumo) {
            ModeloTabelaMatMed.setValueAt(txtQuant.getText() + " " + txtUnidade.getText(), tabMedicamentos.getSelectedRow(), 2);
        } else {
            String validade = new JsonParser().parse(new CtrlLote().RecuperarLote(jcbLotes.getSelectedItem().toString(), txtIdentificador.getText())).getAsJsonObject().get("validade_prod").getAsString();
            ModeloTabelaMatMed.addRow(new String[]{txtIdentificador.getText(),
                txtDescInsumo.getText(), txtQuant.getText() + " " + txtUnidade.getText(), jcbLotes.getSelectedItem().toString(),
                dateFormat(validade), ""});
        }

        // LIMPANDO OS CAMPOS JÁ INSERIDOS NA TABELA
        isAlterarTabInsumo = false;
        txtDescInsumo.requestFocus();
        limpaCamposInsumo();
        controlaAcessoInsumo(true);
        btnSalvar.setEnabled(true);
        pedido_produto_bkp = new JsonObject();
        tabMedicamentos.setEnabled(true);
    }//GEN-LAST:event_btnSalvarInsumoActionPerformed

    private void tabMedicamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMedicamentosMouseClicked
        if (!btnAlterar.isEnabled() && btnIncluirInsumo.isEnabled()) {
            //isAlterarTabInsumo = true;
            //System.out.println("isAlterarTabInsumo true");

            String txtIdentificadorProd = ModeloTabelaMatMed.getValueAt(tabMedicamentos.getSelectedRow(), 0).toString();
            if (txtIdentificadorProd.equals("MEDEXT")) {
                jckMedExt.setSelected(true);
                jcbLotes.removeAllItems();
                jcbLotes.addItem("MEDEXT");
            } else {
                jckMedExt.setSelected(false);
                JsonObject objProduto = new JsonParser().parse(new CtrlProduto().RecuperarProdIdentificador(txtIdentificadorProd)).getAsJsonObject();

                JsonArray lotes = objProduto.get("lotes_prod").getAsJsonArray();
                jcbLotes.removeAllItems();
                //JsonObject fabricante = lotes.get(0).getAsJsonObject().get("fabricante_prod").getAsJsonObject();
                for (int i = 0; i < lotes.size(); i++) {
                    if (lotes.get(i).getAsJsonObject().get("qtd_prod").getAsInt() > 1) {
                        jcbLotes.addItem(lotes.get(i).getAsJsonObject().get("lote").getAsString());
                    }
                }
                jcbLotes.setSelectedItem(ModeloTabelaMatMed.getValueAt(tabMedicamentos.getSelectedRow(), 3).toString());
            }
            txtUnidade.setText(ModeloTabelaMatMed.getValueAt(tabMedicamentos.getSelectedRow(), 2).toString().split(" ")[1]);
            //txtUnidade.setText(objProduto.get("undMedida").getAsJsonObject().get("unidade").getAsString());
            //new JsonParser().parse(new CtrlProduto().RecuperarProdIdentificador(txtIdentificadorProd)).getAsJsonObject().get("undMedida").getAsJsonObject().get("unidade").getAsString()
            txtIdentificador.setText(txtIdentificadorProd);
            txtDescInsumo.setText(ModeloTabelaMatMed.getValueAt(tabMedicamentos.getSelectedRow(), 1).toString());
            txtQuant.setText(ModeloTabelaMatMed.getValueAt(tabMedicamentos.getSelectedRow(), 2).toString().split(" ")[0]);
        }
    }//GEN-LAST:event_tabMedicamentosMouseClicked

    private void btnPrimeiroPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroPedidoActionPerformed
        String json = new CtrlPedido().RecuperaPrimeiroRegistro();

        if (json != null) {
            jsonToTela(json);
        }
    }//GEN-LAST:event_btnPrimeiroPedidoActionPerformed

    private void btnAnteriorPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorPedidoActionPerformed
        //System.out.println("Codigo: " + new JsonParser().parse(new CtrlProduto().RecuperaPrimeiroRegistro()).getAsJsonObject().get("objCod_objBase").getAsString().equals(txtCodigo.getText()));
        if (txtCodigo.getText().isEmpty()) {
            return;
        }
        if (txtCodigo.getText().equals(new JsonParser().parse(new CtrlPedido().RecuperaPrimeiroRegistro()).getAsJsonObject().get("objCod_objBase").getAsString())) {
            Mensagem.information("Este é o primeiro atendimento !!!", this);
            return;
        }

        String json = new CtrlPedido().RecuperaRegistroAnterior(Integer.parseInt(txtCodigo.getText()));

        if (json != null) {
            jsonToTela(json);
        }
    }//GEN-LAST:event_btnAnteriorPedidoActionPerformed

    private void btnProximoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoPedidoActionPerformed
        if (txtCodigo.getText().isEmpty()) {
            return;
        }
        if (txtCodigo.getText().equals(new JsonParser().parse(new CtrlPedido().RecuperaUltimoRegistro()).getAsJsonObject().get("objCod_objBase").getAsString())) {
            Mensagem.information("Este é o último atendimento !!!", this);
            return;
        }

        String json = new CtrlPedido().RecuperaProximoRegistro(Integer.parseInt(txtCodigo.getText()));

        if (json != null) {
            jsonToTela(json);
        }
    }//GEN-LAST:event_btnProximoPedidoActionPerformed

    private void btnUltimoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoPedidoActionPerformed
        String json = new CtrlPedido().RecuperaUltimoRegistro();

        if (json != null) {
            jsonToTela(json);
        }
    }//GEN-LAST:event_btnUltimoPedidoActionPerformed

    private void btnCancelarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarInsumoActionPerformed
        if (!evt.getActionCommand().equals("Cancelar")) {
            if (!Mensagem.confirm("Deseja descartar as alterações?", this)) {
                return;
            }
        }
        /*if (pedido_produto_bkp != null) {
        System.out.println("Cancelar");
        txtIdentificador.setText(pedido_produto_bkp.get("produto").getAsString());
        txtQuant.setText(pedido_produto_bkp.get("qtd_prod_ped").getAsString());
        jcbLotes.removeAllItems();
        jcbLotes.addItem(pedido_produto_bkp.get("lote").getAsString());
        
        pedido_produto_bkp = null;
        } else {*/
        //}
        limpaCamposInsumo();
        controlaAcessoInsumo(true);
        btnSalvar.setEnabled(true);
        isAlterarTabInsumo = false;
        tabMedicamentos.setEnabled(true);
        //System.out.println("isAlterarTabInsumo false");
    }//GEN-LAST:event_btnCancelarInsumoActionPerformed

    private void btnAlterarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarInsumoActionPerformed
        if (txtIdentificador.getText().isEmpty()) {
            return;
        }
        isAlterarTabInsumo = true;
        //System.out.println("isAlterarTabInsumo true");
        pedido_produto_bkp = new JsonObject();
        pedido_produto_bkp.add("produto", new JsonParser().parse(txtIdentificador.getText()).getAsJsonPrimitive());
        pedido_produto_bkp.add("qtd_prod_ped", new JsonParser().parse(txtQuant.getText().replace(',', '.')).getAsJsonPrimitive());
        txtQuant.selectAll();
        if (!txtIdentificador.getText().equals("MEDEXT")) {
            pedido_produto_bkp.add("lote", new JsonParser().parse(jcbLotes.getSelectedItem().toString()).getAsJsonPrimitive());
        }

        controlaAcessoInsumo(false);
        if (txtIdentificador.getText().equals("MEDEXT")) {
            txtDescInsumo.setEditable(true);
            txtUnidade.setEditable(true);
        }
        txtIdentificador.setEditable(false);
        txtQuant.requestFocus();
        btnSalvar.setEnabled(false);
        tabMedicamentos.setEnabled(false);
        jckMedExt.setEnabled(false);
    }//GEN-LAST:event_btnAlterarInsumoActionPerformed

    private void btnIncluirInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirInsumoActionPerformed
        txtIdentificador.requestFocus();
        limpaCamposInsumo();
        isAlterarTabInsumo = false;
        btnSalvar.setEnabled(false);
    }//GEN-LAST:event_btnIncluirInsumoActionPerformed

    private void txtQuantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantKeyPressed
        /*if (evt.getKeyCode() == 10) {
        btnSalvarInsumoActionPerformed(new java.awt.event.ActionEvent(this, WIDTH, "Buscar"));
        }*/
    }//GEN-LAST:event_txtQuantKeyPressed

    private void txtQuantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantFocusLost
        String texto = txtQuant.getText();
        if (texto == null || texto.isEmpty()) {
            return;
        }

        int size = texto.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(texto.charAt(i)) && texto.charAt(i) != '.' && texto.charAt(i) != ',') {
                txtQuant.requestFocus();
                txtQuant.selectAll();
                Mensagem.warning("Informe apenas números!!!", this);//System.out.println("Digito");
                return;
            }
        }
    }//GEN-LAST:event_txtQuantFocusLost

    private void txtClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyPressed
        if (!txtCliente.isEditable()) {
            return;
        }

        if ((evt.getKeyCode() >= 33 && evt.getKeyCode() <= 40) || evt.getKeyCode() == 127 || evt.getKeyCode() == 8 || evt.getKeyCode() == 10) {
            return;
        }

        /*if (evt.getKeyCode() == 10) {
        btnBuscarClienteActionPerformed(new java.awt.event.ActionEvent(this, WIDTH, "Buscar"));
        }*/
        if (!Character.isDigit(evt.getKeyChar())) {
            String texto = txtCliente.getText();
            int tamanho = texto.length();
            txtCliente.setText(texto.substring(0, tamanho));
            Mensagem.warning("Informe apenas números!!!", this);//System.out.println("Digito");
            return;
        }
    }//GEN-LAST:event_txtClienteKeyPressed

    private void jckMedExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jckMedExtActionPerformed
        if (exibeMsgMedExt) {
            Mensagem.information("Ao marcar essa caixa de seleção, você indica que se trata de um medicamento Externo,\n e que não será retirado do estoque.", this);
            exibeMsgMedExt = false;
        }
        if (jckMedExt.isSelected()) {
            txtIdentificador.setEditable(false);
            txtIdentificador.setText("MEDEXT");
            txtQuant.setEnabled(true);
            txtUnidade.setText("");
            jcbLotes.removeAll();
            jcbLotes.addItem("MEDEXT");
            txtDescInsumo.setEditable(true);
            txtDescInsumo.requestFocus();
            txtUnidade.setEditable(true);
        } else if (!jckMedExt.isSelected()) {
            limpaCamposInsumo();
            controlaAcessoInsumo(false);
            txtIdentificador.requestFocus();
        }
    }//GEN-LAST:event_jckMedExtActionPerformed

    private void txtUnidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadeKeyPressed
        if (txtUnidade.getText().length() >= 4) {
            txtUnidade.setText(txtUnidade.getText().substring(0, 3));
        }
    }//GEN-LAST:event_txtUnidadeKeyPressed

    private void txtDescInsumoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescInsumoKeyReleased
        if(evt.getKeyChar() == '-')    
            txtDescInsumo.setText(txtDescInsumo.getText().replace("-", ""));
    }//GEN-LAST:event_txtDescInsumoKeyReleased

    private void txtCuidadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCuidadosKeyPressed
        if (txtCuidados.getText().length() >= 255) {
            txtCuidados.setText(txtCuidados.getText().substring(0, 254));
        }
    }//GEN-LAST:event_txtCuidadosKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            //Pedido dialog = new Pedido(new javax.swing.JFrame(), true, );
            Pedido dialog = new Pedido(new javax.swing.JFrame(), true, "abner", -1);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAlterarInsumo;
    private javax.swing.JButton btnAnteriorPedido;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarInsumo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarInsumo;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnIncluirInsumo;
    private javax.swing.JButton btnPrimeiroPedido;
    private javax.swing.JButton btnProximoPedido;
    private javax.swing.JButton btnRemoverInsumo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvarInsumo;
    private javax.swing.JButton btnUltimoPedido;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton buscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCabecalho;
    private javax.swing.JPanel jPanelMatMEd;
    private javax.swing.JPanel jPanelRodape;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jcbLotes;
    private javax.swing.JComboBox<String> jcbSolicitante;
    private javax.swing.JCheckBox jckMedExt;
    private javax.swing.JLabel jlLote;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelQuant;
    private javax.swing.JLabel labelQuant1;
    private javax.swing.JLabel labelResp;
    private javax.swing.JLabel labelSolicitante;
    private javax.swing.JLabel lbIdentificador;
    private javax.swing.JLabel lbIdentificador1;
    private javax.swing.JTable tabMedicamentos;
    private javax.swing.JFormattedTextField txtCliente;
    private javax.swing.JFormattedTextField txtCodigo;
    private javax.swing.JTextArea txtCuidados;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtDescInsumo;
    private javax.swing.JTextField txtIdentificador;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JFormattedTextField txtQuant;
    private javax.swing.JTextField txtResponsavel;
    private javax.swing.JTextField txtUnidade;
    // End of variables declaration//GEN-END:variables
}
