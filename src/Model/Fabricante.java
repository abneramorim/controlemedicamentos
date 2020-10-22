package Model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Fabricante extends ObjetoBase
{
    private String nome_fab;
    @Column(unique=true)
    private String cnpj_fab;
    
    public Fabricante() {
    }

    public String getNome_fab() {
        return nome_fab;
    }

    public void setNome_fab(String nome_fab) {
        this.nome_fab = nome_fab;
    }

    public String getCnpj_fab() {
        return cnpj_fab;
    }

    public void setCnpj_fab(String cnpj_fab) {
        this.cnpj_fab = cnpj_fab;
    }

}