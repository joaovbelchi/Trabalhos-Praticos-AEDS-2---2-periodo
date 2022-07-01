import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import javax.xml.stream.events.EntityDeclaration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.nio.charset.*;

class TP04Q06 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in, "ISO-8859-1");
        String entrada = new String();
        Filme filmes = new Filme();
        Filme[] arrayFilmes = new Filme[50];
        Hash tabela = new Hash();
        int controlador = 0;
        entrada = "";
        entrada = sc.nextLine();

        try {
            do {
                int valorasciistring = 0;
                // filmes.ler("/mnt/c/temp/2022-1/AULAS/TP04/filmes/" + entrada);

                filmes.ler("/tmp/filmes/" + entrada);
                arrayFilmes[controlador] = Filme.clonador(filmes);
                for (int i = 0; i < filmes.getTituloOriginal().length(); i++) {
                    valorasciistring += filmes.getTituloOriginal().charAt(i);
                }
                tabela.inserir(valorasciistring);
                controlador++;
                entrada = sc.nextLine();
                entrada = entrada.trim();

            } while (isFim(entrada) == false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        entrada = sc.nextLine();

        do {
            boolean pesq = false;
            int valorasciistring = 0;
            System.out.println("=> " + entrada);
            for (int i = 0; i < entrada.length(); i++) {
                valorasciistring += entrada.charAt(i);
            }
            if (!tabela.pesquisar(valorasciistring)) {
                System.out.println("NAO");
            }
            entrada = sc.nextLine();
            entrada = entrada.trim();
        } while (isFim(entrada) == false);

        sc.close();
    }

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

}

class Filme {
    private String nome;
    private String tituloOriginal;
    private Date dataLancamento;
    private int duracao;
    private String genero;
    private String idiomaOriginal;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave;

    public Filme() {
    }

    public Filme(String nome, String tituloOriginal, Date dataLancamento, int duracao, String genero,
            String idiomaOriginal,
            String situacao, float orcamento, String[] palavrasChave) {
        this.nome = nome;
        this.tituloOriginal = tituloOriginal;
        this.dataLancamento = dataLancamento;
        this.duracao = duracao;
        this.genero = genero;
        this.idiomaOriginal = idiomaOriginal;
        this.situacao = situacao;
        this.orcamento = orcamento;
        this.palavrasChave = palavrasChave;
    }

    public static Filme clonador(Filme filme) {
        Filme temporario = new Filme();

        temporario.nome = filme.nome;
        temporario.tituloOriginal = filme.tituloOriginal;
        temporario.dataLancamento = filme.dataLancamento;
        temporario.duracao = filme.duracao;
        temporario.genero = filme.genero;
        temporario.idiomaOriginal = filme.idiomaOriginal;
        temporario.situacao = filme.situacao;
        temporario.orcamento = filme.orcamento;
        temporario.palavrasChave = filme.palavrasChave;

        return temporario;
    }

