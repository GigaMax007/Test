package ru.gigamax.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * ����� TestTask ������� N (N=10) ����� ����������� ���� � ������� ��������� ����� � ����� ���������� � ������� (standart out).
 * ���������  - ��������������� � �������� �������,
 * ������ ���� � ������ �������� � ����������� ���������� ������� ����� � ������!!!
 *
 * @autor ������ ���������, kmaxk2006@mail.ru, �.+7(910)73-88-00-9, https://github.com/GigaMax007/Test.git
 *
 * @version 1.2
 */
public class TestTask {
    public static int countFiles = 0; // ���������� - ���������� ������, �� ������� ����� ������ ������� ����.
    public static Word workList, analizeList, sortList;

    // �������� ������� ���� Word � ����� ������: ��������� ������ alList - ����� � alCount - ���������� �� � �����
    public static class Word {
        ArrayList<String> alList;
        ArrayList<Integer> alCount;

        public Word() {
            alList = new ArrayList<>();
            alCount = new ArrayList<>();
        }
    }

    // ����� ������� ����� ������� ����� �� ����� (countFiles + 1) ������ ��������.
    // ���� ������ ����� ���� - smallInput0.txt ... smallInput10.txt
    // name - ��� ����� �������� �������, ���� null, �� ��������� �������� "input.txt"
    // encoding - ��������� �����, ���� null, �� ��������� �������� "UTF-8"
    public static void divBigFile(String name, String encoding, int sizeMb) {
        int maxlines = sizeMb;
        BufferedWriter writer = null;
        String dBVName = name != null ? name : "input.txt";
        String dBVEncoding = encoding != null ? encoding : "UTF-8";
        int count1Mb = 0;
        long l = 0; // C������ ���� �����
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dBVName), dBVEncoding));
            for (String line1; (line1 = reader.readLine()) != null; ) {
                l += (long)line1.length();
                if (l > 1048576) {
                    l = 0;
                    count1Mb++;
                }
                if (count1Mb % maxlines == 0) {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("smallInput" + (count1Mb / maxlines) + ".txt"), dBVEncoding));
                    countFiles = count1Mb / maxlines; // �������� ����� ������ ����� (countFiles + 1)
                }
                writer.write(line1);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        for (int i = 0; i <= countFiles; i++) {
            (new File("smallInput" + i + ".txt")).deleteOnExit();
        }
    }

     // ����� ������� ������� ����, � ������ �������� �� �����, � ���������� ����������, �
    // ���������� �� � ������� ����
    public static void workFile(String name, String encoding) {
        System.out.println("\nStart TestTask.workFile " + new Date().toString());
        String wfName = name != null ? name : "All";
        String wfEncoding = encoding != null ? encoding : "UTF-8";
        workList = new Word();
        BufferedReader reader;
        // ��������� ���� name, �������� "smallInput12.txt"
        System.out.println("Read File = " + wfName);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(wfName), wfEncoding));
            for (String line; (line = reader.readLine()) != null; ) {
                StringTokenizer st = new StringTokenizer(line, " \t\r\f\\\'\"():,.&!|/��@;*{}[]? ");
                while (st.hasMoreTokens()) {
                    workList.alList.add(st.nextToken());
                    }
                }
            reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        System.out.println("workList.alList.size() = " + workList.alList.size());
        System.out.println("Translate workList to lower case:" + new Date().toString());
        // ������� ������� � ������ �������
        for (int i = 0; i < workList.alList.size(); i++) {
            workList.alList.set(i, workList.alList.get(i).toLowerCase());
        }
    }

    // ������� ���������� ���������� � �������� �������� ����
    public static void analizeFile() {
        System.out.println("\nStart TestTask.analizeFile " + new Date().toString());
        System.out.println("workList = " + workList);
        System.out.println("workList.alList.size() = " + workList.alList.size());
        System.out.println("workList.alCount.size() = " + workList.alCount.size());
        int z = workList.alList.size() - 1;
        int c = 0; // �������
        for (int i = 0; i < z; i++) {
            for (int j = z; (j > i) & (j > 0); j--) {
                if (workList.alList.get(j).equals(workList.alList.get(i))) {
                    c++;
                    workList.alList.remove(j);
                }
            }
            z = z - c;
            workList.alCount.add(c + 1);
            c = 0; // ������� �������
            if ((i == z - 1)&!(workList.alList.get(z - 1).equals(workList.alList.get(z)))) workList.alCount.add(1);
        }
        System.out.println("\nWord countig end: " + new Date().toString());
        System.out.println("workList = " + workList);
        System.out.println("workList.alCount.size() = " + workList.alCount.size());
        System.out.println("workList.alList.size = " + workList.alList.size());
        analizeList = workList;
        System.out.println("analizeList = " + analizeList);
        System.out.println("analizeList.alList.size() = " + analizeList.alList.size());
        System.out.println("analizeList.alCount.size() = " + analizeList.alCount.size());
        // �������� ���������� �� ���������� ������ Word
        workList = null;
    }

    public static void analizeFile(Word nameList) {
            System.out.println("\nStart TestTask.analizeFile " + new Date().toString());
            System.out.println("nameList = " + nameList);
            System.out.println("nameList.alList.size() = " + nameList.alList.size());
            System.out.println("nameList.alCount.size() = " + nameList.alCount.size());
            int z = nameList.alList.size() - 1;
            int c = 0; // �������
            for (int i = 0; i < z; i++) {
                for (int j = z; (j > i) & (j > 0); j--) {
                    if (nameList.alList.get(j).equals(nameList.alList.get(i))) {
                        c++;
                        nameList.alCount.set(i, nameList.alCount.get(i) + nameList.alCount.get(j));
                        nameList.alList.remove(j);
                        nameList.alCount.remove(j);
                    }
                }
                z = z - c;
                c = 0; // ������� �������
            }
            System.out.println("\nWord countig end: " + new Date().toString());
            System.out.println(" = " + nameList);
            System.out.println("nameList.alCount.size() = " + nameList.alCount.size());
            System.out.println("nameList.alList.size = " + nameList.alList.size());
            analizeList = nameList;
            System.out.println("analizeList = " + analizeList);
            System.out.println("analizeList.alList.size() = " + analizeList.alList.size());
            System.out.println("analizeList.alCount.size() = " + analizeList.alCount.size());
            // �������� ���������� �� ���������� ������ Word
            workList = null;
            sortList = null;
    }

    // ��������� ������� �� min �� max - �������� �� ���������� ���� ������� ������� ��������
    public static void sortFile(Word massiv) {
        int size = massiv.alCount.size();
        int dump = 0;
        String dumpList = "";
        for (int i = size - 1; i >= 1 ; i--) {
            for (int j = 0; j < i; j++) {
                if (massiv.alCount.get(j) > massiv.alCount.get(j + 1)) {
                    dump = massiv.alCount.get(j);
                    massiv.alCount.set(j, massiv.alCount.get(j + 1));
                    massiv.alCount.set(j + 1, dump);
                    dumpList = massiv.alList.get(j);
                    massiv.alList.set(j, massiv.alList.get(j +1));
                    massiv.alList.set(j + 1, dumpList);
                }
            }
        }
        sortList = massiv;
        massiv = null;
    }

    // ��������� ������� �� max �� min - �������� �� ���������� ���� ������� ������� ��������
    public static void sortMaxFile(Word massiv) {
        int size = massiv.alCount.size();
        int dump = 0;
        String dumpList = "";
        System.out.println("Start TestTask.sortMaxFile " + new Date().toString());
        for (int i = 0; i <= size - 1 ; i++) {
            for (int j = size - 1; j > i; j--) {
                if (massiv.alCount.get(j) > massiv.alCount.get(j - 1)) {
                    dump = massiv.alCount.get(j);
                    massiv.alCount.set(j, massiv.alCount.get(j - 1));
                    massiv.alCount.set(j - 1, dump);
                    dumpList = massiv.alList.get(j);
                    massiv.alList.set(j, massiv.alList.get(j - 1));
                    massiv.alList.set(j - 1, dumpList);
                }
            }
        }
        sortList = massiv;
        massiv = null;
        System.out.println("End TestTask.sortMaxFile " + new Date().toString());
    }

    // ����� ��������� ������ �� ��������� ����, ��������� ����� ������� ����� ������ � ��������� � �����
    public static void  safeFile(String nameFile, Word massiv) {
        String sfNameFile = "OutTemp_" + nameFile + ".txt";
        System.out.println("Start TestTask.safeFile " + new Date().toString());
        // ��������� �������� ������ ���� � ���������� �� � �����, ��������� �������� 0 � alCount
        if (massiv.alList.size() > massiv.alCount.size()) massiv.alCount.add(0);
        try (FileWriter writer1 = new FileWriter(sfNameFile, true)) {
            for (int i = 0; i < massiv.alList.size(); i++) {
                String text = massiv.alList.get(i) + " " + massiv.alCount.get(i) + "\n";
                writer1.write(text);
            }
            writer1.flush();
            massiv = null;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(sfNameFile + " safed at " + (new Date().toString()));
    }

    // ����� ��������� ������ ���� �� ����� name � ����������� ���������� massiv
    public static void loadFile(String name, String encoding,String nameList) throws IOException {
        System.out.println("Start TestTask.loadFile " + new Date().toString());
        Word massiv = new Word();
        String lfName = name != null ? name : "OutTemp_analizeList_.txt";
        String lfEncoding = encoding != null ? encoding : "UTF-8";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(lfName), lfEncoding));
        for (String line; (line = reader.readLine()) != null; ) {
            StringTokenizer st = new StringTokenizer(line);
            int c = 0;
            while (st.hasMoreTokens()) {
                if (c == 0) massiv.alList.add(st.nextToken());
                else massiv.alCount.add(Integer.parseInt(st.nextToken()));
                c++;
            }
        }
        // ��������� ������ ���� �� ���������� ����� � ������ �� ���������� ����������� ������,
        // ��������� ���������� ��������
        analizeList = nameList == "analizeList" ? massiv : null;
        workList = nameList == "workList" ? massiv : null;
        sortList = nameList == "sortList" ? massiv : null;
        massiv = null;
        System.out.println("End TestTask.loadFile " + new Date().toString());
    }

    // ����� � ������� ��� �������� �������, �� ������� ��������� ����������
    public static void chekIt(Word massiv) {
        for (int i = 0; i < massiv.alList.size(); i++) {
            if (massiv.alCount.size() == massiv.alList.size())
            System.out.println(i +  " - " + massiv.alList.get(i) + "   quantity - " + massiv.alCount.get(i));
            if (massiv.alCount.size() == 0) System.out.println(i +  " - " + massiv.alList.get(i));
        }
    }

    // ����� ��������������� ������������������
    // ����� result() - ������� � ������� (Standart Out) ��������������� � �������� ������� 10 ����
    public static void result() {
        System.out.println("TestTask.result " + new Date().toString());
        for (int i = 0; i < 10; i++) {
            System.out.print(sortList.alList.get(i));
            System.out.println(" " + sortList.alCount.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        // ������ ������ �� ������� ���������� ���������� ���� - ����� � ���
        // ���� input.txt �������� �� ������� � 166 Mb
        String encoding = "Cp1251";
        divBigFile(null, encoding, 3);

        for (int i = 0; i <= 1; i++) {
            workFile("smallInput" + i + ".txt", encoding);
//            chekIt(workList);
            analizeFile();
            safeFile("analizeList", analizeList);
        }

        String nameFile = "OutTemp_analizeList.txt";
        loadFile(nameFile, "Cp1251", "sortList");
        analizeFile(sortList);
        sortMaxFile(analizeList);
        result();
        safeFile("result",sortList);
    }
}