package edu.ebac.projeto;

import edu.ebac.projeto.dao.ClienteMapDAO;
import edu.ebac.projeto.dao.IClienteDAO;
import edu.ebac.projeto.dominio.Cliente;
import edu.ebac.projeto.util.Validador;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    private static IClienteDAO iClienteDAO;


    public static void main(String[] args) {

        iClienteDAO = new ClienteMapDAO();

        String opcao = JOptionPane.showInputDialog(null, "Digite 1 para cadastro, 2 para consulta, " + "3 para exclusão, 4 para alteração ou 5 para sair.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);

        while (!isOpcapValida(opcao)) {

            if ("".equalsIgnoreCase(opcao)) sair();

            opcao = JOptionPane.showInputDialog(null, "Digite 1 para cadastro, 2 para consulta, " + "3 para exclusão, 4 para alteração ou 5 para sair.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }


        while (isOpcapValida(opcao)) {

            if (isOpcaoSair(opcao)) sair();

            if (isOpcaoCadastro(opcao)) {
                String dados = JOptionPane.showInputDialog(null,
                        "Digite os dados do cliente separados por vírgula, exemplo: " +
                                "Nome, Cpf, Telefone, Endereço, Numero, Cidade, Estado.",
                        "Cadastro", JOptionPane.INFORMATION_MESSAGE);
//
                cadastrarCliente(dados);
            }

            if (isOpcaoConsulta(opcao)) {

                String dados = JOptionPane.showInputDialog(null, "Digite o cpf:", "Consulta", JOptionPane.INFORMATION_MESSAGE);

                consultarCliente(dados);


            }

            if (isOpcaoExcluir(opcao)) {

                String dados = JOptionPane.showInputDialog(null, "Digite o cpf para excluir:", "Excluir", JOptionPane.INFORMATION_MESSAGE);

                excluirCliente(dados);

            }

            if (isOpcaoAlterar(opcao)) {


                String dados = JOptionPane.showInputDialog(null, "Digite as informações que deseja alterar separados por vírgula, \n " +
                        "exemplo: Nome, Cpf, Telefone, Endereço, Numero, Cidade, Estado.", "Alterar", JOptionPane.INFORMATION_MESSAGE);

                alterarCliente(dados);

            }

            opcao = JOptionPane.showInputDialog(null, "Digite 1 para cadastro, 2 para consulta, " + "3 para exclusão, 4 para alteração ou 5 para sair.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);


        }


    }

    private static void alterarCliente(String dados) {

        String[] listaDados = gerarDadosCadastroCliente(dados);
        String nome = listaDados[0];
        String cpf = Validador.validaDadoNumerico(listaDados[1]);
        String telefone = Validador.validaDadoNumerico(listaDados[2]);
        String endereco = listaDados[3];
        String numero = Validador.validaDadoNumerico(listaDados[4]);
        String cidade = listaDados[5];
        String estado = listaDados[6];

        Cliente cliente = new Cliente(nome, cpf, telefone, endereco, numero, cidade, estado);

        if (!iClienteDAO.alterar(cliente)) {
            JOptionPane.showMessageDialog(null, "Cliente inexistente!",
                    "Cliente", JOptionPane.INFORMATION_MESSAGE);

        }


    }

    private static <T> boolean isNull(T dado) {

        return dado == null;
    }

    private static boolean isOpcaoExcluir(String opcao) {

        return "3".equalsIgnoreCase(opcao);
    }

    private static boolean isOpcaoAlterar(String opcao) {

        return "4".equalsIgnoreCase(opcao);
    }

    private static void excluirCliente(String dados) {

        if (!validaNumero(dados)) {
            JOptionPane.showMessageDialog(null, "digite apenas numeros", "Falha Tipo", JOptionPane.INFORMATION_MESSAGE);
        } else {

            iClienteDAO.excluir(Long.valueOf(dados));
            JOptionPane.showMessageDialog(null, "Cliente excluído!", "Excluir!", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private static void consultarCliente(String dados) {

        if (!validaNumero(dados)) {
            JOptionPane.showMessageDialog(null, "digite apenas numeros", "Cpf", JOptionPane.INFORMATION_MESSAGE);
        } else {

            Cliente cliente = iClienteDAO.consultar(Long.valueOf(dados));

            if (cliente == null) {

                JOptionPane.showMessageDialog(null, "Cliente não encontrado!", "Cliente", JOptionPane.INFORMATION_MESSAGE);

            } else {

                JOptionPane.showMessageDialog(null, cliente.toString(), "Cliente", JOptionPane.INFORMATION_MESSAGE);

            }

        }


    }

    private static boolean validaNumero(String dados) {
        final boolean isNumber = dados.trim().matches("[0-9]*");
        return isNumber;
    }


    private static void cadastrarCliente(String dados) {

        String[] listaDados = gerarDadosCadastroCliente(dados);


        Cliente cliente = new Cliente(listaDados[0], Validador.validaDadoNumerico(listaDados[1]), Validador.validaDadoNumerico(listaDados[2]), listaDados[3], Validador.validaDadoNumerico(listaDados[4]), listaDados[5], listaDados[6]);

        Boolean isCadastrado = iClienteDAO.cadastrar(cliente);

        if (isCadastrado) {

            JOptionPane.showMessageDialog(null, "Cliente cadastrado!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar o Cliente!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    private static String[] gerarDadosCadastroCliente(String dados) {

        String[] valoresEntradaBruto = dados.split(",");
        String[] valoresEntrada = new String[valoresEntradaBruto.length];

        for (int i = 0; i < valoresEntradaBruto.length; i++) {

            valoresEntrada[i] = valoresEntradaBruto[i].trim();
        }

        String[] camposValidos = new String[7];

        for (int i = 0; i < camposValidos.length; i++) {

            if (valoresEntrada.length > i) {
                if (!valoresEntrada[i].equalsIgnoreCase("")) camposValidos[i] = valoresEntrada[i];
                continue;
            }
            camposValidos[i] = null;
        }

        return camposValidos;
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "Até logo!", "Sair", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean isOpcaoCadastro(String opcao) {

        return "1".equalsIgnoreCase(opcao);
    }

    private static boolean isOpcaoConsulta(String opcao) {

        return "2".equalsIgnoreCase(opcao);
    }

    private static boolean isOpcaoSair(String opcao) {

        return "5".equalsIgnoreCase(opcao);
    }

    private static boolean isOpcapValida(String opcao) {

        List<String> opcoesValidas = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));

        System.out.println(opcoesValidas.contains(opcao));
        return opcoesValidas.contains(opcao);
    }
}
