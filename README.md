Acess Smart - Sistema de Gestão de Acessibilidade

Acess Smart é um sistema de linha de comando para gestão de recursos de acessibilidade, desenvolvido como projeto final para a disciplina de Teste de Software do curso de Engenharia de Computação do IFPB - Campus Campina Grande.


Índice

Sobre o Projeto

Funcionalidades

Padrões de Projeto Aplicados

Tecnologias Utilizadas

Como Executar o Projeto

Como Executar os Testes

Autores


1. Sobre o Projeto
O objetivo do projeto é desenvolver um sistema de gestão de acessibilidade para o IFPB, permitindo que usuários e a administração identifiquem, monitorem e gerenciem recursos como rampas, elevadores e salas adaptadas. A aplicação busca otimizar o uso e a manutenção desses recursos, promovendo um ambiente mais inclusivo e acessível para estudantes, professores e funcionários.

O projeto foi desenvolvido para a disciplina de Padrões de Projeto, ministrada pelo Professor Breno Romero, no semestre 2025.

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

3. Padrões de Projeto Aplicados
A arquitetura do sistema foi construída utilizando diversos padrões de projeto para garantir organização, flexibilidade e manutenibilidade:

Facade: Centraliza o acesso a todas as funcionalidades do sistema através de uma interface única e simplificada, a classe Facade.

Singleton: Garante que exista apenas uma instância da Facade em toda a aplicação, evitando múltiplos pontos de controle.

Observer: Permite que os usuários sejam notificados automaticamente sobre alterações importantes, como o status de uma reserva ou um novo aviso de manutenção.

State: Gerencia os estados de uma reserva (Pendente, Confirmada, Finalizada, Cancelada), permitindo que o comportamento do objeto Reservation mude de acordo com seu estado, de forma segura e organizada.

Command: Encapsula cada ação do usuário (como fazer uma reserva ou registrar um feedback) como um objeto, permitindo a criação de filas de execução e facilitando a implementação de novas ações.

Factory Method: Utilizado para criar diferentes tipos de recursos (Rampa, Elevador, etc.) de forma desacoplada, sem expor a lógica de criação ao cliente.

Template Method: Define o esqueleto de um algoritmo na classe base ReportTemplate, permitindo que as subclasses (reportutilizacao) redefinam etapas específicas para gerar diferentes tipos de relatórios.

4. Tecnologias Utilizadas

Java: Linguagem principal de desenvolvimento do sistema.

JUnit 5: Framework utilizado para a criação e execução dos testes unitários e parametrizados.

5. Como Executar o Projeto
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
6. Como Executar os Testes
A maneira mais simples de executar os testes é através de uma IDE como Eclipse ou IntelliJ IDEA.

Importe o projeto na sua IDE.

Navegue até a pasta de testes (src/Testes_projeto_testes_de_software/).

Clique com o botão direito nos arquivos ProjetoTest.java ou ProjetoParameterizedTest.java.

Selecione a opção "Run As" > "JUnit Test".

A IDE exibirá os resultados dos testes em uma janela específica, mostrando quais passaram e quais falharam.

7. Autores

Allan Victor Fonseca Pontes

Andreza Costa dos Santos

Lavoisier Chaves Ramos

Nivaldo Pereira da Silva Neto

Vinícius Cavalcante Barbosa






