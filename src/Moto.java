public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String marca, String modelo, int ano, String placa, int cilindradas) {
        super(marca, modelo, ano, placa);
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }

    @Override
    public double calcularDiaria() {
        if (cilindradas >= 500) return 90.0;
        return 70.0;
    }
}