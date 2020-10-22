package Model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UndMedida extends ObjetoBase
{
    @Column(unique=true, nullable = false)
    private String unidade;

    public UndMedida() {
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    
}