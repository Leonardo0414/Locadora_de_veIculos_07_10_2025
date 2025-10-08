public class Caminhao extends Veiculo {
    private int eixos;

    public Caminhao(String marca, String modelo, int ano, String placa, int eixos) {
        super(marca, modelo, ano, placa);
        this.eixos = eixos;
    }

    public int getEixos() {
        return eixos;
    }

    public void setEixos(int eixos) {
        this.eixos = eixos;
    }

    @Override
    public double calcularDiaria() {
        if (eixos >= 4) return 300.0;
        return 250.0;
    }
}