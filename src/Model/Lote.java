/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Abner Amorim
 */
@Entity
public class Lote extends ObjetoBase{
    @Column (unique=true)
    private String lote;
    @ManyToOne (cascade = CascadeType.MERGE)
    private Fabricante fabricante_prod;
    @Temporal(TemporalType.DATE)
    private java.util.Date validade_prod;
    @Temporal(TemporalType.DATE)
    //@Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private java.util.Date data_inclusao_prod;
    private String nota_fiscal_prod;
    @Column(scale=2,precision=12)
    private Double qtd_prod;
    private String produtoIdent;

    public Lote(String lote) {
        this.lote = lote;
    }

    public Lote() {
    }
    
    public Fabricante getFabricante_prod() {
        return fabricante_prod;
    }

    public void setFabricante_prod(Fabricante fabricante_prod) {
        this.fabricante_prod = fabricante_prod;
    }

    public String getNota_fiscal_prod() {
        return nota_fiscal_prod;
    }

    public void setNota_fiscal_prod(String nota_fiscal_prod) {
        this.nota_fiscal_prod = nota_fiscal_prod;
    }

    public String getProduto() {
        return produtoIdent;
    }

    public void setProduto(String produto) {
        this.produtoIdent = produto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

}
