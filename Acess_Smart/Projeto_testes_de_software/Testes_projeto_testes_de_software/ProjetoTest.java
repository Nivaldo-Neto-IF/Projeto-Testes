package Testes_projeto_testes_de_software;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import acesssmart.Facade;
import acesssmart.Usuario;

class ProjetoTest {

    private Facade facade;
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() {
        Facade.resetInstanceForTesting();
        facade = Facade.getInstance();
        System.setOut(new PrintStream(outContent));
    }
    
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
    
    private void simulateInput(String data) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(data.getBytes());
        System.setIn(inContent);
    }

    // testes de login e registro
    @ParameterizedTest(name = "[{index}] Cenário de Login: {0}")
    @CsvSource({
     
        "Admin válido,         admin,               admin123,       true,       true",
        "Usuário comum válido, user,                user123,        true,       false",
        "Dados inválidos,      login_errado,        senha_errada,   false,      false",
        "Campos vazios,        '',                  '',             false,      false"
    })
    void testeLogin(String cenario, String login, String senha, boolean deveLogarComSucesso, boolean isAdmin) {
        if (deveLogarComSucesso) {
            assertDoesNotThrow(() -> {
                Usuario user = facade.login(login, senha);
                assertNotNull(user, "O usuário não deveria ser nulo para um login válido.");
                assertEquals(isAdmin, user.isAdmin(), "O status de admin do usuário não corresponde ao esperado.");
            });
        } else {
            Exception exception = assertThrows(Exception.class, () -> facade.login(login, senha));
            assertEquals("Login inválido.", exception.getMessage());
        }
    }

    @ParameterizedTest(name = "[{index}] Cenário de Registro: {0}")
    @CsvSource(value = {
  
        "Login já existente,         Novo Admin,    admin,          outrasenha, 'Este Login está Indisponivel tente novamente'",
        "Nome vazio,                 '',             loginvalido,    senha123,   'Nome, login e senha não podem ser nulos ou vazios.'",
        "Login vazio,                Nome Valido,   '',             senha123,   'Nome, login e senha não podem ser nulos ou vazios.'",
        "Senha vazia,                Nome Valido,   loginvalido,    '',         'Nome, login e senha não podem ser nulos ou vazios.'",
        "Login nulo,                 Nome Valido,   NULL,           senha123,   'Nome, login e senha não podem ser nulos ou vazios.'"
    }, nullValues = "NULL")
    void testeRegistro(String cenario, String nome, String login, String senha, String mensagemDeErroEsperada) {
        Exception exception = assertThrows(Exception.class, () -> facade.registrarUsuario(nome, login, senha, false));
        assertEquals(mensagemDeErroEsperada, exception.getMessage());
    }

    // testes de recursos
    @Test
    @DisplayName("Teste de Gerenciamento de Recursos: Deve adicionar um novo recurso")
    void testAdicionarRecurso() throws Exception {
        facade.login("admin", "admin123");
        facade.adicionarRecurso("rampa");
        assertTrue(outContent.toString().contains("Recurso adicionado: Rampa da quadra"));
    }

    @Test
    @DisplayName("Teste de Gerenciamento de Recursos: Deve consultar a lista de recursos")
    void testConsultarRecursos() throws Exception {
        facade.login("admin", "admin123");
        facade.consultarRecursos();
        String output = outContent.toString();
        assertTrue(output.contains("Recursos disponíveis:"));
    }

    // testes de reserva
    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve criar uma nova reserva")
    void testFazerNovaReserva() throws Exception {
        Usuario user = facade.login("user", "user123");
        facade.fazerReserva(user.getId(), 2, LocalDateTime.now().plusHours(5));
        facade.executarComandos();
        assertTrue(outContent.toString().contains("Sua reserva foi criada com sucesso"));
    }
    
    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve consultar as 'minhas reservas'")
    void testConsultarMinhasReservas() throws Exception {
        Usuario user = facade.login("user", "user123");
        facade.fazerReserva(user.getId(), 1, LocalDateTime.now().plusDays(1));
        facade.executarComandos();
        outContent.reset();
        facade.consultarReservasUsuario(user.getId());
        assertTrue(outContent.toString().contains("Reservas do usuário Nivaldo"));
    }

    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve consultar todas as reservas (admin)")
    void testConsultarTodasReservas() throws Exception {
        facade.fazerReserva(2, 1, LocalDateTime.now().plusDays(3));
        facade.executarComandos();
        facade.login("admin", "admin123");
        facade.consultarTodasReservas();
        assertTrue(outContent.toString().contains("ID: 1"));
    }
    
    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve finalizar uma reserva ativa")
    void testFinalizarReserva() throws Exception {
        facade.fazerReserva(2, 1, LocalDateTime.now().plusDays(2));
        facade.executarComandos();
        facade.finalizarReserva(1);
        assertTrue(outContent.toString().contains("Reserva 1 finalizada."));
    }
    
    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve falhar ao finalizar uma reserva inexistente")
    void testFinalizarReservaInexistente() {
        assertThrows(Exception.class, () -> facade.finalizarReserva(888));
    }

    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve alterar o estado de uma reserva")
    void testAlterarEstadoReserva() throws Exception {
        facade.fazerReserva(2, 1, LocalDateTime.now().plusDays(1));
        facade.executarComandos();
        facade.alterarEstadoReserva(1, 2); // 2: Confirmada
        assertTrue(outContent.toString().contains("Reserva confirmada!"));
    }

    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve falhar ao alterar estado de reserva inexistente")
    void testAlterarEstadoReservaInexistente() {
        assertThrows(Exception.class, () -> facade.alterarEstadoReserva(999, 2));
    }
    
    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Deve falhar ao alterar para um estado de código inválido")
    void testAlterarEstadoReservaParaEstadoInvalido() throws Exception {
        facade.fazerReserva(2, 1, LocalDateTime.now().plusDays(1));
        facade.executarComandos();
        assertThrows(Exception.class, () -> facade.alterarEstadoReserva(1, 99));
    }

    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Não deve permitir alterar estado de reserva cancelada")
    void testNaoPodeAlterarEstadoDeReservaCancelada() throws Exception {
        facade.fazerReserva(2, 1, LocalDateTime.now().plusDays(10));
        facade.executarComandos();
        facade.alterarEstadoReserva(1, 3); // 3: Cancelada
        outContent.reset();
        facade.alterarEstadoReserva(1, 2); // Tenta confirmar
        assertTrue(outContent.toString().contains("Reserva cancelada, não pode ser alterado o estado"));
    }
    
    @Test
    @DisplayName("Teste de Gerenciamento de Reservas: Não deve permitir cancelar reserva finalizada")
    void testNaoPodeCancelarReservaFinalizada() throws Exception {
        facade.fazerReserva(2, 1, LocalDateTime.now().plusDays(10));
        facade.executarComandos();
        facade.finalizarReserva(1);
        outContent.reset();
        facade.alterarEstadoReserva(1, 3); // Tenta cancelar
        assertTrue(outContent.toString().contains("Reserva finalizada não pode ser cancelada."));
    }

    // testes de manutenção de recursos
    @Test
    @DisplayName("Teste de Manutenção de Recursos: Deve impedir reserva com aviso de manutenção")
    void testFazerReservaRecursoEmManutencao() throws Exception {
        facade.gerenciarManutencaoRecurso(1, 1, "Reparos elétricos");
        facade.fazerReserva(2, 1, LocalDateTime.now().plusDays(1));
        assertTrue(outContent.toString().contains("Este recurso está em manutenção e não pode ser reservado."));
    }

    @Test
    @DisplayName("Teste de Manutenção de Recursos: Deve impedir reserva durante manutenção agendada")
    void testFazerReservaDuranteManutencaoAgendada() throws Exception {
        LocalDateTime inicioManutencao = LocalDateTime.now().plusDays(1);
        facade.gerarAgendamentoManutencao(1, inicioManutencao, inicioManutencao.plusHours(5));
        facade.fazerReserva(2, 1, inicioManutencao.plusHours(2));
        facade.executarComandos();
        assertTrue(outContent.toString().contains("O recurso está em manutenção no horário escolhido."));
    }

    @Test
    @DisplayName("Teste de Manutenção de Recursos: Deve definir um aviso de manutenção")
    void testDefinirAvisoManutencao() {
        facade.gerenciarManutencaoRecurso(1, 1, "Pintura");
        assertTrue(outContent.toString().contains("Aviso de manutenção definido para o recurso ID 1: Pintura"));
    }

    @Test
    @DisplayName("Teste de Manutenção de Recursos: Deve remover um aviso de manutenção")
    void testRemoverAvisoManutencao() {
        facade.gerenciarManutencaoRecurso(1, 1, "Pintura");
        outContent.reset();
        facade.gerenciarManutencaoRecurso(1, 2, "");
        assertTrue(outContent.toString().contains("Aviso de manutenção removido para o recurso ID 1"));
    }

    @Test
    @DisplayName("Teste de Manutenção de Recursos: Deve agendar uma nova manutenção")
    void testAgendarManutencao() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(5);
        facade.gerarAgendamentoManutencao(1, inicio, inicio.plusHours(8));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        assertTrue(outContent.toString().contains("Manutenção agendada:"));
        assertTrue(outContent.toString().contains(inicio.format(formatter)));
    }

    @Test
    @DisplayName("Teste de Manutenção de Recursos: Deve consultar manutenções agendadas")
    void testConsultarPedidosManutencao() {
        facade.gerarAgendamentoManutencao(2, LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(8));
        outContent.reset();
        facade.consultarManutencao();
        String output = outContent.toString();
        assertTrue(output.contains("Manutenções agendadas:"));
        assertTrue(output.contains("Recurso: Sala Adaptada"));
    }

    @Test
    @DisplayName("Teste de Manutenção de Recursos: Usuário deve poder solicitar manutenção")
    void testSolicitarManutencao() throws Exception {
        Usuario user = facade.login("user2", "user1234");
        facade.solicitarManutencao(user.getId(), 2, "A porta da sala está rangendo.");
        facade.executarComandos();
        assertTrue(outContent.toString().contains("Solicitação de manutenção registrada para o recurso Sala Adaptada"));
    }
    
    // Testes de feedback e relatorio
    @Test
    @DisplayName("Teste de Feedbacks e Relatórios: Deve registrar um novo feedback")
    void testRegistrarFeedback() throws Exception {
        Usuario user = facade.login("user", "user123");
        facade.registrarFeedback("O sistema é muito intuitivo!", user.getId());
        facade.executarComandos();
        assertTrue(outContent.toString().contains("Feedback registrado!"));
    }

    @Test
    @DisplayName("Teste de Feedbacks e Relatórios: Deve gerar relatório de uso")
    void testGerarRelatorioUso() throws Exception {
        facade.login("admin", "admin123");
        simulateInput("1\n3\n");
        facade.gerarRelatorio();
        facade.executarComandos();
        assertTrue(outContent.toString().contains("Relatório de Uso de Recursos"));
    }

    @Test
    @DisplayName("Teste de Feedbacks e Relatórios: Deve gerar relatório de feedbacks")
    void testGerarRelatorioFeedbacks() throws Exception {
        facade.registrarFeedback("Teste de feedback", 2);
        facade.executarComandos();
        facade.login("admin", "admin123");
        simulateInput("2\n3\n"); 
        facade.gerarRelatorio();
        facade.executarComandos();
        String output = outContent.toString();
        assertTrue(output.contains("Relatório de Feedbacks"));
        assertTrue(output.contains("Teste de feedback"));
    }
}