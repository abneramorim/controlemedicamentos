/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CtrlPedido;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Abner Amorim
 */
public class Inventario extends javax.swing.JDialog {

    private DefaultTableModel ModeloTab;
    private ArrayList lotesUsados;
    private ArrayList lotesNaoUsados;

    /**
     * Creates new form PesquisaView
     *
     * @param parent
     * @param modal
     */
        
    public Inventario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Util.Util.temaVisualizacao(this);
        ModeloTab = (DefaultTableModel) tabResutPesq.getModel();
        lotesUsados = new ArrayList();
        lotesNaoUsados = new ArrayList();
        carregaTela();
        //tabResutPesq.setEnabled(false);
        //limparLinhasTabela();
    }
    
    private void carregaTela(){
        ModeloTab.setNumRows(0);
        new CtrlPedido().SelecionarInventario(lotesUsados, lotesNaoUsados);
        
        int size = lotesUsados.size();
        int sizeSum = size;
        
        for(int i = 0; i < size; i++){
            String[] lote = (String[]) lotesUsados.get(i);
            ModeloTab.addRow(new String[]{lote[0], lote[1], lote[2], lote[3], String.valueOf(Double.parseDouble(lote[2])+Double.parseDouble(lote[3]))});
        }
        
        size = lotesNaoUsados.size();
        sizeSum += size;
        for(int i = 0; i < size; i++){
            String[] lote = (String[]) lotesNaoUsados.get(i);
            ModeloTab.addRow(new String[]{lote[0], lote[1], lote[2], "0", lote[2]});
        }
        
        if(sizeSum == 1)
            jlNumResult.setText("1 Resultado Encontrado");
        else
            jlNumResult.setText(sizeSum + " Resultados Encontrados");
        
        limpaArrays();
        //System.out.println("lotesUsados: " + lotesUsados.size() + " lotesNaoUsados: " + lotesNaoUsados.size());
    }

    private void limpaArrays(){
        lotesUsados = new ArrayList();
        lotesNaoUsados = new ArrayList();
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
        btnAtualizar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jlTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabResutPesq = new javax.swing.JTable();
        jlNumResult = new javax.swing.JLabel();

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

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/refresh_16x16.png"))); // NOI18N
        btnAtualizar.setText(" Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(btnAtualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(359, 359, 359))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAtualizar, btnCancelar});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icons/logo_mini.png"))); // NOI18N

        jlTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jlTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitulo.setText("Inventário");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabResutPesq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Lote", "Produto", "Quant. Estoque", "Quant. Usada", "Quant. Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
        if (tabResutPesq.getColumnModel().getColumnCount() > 0) {
            tabResutPesq.getColumnModel().getColumn(0).setResizable(false);
            tabResutPesq.getColumnModel().getColumn(0).setPreferredWidth(120);
            tabResutPesq.getColumnModel().getColumn(1).setResizable(false);
            tabResutPesq.getColumnModel().getColumn(1).setPreferredWidth(395);
            tabResutPesq.getColumnModel().getColumn(2).setResizable(false);
            tabResutPesq.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabResutPesq.getColumnModel().getColumn(3).setResizable(false);
            tabResutPesq.getColumnModel().getColumn(3).setPreferredWidth(120);
            tabResutPesq.getColumnModel().getColumn(4).setResizable(false);
            tabResutPesq.getColumnModel().getColumn(4).setPreferredWidth(120);
        }

        jlNumResult.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlNumResult, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlNumResult, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregaTela();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tabResutPesqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabResutPesqMouseClicked

    }//GEN-LAST:event_tabResutPesqMouseClicked

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
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlNumResult;
    private javax.swing.JLabel jlTitulo;
    private javax.swing.JTable tabResutPesq;
    // End of variables declaration//GEN-END:variables
}
