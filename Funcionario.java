public class Funcionario extends Pessoa {
    private int idFuncionario;

    public Funcionario(String nome, String endereco, int idFuncionario) {
        super(nome, endereco);
        this.idFuncionario = idFuncionario;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    @Override
    public void imprimirInformacoes() {
        System.out.println("Funcionario: " + getNome() + ", Endereço: " + getEndereco() + ", ID: " + idFuncionario);
    }
}
