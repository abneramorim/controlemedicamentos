package Model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cliente extends ObjetoBase
{
    private String nome_cli;
    @Column(unique=true)
    private String cartao_cli;

    public Cliente() {
    }

    public String getCartao_cli() {
        return cartao_cli;
    }

    public void setCartao_cli(String cartao_cli) {
        this.cartao_cli = cartao_cli;
    }
    
    public String getNome_cli()
    {
        return nome_cli;
    }

    public void setNome_cli(String nome_cli)
    {
        this.nome_cli = nome_cli;
    }    
}