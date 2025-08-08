Acess Smart - Sistema de Gestão de Acessibilidade

Acess Smart é um sistema de linha de comando para gestão de recursos de acessibilidade, desenvolvido como projeto final para a disciplina de Teste de Software do curso de Engenharia de Computação do IFPB - Campus Campina Grande.


Índice

Sobre o Projeto

Funcionalidades

Tecnologias Utilizadas

Como Executar o Projeto

Como Executar os Testes

Autores


1. Sobre o Projeto
O objetivo do projeto é desenvolver um sistema de gestão de acessibilidade para o IFPB, permitindo que usuários e a administração identifiquem, monitorem e gerenciem recursos como rampas, elevadores e salas adaptadas. A aplicação busca otimizar o uso e a manutenção desses recursos, promovendo um ambiente mais inclusivo e acessível para estudantes, professores e funcionários.

O projeto foi desenvolvido para a disciplina de Teste de Software, ministrada pelo Professor Breno Romero, no semestre 2025.

2. Funcionalidades
O sistema oferece funcionalidades distintas para dois tipos de usuários: Comum e Administrador.

Para Usuários Comuns:
Fazer login e se registrar no sistema.

Consultar recursos de acessibilidade disponíveis.

Fazer, consultar e finalizar suas próprias reservas de salas e equipamentos adaptados.

Registrar feedback sobre o sistema ou sobre os recursos.

Solicitar manutenção para um recurso que apresente problemas.

Para Administradores:
Todas as funcionalidades de um usuário comum.

Adicionar e consultar todos os recursos de acessibilidade.

Consultar e gerenciar o estado de 

todas as reservas do sistema (confirmar, cancelar, etc.).

Promover outros usuários a administradores.

Gerar relatórios estatísticos de uso de recursos e de feedbacks dos usuários.

Agendar e comunicar períodos de manutenção para os recursos.

3. Tecnologias Utilizadas

Java: Linguagem principal de desenvolvimento do sistema.

JUnit 5: Framework utilizado para a criação e execução dos testes unitários e parametrizados.

4. Como Executar o Projeto
Pré-requisitos:

Java Development Kit (JDK) 11 ou superior instalado.

Passos para execução via linha de comando:

Clone o repositório:

Bash

git clone https://github.com/seu-usuario/acess-smart.git
Navegue até o diretório do projeto:

Bash

cd acess-smart
Compile os arquivos .java (execute este comando a partir do diretório raiz do projeto):

Bash

javac -d bin src/acesssmart/*.java src/Testes_projeto_testes_de_software/*.java
Execute a classe principal:

Bash

java -cp bin acesssmart.Main
5. Como Executar os Testes
A maneira mais simples de executar os testes é através de uma IDE como Eclipse ou IntelliJ IDEA.

Importe o projeto na sua IDE.

Navegue até a pasta de testes (src/Testes_projeto_testes_de_software/).

Clique com o botão direito nos arquivos ProjetoTest.java ou ProjetoParameterizedTest.java.

Selecione a opção "Run As" > "JUnit Test".

A IDE exibirá os resultados dos testes em uma janela específica, mostrando quais passaram e quais falharam.

6. Autores

Allan Victor Fonseca Pontes

Andreza Costa dos Santos

Lavoisier Chaves Ramos

Nivaldo Pereira da Silva Neto

Vinícius Cavalcante Barbosa

7 – Relação dos Testes Executados
Nesta seção, são apresentados os resultados da execução da suíte de testes automatizados desenvolvida para o sistema Acess Smart. A suíte é composta por testes unitários tradicionais e por testes parametrizados, ambos criados com o framework JUnit 5. A execução foi realizada em ambiente de desenvolvimento e todos os testes foram concluídos com sucesso, indicando que as funcionalidades do sistema estão operando conforme o especificado.

7.1 – Execução dos Testes Unitários (ProjetoTest.java)
Foram executados 29 testes unitários que cobrem os principais fluxos de funcionalidades do sistema, incluindo cenários de sucesso e de falha. Todos os 29 testes passaram.

Sumário da Execução:

Total de testes executados: 29

Testes que passaram: 29

Testes que falharam: 0

Resultado: ✅ SUCESSO

Amostra de Testes Executados com Sucesso:

✅ Teste de Login e Usuários: Deve logar com sucesso como admin

✅ Teste de Login e Usuários: Deve falhar o login com dados inválidos

✅ Teste de Login e Usuários: Não deve permitir registrar usuário que já existe

✅ Teste de Gerenciamento de Recursos: Deve adicionar um novo recurso

✅ Teste de Gerenciamento de Reservas: Deve criar uma nova reserva

✅ Teste de Gerenciamento de Reservas: Não deve permitir alterar estado de reserva cancelada

✅ Teste de Manutenção de Recursos: Deve impedir reserva durante manutenção agendada

✅ Teste de Feedbacks e Relatórios: Deve gerar relatório de feedbacks

7.2 – Execução dos Testes Parametrizados (ProjetoParameterizedTest.java)
Foram executados 2 testes parametrizados, que se desdobraram em um total de 9 execuções individuais com diferentes conjuntos de dados, validando de forma eficiente múltiplos cenários com o mínimo de código.

Sumário da Execução:

Total de testes parametrizados: 2

Total de invocações: 9

Invocações com sucesso: 9

Invocações com falha: 0

Resultado: ✅ SUCESSO

Detalhes da Execução:

testeLoginParametrizado (4 invocações):

[1] login=admin, senha=admin123, deveLogarComSucesso=true ➔ ✅ SUCESSO

[2] login=user, senha=user123, deveLogarComSucesso=true ➔ ✅ SUCESSO

[3] login=login_errado, senha=senha_errada, deveLogarComSucesso=false ➔ ✅ SUCESSO

[4] login='', senha='', deveLogarComSucesso=false ➔ ✅ SUCESSO

testeRegistroUsuarioParametrizado (5 invocações):

[1] login=admin (existente) ➔ ✅ SUCESSO (Exceção esperada foi lançada)

[2] nome='' ➔ ✅ SUCESSO (Exceção esperada foi lançada)

[3] login='' ➔ ✅ SUCESSO (Exceção esperada foi lançada)

[4] senha='' ➔ ✅ SUCESSO (Exceção esperada foi lançada)

[5] login=NULL ➔ ✅ SUCESSO (Exceção esperada foi lançada)

Conclusão dos Testes

A execução de toda a suíte de testes foi concluída com 100% de sucesso. Isso demonstra que as funcionalidades implementadas no sistema Acess Smart, desde a autenticação de usuários até o gerenciamento de reservas e manutenções, estão operando conforme o especificado nos requisitos e nos casos de teste, atestando a robustez e a correção do código-fonte. Não foram encontradas falhas que necessitassem de indicações de correção.






