/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CtrlCliente;
import Controller.CtrlFabricante;
import Controller.CtrlProduto;
import Controller.CtrlSolicitante;
import Controller.CtrlUsuario;
import View.Mensagens.Mensagem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Heverton Pires
 */
public class Pesquisa extends javax.swing.JDialog {

    private DefaultTableModel ModeloTab;
    private final String[] COL_CLIENTE = {"Código", "Cartão", "Nome"};
    private final int[] WID_CLIENTE = {120, 395, 395};
    private final String[] COL_FABRICANTE = {"Código", "CNPJ", "Razão Social"};
    private final int[] WID_FABRICANTE = {120, 395, 395};
    private final String[] COL_PRODUTO = {"Codigo", "Identificador", "Descrição"};
    private final int[] WID_PRODUTO = {120, 200, 590};
    private final String[] COL_SOLICITANTE = {"Codigo", "CRM", "Nome"};
    private final int[] WID_SOLICITANTE = {120, 395, 395};
    private final String[] COL_USUARIO = {"Codigo", "Login", "Nome"};
    private final int[] WID_USUARIO = {120, 395, 395};
    private final String[] COL_LOTE = {"Codigo", "Lote", "Produto", "Validade", "Qtd.", "Fabricante", "Nota Fiscal"};
    private final String[] COL_ATENDIMENTO_USUARIO = {"Codigo", "Placa", "Descrição", "Carga", "Volume", "Peso", "Modalidade", "KM"};

    private String tipoPesq;
    private String[] dadosRegSelec; //guarda os dados do ultimo registro selecionado
    private List<Object[]> arListProd;
    private ArrayList<Integer> arIndexProds; //guarda os codigos dos produtos que estão pr ser inseridos no pedido
    private String retorno;
    private int codigo;
    private String txtPesqAnterior;

    /**
     * Creates new form PesquisaView
     *
     * @param parent
     * @param modal
     * @param TipoPesq
     * @param retorno
     */
        
    public Pesquisa(java.awt.Frame parent, boolean modal, String TipoPesq, String retorno) {
        super(parent, modal);
        this.retorno = retorno;
        //System.out.println("entrou na tela de pesquisa  - tipopesq: " + TipoPesq);
        initComponents();
        Util.Util.temaVisualizacao(this);
        arListProd = new ArrayList<>();
        arIndexProds = new ArrayList<>();
        this.tipoPesq = TipoPesq.toUpperCase();
        jckTodaParte.setSelected(true);
        ModeloTab = (DefaultTableModel) tabResutPesq.getModel();
        txtPesqAnterior = "";
        habilitaDuploCliqueTabela();
        txtPesquisa.setEditable(true);
        limparLinhasTabela();
        switch (TipoPesq) {
            case "CLIENTE":
                montarTable(COL_CLIENTE, WID_CLIENTE);
                jcbOpcoes.addItem("Nome");
                jcbOpcoes.addItem("Cartão");
                jcbOpcoes.addItem("Código");
                break;
            case "FABRICANTE":
                montarTable(COL_FABRICANTE, WID_FABRICANTE);
                jcbOpcoes.addItem("Razão Social");
                jcbOpcoes.addItem("CNPJ");
                jcbOpcoes.addItem("Código");
                break;
            case "SOLICITANTE":
                montarTable(COL_SOLICITANTE, WID_SOLICITANTE);
                jcbOpcoes.addItem("Nome");
                jcbOpcoes.addItem("CRM");
                jcbOpcoes.addItem("Código");
                break;
            case "PRODUTO":
                montarTable(COL_PRODUTO, WID_PRODUTO);
                jcbOpcoes.addItem("Descrição");
                jcbOpcoes.addItem("Identificador");
                jcbOpcoes.addItem("Código");
                break;
            case "USUARIO":
                montarTable(COL_USUARIO, WID_USUARIO);
                jcbOpcoes.addItem("Nome");
                jcbOpcoes.addItem("Login");
                jcbOpcoes.addItem("Codigo");
                break;
            case "FUNC":
                //montarTable(COL_USUARIO);
                jcbOpcoes.addItem("Nome");
                jcbOpcoes.addItem("CPF");
                jcbOpcoes.addItem("Codigo");
                break;
            case "TRANSP":
                //montarTable(COL_LOTE_ATENDIMENTO);
                jcbOpcoes.addItem("Nome");
                jcbOpcoes.addItem("Placa");
                jcbOpcoes.addItem("Codigo");
                break;
            default:
                break;
        }
        txtPesquisa.requestFocus();
    }

