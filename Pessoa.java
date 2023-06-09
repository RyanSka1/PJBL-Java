public abstract class Pessoa {
    private String nome;
    private String endereco;

    public Pessoa() {}

    public Pessoa(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public abstract void imprimirInformacoes();
}
