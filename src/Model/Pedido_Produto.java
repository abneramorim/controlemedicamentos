package Model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author Abner
 */
@Entity
public class Pedido_Produto extends ObjetoBase{
    
    private String produto;  //Identificador Produto
    @OneToOne
    private Lote lote_prod;
    private Double qtd_prod_ped;

    public Lote getLote_prod() {
        return lote_prod;
    }

    public void setLote_prod(Lote lote_prod) {
        this.lote_prod = lote_prod;
    }

    public Pedido_Produto() {
    }

    public Pedido_Produto(Pedido pPedido, String pProduto, Lote pLote) {
        this.produto = pProduto;
        this.lote_prod = pLote;
    }
}
