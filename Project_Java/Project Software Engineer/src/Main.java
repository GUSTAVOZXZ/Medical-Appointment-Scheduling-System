import Classes.*;
import Estruturas.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- TELA DE LOGIN ---
        CredencialEstrutura credencialEst = new CredencialEstrutura();

        System.out.println("\n=== SISTEMA DE GESTÃO MÉDICA ===");
        System.out.println("--- LOGIN ---");

        boolean logado = credencialEst.realizarLogin(scanner);

        if (!logado) {
            System.out.println("\nDeseja redefinir sua senha?");
            System.out.println("1. Sim");
            System.out.println("2. Não (encerrar)");
            System.out.print("Escolha: ");
            String opcaoRed = scanner.nextLine();

            if (opcaoRed.equals("1")) {
                credencialEst.redefinirSenha(scanner);
                System.out.println("Reinicie o sistema para fazer login com a nova senha.");
            } else {
                System.out.println("Encerrando sistema...");
            }

            scanner.close();
            return;
        }

        // --- MENU PRINCIPAL ---
        // Pacientes e Médicos carregados antes das Consultas (que dependem deles)
        PacienteEstrutura pacienteEst    = new PacienteEstrutura();
        MedicoEstruturas medicoEst       = new MedicoEstruturas();
        ConsultasEstruturas consultasEst = new ConsultasEstruturas(pacienteEst, medicoEst);

        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n--- SISTEMA DE GESTÃO MÉDICA ---");
            System.out.println("1. Agendar Consulta");
            System.out.println("2. Listar Consultas");
            System.out.println("3. Cadastrar (Paciente ou Médico)");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("CPF do Paciente: ");
                    String cpf = scanner.nextLine();
                    Paciente p = pacienteEst.buscarPorCpf(cpf);

                    if (p == null) {
                        System.out.println("Erro: Paciente não encontrado! Cadastre-o primeiro.");
                        break;
                    }

                    System.out.print("CRM do Médico: ");
                    String crm = scanner.nextLine();
                    Medico m = medicoEst.buscarPorCrm(crm);

                    if (m == null) {
                        System.out.println("Erro: Médico não encontrado!");
                        break;
                    }

                    System.out.print("Data (dd/mm/aaaa): ");
                    String data = scanner.nextLine();
                    System.out.print("Horário: ");
                    String horario = scanner.nextLine();
                    System.out.print("Observação: ");
                    String obs = scanner.nextLine();

                    Consulta nova = new Consulta(p, m, data, horario, obs);
                    consultasEst.agendar(nova);
                    break;

                case 2:
                    System.out.println(consultasEst.gerarRelatorio());
                    break;

                case 3:
                    System.out.println("\nO que deseja cadastrar?");
                    System.out.println("1. Paciente");
                    System.out.println("2. Médico");
                    int subOpcao = scanner.nextInt();
                    scanner.nextLine();

                    if (subOpcao == 1) {
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("CPF: ");
                        String cpfPac = scanner.nextLine();
                        System.out.print("Data de Nascimento: ");
                        String dataN = scanner.nextLine();
                        System.out.print("Plano de Saúde: ");
                        String plano = scanner.nextLine();
                        System.out.print("Telefone: ");
                        String tel = scanner.nextLine();
                        System.out.print("Rua: ");
                        String rua = scanner.nextLine();
                        System.out.print("Número: ");
                        String num = scanner.nextLine();
                        System.out.print("Cidade: ");
                        String cidade = scanner.nextLine();
                        System.out.print("Estado (UF): ");
                        String estado = scanner.nextLine();

                        Endereco end   = new Endereco(rua, num, cidade, estado);
                        Paciente novoP = new Paciente(end, tel, nome, cpfPac, dataN, plano);
                        pacienteEst.adicionar(novoP);
                        System.out.println("Paciente cadastrado com sucesso!");

                    } else if (subOpcao == 2) {
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("CPF: ");
                        String cpfMed = scanner.nextLine();
                        System.out.print("Data de Nascimento: ");
                        String dataN = scanner.nextLine();
                        System.out.print("CRM: ");
                        int crmMed = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Especialidade: ");
                        String esp = scanner.nextLine();

                        Medico novoM = new Medico(crmMed, esp, nome, cpfMed, dataN);
                        medicoEst.adicionar(novoM);
                        System.out.println("Médico cadastrado com sucesso!");
                    }
                    break;

                case 4:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }
}
