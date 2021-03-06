/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CtrlLote;
import Controller.CtrlProduto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Abner Amorim
 */
public class Principal extends javax.swing.JFrame {

    private DefaultTableModel ModeloTabelaQtdMin;
    private DefaultTableModel ModeloTabelaValidade60;
    private DefaultTableModel ModeloTabelaValidade90;
    private DecimalFormat df;

    public Principal(String pLoginResp) {
        //Locale.setDefault(new Locale("pt", "BR"));
        initComponents();
        Util.Util.temaVisualizacao(this);
        lbUsuario.setText(pLoginResp);
        if (!pLoginResp.equals("admin")) {
            jMenuUsuario.setVisible(false);
            jMenuBuscaUsu.setVisible(false);
            jMenuConfig.setVisible(false);
            jMenuBDConfig.setVisible(false);
        }
        //df = new DecimalFormat("#.##0,00");
        ModeloTabelaQtdMin = (DefaultTableModel) tabQtdMin.getModel();
        ModeloTabelaValidade60 = (DefaultTableModel) tabValidade60.getModel();
        ModeloTabelaValidade90 = (DefaultTableModel) tabValidade90.getModel();

        //preencheTabelas();
    }

    public String getResponsavel() {
        return lbUsuario.getText();
    }

    private void preencheTabelas() {
        List<String[]> produtos = new CtrlProduto().RecuperaProdutosQtdMin();

        ModeloTabelaQtdMin.setNumRows(0);
        ModeloTabelaValidade60.setNumRows(0);
        ModeloTabelaValidade90.setNumRows(0);

        for (int i = 0; i < produtos.size(); i++) {
            ModeloTabelaQtdMin.addRow(new String[]{produtos.get(i)[0], produtos.get(i)[1], produtos.get(i)[2].replace(",", "").replace(".", ",")});
        }

        JsonArray lotes_v = new CtrlLote().LotesVencimento(60);
        Date data = new Date();
        for (int i = 0; i < lotes_v.size(); i++) {
            JsonObject lote = new JsonParser().parse(lotes_v.get(i).getAsString()).getAsJsonObject();
            ModeloTabelaValidade60.addRow(new String[]{lote.get("produtoIdent").getAsString(),
                new JsonParser().parse(new CtrlProduto().RecuperarProdIdentificador(lote.get("produtoIdent").getAsString())).getAsJsonObject().get("descricao_prod").getAsString(),
                lote.get("lote").getAsString(),
                dateFormat(lote.get("validade_prod").getAsString())});
            data.setTime(Date.parse(lote.get("validade_prod").getAsString()));
            //System.out.println("Validade: " + ((data.getTime() - new Date().getTime()) / 86400000));
        }

        lotes_v = new CtrlLote().LotesVencimento(90);

        for (int i = 0; i < lotes_v.size(); i++) {
            JsonObject lote = new JsonParser().parse(lotes_v.get(i).getAsString()).getAsJsonObject();
            ModeloTabelaValidade90.addRow(new String[]{lote.get("produtoIdent").getAsString(),
                new JsonParser().parse(new CtrlProduto().RecuperarProdIdentificador(lote.get("produtoIdent").getAsString())).getAsJsonObject().get("descricao_prod").getAsString(),
                lote.get("lote").getAsString(),
                dateFormat(lote.get("validade_prod").getAsString())});
        }
        //System.out.println("Data atual: " + (new Date().getTime() / 86400000));
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

        jButton6 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbUsuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabQtdMin = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabValidade60 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabValidade90 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuCliente = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuUsuario = new javax.swing.JMenuItem();
        jMenuProduto = new javax.swing.JMenuItem();
        jMenuAtendimento = new javax.swing.JMenuItem();
        jMenuPesquisa = new javax.swing.JMenu();
        jMenuPessoas = new javax.swing.JMenu();
        jMenuBuscaCli = new javax.swing.JMenuItem();
        jMenuBuscaSol = new javax.swing.JMenuItem();
        jMenuBuscaFab = new javax.swing.JMenuItem();
        jMenuBuscaUsu = new javax.swing.JMenuItem();
        jMenuBuscaProd = new javax.swing.JMenuItem();
        jMenuBuscaAtendimento = new javax.swing.JMenuItem();
        jMenuRelatorios = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuConfig = new javax.swing.JMenu();
        jMenuBDConfig = new javax.swing.JMenuItem();

        jButton6.setText("jButton6");

        jMenu1.setText("File");
        jMenuBar2.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar3.add(jMenu3);

        jMenu5.setText("Edit");
        jMenuBar3.add(jMenu5);

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Medicamentos | Principal");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/user.png"))); // NOI18N

        lbUsuario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbUsuario.setText("____________________");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/logo_mini.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, lbUsuario});

        tabQtdMin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Descrição", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabQtdMin.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabQtdMin);
        if (tabQtdMin.getColumnModel().getColumnCount() > 0) {
            tabQtdMin.getColumnModel().getColumn(0).setMaxWidth(100);
            tabQtdMin.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        tabValidade60.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Descrição", "Lote", "Validade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabValidade60.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tabValidade60);
        if (tabValidade60.getColumnModel().getColumnCount() > 0) {
            tabValidade60.getColumnModel().getColumn(0).setMaxWidth(100);
            tabValidade60.getColumnModel().getColumn(2).setMaxWidth(130);
            tabValidade60.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel2.setText("Produtos Abaixo da Quantidade Mínima");

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setText("Lotes com Fim de Validade em 60 Dias");

        tabValidade90.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Descrição", "Lote", "Validade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabValidade90.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabValidade90);
        if (tabValidade90.getColumnModel().getColumnCount() > 0) {
            tabValidade90.getColumnModel().getColumn(0).setMaxWidth(100);
            tabValidade90.getColumnModel().getColumn(2).setMaxWidth(130);
            tabValidade90.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setText("Lotes com Fim de Validade em 120 Dias");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(146, 146, 146))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane2, jScrollPane3});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jScrollPane1.setViewportView(jPanel2);

        jMenuBar1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenu4.setText("Cadastros");

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/people.png"))); // NOI18N
        jMenu6.setText("Pessoas");

        jMenuCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/person-add.png"))); // NOI18N
        jMenuCliente.setText("Cliente");
        jMenuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuClienteActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuCliente);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/doctor.png"))); // NOI18N
        jMenuItem1.setText("Médico Solicitante");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/factory.png"))); // NOI18N
        jMenuItem2.setText("Fabricante");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem2);

        jMenuUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/user.png"))); // NOI18N
        jMenuUsuario.setText("Usuario");
        jMenuUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuUsuarioActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuUsuario);

        jMenu4.add(jMenu6);

        jMenuProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/medicine.png"))); // NOI18N
        jMenuProduto.setText("Produto");
        jMenuProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuProdutoActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuProduto);

        jMenuAtendimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/medical-prescription.png"))); // NOI18N
        jMenuAtendimento.setText("Atendimento");
        jMenuAtendimento.setToolTipText("");
        jMenuAtendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAtendimentoActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuAtendimento);

        jMenuBar1.add(jMenu4);

        jMenuPesquisa.setText("Pesquisa");

        jMenuPessoas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/people.png"))); // NOI18N
        jMenuPessoas.setText("Pessoas");

        jMenuBuscaCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/person-add.png"))); // NOI18N
        jMenuBuscaCli.setText("Cliente");
        jMenuBuscaCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscaCliActionPerformed(evt);
            }
        });
        jMenuPessoas.add(jMenuBuscaCli);

        jMenuBuscaSol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/doctor.png"))); // NOI18N
        jMenuBuscaSol.setText("Médico Solicitante");
        jMenuBuscaSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscaSolActionPerformed(evt);
            }
        });
        jMenuPessoas.add(jMenuBuscaSol);

        jMenuBuscaFab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/factory.png"))); // NOI18N
        jMenuBuscaFab.setText("Fabricante");
        jMenuBuscaFab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscaFabActionPerformed(evt);
            }
        });
        jMenuPessoas.add(jMenuBuscaFab);

        jMenuBuscaUsu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/user.png"))); // NOI18N
        jMenuBuscaUsu.setText("Usuário");
        jMenuBuscaUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscaUsuActionPerformed(evt);
            }
        });
        jMenuPessoas.add(jMenuBuscaUsu);

        jMenuPesquisa.add(jMenuPessoas);

        jMenuBuscaProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/medicine.png"))); // NOI18N
        jMenuBuscaProd.setText("Produto");
        jMenuBuscaProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscaProdActionPerformed(evt);
            }
        });
        jMenuPesquisa.add(jMenuBuscaProd);

        jMenuBuscaAtendimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/medical-prescription.png"))); // NOI18N
        jMenuBuscaAtendimento.setText("Atendimento");
        jMenuBuscaAtendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscaAtendimentoActionPerformed(evt);
            }
        });
        jMenuPesquisa.add(jMenuBuscaAtendimento);

        jMenuBar1.add(jMenuPesquisa);

        jMenuRelatorios.setText("Relatorios");
        jMenuRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRelatoriosActionPerformed(evt);
            }
        });

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/report_16x16.png"))); // NOI18N
        jMenuItem4.setText("Inventário");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenuRelatorios.add(jMenuItem4);

        jMenuBar1.add(jMenuRelatorios);

        jMenuConfig.setText("Configurações");

        jMenuBDConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/database.png"))); // NOI18N
        jMenuBDConfig.setText("Conexão Banco de Dados");
        jMenuBDConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBDConfigActionPerformed(evt);
            }
        });
        jMenuConfig.add(jMenuBDConfig);

        jMenuBar1.add(jMenuConfig);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuProdutoActionPerformed
        new Produto(null, false, 0).setVisible(true);
    }//GEN-LAST:event_jMenuProdutoActionPerformed

    private void jMenuRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRelatoriosActionPerformed

    }//GEN-LAST:event_jMenuRelatoriosActionPerformed

    private void jMenuUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuUsuarioActionPerformed
        new Usuario(null, true, -1).setVisible(true);
    }//GEN-LAST:event_jMenuUsuarioActionPerformed

    private void jMenuClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuClienteActionPerformed
        new Cliente(null, true, -1).setVisible(true);
    }//GEN-LAST:event_jMenuClienteActionPerformed

    private void jMenuAtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAtendimentoActionPerformed
        new Pedido(null, true, lbUsuario.getText(), -1).setVisible(true);
    }//GEN-LAST:event_jMenuAtendimentoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new Solicitante(this, true, -1).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new Fabricante(this, true, -1).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        preencheTabelas();
    }//GEN-LAST:event_formWindowGainedFocus

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        preencheTabelas();
    }//GEN-LAST:event_formFocusGained

    private void jMenuBuscaProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscaProdActionPerformed
        new Pesquisa(this, true, "PRODUTO", null).setVisible(true);
    }//GEN-LAST:event_jMenuBuscaProdActionPerformed

    private void jMenuBuscaFabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscaFabActionPerformed
        new Pesquisa(this, true, "FABRICANTE", null).setVisible(true);
    }//GEN-LAST:event_jMenuBuscaFabActionPerformed

    private void jMenuBuscaCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscaCliActionPerformed
        new Pesquisa(this, true, "CLIENTE", null).setVisible(true);
    }//GEN-LAST:event_jMenuBuscaCliActionPerformed

    private void jMenuBuscaSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscaSolActionPerformed
        new Pesquisa(this, true, "SOLICITANTE", null).setVisible(true);
    }//GEN-LAST:event_jMenuBuscaSolActionPerformed

    private void jMenuBuscaUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscaUsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuBuscaUsuActionPerformed

    private void jMenuBuscaAtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscaAtendimentoActionPerformed
        new Pesquisa_Atendimento(this, true, null, lbUsuario.getText()).setVisible(true);
    }//GEN-LAST:event_jMenuBuscaAtendimentoActionPerformed

    private void jMenuBDConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBDConfigActionPerformed
        new Configuracao().setVisible(true);
    }//GEN-LAST:event_jMenuBDConfigActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new View.Inventario(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuItem jMenuAtendimento;
    private javax.swing.JMenuItem jMenuBDConfig;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuBuscaAtendimento;
    private javax.swing.JMenuItem jMenuBuscaCli;
    private javax.swing.JMenuItem jMenuBuscaFab;
    private javax.swing.JMenuItem jMenuBuscaProd;
    private javax.swing.JMenuItem jMenuBuscaSol;
    private javax.swing.JMenuItem jMenuBuscaUsu;
    private javax.swing.JMenuItem jMenuCliente;
    private javax.swing.JMenu jMenuConfig;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenu jMenuPesquisa;
    private javax.swing.JMenu jMenuPessoas;
    private javax.swing.JMenuItem jMenuProduto;
    private javax.swing.JMenu jMenuRelatorios;
    private javax.swing.JMenuItem jMenuUsuario;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbUsuario;
    private javax.swing.JTable tabQtdMin;
    private javax.swing.JTable tabValidade60;
    private javax.swing.JTable tabValidade90;
    // End of variables declaration//GEN-END:variables
}
