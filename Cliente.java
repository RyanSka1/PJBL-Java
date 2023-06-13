import java.util.ArrayList;

public class Cliente extends Pessoa {
    private ArrayList<Prescricao> prescricoes;

    public Cliente() {
        super();
        this.prescricoes = new ArrayList<>();
    }

    public Cliente(String nome, String endereco) {
        super(nome, endereco);
        this.prescricoes = new ArrayList<>();
    }

    public void adicionarPrescricao(Prescricao prescricao) {
        this.prescricoes.add(prescricao);
    }

    @Override
    public void imprimirInformacoes() {
        System.out.println("Cliente: " + getNome() + ", Endereço: " + getEndereco());
    }
}

