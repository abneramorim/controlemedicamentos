package Model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Produto extends ObjetoBase {

    private String descricao_prod;
    @Column(unique=true)
    private String identificador_prod;
    @Column(scale=2,precision=12)
    private Double qtd_min;
    @OneToMany (cascade = CascadeType.ALL)
    private List<Lote> lotes_prod;
    @ManyToOne
    private Model.UndMedida undMedida;

    
    public Produto (){
        
    }

    public String getIdentificador_prod() {
        return identificador_prod;
    }

    public void setIdentificador_prod(String identificador_prod) {
        this.identificador_prod = identificador_prod;
    }

    public String getDescricao_prod() {
        return descricao_prod;
    }

    public void setDescricao_prod(String descricao_prod) {
        this.descricao_prod = descricao_prod;
    }

}
