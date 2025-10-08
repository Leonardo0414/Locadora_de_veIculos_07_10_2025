import java.time.*;
import java.util.*;

public class Locadora {
    private List<Veiculo> veiculos;
    private List<Locacao> locacoes;

    public Locadora() {
        this.veiculos = new ArrayList<>();
        this.locacoes = new ArrayList<>();
    }

    public void cadastrar(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public List<Veiculo> getVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }

    public List<Locacao> getLocacoes() {
        return Collections.unmodifiableList(locacoes);
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return veiculos.stream().filter(veiculo -> veiculo.getPlaca().equalsIgnoreCase(placa)).findFirst();
    }

    public Optional<Locacao> locar(String placa, LocalDate inicio, LocalDate fim) {
        Optional<Veiculo> opcaoVeiculo = buscarPorPlaca(placa);
        if (opcaoVeiculo.isEmpty()) return Optional.empty();
        
        Veiculo veiculo = opcaoVeiculo.get();
        if (!veiculo.isDisponivel()) return Optional.empty();
        
        Locacao locacao = new Locacao(veiculo, inicio, fim);
        locacoes.add(locacao);
        return Optional.of(locacao);
    }

    public double totalFaturado() {
        return locacoes.stream().mapToDouble(Locacao::getValorTotal).sum();
    }

    public String relatorioVeiculos() {
        StringBuilder relatorio = new StringBuilder();
        for (Veiculo veiculo : veiculos) {
            relatorio.append(veiculo.detalhes()).append(System.lineSeparator());
        }
        return relatorio.toString();
    }

    public String relatorioLocacoes() {
        StringBuilder relatorio = new StringBuilder();
        for (Locacao locacao : locacoes) {
            relatorio.append(locacao.getVeiculo().getPlaca())
                .append(" | ")
                .append(locacao.getInicio())
                .append(" -> ")
                .append(locacao.getFim())
                .append(" | Diária: ")
                .append(String.format("%.2f", locacao.getValorDiaria()))
                .append(" | Total: ")
                .append(String.format("%.2f", locacao.getValorTotal()))
                .append(locacao.isAtiva() ? " | Ativa" : " | Finalizada")
                .append(System.lineSeparator());
        }
        return relatorio.toString();
    }
}