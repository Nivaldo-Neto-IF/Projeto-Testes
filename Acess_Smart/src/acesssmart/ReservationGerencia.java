package acesssmart;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReservationGerencia {
    private Map<Integer, Reservation> reservas;
    private int nextId;
    private ManutencaoGestao manutencaoGestao;
    
    public ReservationGerencia(ManutencaoGestao manutencaoGestao) {
        reservas = new HashMap<>();
        nextId = 1;
        this.manutencaoGestao = manutencaoGestao;
    }
    
    public int criarReserva(int recursoId, String recursoNome, int usuarioId, String usuarioNome, LocalDateTime dateTime) {
        if (manutencaoGestao.isEmManutencao(recursoId, dateTime)) {
            System.out.println("O recurso está em manutenção no horário escolhido.");
            return -1;
        }
        Reservation reserva = new Reservation(usuarioId, recursoId, recursoNome, dateTime);
        reservas.put(nextId, reserva);
        
        for (Usuario admin : Facade.getInstance().getUserManager().getAdministradores()) {
            Notificationsi.notifyUser(admin.getId(), "Nova reserva criada para o recurso " + recursoNome +
                " pelo usuário " + usuarioNome + " (Reserva ID: " + nextId + ")");
        }
        return nextId++;
    }



    
    public void consultarReservasUsuario(int usuarioId) {
        Usuario usuario = Facade.getInstance().getUserManager().getUsuarioById(usuarioId);
        String nome = (usuario != null) ? usuario.getNome() : "Desconhecido";
        System.out.println("Reservas do usuário " + nome + " (ID: " + usuarioId + "):");
        for (Map.Entry<Integer, Reservation> entry : reservas.entrySet()) {
            if (entry.getValue().getUsuarioId() == usuarioId) {
                System.out.println("Reserva ID: " + entry.getKey() + " - " + entry.getValue());
            }
        }
    }
    public void consultarTodasReservas() {
        System.out.println("Todas as reservas:");
        for (Map.Entry<Integer, Reservation> entry : reservas.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " - " + entry.getValue());
        }
    }
    
    public Map<Integer, Reservation> getReservas() {
        return reservas;
    }
    
    public void alterarEstadoReserva(int reservaId, int novoEstado) throws Exception {
        Reservation reserva = reservas.get(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva não encontrada.");
        }

        String estadoAnterior = reserva.getEstado();

        // AGORA USAMOS A LÓGICA DO STATE PATTERN
        switch (novoEstado) {
            case 1: // Finalizada
                reserva.setState(new FinalizadaState()); // Finalizar ainda pode ser uma ação direta do admin
                System.out.println("Reserva " + reservaId + " finalizada pelo administrador.");
                break;
            case 2: // Confirmada
                reserva.proximoEstado(); // Isso chama a lógica do estado atual (ex: Pendente -> Confirmada)
                break;
            case 3: // Cancelada
                reserva.cancelarReserva(); // Isso chama a lógica do estado atual (ex: Pendente -> Cancelada)
                break;
            default:
                throw new Exception("Estado inválido.");
        }

        String estadoNovo = reserva.getEstado();
        
       
        if (!estadoAnterior.equals(estadoNovo)) {
            Usuario usuario = Facade.getInstance().getUserManager().getUsuarioById(reserva.getUsuarioId());
            if (usuario != null) {
                String mensagemNotificacao = "Olá, " + usuario.getNome() + ", o estado da sua reserva " + reservaId +
                    " foi alterado para " + estadoNovo + ".";
                Notificationsi.notifyUser(usuario.getId(), mensagemNotificacao);
            }
        }
    }

    
    public void finalizarReserva(int reservaId) throws Exception {
        Reservation reserva = reservas.get(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva não encontrada.");
        }
        reserva.setState(new FinalizadaState());
        System.out.println("Reserva " + reservaId + " finalizada.");
        Usuario usuario = Facade.getInstance().getUserManager().getUsuarioById(reserva.getUsuarioId());
        String mensagem = "";
        if (usuario != null) {
            mensagem = "Reserva " + reservaId + " finalizada pelo usuário " +
                usuario.getNome() + " (ID: " + usuario.getId() + ")";
        } else {
            mensagem = "Reserva " + reservaId + " finalizada.";
        }
        for (Usuario admin : Facade.getInstance().getUserManager().getAdministradores()) {
            Notificationsi.notifyUser(admin.getId(), mensagem);
        }
    }
}