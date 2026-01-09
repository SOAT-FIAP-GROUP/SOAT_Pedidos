Feature: Pedidos
  Como usuário desejo consultar pedidos no sistema

  @Pedido
  Scenario: Consultar pedido com sucesso
    Given que foi cadastrado um pedido no sistema
    When o servico invocar a funcao de buscar pedido
    Then a funcao deve ser executada com sucesso