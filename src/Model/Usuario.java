package Model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Usuario extends ObjetoBase
{
    @Column(unique = true)
    private String login_usu;
    @Column(unique = true)
    private String nome_usu;
    private String senha_usu;

    public String getSenha_usu() 
    {
        return senha_usu;
    }

    public void setSenha_usu(String senha_usu) 
    {
        this.senha_usu = senha_usu;
    }

    public String getLogin_usu()
    {
        return login_usu;
    }

    public void setLogin_usu(String login_usu)
    {
        this.login_usu = login_usu;
    }

    public String getNome_usu()
    {
        return nome_usu;
    }

    public void setNome_usu(String nome_usu)
    {
        this.nome_usu = nome_usu;
    }
}