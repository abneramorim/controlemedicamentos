package DAO;

import Model.ObjetoBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class FuncoesJPA {

    public static EntityManager AbrirTransacao() {
        EntityManager gerente = FabricaJPA.getManager();
        if (gerente == null) {
            return null;
        }
        gerente.getEntityManagerFactory().getCache().evictAll();
        gerente.getTransaction().begin();
        return gerente;
    }

    public static void FecharTransacao(EntityManager gerente, boolean bCommit) {
        if (bCommit) {
            gerente.getTransaction().commit();
        } else {
            gerente.getTransaction().rollback();
        }

        gerente.close();
    }

    public static int Persistir(Object obj) {
        EntityManager trans = AbrirTransacao();
        trans.persist(obj);
        FecharTransacao(trans, true);
        //System.out.println("Chave após persistir: " + obj.toString());
        return ((ObjetoBase) obj).getObjCod_objBase();
    }

    public static void Persistir(Object obj, EntityManager cnx) {
        cnx.persist(obj);
    }

    public static void Fundir(Object obj) {
        EntityManager trans = AbrirTransacao();
        trans.merge(obj);
        FecharTransacao(trans, true);
        //System.out.println("Chave após fundir: " + obj.getCodigo());
    }

    public static void Fundir(Object obj, EntityManager cnx) {
        cnx.merge(obj);
    }

    public static void Excluir(int iChave, Class classe) {
        EntityManager trans = AbrirTransacao();
        Object obj = trans.find(classe, iChave);
        trans.remove(obj);
        FecharTransacao(trans, true);
    }

    public static void Excluir(int iChave, Class classe, EntityManager cnx) {
        Object obj = cnx.find(classe, iChave);
        cnx.remove(obj);
    }

    public static Object recuperar(int chave, Class classe) {
        EntityManager trans = FabricaJPA.getManager();
        //
        //trans.getEntityManagerFactory().getCache().evictAll();
        return trans.find(classe, chave);
    }

    public static Object recuperar(int chave, Class classe, EntityManager cnx) {
        //
        //cnx.getEntityManagerFactory().getCache().evictAll();
        return cnx.find(classe, chave);
    }

    public static int Update(Class classe, String pCampo, String pValor, String pCampoID, String pCodigo) {
        EntityManager trans = FabricaJPA.getManager();
        String sJPQL = "UPDATE " + classe.getSimpleName() + " SET " + pCampo + "=\"" + pValor + "\" WHERE " + pCampoID + "=" + pCodigo;
        //System.out.println("SQL GERADO: " + sJPQL);
        //
        //trans.getEntityManagerFactory().getCache().evictAll();
        trans.getTransaction().begin();
        Query minhaQuery = trans.createQuery(sJPQL);
        int retorno = minhaQuery.executeUpdate();
        trans.getTransaction().commit();
        return retorno;
    }

    public static String Selecionar(Class classe, String whereJPQL, String pCampo) {
        EntityManager trans = FabricaJPA.getManager();
        String sJPQL = "SELECT U." + pCampo + " FROM " + classe.getSimpleName() + " U " + whereJPQL;
        //System.out.println("SQL GERADO: " + sJPQL);
        //
        //trans.getEntityManagerFactory().getCache().evictAll();
        Query minhaQuery = trans.createQuery(sJPQL);
        if (minhaQuery.getMaxResults() == 0) {
            return null;
        }
        return minhaQuery.getSingleResult().toString();
    }

    public static List<?> Selecionar(Class classe, String whereJPQL) {
        EntityManager trans = FabricaJPA.getManager();
        if (trans == null) {
            return null;
        }
        String sJPQL = "SELECT U FROM " + classe.getSimpleName() + " U " + whereJPQL;
        //System.out.println("SQL GERADO: " + sJPQL);
        //
        //trans.getEntityManagerFactory().getCache().evictAll();
        Query minhaQuery = trans.createQuery(sJPQL);
        return minhaQuery.getResultList();
    }

    public static List<?> Selecionar_Pesquisa(Class classe, String whereJPQL) {
        EntityManager trans = FabricaJPA.getManager();
        if (trans == null) {
            return null;
        }
        String sJPQL = "SELECT U FROM " + classe.getSimpleName() + " U " + whereJPQL;
        //System.out.println("SQL GERADO: " + sJPQL);
        //
        //trans.getEntityManagerFactory().getCache().evictAll();
        Query minhaQuery = trans.createQuery(sJPQL);
        return minhaQuery.getResultList();
    }

    public static String SelecionarPassaSQL(Class classe, String JPQL) {
        EntityManager trans = FabricaJPA.getManager();
        //
        //trans.getEntityManagerFactory().getCache().evictAll();
        Query minhaQuery = trans.createQuery(JPQL);
        if (minhaQuery.getMaxResults() == 0) {
            return null;
        }
        return minhaQuery.getSingleResult().toString();
    }

    public static List<?> SelecionarListPassaSQL(Class classe, String JPQL) {
        EntityManager trans = FabricaJPA.getManager();
        if (trans == null) {
            return null;
        }
        //
        //trans.getEntityManagerFactory().getCache().evictAll();
        Query minhaQuery = trans.createQuery(JPQL);
        if (minhaQuery.getMaxResults() == 0) {
            return null;
        }
        return minhaQuery.getResultList();
    }

    public static List<?> SelecionarProdutos(String JPQL) {
        Connection cnx = FabricaConexao.getNovaConexao();
        PreparedStatement sql;
        try {
            sql = cnx.prepareStatement(JPQL);
            ResultSet rs = sql.executeQuery();
            List produtos = new ArrayList<>();
            //int size = 0;
            while (rs.next()) {
                //size++;
                double qtd_total = rs.getDouble("sum(L.qtd_prod)");
                if (qtd_total < rs.getDouble("qtd_min")) {
                    String[] dados = new String[3];
                    dados[0] = rs.getString("identificador_prod");
                    dados[1] = rs.getString("descricao_prod");
                    dados[2] = String.valueOf(qtd_total);
                    produtos.add(dados);
                    //System.out.println("Total: " + qtd_total + " Produto: " + rs.getString("identificador_prod"));
                }
            }
            //System.out.println("Qtd_Produtos: " + size);
            cnx.close();
            return produtos;
        } catch (SQLException ex) {
            Logger.getLogger(FuncoesJPA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    public static List<?> SelecionarPedidos(String JPQL) {
        Connection cnx = FabricaConexao.getNovaConexao();
        PreparedStatement sql;
        try {
            sql = cnx.prepareStatement(JPQL);
            ResultSet rs = sql.executeQuery();
            List pedidos = new ArrayList<>();
            //int size = 0;
            while (rs.next()) {
                //size++;
                String[] dados = new String[6];
                dados[0] = rs.getString("objCod_objBase");
                dados[1] = rs.getString("data_pedido");
                dados[2] = rs.getString("responsavel_pedido");
                dados[3] = rs.getString("nome_cli");
                dados[4] = rs.getString("nome_sol");
                dados[5] = rs.getString("cuidados");
                pedidos.add(dados);
            }
            //System.out.println("Qtd_Pedidos: " + size);
            cnx.close();
            return pedidos;
        } catch (SQLException ex) {
            Logger.getLogger(FuncoesJPA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    public static void SelecionarInventário(List pLotesUsados, List pLotesNaoUsados) {
        if (pLotesUsados == null) {
            pLotesUsados = new ArrayList();
        }

        if (pLotesNaoUsados == null) {
            pLotesNaoUsados = new ArrayList();
        }

        Connection cnx = FabricaConexao.getNovaConexao();
        PreparedStatement sql;
        try {
            sql = cnx.prepareStatement("select distinct l.qtd_prod as qtd_estq, "
                    + "sum(p.qtd_prod_ped) as qtd_usada, t.descricao_prod, l.lote\n"
                    + "from lote l, pedido_produto p, produto t\n"
                    + "where l.objcod_objbase = p.lote_prod_objcod_objbase\n"
                    + "and p.produto = t.identificador_prod\n"
                    + "and l.produtoident = t.identificador_prod\n"
                    + "group by lote");
            ResultSet rs = sql.executeQuery();
            //int size = 0;
            while (rs.next()) {
                //size++;
                String[] dados = new String[4];
                dados[0] = rs.getString("lote");
                dados[1] = rs.getString("descricao_prod");
                dados[2] = rs.getString("qtd_estq");
                dados[3] = rs.getString("qtd_usada");
                pLotesUsados.add(dados);
            }

            sql = cnx.prepareStatement("select distinct sum(l.qtd_prod) "
                    + "as qtd_estq, t.descricao_prod, l.lote\n"
                    + "from lote l, produto t\n"
                    + "where l.produtoident = t.identificador_prod\n"
                    + "and l.OBJCOD_OBJBASE not in (select distinct l.OBJCOD_OBJBASE\n"
                    + "from lote l, pedido_produto p, produto t\n"
                    + "where l.objcod_objbase = p.lote_prod_objcod_objbase\n"
                    + "and p.produto = t.identificador_prod\n"
                    + "and l.produtoident = t.identificador_prod\n"
                    + "group by lote)\n"
                    + "group by lote");
            rs = sql.executeQuery();
            //int size = 0;
            while (rs.next()) {
                //size++;
                String[] dados = new String[3];
                dados[0] = rs.getString("lote");
                dados[1] = rs.getString("descricao_prod");
                dados[2] = rs.getString("qtd_estq");
                pLotesNaoUsados.add(dados);
            }
            //System.out.println("Qtd_Pedidos: " + size);
            cnx.close();
        } catch (SQLException ex) {
            Logger.getLogger(FuncoesJPA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<?> Selecionar(Class classe, String[][] parametros) {
        String sWhere = "";
        if (parametros.length > 0) {
            for (int i = 0; i < parametros.length; i++) {
                if (i == 0) {
                    sWhere = sWhere + " WHERE ";
                } else {
                    sWhere = sWhere + " AND ";
                }

                String campo = parametros[i][0];
                String valor = parametros[i][1];

                sWhere = sWhere + campo + " = " + valor;
            }
        }

        return Selecionar(classe, sWhere);
    }

    public static List<?> Selecionar(Class classe) {
        return Selecionar(classe, "");
    }

    public static Object RecuperarPrimeiroRegistro(Class classe) {
        EntityManager trans = FuncoesJPA.AbrirTransacao();
        Query query = trans.createQuery("SELECT MIN(U.objCod_objBase) FROM " + classe.getSimpleName() + " U WHERE U.objCod_objBase>0");

        int codigo = Integer.parseInt(query.getSingleResult().toString());
        //System.out.println("Primeiro codigo: " + codigo);
        return FuncoesJPA.recuperar(codigo, classe);
    }

    public static Object RecuperarUltimoRegistro(Class classe) {
        EntityManager trans = FuncoesJPA.AbrirTransacao();
        if (trans == null) {
            return null;
        }
        Query query = trans.createQuery("SELECT MAX(U.objCod_objBase) FROM " + classe.getSimpleName() + " U");

        int codigo = Integer.parseInt(query.getSingleResult().toString());
        //System.out.println("Primeiro codigo: " + codigo);
        return FuncoesJPA.recuperar(codigo, classe);
    }

    public static Object RecuperarProximoRegistro(Class classe, int pCodigo) {
        EntityManager trans = FuncoesJPA.AbrirTransacao();
        Query query = trans.createQuery("SELECT MIN(U.objCod_objBase) FROM " + classe.getSimpleName() + " U WHERE U.objCod_objBase>" + pCodigo);

        int codigo = Integer.parseInt(query.getSingleResult().toString());
        //System.out.println("Primeiro codigo: " + codigo);
        return FuncoesJPA.recuperar(codigo, classe);
    }

    public static Object RecuperarRegistroAnterior(Class classe, int pCodigo) {
        EntityManager trans = FuncoesJPA.AbrirTransacao();
        Query query = trans.createQuery("SELECT MAX(U.objCod_objBase) FROM " + classe.getSimpleName() + " U WHERE U.objCod_objBase<" + pCodigo + " AND U.objCod_objBase>0");

        int codigo = Integer.parseInt(query.getSingleResult().toString());
        //System.out.println("Primeiro codigo: " + codigo);
        return FuncoesJPA.recuperar(codigo, classe);
    }
}
