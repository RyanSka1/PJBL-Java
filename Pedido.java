import java.util.ArrayList;

public class Pedido extends Pessoa {
    private ArrayList<Medicamento> medicamentos;

    public Pedido(String nome, String endereco) {
        super(nome, endereco);
        this.medicamentos = new ArrayList<>();
    }

    public void adicionarMedicamento(Medicamento medicamento) {
        this.medicamentos.add(medicamento);
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    @Override
    public void imprimirInformacoes() {
        System.out.println("Pedido: " + getNome() + ", Endereço: " + getEndereco());
        for (Medicamento medicamento : medicamentos) {
            System.out.println("Medicamento: " + medicamento.getNome());
        }
    }
}