    public String getTipoPesq() {
        return tipoPesq;
    }

    public void setTipoPesq(String tipoPesq) {
        this.tipoPesq = tipoPesq;
    }

    public DefaultTableModel getModeloTab() {
        return ModeloTab;
    }

    public String getComOpcao() {
        return jcbOpcoes.getSelectedItem().toString();
    }

    public String getTxtPesquisa() {
        return txtPesquisa.getText();
    }

    private void montarTable(String colunas[], int width[]) {
        tabResutPesq.removeAll();
        for (String coluna : colunas) {
            ModeloTab.addColumn(coluna);
        }
        int i = 0;
        for (int widt : width) {
            tabResutPesq.getColumnModel().getColumn(i).setPreferredWidth(widt);
            i++;
        }
    }

    private void limparLinhasTabela() {
        ModeloTab.setNumRows(0);
    }

    private void addRegTable(ArrayList<String[]> dados) {
        for (int i = 0; i < dados.size(); i++) {
            ModeloTab.addRow(dados.get(i));
        }
    }

    public JCheckBox getChParte() {
        return jckTodaParte;
    }
    
    public String getResultPesq(){
        return retorno;
    }
    
    public int getCodigo(){
        return this.codigo;
    }

    private void buscaRegistrosPesquisa(boolean exibeMensagemSemResult) {
        if(txtPesqAnterior.equals(txtPesquisa.getText()) && !exibeMensagemSemResult){
            txtPesqAnterior = txtPesquisa.getText();
            return;
        }
        if(txtPesquisa.getText().isEmpty()){
            return;
        }
        String nome = txtPesquisa.getText();
        JsonArray arResult = new JsonArray();
        if (jckTodaParte.isSelected()) {
            nome = "%" + nome + "%";
        }
        int size;
        switch (getTipoPesq()) {
            case "CLIENTE":
                if (jcbOpcoes.getSelectedItem().toString().equals("Nome")) {
                    arResult = new CtrlCliente().SelecionarPorCampo_JsonArray("nome_cli", nome);
                    //System.out.println("Qtd Retorno: " + arResult.size());
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Cartão")){
                    arResult = new CtrlCliente().SelecionarPorCampo_JsonArray("cartao_cli", nome);
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Código")){
                    arResult = new CtrlCliente().SelecionarPorCampo_JsonArray("objCod_objBase", nome);
                }
                size = arResult.size();
                if (size == 0 && exibeMensagemSemResult) {
                    Mensagem.information("Não foram encontrados resultados \npara os parâmetros informados", this);
                }
                for (int i = 0; i < size; i++) {
                    JsonObject objeto = arResult.get(i).getAsJsonObject();
                    ModeloTab.addRow(new String[]{objeto.get("objCod_objBase").getAsString(),
                        objeto.get("cartao_cli").getAsString(),
                        objeto.get("nome_cli").getAsString()});
                }
                break;
            case "FABRICANTE":
                if (jcbOpcoes.getSelectedItem().toString().equals("Razão Social")) {
                    arResult = new CtrlFabricante().SelecionarPorCampo_JsonArray("nome_fab", nome);
                    //System.out.println("Qtd Retorno: " + arResult.size());
                } else if(jcbOpcoes.getSelectedItem().toString().equals("CNPJ")){
                    arResult = new CtrlFabricante().SelecionarPorCampo_JsonArray("cnpj_fab", nome);
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Código")){
                    arResult = new CtrlFabricante().SelecionarPorCampo_JsonArray("objCod_objBase", nome);
                }
                size = arResult.size();
                if (size == 0 && exibeMensagemSemResult) {
                    Mensagem.information("Não foram encontrados resultados \npara os parâmetros informados", this);
                }
                for (int i = 0; i < size; i++) {
                    JsonObject objeto = arResult.get(i).getAsJsonObject();
                    ModeloTab.addRow(new String[]{objeto.get("objCod_objBase").getAsString(),
                        objeto.get("cnpj_fab").getAsString(),
                        objeto.get("nome_fab").getAsString()});
                }
                break;
            case "SOLICITANTE":
                if (jcbOpcoes.getSelectedItem().toString().equals("Nome")) {
                    arResult = new CtrlSolicitante().SelecionarPorCampo_JsonArray("nome_sol", nome);
                    //System.out.println("Qtd Retorno: " + arResult.size());
                } else if(jcbOpcoes.getSelectedItem().toString().equals("CRM")){
                    arResult = new CtrlSolicitante().SelecionarPorCampo_JsonArray("crm_sol", nome);
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Código")){
                    arResult = new CtrlSolicitante().SelecionarPorCampo_JsonArray("objCod_objBase", nome);
                }
                size = arResult.size();
                if (size == 0 && exibeMensagemSemResult) {
                    Mensagem.information("Não foram encontrados resultados \npara os parâmetros informados", this);
                }
                for (int i = 0; i < size; i++) {
                    JsonObject objeto = arResult.get(i).getAsJsonObject();
                    ModeloTab.addRow(new String[]{objeto.get("objCod_objBase").getAsString(),
                        objeto.get("crm_sol").getAsString(),
                        objeto.get("nome_sol").getAsString()});
                }
                break;
            case "PRODUTO":
                if (jcbOpcoes.getSelectedItem().toString().equals("Descrição")) {
                    arResult = new CtrlProduto().SelecionarPorCampo_JsonArray("descricao_prod", nome);
                    //System.out.println("Qtd Retorno: " + arResult.size());
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Identificador")){
                    arResult = new CtrlProduto().SelecionarPorCampo_JsonArray("identificador_prod", nome);
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Código")){
                    arResult = new CtrlProduto().SelecionarPorCampo_JsonArray("objCod_objBase", nome);
                }
                size = arResult.size();
                if (size == 0 && exibeMensagemSemResult) {
                    Mensagem.information("Não foram encontrados resultados \npara os parâmetros informados", this);
                }
                for (int i = 0; i < size; i++) {
                    JsonObject objeto = arResult.get(i).getAsJsonObject();
                    ModeloTab.addRow(new String[]{objeto.get("objCod_objBase").getAsString(),
                        objeto.get("identificador_prod").getAsString(),
                        objeto.get("descricao_prod").getAsString()});
                }
                break;
            case "USUARIO":
                if (jcbOpcoes.getSelectedItem().toString().equals("Nome")) {
                    arResult = new CtrlUsuario().SelecionarPorCampo_JsonArray("nome_usu", nome);
                    //System.out.println("Qtd Retorno: " + arResult.size());
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Login")){
                    arResult = new CtrlUsuario().SelecionarPorCampo_JsonArray("login_usu", nome);
                } else if(jcbOpcoes.getSelectedItem().toString().equals("Código")){
                    arResult = new CtrlUsuario().SelecionarPorCampo_JsonArray("objCod_objBase", nome);
                }
                size = arResult.size();
                if (size == 0 && exibeMensagemSemResult) {
                    Mensagem.information("Não foram encontrados resultados \npara os parâmetros informados", this);
                }
                for (int i = 0; i < size; i++) {
                    JsonObject objeto = arResult.get(i).getAsJsonObject();
                    ModeloTab.addRow(new String[]{objeto.get("objCod_objBase").getAsString(),
                        objeto.get("login_usu").getAsString(),
                        objeto.get("nome_usu").getAsString()});
                }
                break;
            case "FUNC":

                break;
            case "TRANSP":

                break;
            default:
                break;
        }
        txtPesqAnterior = txtPesquisa.getText();
    }

