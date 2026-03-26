package ProjetoJava;

import java.util.Scanner;

public class Projeto {

    static Scanner sc = new Scanner(System.in);

    static String[] nomes = new String[5];
    static double[][][] notas = new double[5][3][3];

    static String[] disciplinas = {"Matemática", "Português", "Geografia"};

    public static void pausar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            System.out.println("Erro na pausa!");
        }
    }

    // FUNÇÃO LIMPAR TELA
     public static void limparTela() { 

    try {

        String os = System.getProperty("os.name");
        // Obtém o nome do sistema operacional (ex: Windows, Linux, Mac)

        if (os.contains("Windows")) {
            // Verifica se o sistema operacional é Windows

            new ProcessBuilder("cmd", "/c", "cls")
                .inheritIO()   // Usa o mesmo terminal do programa
                .start()       // Inicia o processo (executa o comando)
                .waitFor();    // Espera o comando terminar antes de continuar

        }

    } catch (Exception e) {
        // Captura qualquer erro que acontecer dentro do try

        System.out.println("Erro ao limpar tela!");
        // Mostra mensagem caso não consiga limpar a tela
    }
}

    public static void cadnome() {
        for (int i = 0; i < nomes.length; i++) {
            do {
                System.out.print("Digite o nome do aluno " + (i + 1) + ": ");
                nomes[i] = sc.nextLine().trim();

                if (nomes[i].isEmpty()) {
                    System.out.println("Nome não pode ser vazio!");
                }
            } while (nomes[i].isEmpty());
        }

        System.out.println("\nAlunos cadastrados!");
        pausar(2);
    }

    public static void cadnota() {
        for (int i = 0; i < nomes.length; i++) {

            if (nomes[i] == null) {
                System.out.println("Cadastre os nomes primeiro!");
                return;
            }

            System.out.println("\nAluno: " + nomes[i]);

            for (int j = 0; j < 3; j++) {
                System.out.println("Notas de " + disciplinas[j]);

                for (int k = 0; k < 3; k++) {
                    while (true) {
                        try {
                            System.out.print("Digite a nota " + (k + 1) + ": ");
                            double nota = Double.parseDouble(sc.nextLine());

                            if (nota < 0 || nota > 10) {
                                System.out.println("A nota deve estar entre 0 e 10!");
                                continue;
                            }

                            notas[i][j][k] = nota;
                            break;

                        } catch (NumberFormatException e) {
                            System.out.println("Erro: digite um número válido!");
                        }
                    }
                }
            }
        }

        System.out.println("Notas cadastradas!");
        pausar(2);
    }

    public static void mostrartabela() {
        System.out.println("\n========== TABELA DE NOTAS ==========");

        for (int i = 0; i < nomes.length; i++) {
            System.out.println("----------------------------------");
            System.out.println("Aluno: " + nomes[i]);

            for (int j = 0; j < 3; j++) {
                System.out.printf("%-12s | ", disciplinas[j]);

                for (int k = 0; k < 3; k++) {
                    System.out.printf("%.1f ", notas[i][j][k]);
                }

                System.out.println();
            }

            System.out.printf("Média Final: %.2f\n", mediaAluno(i));
        }

        System.out.println("----------------------------------");
    }

    public static void aprovado() {
        System.out.println("\n--- APROVADOS ---");

        for (int i = 0; i < nomes.length; i++) {
            if (mediaAluno(i) >= 7) {
                System.out.println(nomes[i]);
            }
        }
    }

    public static double mediaAluno(int i) {
        double soma = 0;

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                soma += notas[i][j][k];
            }
        }

        return soma / 9;
    }

    public static void maiormedia() {
        double maior = -1;
        String nomeMaior = "";

        for (int i = 0; i < nomes.length; i++) {
            double media = mediaAluno(i);

            if (media > maior) {
                maior = media;
                nomeMaior = nomes[i];
            }
        }

        System.out.printf("\nMaior média: %s (%.2f)\n", nomeMaior, maior);
    }

    public static void mediaturma() {
        double soma = 0;

        for (int i = 0; i < nomes.length; i++) {
            soma += mediaAluno(i);
        }

        System.out.printf("\nMédia da turma: %.2f\n", soma / nomes.length);
    }

    public static void disciplina_maiormedia() {
        double maiorMedia = 0;
        int indice = 0;

        for (int j = 0; j < 3; j++) {
            double soma = 0;

            for (int i = 0; i < nomes.length; i++) {
                for (int k = 0; k < 3; k++) {
                    soma += notas[i][j][k];
                }
            }

            double media = soma / (nomes.length * 3);

            if (j == 0 || media > maiorMedia) {
                maiorMedia = media;
                indice = j;
            }
        }

        System.out.printf("\nDisciplina com maior média: %s (%.2f)\n",
                disciplinas[indice], maiorMedia);
    }

    public static void aprovados_alfabetica() {
        String[] copia = nomes.clone();
        java.util.Arrays.sort(copia);

        System.out.println("\n--- APROVADOS (A-Z) ---");

        for (String nome : copia) {
            for (int j = 0; j < nomes.length; j++) {
                if (nome.equals(nomes[j]) && mediaAluno(j) >= 7) {
                    System.out.println(nome);
                }
            }
        }
    }

    public static void reprovados_alfabetica() {
        String[] copia = nomes.clone();
        java.util.Arrays.sort(copia);

        System.out.println("\n--- REPROVADOS (A-Z) ---");

        for (String nome : copia) {
            for (int j = 0; j < nomes.length; j++) {
                if (nome.equals(nomes[j]) && mediaAluno(j) < 7) {
                    System.out.println(nome);
                }
            }
        }
    }

    public static void mostrarmenu() {
        String opc;

        do {
            limparTela();

            System.out.println("--------------- MENU PRINCIPAL ---------------");
            System.out.println("| 1 - Cadastrar nomes                        |");
            System.out.println("| 2 - Cadastrar notas                        |");
            System.out.println("| 3 - Mostrar tabela                         |");
            System.out.println("| 4 - Mostrar aprovados                      |");
            System.out.println("| 5 - Maior média                            |");
            System.out.println("| 6 - Média da turma                         |");
            System.out.println("| 7 - Disciplina com maior média             |");
            System.out.println("| 8 - Aprovados em ordem alfabética          |");
            System.out.println("| 9 - Reprovados em ordem alfabética         |");
            System.out.println("| 0 - Sair                                   |");
            System.out.println("----------------------------------------------");
            System.out.print("Escolha uma opção: ");

            try {
                opc = sc.nextLine();
            } catch (Exception e) {
                System.out.println("Erro ao ler opção!");
                opc = "";
            }

            switch (opc) {
                case "1": limparTela(); cadnome(); break;
                case "2": limparTela(); cadnota(); break;
                case "3": limparTela(); mostrartabela(); pausar(8); break;
                case "4": limparTela(); aprovado(); pausar(3); break;
                case "5": limparTela(); maiormedia(); pausar(3); break;
                case "6": limparTela(); mediaturma(); pausar(3); break;
                case "7": limparTela(); disciplina_maiormedia(); pausar(3); break;
                case "8": limparTela(); aprovados_alfabetica(); pausar(3); break;
                case "9": limparTela(); reprovados_alfabetica(); pausar(3); break;
                case "0": System.out.println("Encerrado."); break;
                default:
                    System.out.println("Opção inválida!");
                    pausar(2);
            }

        } while (!opc.equals("0"));
    }

    public static void main(String[] args) {
        mostrarmenu();
    }
}