    public String getNome() {
        return nome;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public String getDataLancamento() {
        String newdate = new SimpleDateFormat("dd/MM/yyyy").format(dataLancamento);
        return newdate;
    }

    public Date getDataLancamentoDate() {
        return dataLancamento;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public String getSituacao() {
        return situacao;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public void setDataLancamento(String s) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.dataLancamento = dateFormat.parse(s);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setPalavrasChave(String[] palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    String removeTags(String line) {
        String newline = "";
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '<') {
                i++;
                while (line.charAt(i) != '>') {
                    i++;
                }
            } else {
                newline += line.charAt(i);
            }
        }
        return newline;
    }

    /**
     * String removeTagsInverso(String line) {
     * String newline = "";
     * for (int i = 0; i < line.length(); i++) {
     * if (line.charAt(i) == '>') {
     * i++;
     * while (line.charAt(i) != '<') {
     * newline += line.charAt(i);
     * i++;
     * }
     * } else {
     * 
     * }
     * }
     * return newline;
     * }
     **/
    String removeTagsDois(String line) {
        String newline = "";
        int i = 0;
        while (line.charAt(i) != '(') {
            newline += line.charAt(i);
            i++;
        }

        newline.trim();
        return newline;
    }

    int duracaoTreat(String s) {
        String horas = "";
        String minutos = "";
        char teste = 'h';
        int resultado = 0;
        int i = 0;
        s = (s.trim()).replace(" ", "");
        if (s.indexOf(teste) != -1) {
            try {
                while (s.charAt(i) != 'h' && s.charAt(i) != 'm') {
                    horas += s.charAt(i);
                    i++;
                }
                i++;
                while (s.charAt(i) != 'm') {
                    minutos += s.charAt(i);
                    i++;
                }
            } catch (Exception e) {
                return 0;
            }
            resultado = ((Integer.parseInt(horas) * 60) + Integer.parseInt(minutos));
        } else {
            while (s.charAt(i) != 'm') {
                minutos += s.charAt(i);
                i++;
            }
            resultado = Integer.parseInt(minutos);
        }

        return resultado;
    }

    String removeUmZero(String s) {
        String newstring = new String();
        for (int i = 0; i < (s.length() - 1); i++) {
            newstring += s.charAt(i);
        }

        return newstring;
    }

    public void ler(String nomeArquivo) throws Exception {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(nomeArquivo), Charset.forName("ISO-8859-1"));
        int controlador = 0;

        BufferedReader br = new BufferedReader(isr);
        String linhatemp = new String();

        linhatemp = br.readLine();
        while (!(linhatemp.contains("<title>"))) {
            linhatemp = br.readLine();
        }

        this.nome = removeTagsDois(removeTags(linhatemp));
        this.nome = nome.trim();

        while (!(linhatemp.contains("\"release\""))) {
            linhatemp = br.readLine();
        }
        linhatemp = br.readLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.dataLancamento = dateFormat.parse((removeTagsDois(removeTags(linhatemp))).trim());

        while (!(linhatemp.contains("\"genres\""))) {
            linhatemp = br.readLine();
        }
        linhatemp = br.readLine();
        linhatemp = br.readLine();
        this.genero = ((removeTags(linhatemp).replace("&nbsp;", "")).trim());

        while (!(linhatemp.contains("\"runtime\""))) {
            linhatemp = br.readLine();
        }
        linhatemp = br.readLine();
        linhatemp = br.readLine();
        this.duracao = duracaoTreat(linhatemp);

        try {
            while (!(linhatemp.contains("\"wrap\""))) {
                linhatemp = br.readLine();
            }
            this.tituloOriginal = ((removeTags(linhatemp).replace("T�tulo original", "")).trim());
        } catch (NullPointerException npe) {
            this.tituloOriginal = this.nome;
        }
        br.close();
        isr.close();
        isr = new InputStreamReader(new FileInputStream(nomeArquivo), Charset.forName("ISO-8859-1"));
        br = new BufferedReader(isr);
        linhatemp = br.readLine();

        while (!(linhatemp.contains("Situa��o"))) {
            linhatemp = br.readLine();
        }
        this.situacao = ((removeTags(linhatemp).replace("Situa��o", "")).trim());

        while (!(linhatemp.contains("Idioma original"))) {
            linhatemp = br.readLine();
        }
        this.idiomaOriginal = ((removeTags(linhatemp).replace("Idioma original", "")).trim());

        while (!(linhatemp.contains("Or�amento"))) {
            linhatemp = br.readLine();
        }
        linhatemp = ((removeTags(linhatemp).replace("Or�amento", "")).trim());
        linhatemp = (linhatemp.replace(",", "").replace("$", ""));
        try {
            linhatemp = removeUmZero(linhatemp);
            this.orcamento = Float.parseFloat(linhatemp);
        } catch (NumberFormatException nfe) {
            this.orcamento = 0;
        }

        palavrasChave = new String[100];
        try {
            while (!linhatemp.contains("/keyword/")) {
                linhatemp = br.readLine();
            }
            while (linhatemp.contains("/keyword/")) {
                this.palavrasChave[controlador] = (removeTags(linhatemp).trim());
                controlador++;
                linhatemp = br.readLine();
                linhatemp = br.readLine();
            }
        } catch (NullPointerException npe) {
        }
        String[] tempArray = new String[controlador];
        for (int i = 0; i < controlador; i++) {
            tempArray[i] = palavrasChave[i];
        }
        palavrasChave = Arrays.copyOf(tempArray, tempArray.length);
        br.close();

    }

    public String imprimir() {
        return nome + " " + tituloOriginal + " " + getDataLancamento() + " " + String.valueOf(duracao) + " " + genero
                + " " + idiomaOriginal
                + " Lan�ado " + situacao + " " + String.valueOf(orcamento) + " " + Arrays.toString(palavrasChave);
    }

}

class No {
    public Filme elemento; // Conteudo do no.
    public No esq, dir; // Filhos da esq e dir.
    public int nivel;
    public boolean cor;

