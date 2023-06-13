import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("PJBL Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textFieldNome = new JTextField();
        JTextField textFieldEndereco = new JTextField();
        JButton buttonEnviar = new JButton("Enviar");
        JButton buttonConsultar = new JButton("Consultar");

        buttonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String endereco = textFieldEndereco.getText();

                Cliente cliente = new Cliente(nome, endereco);

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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Random random = new Random();
                int randomIndex = random.nextInt(medicamentos.size());
                Medicamento medicamentoAleatorio = medicamentos.get(randomIndex);

                Prescricao prescricao = new Prescricao();
                try {
                    prescricao.adicionarMedicamento(medicamentoAleatorio);
                    cliente.adicionarPrescricao(prescricao);
                } catch (MedicamentoInexistenteException ex) {
                    System.out.println("Erro: " + ex.getMessage());
                }

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream("clienteData.ser");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(cliente);
                    objectOutputStream.close();
                    fileOutputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // Append client information to clientes.txt
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("clientes.txt", true)))) {
                    out.println(cliente.getNome() + "," + cliente.getEndereco());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                cliente.imprimirInformacoes();
            }
        });

        buttonConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nome: "));
        panel.add(textFieldNome);
        panel.add(new JLabel("Endereço: "));
        panel.add(textFieldEndereco);
        panel.add(buttonEnviar);
        panel.add(buttonConsultar);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(400, 150);
        frame.setVisible(true);
    }
}
