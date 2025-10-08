public class Carro extends Veiculo {
    private String categoria;

    public Carro(String marca, String modelo, int ano, String placa, String categoria) {
        super(marca, modelo, ano, placa);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public double calcularDiaria() {
        if ("premium".equalsIgnoreCase(categoria)) return 180.0;
        if ("suv".equalsIgnoreCase(categoria)) return 150.0;
        return 120.0;
    }
}