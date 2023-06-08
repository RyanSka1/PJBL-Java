import java.io.Serializable;
import java.util.ArrayList;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Medicamento> medicamentos;

    public Pedido() {
        this.medicamentos = new ArrayList<>();
    }

    public void adicionarMedicamento(Medicamento medicamento) {
        this.medicamentos.add(medicamento);
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }
}