    /**
     * Construtor da classe.
     * 
     * @param elemento Conteudo do no.
     */
    public No(Filme elemento) {
        this.elemento = Filme.clonador(elemento);
        this.esq = null;
        this.dir = null;
        this.cor = false;
    }

    public No(Filme elemento, boolean cor) {
        this(elemento, cor, null, null);
    }

    /**
     * Construtor da classe.
     * 
     * @param elemento Conteudo do no.
     * @param esq      No da esquerda.
     * @param dir      No da direita.
     */
    public No(Filme elemento, boolean cor, No esq, No dir) {
        this.elemento = Filme.clonador(elemento);
        this.esq = esq;
        this.dir = dir;
        this.cor = cor;
    }

    public void setNivel() {
        this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
    }

    /**
     * Retorna o n�mero de n�veis a partir de um v�rtice
     * 
     * @param no n� que se deseja o n�vel.
     */
    public static int getNivel(No no) {
        return (no == null) ? 0 : no.nivel;
    }
}

class AVL {
    private No raiz; // Raiz da arvore.
    private String resposta = "raiz ";
    private String print = new String();

    /**
     * Construtor da classe.
     */
    public AVL() {
        raiz = null;
    }

    /**
     * Metodo publico iterativo para pesquisar elemento.
     * 
     * @param x Elemento que sera procurado.
     * @return <code>true</code> se o elemento existir,
     *         <code>false</code> em caso contrario.
     */
    public boolean pesquisar(String tituloOriginal) {
        resposta = "raiz ";
        return pesquisar(tituloOriginal, raiz);
    }

    public String getResposta() {
        return resposta;
    }

    public boolean isNoTipo4(No i) {
        return (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true);
    }

    /**
     * Metodo privado recursivo para pesquisar elemento.
     * 
     * @param x Elemento que sera procurado.
     * @param i No em analise.
     * @return <code>true</code> se o elemento existir,
     *         <code>false</code> em caso contrario.
     */
    private boolean pesquisar(String x, No i) {
        boolean resp;
        if (i == null) {
            resp = false;

        } else if (x.compareTo(i.elemento.getTituloOriginal()) == 0) {
            resp = true;

        } else if (x.compareTo(i.elemento.getTituloOriginal()) < 0) {
            resposta += "esq ";
            resp = pesquisar(x, i.esq);

        } else {
            resposta += "dir ";
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharCentral() {
        print = new String();
        caminharCentral(raiz);
        System.out.println(print.trim());
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i No em analise.
     */
    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq); // Elementos da esquerda.
            print += i.elemento;
            print += ' ';
            caminharCentral(i.dir); // Elementos da direita.
        }
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharPre() {
        print = new String();
        caminharPre(raiz);
        System.out.println(print.trim());
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i No em analise.
     */
    private void caminharPre(No i) {
        if (i != null) {
            print += i.elemento;
            print += ' '; // Conteudo do no.
            caminharPre(i.esq); // Elementos da esquerda.
            caminharPre(i.dir); // Elementos da direita.
        }
    }

    /**
     * Metodo publico iterativo para exibir elementos.
     */
    public void caminharPos() {
        print = new String();
        caminharPos(raiz);
        System.out.println(print.trim());
    }

    /**
     * Metodo privado recursivo para exibir elementos.
     * 
     * @param i No em analise.
     */
    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq); // Elementos da esquerda.
            caminharPos(i.dir); // Elementos da direita.
            print += i.elemento;
            print += ' '; // Conteudo do no.
        }
    }

    /**
     * Metodo publico iterativo para inserir elemento.
     * 
     * @param x Elemento a ser inserido.
     * @throws Exception Se o elemento existir.
     */

