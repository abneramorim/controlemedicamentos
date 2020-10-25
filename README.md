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

**Cabeçalho Produto**
O código identificador será utilizado para buscar o produto no lançamento do atendimento
A quantidade mínima (que será considerada para exibição na tela principal) 
A Unidade de Medida é apenas informativo, caso precise cadastrar uma nova unidade é só clicar no ícone +

**Lote**
O Código do lote é um campo *unique* portanto, não é permitido criar dois lotes com o mesmo código
O Fabricante pode ser buscado pelo CNPJ, caso não localize será aberta a tela de pesquisa de Fabricantes onde será possível pesquisa por outros campos do cadastro do Fabricante.

##### Tela de cadastro - Atendimento
O cadastro do atendimento registrará todos os medicamentos que foram utilizados naquele atendimento, o cliente que foi atendido e o Médico responsável. Também é possível inserir informações do atendimento no campo Cuidados.
Em todos os campos do sistema que têm o botão Lupa para pesquisar, caso não localize registros com a informação inserida, será aberta a tela de pesquisa da entidade correspondente.
![Alt text](https://github.com/abneramorim/meuprojeto/blob/master/cadastro_atendimento.png)

**-** O cliente é localizado pelo número da carteiral
**-** O produto é localizado pelo identificador cadastrado
**-** A flag Medicamento Externo é utilizada para indicar que o produto não deverá ser abatido no estoque