    private void habilitaDuploCliqueTabela() {
        tabResutPesq.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if(retorno == null){
                        btnSelecionarActionPerformed(new ActionEvent(this, 0, "BUSCAR"));
                    } else {
                        btnSelecionarActionPerformed(new ActionEvent(this, 0, "RETORNAR"));
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnSelecionar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jcbOpcoes = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jckTodaParte = new javax.swing.JCheckBox();
        buscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabResutPesq = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisa");

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/back.png"))); // NOI18N
        btnCancelar.setText("Voltar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSelecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/marcada.png"))); // NOI18N
        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(btnSelecionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(359, 359, 359))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnSelecionar});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnSelecionar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Opções"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcbOpcoes, 0, 187, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcbOpcoes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        txtPesquisa.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPesquisaCaretUpdate(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        jckTodaParte.setText("Em toda parte");

        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/search.png"))); // NOI18N
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jckTodaParte))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jckTodaParte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/logo_mini.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        tabResutPesq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ){
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabResutPesq.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabResutPesq.getTableHeader().setReorderingAllowed(false);
        tabResutPesq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabResutPesqMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabResutPesq);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inserirDadosVet(int linha) {
        dadosRegSelec = new String[ModeloTab.getColumnCount()];
        for (int i = 0; i < ModeloTab.getColumnCount(); i++) {
            dadosRegSelec[i] = String.valueOf(ModeloTab.getValueAt(linha, i));
        }
    }

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        if(tabResutPesq.getRowCount() == 0){
            return;
        }
        int codigoObjeto = Integer.parseInt(ModeloTab.getValueAt(tabResutPesq.getSelectedRow(), 0).toString());
        switch (getTipoPesq()) {
            case "CLIENTE":
                if(retorno == null){
                    new Cliente(new Frame(), true, codigoObjeto).setVisible(true);
                } else {
                    retorno = tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 1).toString();
                    //System.out.println("Retorno Cliente: " + retorno);
                    this.codigo = Integer.parseInt(tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 0).toString());
                    //System.out.println("Retorno na tela de pesquisa: " + retorno);
                    this.dispose();
                }
                break;
            case "FABRICANTE":
                if(retorno == null){
                    new Fabricante(new Frame(), true, codigoObjeto).setVisible(true);
                } else {
                    retorno = tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 1).toString();
                    this.codigo = Integer.parseInt(tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 0).toString());
                    //System.out.println("Retorno na tela de pesquisa: " + retorno);
                    this.dispose();
                }
                break;
            case "SOLICITANTE":
                if(retorno == null){
                    new Solicitante(new Frame(), true, codigoObjeto).setVisible(true);
                } else {
                    retorno = tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 1).toString();
                    this.codigo = Integer.parseInt(tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 0).toString());
                    //System.out.println("Retorno na tela de pesquisa: " + retorno);
                    this.dispose();
                }
                break;
            case "PRODUTO":
                if(retorno == null){
                    new Produto(new Frame(), true, codigoObjeto).setVisible(true);
                } else {
                    retorno = tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 1).toString();
                    this.codigo = Integer.parseInt(tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 0).toString());
                    //System.out.println("Retorno na tela de pesquisa: " + retorno);
                    this.dispose();
                }
                break;
            case "USUARIO":
                if(retorno == null){
                    new Usuario(new Frame(), true, codigoObjeto).setVisible(true);
                } else {
                    retorno = tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 1).toString();
                    this.codigo = Integer.parseInt(tabResutPesq.getValueAt(tabResutPesq.getSelectedRow(), 0).toString());
                    //System.out.println("Retorno na tela de pesquisa: " + retorno);
                    this.dispose();
                }
                break;
            case "FUNC":

                break;
            case "TRANSP":

                break;
            default:
                break;
        }
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtPesquisaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPesquisaCaretUpdate
        //System.out.println("txtPesq: " + txtPesquisa.getText());
        ModeloTab.setNumRows(0);
        buscaRegistrosPesquisa(false);
    }//GEN-LAST:event_txtPesquisaCaretUpdate

    private void tabResutPesqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabResutPesqMouseClicked

    }//GEN-LAST:event_tabResutPesqMouseClicked

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        ModeloTab.setNumRows(0);
        buscaRegistrosPesquisa(true);
    }//GEN-LAST:event_buscarActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        /*if (evt.getKeyCode() == 10) {
        ModeloTab.setNumRows(0);
        buscaRegistrosPesquisa();
        }*/
    }//GEN-LAST:event_txtPesquisaKeyReleased

    /*public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> {
    //Pedido dialog = new Pedido(new javax.swing.JFrame(), true, );
    Pesquisa dialog = new Pesquisa(new javax.swing.JFrame(), true, "PRODUTO");
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
    System.exit(0);
    }
    });
    dialog.setVisible(true);
    });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JButton buscar;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbOpcoes;
    private javax.swing.JCheckBox jckTodaParte;
    private javax.swing.JTable tabResutPesq;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
