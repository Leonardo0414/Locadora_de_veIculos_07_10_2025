import java.time.*;
import java.time.temporal.ChronoUnit;
import utils.Teclado;

public class Main {
    private static Locadora locadora = new Locadora();

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE LOCAÇÃO DE VEÍCULOS ===");
        System.out.println("Bem-vindo ao sistema de gestão de locação!");
        
        boolean continuar = true;
        while (continuar) {
            exibirMenu();
            int opcao = Teclado.readInt("Escolha uma opção");
            
            switch (opcao) {
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    realizarLocacao();
                    break;
                case 3:
                    finalizarLocacao();
                    break;
                case 4:
                    consultarVeiculos();
                    break;
                case 5:
                    consultarLocacoes();
                    break;
                case 6:
                    exibirRelatorioFinanceiro();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Obrigado por usar o sistema!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                Teclado.readString();
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1 - Cadastrar Veículo");
        System.out.println("2 - Realizar Locação");
        System.out.println("3 - Finalizar Locação");
        System.out.println("4 - Consultar Veículos");
        System.out.println("5 - Consultar Locações");
        System.out.println("6 - Relatório Financeiro");
        System.out.println("0 - Sair");
        System.out.println("========================");
    }

    private static void cadastrarVeiculo() {
        System.out.println("\n=== CADASTRO DE VEÍCULO ===");
        System.out.println("Tipos disponíveis:");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");
        System.out.println("3 - Caminhão");
        
        int tipo = Teclado.readInt("Escolha o tipo de veículo");
        
        String marca = Teclado.readString("Digite a marca");
        String modelo = Teclado.readString("Digite o modelo");
        int ano = Teclado.readInt("Digite o ano");
        String placa = Teclado.readString("Digite a placa");
        
        Veiculo veiculo = null;
        
        switch (tipo) {
            case 1:
                System.out.println("Categorias: hatch, suv, premium");
                String categoria = Teclado.readString("Digite a categoria do carro");
                veiculo = new Carro(marca, modelo, ano, placa, categoria);
                break;
            case 2:
                int cilindradas = Teclado.readInt("Digite as cilindradas");
                veiculo = new Moto(marca, modelo, ano, placa, cilindradas);
                break;
            case 3:
                int eixos = Teclado.readInt("Digite o número de eixos");
                veiculo = new Caminhao(marca, modelo, ano, placa, eixos);
                break;
            default:
                System.out.println("Tipo inválido!");
                return;
        }
        
        if (veiculo != null) {
            locadora.cadastrar(veiculo);
            System.out.println("Veículo cadastrado com sucesso!");
            System.out.println("Diária: R$ " + String.format("%.2f", veiculo.calcularDiaria()));
        }
    }

    private static void realizarLocacao() {
        System.out.println("\n=== REALIZAR LOCAÇÃO ===");
        
        if (locadora.getVeiculos().isEmpty()) {
            System.out.println("Nenhum veículo cadastrado!");
            return;
        }
        
        String placa = Teclado.readString("Digite a placa do veículo");
        
        if (locadora.buscarPorPlaca(placa).isEmpty()) {
            System.out.println("Veículo não encontrado!");
            return;
        }
        
        Veiculo veiculo = locadora.buscarPorPlaca(placa).get();
        if (!veiculo.isDisponivel()) {
            System.out.println("Veículo não está disponível!");
            return;
        }
        
        System.out.println("Veículo: " + veiculo.detalhes());
        System.out.println("Diária: R$ " + String.format("%.2f", veiculo.calcularDiaria()));
        
        System.out.println("Digite a data de início (formato: AAAA-MM-DD)");
        LocalDate inicio = Teclado.readDate();
        
        System.out.println("Digite a data de fim (formato: AAAA-MM-DD)");
        LocalDate fim = Teclado.readDate();
        
        if (fim.isBefore(inicio) || fim.isEqual(inicio)) {
            System.out.println("Data de fim deve ser posterior à data de início!");
            return;
        }
        
        if (locadora.locar(placa, inicio, fim).isPresent()) {
            System.out.println("Locação realizada com sucesso!");
            long dias = ChronoUnit.DAYS.between(inicio, fim);
            double total = dias * veiculo.calcularDiaria();
            System.out.println("Período: " + dias + " dias");
            System.out.println("Valor total: R$ " + String.format("%.2f", total));
        } else {
            System.out.println("Erro ao realizar locação!");
        }
    }

    private static void finalizarLocacao() {
        System.out.println("\n=== FINALIZAR LOCAÇÃO ===");
        
        if (locadora.getLocacoes().isEmpty()) {
            System.out.println("Nenhuma locação ativa!");
            return;
        }
        
        System.out.println("Locações ativas:");
        int index = 1;
        for (Locacao locacao : locadora.getLocacoes()) {
            if (locacao.isAtiva()) {
                System.out.println(index + " - " + locacao.getVeiculo().getPlaca() + 
                    " (" + locacao.getInicio() + " a " + locacao.getFim() + ")");
                index++;
            }
        }
        
        if (index == 1) {
            System.out.println("Nenhuma locação ativa encontrada!");
            return;
        }
        
        int escolha = Teclado.readInt("Escolha a locação para finalizar (número)");
        
        int contador = 1;
        for (Locacao locacao : locadora.getLocacoes()) {
            if (locacao.isAtiva()) {
                if (contador == escolha) {
                    locacao.finalizar();
                    System.out.println("Locação finalizada com sucesso!");
                    return;
                }
                contador++;
            }
        }
        
        System.out.println("Opção inválida!");
    }

    private static void consultarVeiculos() {
        System.out.println("\n=== CONSULTA DE VEÍCULOS ===");
        
        if (locadora.getVeiculos().isEmpty()) {
            System.out.println("Nenhum veículo cadastrado!");
            return;
        }
        
        System.out.println(locadora.relatorioVeiculos());
    }

    private static void consultarLocacoes() {
        System.out.println("\n=== CONSULTA DE LOCAÇÕES ===");
        
        if (locadora.getLocacoes().isEmpty()) {
            System.out.println("Nenhuma locação registrada!");
            return;
        }
        
        System.out.println(locadora.relatorioLocacoes());
    }

    private static void exibirRelatorioFinanceiro() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.println("Total faturado: R$ " + String.format("%.2f", locadora.totalFaturado()));
        
        if (!locadora.getLocacoes().isEmpty()) {
            System.out.println("\nDetalhamento das locações:");
            System.out.println(locadora.relatorioLocacoes());
        }
    }
}
