import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Digite seu nome");
        String endereco = JOptionPane.showInputDialog("Digite seu endereço");

        Cliente cliente = new Cliente(nome, endereco);

        // Lê todos os medicamentos do arquivo medicamentos.txt
        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("medicamentos.txt"));
            String line;
            while ((line = reader.readLine()) != null) {  // Lê até o fim do arquivo
                String[] medicamentoData = line.split(",");  // Separa o nome e o preço pelo delimitador ","
                Medicamento medicamento = new Medicamento(medicamentoData[0], Double.parseDouble(medicamentoData[1]));
                medicamentos.add(medicamento);
            }
            reader.close();  // Importante fechar o reader quando terminar
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Seleciona um medicamento aleatório e adiciona à prescrição do cliente
        Random random = new Random();
        int randomIndex = random.nextInt(medicamentos.size());
        Medicamento medicamentoAleatorio = medicamentos.get(randomIndex);
        Prescricao prescricao = new Prescricao();
        prescricao.adicionarMedicamento(medicamentoAleatorio);
        cliente.adicionarPrescricao(prescricao);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("clienteData.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(cliente);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cliente clienteRecuperado = null;

        try {
            FileInputStream fileInputStream = new FileInputStream("clienteData.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            clienteRecuperado = (Cliente) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (clienteRecuperado != null) {
            clienteRecuperado.imprimirInformacoes();
        }
    }
}
