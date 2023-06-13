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
        JButton buttonHistorico = new JButton("Historico");

        buttonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
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

                cliente.imprimirInformacoes();
                
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt", true));
                    writer.append("Cliente: ").append(cliente.getNome()).append(", Endereço: ").append(cliente.getEndereco()).append("\n");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                try {
                    FileInputStream fileInputStream = new FileInputStream("clienteData.ser");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    Cliente clienteRecuperado = (Cliente) objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStream.close();

                    if (clienteRecuperado != null) {
                        clienteRecuperado.imprimirInformacoes();
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonHistorico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e3) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nome: "));
        panel.add(textFieldNome);
        panel.add(new JLabel("Endereço: "));
        panel.add(textFieldEndereco);
        panel.add(buttonEnviar);
        panel.add(buttonConsultar);
        panel.add(buttonHistorico);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}
