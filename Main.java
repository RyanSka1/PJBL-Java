import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pharmacy");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        JTextField nameField = new JTextField(20);
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);

        JTextField addressField = new JTextField(20);
        panel.add(new JLabel("Endereço:"));
        panel.add(addressField);

        JButton saveButton = new JButton("Salvar");
        panel.add(saveButton);

        JButton consultButton = new JButton("Consultar");
        panel.add(consultButton);

        frame.setVisible(true);

        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("medicamentos.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] medicamentoData = line.split(",");
                Medicamento medicamento = new Medicamento(medicamentoData[0], Double.parseDouble(medicamentoData[1]));
                medicamentos.add(medicamento);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nameField.getText();
                String endereco = addressField.getText();

                Cliente cliente = new Cliente(nome, endereco);

                Prescricao prescricao = new Prescricao();
                try {
                    prescricao.adicionarMedicamento(getRandomMedicamento(medicamentos));
                } catch (MedicamentoInexistenteException exception) {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
                cliente.adicionarPrescricao(prescricao);

                cliente.imprimirInformacoes();

                try {
                    FileWriter writer = new FileWriter("clientes.txt", true); // Abre em modo de anexação
                    writer.write("Nome: " + nome + ", Endereço: " + endereco + "\n");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            private Medicamento getRandomMedicamento(ArrayList<Medicamento> medicamentos) throws MedicamentoInexistenteException {
                if (medicamentos.size() > 0) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(medicamentos.size());
                    return medicamentos.get(randomIndex);
                } else {
                    throw new MedicamentoInexistenteException("Nenhum medicamento disponível");
                }
            }
        });

        consultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("clientes.txt");
                    FileReader reader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    bufferedReader.close();

                    JOptionPane.showMessageDialog(frame, content.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
