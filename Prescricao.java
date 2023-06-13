import java.util.ArrayList;
import java.io.Serializable;

public class Prescricao implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Medicamento> medicamentos;

    public Prescricao() {
        this.medicamentos = new ArrayList<>();
    }

    public void adicionarMedicamento(Medicamento medicamento) throws MedicamentoInexistenteException {
        if(medicamento == null){
            throw new MedicamentoInexistenteException("Erro: Medicamento não encontrado.");
        }
        this.medicamentos.add(medicamento);
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void imprimirMedicamentos() {
        for(Medicamento medicamento : medicamentos) {
            System.out.println("Medicamento: " + medicamento.getNome() + ", Preço: " + medicamento.getPreco());
        }
    }
}
