import java.io.Serializable;
import java.util.ArrayList;

public class Prescricao implements Serializable {
    private ArrayList<Medicamento> medicamentos;

    public Prescricao() {
        this.medicamentos = new ArrayList<>();
    }

    public void adicionarMedicamento(Medicamento medicamento) {
        this.medicamentos.add(medicamento);
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }
}
