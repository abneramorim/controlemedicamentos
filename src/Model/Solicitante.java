package Model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Solicitante extends ObjetoBase
{
    private String nome_sol;
    @Column(unique=true)
    private String crm_sol;
    
    public Solicitante() {
    }

    public String getNome_sol() {
        return nome_sol;
    }

    public void setNome_sol(String nome_sol) {
        this.nome_sol = nome_sol;
    }

    public String getCrm_sol() {
        return crm_sol;
    }

    public void setCrm_sol(String crm_sol) {
        this.crm_sol = crm_sol;
    }

}