package acesssmart;
public class CanceladaState implements ReservationState {
    public void proximoEstado(Reservation reservation) {
        System.out.println("Reserva cancelada, não pode ser alterado o estado");
    }
    
    public void cancelarReserva(Reservation reservation) {
        System.out.println("Reserva já está cancelada.");
    }
}