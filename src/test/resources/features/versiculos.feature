Feature: Gerenciar Versículos Litúrgicos

  Scenario: Cadastrar novo versículo
    Given um novo versículo do tipo EVANGELHO
    When eu salvo o versículo
    Then ele deve ser armazenado no sistema

  Scenario: Listar todos os versículos
    When eu solicito a lista de versículos
    Then deve retornar uma lista de versículos

  Scenario: Buscar versículo por data
    Given existe um versículo para a data "2025-04-27"
    When eu busco versículos por essa data
    Then o versículo correspondente deve ser retornado

  Scenario: Atualizar um versículo existente
    Given um versículo existente
    When eu atualizo o conteúdo do versículo
    Then o sistema deve refletir a atualização

  Scenario: Remover um versículo
    Given um versículo existente
    When eu deleto o versículo
    Then ele não deve mais existir no sistema
