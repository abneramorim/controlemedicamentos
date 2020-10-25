# Sistema para controle de medicamentos
Esse sistema foi desenvolvido para controle de estoque de medicamentos e registro de atendimentos

#### Tela de Login
![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/login.png "Tela de Login")

#### Tela Principal
Tela para onde é direcionado ao realizar login. Nela tem as informações de produtos que estão abaixo da quantidade mínima e os lotes que estão próximos da data de vencimento. No topo da tela ficam os menus do sistema.
![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/principal.png)

#### Cadastros
É necessário o cadastro de entidades no sistema. Sendo elas, Cliente, Fabricante, Médico Solicitante, Usuário, Produto e Atendimento.

##### Tela de Cadastro - Pessoas
As telas de cadastro de pessoas seguem o mesmo padrão no sistema, tendo descrição/nome e identificador da pessoa (Número cartão, CRM ou CNPJ)

![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/cadastro_cliente.png)

##### Tela de Cadastro - Produto

O cadastro de produto requer que sejam preenchidos os seguintes campos:
![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/cadastro_produto.png)

**Cabeçalho Produto -**
O código identificador será utilizado para buscar o produto no lançamento do atendimento
A quantidade mínima (que será considerada para exibição na tela principal) 
A Unidade de Medida é apenas informativo, caso precise cadastrar uma nova unidade é só clicar no ícone +

**Lote -**
O Código do lote é um campo *unique* portanto, não é permitido criar dois lotes com o mesmo código
O Fabricante pode ser buscado pelo CNPJ, caso não localize será aberta a tela de pesquisa de Fabricantes onde será possível pesquisa por outros campos do cadastro do Fabricante.

##### Tela de cadastro - Atendimento
O cadastro do atendimento registrará todos os medicamentos que foram utilizados naquele atendimento, o cliente que foi atendido e o Médico responsável. Também é possível inserir informações do atendimento no campo Cuidados.
Em todos os campos do sistema que têm o botão Lupa para pesquisar, caso não localize registros com a informação inserida, será aberta a tela de pesquisa da entidade correspondente.
![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/cadastro_atendimento.png)

- O cliente é localizado pelo número da carteira
- O produto é localizado pelo identificador cadastrado
- A flag Medicamento Externo é utilizada para indicar que o produto não deverá ser abatido no estoque


#### Pesquisas

As telas de pesquisas permitem que as entidades do sistema sejam localizadas.

![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/pesquisa_produto.png)

As telas de pesquisa de Cliente, Médico Solicitante, Fabricante ou Produto seguem o mesmo padrão. Para realizar a pesquisa basta selecionar o campo do cadastro que será filtrado no combo Opções.
Nessas telas para abrir o produto basta clicar em Selecionar ou dar duplo clique no registro que deseja abrir o cadastro.

##### Tela de pesquisa - Atendimento

Como o atendimento possui vários campos que tornam a busca mais complexa a tela foi montada de forma diferenciada, permitindo o uso das outras telas de pesquisas para facilitar o uso.

![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/pesquisa_atendimento.png)

**Obs:** Os campos que possuem Lupa caso não localize um registro com o conteúdo pesquisado será aberta a tela de pesquisa já exibida anteriormente.

#### Relatórios

A tela de relatórios exibirá todos os lotes que estão cadastrados no sistema, a quantidade disponível, utilizada e total.

![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/relatorio_inventario.png)

#### Conexão Banco de Dados

Caso precise fazer algum ajuste na configuração de conexão com o banco de dados, para não precisar alterar diretamente no arquivo, basta alterar em tela.

![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/configuracao_conexao_banco.png)
