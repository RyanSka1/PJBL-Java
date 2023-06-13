import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Cria a interface gráfica
        String nome = JOptionPane.showInputDialog("Insira o nome do cliente");
        String endereco = JOptionPane.showInputDialog("Insira o endereço do cliente");

        Cliente cliente = new Cliente(nome, endereco);

        // Lê os medicamentos do arquivo
        try {
            File file = new File("medicamentos.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] parts = data.split(",");

                String nomeMedicamento = parts[0];
                double preco = Double.parseDouble(parts[1]);

                Medicamento medicamento = new Medicamento(nomeMedicamento, preco);

                Prescricao prescricao = new Prescricao();
                prescricao.adicionarMedicamento(medicamento);

                cliente.adicionarPrescricao(prescricao);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo de medicamentos.");
            e.printStackTrace();
        }

        // Salva o objeto
        try {
            FileOutputStream fos = new FileOutputStream("cliente.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(cliente);

            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Recupera o objeto
        try {
            FileInputStream fis = new FileInputStream("cliente.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Cliente clienteRecuperado = (Cliente) ois.readObject();
            ois.close();

            // Imprime as informações do cliente recuperado
            clienteRecuperado.imprimirInformacoes();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }
}
