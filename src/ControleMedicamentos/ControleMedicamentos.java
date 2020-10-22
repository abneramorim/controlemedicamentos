package binaotransportes;

import DAO.FuncoesJPA;
import DAO.PersistenciaJPA;
import Model.Cliente;

import View.Login;

import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class BinaoTransportes {

    public static void main(String[] args) throws SQLException {


        /*CategoriaFrete categoriaFrete001 = new CategoriaFrete();
        categoriaFrete001.setDescricao_cfrt("Primeira Categoria");
        categoriaFrete001.setPercentual_cfrt(2);
        
        CategoriaFrete categoriaFrete002 = new CategoriaFrete();
        categoriaFrete002.setDescricao_cfrt("Segunda Categoria");
        categoriaFrete002.setPercentual_cfrt(2);
        
        PersistenciaJPA<CategoriaFrete> persistenciaJPACategoria001 = new PersistenciaJPA<CategoriaFrete>(CategoriaFrete.class);
        persistenciaJPACategoria001.salvar(categoriaFrete001);
        persistenciaJPACategoria001.salvar(categoriaFrete002);
        
        Cidade cidade001 = new Cidade();
        cidade001.setNome_cid("Uba");
        
        Cidade cidade002 = new Cidade();
        cidade002.setNome_cid("BnaoCity");
        
        PersistenciaJPA<Cidade> persistenciaJPACategoria002 = new PersistenciaJPA<Cidade>(Cidade.class);
        persistenciaJPACategoria002.salvar(cidade001);
        persistenciaJPACategoria002.salvar(cidade002);*/
        // System.out.println("Chamando tela login");
        // View.Login.main(args);
        //Facade.Facade teste = new Facade.Facade();

        /*Facade.Facade teste = new Facade.Facade();

        Model.Encomenda obj_Enc = new Model.Encomenda();
        CategoriaFrete categoriaFrete_enc = new CategoriaFrete();
        categoriaFrete_enc.setObjCod_objBase(4);
        categoriaFrete_enc.setPercentual_cfrt(0.20);
        categoriaFrete_enc.setDescricao_cfrt("Rápido");
        obj_Enc.setCategoriaFrete_enc(categoriaFrete_enc);
        Telefone telefoneC2_1 = new Telefone();
        telefoneC2_1.setDDD("32");
        telefoneC2_1.setDDI("+55");
        telefoneC2_1.setNumero("8754544");
        Telefone telefoneC2_2 = new Telefone();
        telefoneC2_2.setDDD("32");
        telefoneC2_2.setDDI("+55");
        telefoneC2_2.setNumero("7754544");
        ArrayList<Telefone> telefonesC2 = new ArrayList<>();
        telefonesC2.add(telefoneC2_1);
        telefonesC2.add(telefoneC2_2);
        Cliente clienteOrigem_enc = new Cliente();
        clienteOrigem_enc.setObjCod_objBase(7);
        clienteOrigem_enc.setNome_cli("Abner");
        clienteOrigem_enc.setTelefones_cli(telefonesC2);
        obj_Enc.setClienteOrigem_enc(clienteOrigem_enc);
        Telefone telefoneC1_1 = new Telefone();
        telefoneC1_1.setDDD("32");
        telefoneC1_1.setDDI("+55");
        telefoneC1_1.setNumero("98754544");
        Telefone telefoneC1_2 = new Telefone();
        telefoneC1_2.setDDD("32");
        telefoneC1_2.setDDI("+55");
        telefoneC1_2.setNumero("97754544");
        ArrayList<Telefone> telefonesC1 = new ArrayList<>();
        telefonesC1.add(telefoneC1_1);
        telefonesC1.add(telefoneC1_2);
        Model.Cliente clienteDestino_enc = new Model.Cliente();
        clienteDestino_enc.setObjCod_objBase(8);
        clienteDestino_enc.setNome_cli("Frango");
        clienteDestino_enc.setTelefones_cli(telefonesC1);
        obj_Enc.setClienteDestino_enc(clienteDestino_enc);
        obj_Enc.setCodigoIdent_enc("123456789");
        Cidade cidade = new Cidade();
        Estado estado = new Estado();
        estado.setNome_est("Minas Gerais");
        estado.setObjCod_objBase(5);
        cidade.setEstado_cid(estado);
        cidade.setNome_cid("Ubá");
        Regiao regiao1 = new Regiao();
        regiao1.setNomeDaRegiao_reg("Zona da Mata");
        regiao1.setObjCod_objBase(5);
        cidade.setRegiao_cid(regiao1);
        cidade.setObjCod_objBase(7);
        Endereco enderecoOrigem_enc = new Endereco();
        enderecoOrigem_enc.setCEP_end("36500-000");
        enderecoOrigem_enc.setObjCod_objBase(8);
        enderecoOrigem_enc.setBairro_end("Teste");
        enderecoOrigem_enc.setNumero_end(123);
        enderecoOrigem_enc.setLogradouro_end("Rua João");
        enderecoOrigem_enc.setCidade_end(cidade);
        Endereco enderecoDestino_enc = new Endereco();
        enderecoDestino_enc.setCEP_end("36500-000");
        enderecoDestino_enc.setObjCod_objBase(7);
        enderecoDestino_enc.setBairro_end("Teste");
        enderecoDestino_enc.setNumero_end(123);
        enderecoDestino_enc.setLogradouro_end("Rua José");
        enderecoDestino_enc.setCidade_end(cidade);
        obj_Enc.setEnderecoOrigem_enc(enderecoOrigem_enc);
        obj_Enc.setEnderecoDestino_enc(enderecoDestino_enc);
        obj_Enc.setEstado_enc("Entregue");
        MeioTransporte meioTransporte_enc = new MeioTransporte();
        meioTransporte_enc.setAnoFabricacao_mt("2012");
        meioTransporte_enc.setCapacidadeCarga_mt(3.5);
        meioTransporte_enc.setCapacidadeVolume_mt(30);
        meioTransporte_enc.setClassificacaoModalidade_mt("Terrestre");
        meioTransporte_enc.setClassificacaoPeso_mt("Acima de 3.5");
        meioTransporte_enc.setValorPorKM_mt(4);
        meioTransporte_enc.setObjCod_objBase(4);
        obj_Enc.setMeioTransporte_enc(meioTransporte_enc);
        ItemTransporte it1 = new ItemTransporte();
        it1.setDescricao_item("Item 1");
        it1.setEncomenda_item(obj_Enc);
        it1.setObjCod_objBase(10);
        it1.setPeso_item(2);
        it1.setTipoEmbalagem_item("Caixa Papelão");
        ItemTransporte it2 = new ItemTransporte();
        it2.setDescricao_item("Item 2");
        it2.setEncomenda_item(obj_Enc);
        it2.setObjCod_objBase(11);
        it2.setPeso_item(1);
        it2.setTipoEmbalagem_item("Plástico");
        ArrayList obj_Item = new ArrayList();
        obj_Item.add(it1);
        obj_Item.add(it2);
        obj_Enc.setObjCod_objBase(1);

        Funcionario funcionario = new Funcionario();
        funcionario.setObjCod_objBase(10);
        funcionario.setNome("Furrô");
        funcionario.setIdentidadeFuncional_func("12345678");
        funcionario.setIdentidadePessoal_func("87654321");
        Funcionario funcionario2 = new Funcionario();
        funcionario2.setObjCod_objBase(11);
        funcionario2.setNome("Frango");
        funcionario2.setIdentidadeFuncional_func("2345678");
        funcionario2.setIdentidadePessoal_func("8765432");
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(funcionario);
        funcionarios.add(funcionario2);
        GregorianCalendar gc = new GregorianCalendar();
        Date data = gc.getTime();
        Telefone telefone = new Telefone();
        telefone.setDDD("32");
        telefone.setDDI("+55");
        telefone.setNumero("123456789");
        telefone.setObjCod_objBase(10);
        Telefone telefone2 = new Telefone();
        telefone2.setDDD("33");
        telefone2.setDDI("+55");
        telefone2.setNumero("12345679");
        telefone2.setObjCod_objBase(11);
        ArrayList<Telefone> telefones = new ArrayList<>();
        telefones.add(telefone);
        telefones.add(telefone2);
        Endereco enderecoDist = new Endereco();
        Cidade cubatao = new Cidade();
        Estado saoPaulo = new Estado();
        Regiao regiaoCubatao = new Regiao();
        enderecoDist.setBairro_end("Bairrão");
        enderecoDist.setCEP_end("36580-000");
        saoPaulo.setNome_est("São Paulo");
        saoPaulo.setObjCod_objBase(90);
        regiao1.setNomeDaRegiao_reg("Planalto");
        regiao1.setObjCod_objBase(8);
        cubatao.setEstado_cid(saoPaulo);
        cubatao.setNome_cid("Cubatão");
        cubatao.setRegiao_cid(regiaoCubatao);
        enderecoDist.setCidade_end(cubatao);
        Distribuidora distribuidora = new Distribuidora();
        distribuidora.setEndereco_dist(enderecoDestino_enc);
        distribuidora.setFuncionarios_dist(funcionarios);
        distribuidora.setNomeFantasia_dist("Distribuidora 1");
        distribuidora.setRazaoSocial_dist("Distribuidora LTDA");
        distribuidora.setTelefone_dist(telefones);
        distribuidora.setObjCod_objBase(1);

        Funcionario funcionario3 = new Funcionario();
        funcionario3.setObjCod_objBase(12);
        funcionario3.setNome("Luiz");
        funcionario3.setIdentidadeFuncional_func("123451678");
        funcionario3.setIdentidadePessoal_func("876543321");
        Funcionario funcionario4 = new Funcionario();
        funcionario4.setObjCod_objBase(13);
        funcionario4.setNome("Abenr");
        funcionario4.setIdentidadeFuncional_func("42345678");
        funcionario4.setIdentidadePessoal_func("87654312");
        ArrayList<Funcionario> funcionarios2 = new ArrayList<>();
        funcionarios2.add(funcionario3);
        funcionarios2.add(funcionario4);
        Telefone telefone3 = new Telefone();
        telefone3.setDDD("32");
        telefone3.setDDI("+55");
        telefone3.setNumero("123456789");
        telefone3.setObjCod_objBase(12);
        Telefone telefone4 = new Telefone();
        telefone4.setDDD("33");
        telefone4.setDDI("+55");
        telefone4.setNumero("12345679");
        telefone4.setObjCod_objBase(13);
        ArrayList<Telefone> telefones2 = new ArrayList<>();
        telefones2.add(telefone3);
        telefones2.add(telefone4);
        Endereco enderecoDist2 = new Endereco();
        Cidade butanta = new Cidade();
        Estado minasGerais = new Estado();
        Regiao regiaoButanta = new Regiao();
        enderecoDist2.setBairro_end("Bairrão");
        enderecoDist2.setCEP_end("36520-000");
        minasGerais.setNome_est("Minas Gerais");
        minasGerais.setObjCod_objBase(5);
        regiaoButanta.setNomeDaRegiao_reg("Baixada");
        regiaoButanta.setObjCod_objBase(6);
        butanta.setEstado_cid(minasGerais);
        butanta.setNome_cid("Butantã");
        butanta.setRegiao_cid(regiaoButanta);
        enderecoDist2.setCidade_end(butanta);
        Distribuidora distribuidora2 = new Distribuidora();
        distribuidora2.setEndereco_dist(enderecoDist2);
        distribuidora2.setFuncionarios_dist(funcionarios2);
        distribuidora2.setNomeFantasia_dist("Distribuidora 2");
        distribuidora2.setRazaoSocial_dist("Distribuidora 2 LTDA");
        distribuidora2.setTelefone_dist(telefones);
        distribuidora2.setObjCod_objBase(2);
        Historico historico = new Historico();
        historico.setEncomenda_hist(obj_Enc);
        historico.setDataChegada_hist(data);
        historico.setDataSaida_hist(data);
        historico.setFuncionarioResp_hist(funcionario);
        historico.setMeioTransporte_hist(meioTransporte_enc);
        historico.setDistribuidoraDestino_hist(distribuidora);
        historico.setDistribuidoraOrigem_hist(distribuidora2);
        historico.setObjCod_objBase(1);
        teste.GravacaoEncomenda(obj_Enc, obj_Item, historico);
        List itens = FuncoesJPA.Selecionar(ItemTransporte.class, " WHERE U.objCod_objBase=" + 10);
        System.out.println("Total itens recuperados: " + itens.size());
        System.out.println("Fim do processamento");

        teste.transacaoGravacaoEncomenda(obj_Enc, obj_Item);
        System.out.println("Fim do processamento");*/
        //Cadastros Via REST
        //CategoriaFrete categoriaFrete = new CategoriaFrete();
        //categoriaFrete.setDescricao_cfrt("Lucas");
        //categoriaFrete.setPercentual_cfrt(2);
        //PersistenciaJPA<CategoriaFrete> persistenciaJPACategoria = new PersistenciaJPA<CategoriaFrete>(CategoriaFrete.class);
        //persistenciaJPACategoria.salvar(categoriaFrete);
        //Cidade cidade = new Cidade();
        //cidade.setObjCod_objBase(1);
        //PersistenciaJPA<Cidade> persistenciaJPACategoria2 = new PersistenciaJPA<Cidade>(Cidade.class);
        //persistenciaJPACategoria2.salvar(cidade);
        //System.out.println("Chamando tela login");
        //View.Login.main(args);
        /*JsonParser objParser = new JsonParser();

//        /*CategoriaFrete categoriaFrete001 = new CategoriaFrete();
//        categoriaFrete001.setDescricao_cfrt("Primeira Categoria");
//        categoriaFrete001.setPercentual_cfrt(2);
//        
//        CategoriaFrete categoriaFrete002 = new CategoriaFrete();
//        categoriaFrete002.setDescricao_cfrt("Segunda Categoria");
//        categoriaFrete002.setPercentual_cfrt(2);
//        
//        PersistenciaJPA<CategoriaFrete> persistenciaJPACategoria001 = new PersistenciaJPA<CategoriaFrete>(CategoriaFrete.class);
//        persistenciaJPACategoria001.salvar(categoriaFrete001);
//        persistenciaJPACategoria001.salvar(categoriaFrete002);
//        
//        Cidade cidade001 = new Cidade();
//        cidade001.setNome_cid("Uba");
//        
//        Cidade cidade002 = new Cidade();
//        cidade002.setNome_cid("BnaoCity");
//        
//        PersistenciaJPA<Cidade> persistenciaJPACategoria002 = new PersistenciaJPA<Cidade>(Cidade.class);
//        persistenciaJPACategoria002.salvar(cidade001);
//        persistenciaJPACategoria002.salvar(cidade002);*/
//        // System.out.println("Chamando tela login");
//        // View.Login.main(args);
//
//
//        Facade.Facade teste = new Facade.Facade();
//
//        /*Facade.Facade teste = new Facade.Facade();
//
//        Model.Encomenda obj_Enc = new Model.Encomenda();
//        CategoriaFrete categoriaFrete_enc = new CategoriaFrete();
//        categoriaFrete_enc.setObjCod_objBase(4);
//        categoriaFrete_enc.setPercentual_cfrt(0.20);
//        categoriaFrete_enc.setDescricao_cfrt("Rápido");
//        obj_Enc.setCategoriaFrete_enc(categoriaFrete_enc);
//        Telefone telefoneC2_1 = new Telefone();
//        telefoneC2_1.setDDD("32");
//        telefoneC2_1.setDDI("+55");
//        telefoneC2_1.setNumero("8754544");
//        Telefone telefoneC2_2 = new Telefone();
//        telefoneC2_2.setDDD("32");
//        telefoneC2_2.setDDI("+55");
//        telefoneC2_2.setNumero("7754544");
//        ArrayList<Telefone> telefonesC2 = new ArrayList<>();
//        telefonesC2.add(telefoneC2_1);
//        telefonesC2.add(telefoneC2_2);
//        Cliente clienteOrigem_enc = new Cliente();
//        clienteOrigem_enc.setObjCod_objBase(7);
//        clienteOrigem_enc.setNome_cli("Abner");
//        clienteOrigem_enc.setTelefones_cli(telefonesC2);
//        obj_Enc.setClienteOrigem_enc(clienteOrigem_enc);
//        Telefone telefoneC1_1 = new Telefone();
//        telefoneC1_1.setDDD("32");
//        telefoneC1_1.setDDI("+55");
//        telefoneC1_1.setNumero("98754544");
//        Telefone telefoneC1_2 = new Telefone();
//        telefoneC1_2.setDDD("32");
//        telefoneC1_2.setDDI("+55");
//        telefoneC1_2.setNumero("97754544");
//        ArrayList<Telefone> telefonesC1 = new ArrayList<>();
//        telefonesC1.add(telefoneC1_1);
//        telefonesC1.add(telefoneC1_2);
//        Model.Cliente clienteDestino_enc = new Model.Cliente();
//        clienteDestino_enc.setObjCod_objBase(8);
//        clienteDestino_enc.setNome_cli("Frango");
//        clienteDestino_enc.setTelefones_cli(telefonesC1);
//        obj_Enc.setClienteDestino_enc(clienteDestino_enc);
//        obj_Enc.setCodigoIdent_enc("123456789");
//        Cidade cidade = new Cidade();
//        Estado estado = new Estado();
//        estado.setNome_est("Minas Gerais");
//        estado.setObjCod_objBase(5);
//        cidade.setEstado_cid(estado);
//        cidade.setNome_cid("Ubá");
//        Regiao regiao1 = new Regiao();
//        regiao1.setNomeDaRegiao_reg("Zona da Mata");
//        regiao1.setObjCod_objBase(5);
//        cidade.setRegiao_cid(regiao1);
//        cidade.setObjCod_objBase(7);
//        Endereco enderecoOrigem_enc = new Endereco();
//        enderecoOrigem_enc.setCEP_end("36500-000");
//        enderecoOrigem_enc.setObjCod_objBase(8);
//        enderecoOrigem_enc.setBairro_end("Teste");
//        enderecoOrigem_enc.setNumero_end(123);
//        enderecoOrigem_enc.setLogradouro_end("Rua João");
//        enderecoOrigem_enc.setCidade_end(cidade);
//        Endereco enderecoDestino_enc = new Endereco();
//        enderecoDestino_enc.setCEP_end("36500-000");
//        enderecoDestino_enc.setObjCod_objBase(7);
//        enderecoDestino_enc.setBairro_end("Teste");
//        enderecoDestino_enc.setNumero_end(123);
//        enderecoDestino_enc.setLogradouro_end("Rua José");
//        enderecoDestino_enc.setCidade_end(cidade);
//        obj_Enc.setEnderecoOrigem_enc(enderecoOrigem_enc);
//        obj_Enc.setEnderecoDestino_enc(enderecoDestino_enc);
//        obj_Enc.setEstado_enc("Entregue");
//        MeioTransporte meioTransporte_enc = new MeioTransporte();
//        meioTransporte_enc.setAnoFabricacao_mt("2012");
//        meioTransporte_enc.setCapacidadeCarga_mt(3.5);
//        meioTransporte_enc.setCapacidadeVolume_mt(30);
//        meioTransporte_enc.setClassificacaoModalidade_mt("Terrestre");
//        meioTransporte_enc.setClassificacaoPeso_mt("Acima de 3.5");
//        meioTransporte_enc.setValorPorKM_mt(4);
//        meioTransporte_enc.setObjCod_objBase(4);
//        obj_Enc.setMeioTransporte_enc(meioTransporte_enc);
//        ItemTransporte it1 = new ItemTransporte();
//        it1.setDescricao_item("Item 1");
//        it1.setEncomenda_item(obj_Enc);
//        it1.setObjCod_objBase(10);
//        it1.setPeso_item(2);
//        it1.setTipoEmbalagem_item("Caixa Papelão");
//        ItemTransporte it2 = new ItemTransporte();
//        it2.setDescricao_item("Item 2");
//        it2.setEncomenda_item(obj_Enc);
//        it2.setObjCod_objBase(11);
//        it2.setPeso_item(1);
//        it2.setTipoEmbalagem_item("Plástico");
//        ArrayList obj_Item = new ArrayList();
//        obj_Item.add(it1);
//        obj_Item.add(it2);
//        obj_Enc.setObjCod_objBase(1);
//
//        Funcionario funcionario = new Funcionario();
//        funcionario.setObjCod_objBase(10);
//        funcionario.setNome("Furrô");
//        funcionario.setIdentidadeFuncional_func("12345678");
//        funcionario.setIdentidadePessoal_func("87654321");
//        Funcionario funcionario2 = new Funcionario();
//        funcionario2.setObjCod_objBase(11);
//        funcionario2.setNome("Frango");
//        funcionario2.setIdentidadeFuncional_func("2345678");
//        funcionario2.setIdentidadePessoal_func("8765432");
//        ArrayList<Funcionario> funcionarios = new ArrayList<>();
//        funcionarios.add(funcionario);
//        funcionarios.add(funcionario2);
//        GregorianCalendar gc = new GregorianCalendar();
//        Date data = gc.getTime();
//        Telefone telefone = new Telefone();
//        telefone.setDDD("32");
//        telefone.setDDI("+55");
//        telefone.setNumero("123456789");
//        telefone.setObjCod_objBase(10);
//        Telefone telefone2 = new Telefone();
//        telefone2.setDDD("33");
//        telefone2.setDDI("+55");
//        telefone2.setNumero("12345679");
//        telefone2.setObjCod_objBase(11);
//        ArrayList<Telefone> telefones = new ArrayList<>();
//        telefones.add(telefone);
//        telefones.add(telefone2);
//        Endereco enderecoDist = new Endereco();
//        Cidade cubatao = new Cidade();
//        Estado saoPaulo = new Estado();
//        Regiao regiaoCubatao = new Regiao();
//        enderecoDist.setBairro_end("Bairrão");
//        enderecoDist.setCEP_end("36580-000");
//        saoPaulo.setNome_est("São Paulo");
//        saoPaulo.setObjCod_objBase(90);
//        regiao1.setNomeDaRegiao_reg("Planalto");
//        regiao1.setObjCod_objBase(8);
//        cubatao.setEstado_cid(saoPaulo);
//        cubatao.setNome_cid("Cubatão");
//        cubatao.setRegiao_cid(regiaoCubatao);
//        enderecoDist.setCidade_end(cubatao);
//        Distribuidora distribuidora = new Distribuidora();
//        distribuidora.setEndereco_dist(enderecoDestino_enc);
//        distribuidora.setFuncionarios_dist(funcionarios);
//        distribuidora.setNomeFantasia_dist("Distribuidora 1");
//        distribuidora.setRazaoSocial_dist("Distribuidora LTDA");
//        distribuidora.setTelefone_dist(telefones);
//        distribuidora.setObjCod_objBase(1);
//
//        Funcionario funcionario3 = new Funcionario();
//        funcionario3.setObjCod_objBase(12);
//        funcionario3.setNome("Luiz");
//        funcionario3.setIdentidadeFuncional_func("123451678");
//        funcionario3.setIdentidadePessoal_func("876543321");
//        Funcionario funcionario4 = new Funcionario();
//        funcionario4.setObjCod_objBase(13);
//        funcionario4.setNome("Abenr");
//        funcionario4.setIdentidadeFuncional_func("42345678");
//        funcionario4.setIdentidadePessoal_func("87654312");
//        ArrayList<Funcionario> funcionarios2 = new ArrayList<>();
//        funcionarios2.add(funcionario3);
//        funcionarios2.add(funcionario4);
//        Telefone telefone3 = new Telefone();
//        telefone3.setDDD("32");
//        telefone3.setDDI("+55");
//        telefone3.setNumero("123456789");
//        telefone3.setObjCod_objBase(12);
//        Telefone telefone4 = new Telefone();
//        telefone4.setDDD("33");
//        telefone4.setDDI("+55");
//        telefone4.setNumero("12345679");
//        telefone4.setObjCod_objBase(13);
//        ArrayList<Telefone> telefones2 = new ArrayList<>();
//        telefones2.add(telefone3);
//        telefones2.add(telefone4);
//        Endereco enderecoDist2 = new Endereco();
//        Cidade butanta = new Cidade();
//        Estado minasGerais = new Estado();
//        Regiao regiaoButanta = new Regiao();
//        enderecoDist2.setBairro_end("Bairrão");
//        enderecoDist2.setCEP_end("36520-000");
//        minasGerais.setNome_est("Minas Gerais");
//        minasGerais.setObjCod_objBase(5);
//        regiaoButanta.setNomeDaRegiao_reg("Baixada");
//        regiaoButanta.setObjCod_objBase(6);
//        butanta.setEstado_cid(minasGerais);
//        butanta.setNome_cid("Butantã");
//        butanta.setRegiao_cid(regiaoButanta);
//        enderecoDist2.setCidade_end(butanta);
//        Distribuidora distribuidora2 = new Distribuidora();
//        distribuidora2.setEndereco_dist(enderecoDist2);
//        distribuidora2.setFuncionarios_dist(funcionarios2);
//        distribuidora2.setNomeFantasia_dist("Distribuidora 2");
//        distribuidora2.setRazaoSocial_dist("Distribuidora 2 LTDA");
//        distribuidora2.setTelefone_dist(telefones);
//        distribuidora2.setObjCod_objBase(2);
//        Historico historico = new Historico();
//        historico.setEncomenda_hist(obj_Enc);
//        historico.setDataChegada_hist(data);
//        historico.setDataSaida_hist(data);
//        historico.setFuncionarioResp_hist(funcionario);
//        historico.setMeioTransporte_hist(meioTransporte_enc);
//        historico.setDistribuidoraDestino_hist(distribuidora);
//        historico.setDistribuidoraOrigem_hist(distribuidora2);
//        historico.setObjCod_objBase(1);
//        teste.GravacaoEncomenda(obj_Enc, obj_Item, historico);
//        List itens = FuncoesJPA.Selecionar(ItemTransporte.class, " WHERE U.objCod_objBase=" + 10);
//        System.out.println("Total itens recuperados: " + itens.size());
//        System.out.println("Fim do processamento");
//
//        teste.transacaoGravacaoEncomenda(obj_Enc, obj_Item);
//        System.out.println("Fim do processamento");*/
//
//        // Cadastros Via REST
//        //     CategoriaFrete categoriaFrete = new CategoriaFrete();
////        categoriaFrete.setDescricao_cfrt("Lucas");
////        categoriaFrete.setPercentual_cfrt(2);
////        
////        PersistenciaJPA<CategoriaFrete> persistenciaJPACategoria = new PersistenciaJPA<CategoriaFrete>(CategoriaFrete.class);
////        persistenciaJPACategoria.salvar(categoriaFrete);
////        
////        Cidade cidade = new Cidade();
////        cidade.setObjCod_objBase(1);//        PersistenciaJPA<Cidade> persistenciaJPACategoria2 = new PersistenciaJPA<Cidade>(Cidade.class);
//        persistenciaJPACategoria2.salvar(cidade);
//        System.out.println("Chamando tela login");
//        View.Login.main(args);
        /*JsonParser objParser = new JsonParser();
        String        JsonParser objParser = new JsonParser();
                String dados = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/mesorregioes");
                JsonElement objJsonElement = objParser.parse(dados);
                JsonArray obj = objJsonElement.getAsJsonArray();
                System.out.println(obj);
                for (int i = 0; i < obj.size(); i++) {
                Regiao regiao = new Regiao();
                regiao.setNomeDaRegiao_reg(obj.get(i).getAsJsonObject().get("nome").getAsString());
                new CtrlRegiao().Salvar(new Gson().toJson(regiao));
                }
                
                dados = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
                objJsonElement = objParser.parse(dados);
                obj = objJsonElement.getAsJsonArray();
                System.out.println(obj);
                
                for (int i = 0; i < obj.size(); i++) {
                
                new CtrlEstado().Salvar(new Gson().toJson(new Estado(obj.get(i).getAsJsonObject().get("sigla").getAsString())));
                }
                
                dados = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
                objJsonElement = objParser.parse(dados);
                obj = objJsonElement.getAsJsonArray();
                System.out.println(obj);
                String nom;
                for (int i = 0; i < obj.size(); i++) {
                nom = obj.get(i).getAsJsonObject().get("sigla").getAsString();
                int id = obj.get(i).getAsJsonObject().get("id").getAsInt();
                String dados2 = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + id + "/municipios");
                objJsonElement = objParser.parse(dados2);
                JsonArray obj2 = objJsonElement.getAsJsonArray();
                for (int j = 0; j < obj2.size(); j++) {
                String sql = " WHERE U.nome_est LIKE \"" + nom + "\"";
                Cidade regiao = new Cidade();
                List<Estado> a = (List<Estado>) FuncoesJPA.Selecionar(Estado.class, sql);
                regiao.setEstado_cid((a.get(0)));
                sql = " WHERE U.nomeDaRegiao_reg LIKE " + obj2.get(j).getAsJsonObject().get("microrregiao").getAsJsonObject().get("mesorregiao").getAsJsonObject().get("nome");
                regiao.setRegiao_cid((Regiao) FuncoesJPA.Selecionar(Regiao.class, sql).get(0));
                regiao.setNome_cid(obj2.get(j).getAsJsonObject().get("nome").getAsString());
                new CtrlCidade().Salvar(new Gson().toJson(regiao));
                }
                }
                }dados = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/mesorregioes");
        JsonElement objJsonElement = objParser.parse(dados);
        JsonArray obj = objJsonElement.getAsJsonArray();
        System.out.println(obj);
        for (int i = 0; i < obj.size(); i++) {
        Regiao regiao = new Regiao();
        regiao.setNomeDaRegiao_reg(obj.get(i).getAsJsonObject().get("nome").getAsString());
        new CtrlRegiao().Salvar(new Gson().toJson(regiao));
        }

        dados = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
        objJsonElement = objParser.parse(dados);
        obj = objJsonElement.getAsJsonArray();
        System.out.println(obj);

        for (int i = 0; i < obj.size(); i++) {

            new CtrlEstado().Salvar(new Gson().toJson(new Estado(obj.get(i).getAsJsonObject().get("sigla").getAsString())));
        }

        dados = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
        objJsonElement = objParser.parse(dados);
        obj = objJsonElement.getAsJsonArray();
        System.out.println(obj);
        String nom;
        for (int i = 0; i < obj.size(); i++) {
            nom = obj.get(i).getAsJsonObject().get("sigla").getAsString();
            int id = obj.get(i).getAsJsonObject().get("id").getAsInt();
            String dados2 = Util.Util.consumirAPI("https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + id + "/municipios");
            objJsonElement = objParser.parse(dados2);
            JsonArray obj2 = objJsonElement.getAsJsonArray();
            for (int j = 0; j < obj2.size(); j++) {
                String sql = " WHERE U.nome_est LIKE \"" + nom + "\"";
                Cidade regiao = new Cidade();
                List<Estado> a = (List<Estado>) FuncoesJPA.Selecionar(Estado.class, sql);
                regiao.setEstado_cid((a.get(0)));
                sql = " WHERE U.nomeDaRegiao_reg LIKE " + obj2.get(j).getAsJsonObject().get("microrregiao").getAsJsonObject().get("mesorregiao").getAsJsonObject().get("nome");
                regiao.setRegiao_cid((Regiao) FuncoesJPA.Selecionar(Regiao.class, sql).get(0));
                regiao.setNome_cid(obj2.get(j).getAsJsonObject().get("nome").getAsString());
                new CtrlCidade().Salvar(new Gson().toJson(regiao));
            }
        }*/
    }
}
