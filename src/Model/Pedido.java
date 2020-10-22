package Model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pedido extends ObjetoBase{
    @ManyToOne
    private Cliente cliente_pedido;
    @OneToMany (cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Pedido_Produto> produtos_pedido;
    //@Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @Temporal(TemporalType.DATE)
    private java.util.Date data_pedido;
    private String cuidados;
    private String responsavel_pedido; //Login do responsavel pelo pedido
    @ManyToOne
    private Solicitante solicitante_pedido;

    public Pedido(){
        produtos_pedido = new ArrayList<Model.Pedido_Produto>();
    }
    
    public Solicitante getSolicitante_pedido() {
        return solicitante_pedido;
    }

    public void setSolicitante_pedido(Solicitante solicitante_pedido) {
        this.solicitante_pedido = solicitante_pedido;
    }

    public Cliente getCliente_pedido() {
        return cliente_pedido;
    }

    public void setCliente_pedido(Cliente cliente_pedido) {
        this.cliente_pedido = cliente_pedido;
    }

    public List getProdutos_pedido() {
        return produtos_pedido;
    }

    public void setProdutos_pedido(List produtos_pedido) {
        this.produtos_pedido = produtos_pedido;
    }
}