    /**
     * Metodo privado recursivo para inserir elemento.
     * 
     * @param x Elemento a ser inserido.
     * @param i No em analise.
     * @return No em analise, alterado ou nao.
     * @throws Exception Se o elemento existir.
     */
    public void inserir(Filme x) throws Exception {
        if (raiz == null) {
            raiz = new No(Filme.clonador(x));

            // Senao, se a arvore tiver um elemento
        } else if (raiz.esq == null && raiz.dir == null) {
            if (x.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) < 0) {
                raiz.esq = new No(Filme.clonador(x));
            } else {
                raiz.dir = new No(Filme.clonador(x));
            }

            // Senao, se a arvore tiver dois elementos (raiz e dir)
        } else if (raiz.esq == null) {
            if (x.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) < 0) {
                raiz.esq = new No(Filme.clonador(x));

            } else if (x.getTituloOriginal().compareTo(raiz.dir.elemento.getTituloOriginal()) < 0) {
                raiz.esq = new No(Filme.clonador(raiz.elemento));
                raiz.elemento = Filme.clonador(x);

            } else {
                raiz.esq = new No(Filme.clonador(raiz.elemento));
                raiz.elemento = Filme.clonador(raiz.dir.elemento);
                raiz.dir.elemento = Filme.clonador(x);
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, se a arvore tiver dois elementos (raiz e esq)
        } else if (raiz.dir == null) {
            if (x.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) > 0) {
                raiz.dir = new No(Filme.clonador(x));

            } else if (x.getTituloOriginal().compareTo(raiz.esq.elemento.getTituloOriginal()) > 0) {
                raiz.dir = new No(raiz.elemento);
                raiz.elemento = Filme.clonador(x);

            } else {
                raiz.dir = new No(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = Filme.clonador(x);
                System.out.println("Antes, dois elementos(F). Agora, raiz(" + raiz.elemento + "), esq ("
                        + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, a arvore tem tres ou mais elementos
        } else {
            inserir(Filme.clonador(x), null, null, null, raiz);
        }
        raiz.cor = false;
    }

    private void inserir(Filme x, No bisavo, No avo, No pai, No i) throws Exception {
        if (i == null) {
            if (x.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) < 0) {
                i = pai.esq = new No(Filme.clonador(x), true);
            } else {
                i = pai.dir = new No(Filme.clonador(x), true);
            }
            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }
        } else {
            // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if (i == raiz) {
                    i.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }
            if (x.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) < 0) {
                inserir(x, avo, pai, i, i.esq);
            } else if (x.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) > 0) {
                inserir(x, avo, pai, i, i.dir);
            } else {
                throw new Exception("Erro inserir (elemento repetido)!");
            }
        }
    }

    /**
     * Metodo publico para inserir elemento.
     * 
     * @param x Elemento a ser inserido.
     * @throws Exception Se o elemento existir.
     */
    /*
     * public void inserirPai(char x) throws Exception {
     * if (raiz == null) {
     * raiz = new No(x);
     * } else if (x < raiz.elemento) {
     * inserirPai(x, raiz.esq, raiz);
     * } else if (x > raiz.elemento) {
     * inserirPai(x, raiz.dir, raiz);
     * } else {
     * throw new Exception("Erro ao inserirPai!");
     * }
     * }
     */

    /**
     * Metodo privado recursivo para inserirPai elemento.
     * 
     * @param x   Elemento a ser inserido.
     * @param i   No em analise.
     * @param pai No superior ao em analise.
     * @throws Exception Se o elemento existir.
     */

    /*
     * private void inserirPai(char x, No i, No pai) throws Exception {
     * if (i == null) {
     * if (x < pai.elemento) {
     * pai.esq = new No(x);
     * } else {
     * pai.dir = new No(x);
     * }
     * } else if (x < i.elemento) {
     * inserirPai(x, i.esq, i);
     * } else if (x > i.elemento) {
     * inserirPai(x, i.dir, i);
     * } else {
     * throw new Exception("Erro ao inserirPai!");
     * }
     * }
     */

    /**
     * Metodo publico iterativo para remover elemento.
     * 
     * @param x Elemento a ser removido.
     * @throws Exception Se nao encontrar elemento.
     */
    /*
     * public void remover(String x) throws Exception {
     * raiz = remover(x, raiz);
     * }
     */

    /**
     * Metodo privado recursivo para remover elemento.
     * 
     * @param x Elemento a ser removido.
     * @param i No em analise.
     * @return No em analise, alterado ou nao.
     * @throws Exception Se nao encontrar elemento.
     */
    /*
     * private No remover(String x, No i) throws Exception {
     * 
     * if (i == null) {
     * 
     * 
     * } else if (x.compareTo(i.elemento.getTituloOriginal()) < 0) {
     * i.esq = remover(x, i.esq);
     * 
     * } else if (x.compareTo(i.elemento.getTituloOriginal()) > 0) {
     * i.dir = remover(x, i.dir);
     * 
     * // Sem no a direita.
     * } else if (i.dir == null) {
     * i = i.esq;
     * 
     * // Sem no a esquerda.
     * } else if (i.esq == null) {
     * i = i.dir;
     * 
     * // No a esquerda e no a direita.
     * } else {
     * i.esq = maiorEsq(i, i.esq);
     * }
     * 
     * return balancear(i);
     * }
     */

    private void balancear(No bisavo, No avo, No pai, No i) {
        // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
        if (pai.cor == true) {
            // 4 tipos de reequilibrios e acoplamento
            if (pai.elemento.getTituloOriginal().compareTo(avo.elemento.getTituloOriginal()) > 0) { // rotacao a
                                                                                                    // esquerda ou
                                                                                                    // direita-esquerda
                if (i.elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) > 0) {
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }
            } else { // rotacao a direita ou esquerda-direita
                if (i.elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) < 0) {
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }
            if (bisavo == null) {
                raiz = avo;
            } else if (avo.elemento.getTituloOriginal().compareTo(bisavo.elemento.getTituloOriginal()) < 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }
            // reestabelecer as cores apos a rotacao
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        } // if(pai.cor == true)
    }

    private No rotacaoDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    private No rotacaoEsq(No no) {
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        return noDir;
    }

    private No rotacaoDirEsq(No no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private No rotacaoEsqDir(No no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }

    /**
     * Metodo para trocar o elemento "removido" pelo maior da esquerda.
     * 
     * @param i No que teve o elemento removido.
     * @param j No da subarvore esquerda.
     * @return No em analise, alterado ou nao.
     */
    private No maiorEsq(No i, No j) {

        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = Filme.clonador(j.elemento); // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.

            // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    /**
     * Metodo que retorna o maior elemento da �rvore
     * 
     * @return int maior elemento da �rvore
     */
    /*
     * public int getMaior() {
     * int resp = -1;
     * 
     * if (raiz != null) {
     * No i;
     * for (i = raiz; i.dir != null; i = i.dir)
     * ;
     * resp = i.elemento;
     * }
     * 
     * return resp;
     * }
     */

    /**
     * Metodo que retorna o menor elemento da �rvore
     * 
     * @return int menor elemento da �rvore
     */
    /*
     * public int getMenor() {
     * int resp = -1;
     * 
     * if (raiz != null) {
     * No i;
     * for (i = raiz; i.esq != null; i = i.esq)
     * ;
     * resp = i.elemento;
     * }
     * 
     * return resp;
     * }
     */

    /**
     * Metodo que retorna a altura da �rvore
     * 
     * @return int altura da �rvore
     */
    public int getAltura() {
        return getAltura(raiz, 0);
    }

    /**
     * Metodo que retorna a altura da �rvore
     * 
     * @return int altura da �rvore
     */
    public int getAltura(No i, int altura) {
        if (i == null) {
            altura--;
        } else {
            int alturaEsq = getAltura(i.esq, altura + 1);
            int alturaDir = getAltura(i.dir, altura + 1);
            altura = (alturaEsq > alturaDir) ? alturaEsq : alturaDir;
        }
        return altura;
    }

    /**
     * Metodo publico iterativo para remover elemento.
     * 
     * @param x Elemento a ser removido.
     * @throws Exception Se nao encontrar elemento.
     */
    /*
     * public void remover2(int x) throws Exception {
     * if (raiz == null) {
     * throw new Exception("Erro ao remover2!");
     * } else if (x < raiz.elemento) {
     * remover2(x, raiz.esq, raiz);
     * } else if (x > raiz.elemento) {
     * remover2(x, raiz.dir, raiz);
     * } else if (raiz.dir == null) {
     * raiz = raiz.esq;
     * } else if (raiz.esq == null) {
     * raiz = raiz.dir;
     * } else {
     * raiz.esq = maiorEsq(raiz, raiz.esq);
     * }
     * }
     */

    /**
     * Metodo privado recursivo para remover elemento.
     * 
     * @param x   Elemento a ser removido.
     * @param i   No em analise.
     * @param pai do No em analise.
     * @throws Exception Se nao encontrar elemento.
     */
    /*
     * private void remover2(int x, No i, No pai) throws Exception {
     * if (i == null) {
     * throw new Exception("Erro ao remover2!");
     * } else if (x < i.elemento) {
     * remover2(x, i.esq, i);
     * } else if (x > i.elemento) {
     * remover2(x, i.dir, i);
     * } else if (i.dir == null) {
     * pai = i.esq;
     * } else if (i.esq == null) {
     * pai = i.dir;
     * } else {
     * i.esq = maiorEsq(i, i.esq);
     * }
     * }
     */

    /*
     * public String getRaiz() throws Exception {
     * return raiz.elemento.getTituloOriginal();
     * }
     * 
     * public static boolean igual(ArvoreBinaria a1, ArvoreBinaria a2) {
     * return igual(a1.raiz, a2.raiz);
     * }
     * 
     * private static boolean igual(No i1, No i2) {
     * boolean resp;
     * if (i1 != null && i2 != null) {
     * resp = (i1.elemento == i2.elemento) && igual(i1.esq, i2.esq) && igual(i1.dir,
     * i2.dir);
     * } else if (i1 == null && i2 == null) {
     * resp = true;
     * } else {
     * resp = false;
     * }
     * return resp;
     * }
     * 
     * public int soma() {
     * return soma(raiz);
     * }
     * 
     * public int soma(No i) {
     * int resp = 0;
     * if (i != null) {
     * resp = i.elemento + soma(i.esq) + soma(i.dir);
     * }
     * return resp;
     * }
     * 
     * public int quantidadePares() {
     * return quantidadePares(raiz);
     * }
     * 
     * public int quantidadePares(No i) {
     * int resp = 0;
     * if (i != null) {
     * resp = ((i.elemento % 2 == 0) ? 1 : 0) + quantidadePares(i.esq) +
     * quantidadePares(i.dir);
     * }
     * return resp;
     * }
     * 
     * public boolean hasDiv11() {
     * return hasDiv11(raiz);
     * }
     * 
     * public boolean hasDiv11(No i) {
     * boolean resp = false;
     * if (i != null) {
     * resp = (i.elemento % 11 == 0) || hasDiv11(i.esq) || hasDiv11(i.dir);
     * }
     * return resp;
     * }
     */
}

class Hash {
    int tabela[];
    int m1, m2, m, reserva;
    final int NULO = -1;

    public Hash() {
        this(21, 9);
    }

    public Hash(int m1, int m2) {
        this.m1 = m1;
        this.m2 = m2;
        this.m = m1 + m2;
        this.tabela = new int[this.m];
        for (int i = 0; i < m1; i++) {
            tabela[i] = NULO;
        }
        reserva = 0;
    }

    public int h(int elemento) {
        return elemento % m1;
    }

    public boolean inserir(int elemento) {
        boolean resp = false;
        if (elemento != NULO) {
            int pos = h(elemento);
            if (tabela[pos] == NULO) {
                tabela[pos] = elemento;
                resp = true;
            } else {
                pos = reh(elemento);
                if (tabela[pos] == NULO) {
                    tabela[pos] = elemento;
                    resp = true;
                }
            }
        }
        return resp;
    }

    public int reh(int elemento) {
        return ++elemento % m;
    }

    public boolean pesquisar(int elemento) {
        boolean resp = false;
        int pos = h(elemento);
        if (tabela[pos] == elemento) {
            resp = true;
            System.out.println("Posicao: " + pos);
        } else if (tabela[pos] != NULO) {
            for (int i = 0; i < reserva; i++) {
                if (tabela[m1 + i] == elemento) {
                    resp = true;
                    System.out.println("Posicao: " + (m1 + i));
                    i = reserva;
                }
            }
        }
        return resp;
    }

    boolean remover(int elemento) {
        boolean resp = false;
        // ...
        return resp;
    }
}