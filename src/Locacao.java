import java.time.*;
import java.time.temporal.ChronoUnit;

public class Locacao {
    private Veiculo veiculo;
    private LocalDate inicio;
    private LocalDate fim;
    private double valorDiaria;
    private double valorTotal;
    private boolean ativa;

    public Locacao(Veiculo veiculo, LocalDate inicio, LocalDate fim) {
        this.veiculo = veiculo;
        this.inicio = inicio;
        this.fim = fim;
        this.valorDiaria = veiculo.calcularDiaria();
        long dias = Math.max(1, ChronoUnit.DAYS.between(inicio, fim));
        this.valorTotal = dias * valorDiaria;
        this.ativa = true;
        veiculo.setDisponivel(false);
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void finalizar() {
        if (ativa) {
            ativa = false;
            veiculo.setDisponivel(true);
        }
    }
}