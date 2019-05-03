package ru.gigamax.test;

import java.io.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/*
*         Тестовое задание на вакансию Java разработчик-стажер
*
* Разработать программу на Java, которая найдет N (N=10) самых упоминаемых слов в большом текстовом файле.
* Результат — список слов в нижнем регистре с количеством упоминаний каждого слова в тексте,
* отсортирован в обратном порядке по количество упоминаний. Вывод результата в консоль (standart out).
*
* Требования:

* Ограничение по ОЗУ — не более 512МБ (-Xmx512m).

* Размер текстового файла намного больше ОЗУ.
* Количество уникальных слов в текстовом файле не более 10000 шт.
*
* Пример работы.
*
* Файл на входе, input.txt:
* Мама мыла раму, рама мыла маму.
*
* Вывод:
* мыла 2
* мама 1
* раму 1
* рама 1
* маму 1
*/

/**
 * Класс TestTask находит N (N=10) самых упоминаемых слов в большом текстовом файле и вывод результата в консоль (standart out).
 * Результат  - отсортированный в обратном порядке,
 * список слов в нижнем регистре с количеством упоминаний каждого слова в тексте!!!
 *
 * @autor Максим Кишинский, kmaxk2006@mail.ru, т.+7(910)73-88-00-9
 *
 * @version 1.0
 */
public class TestTask {
    public static int countFiles = 0; // Переменная - количество файлов, на которые будет разбит большой файл.
    public static Word workList;
    public static Word lowerCase;
    public static Word analizeList;
    public static Word sortList;

    // Создание класса с двумя полями: списка слов и количества повторений
    public static class Word {
        ArrayList<String> alList;
        ArrayList<Integer> alCount;

        public Word(int quantity) {
           alList = new ArrayList<>(quantity);
           alCount = new ArrayList<>(quantity);
        }

        public Word() {
            alList = new ArrayList<>();
            alCount = new ArrayList<>();
        }
    }

    // Метод деления файла большой длины на число (countFiles + 1) файлов поменьше.
    // Итог работы файлы вида - smallInput0.txt ... smallInput10.txt
    // name - имя файла большого размера, если null, то принимает значение "input.txt"
    // encoding - кодировка файла, если null, то принимает значение "UTF-8"
    public static void divBigFile(String name, String encoding) {
        int maxlines = 25000;
        BufferedWriter writer = null;
        String dBVName = name != null ? name : "input.txt";
        String dBVEncoding = encoding != null ? encoding : "UTF-8";
        int countLine = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dBVName), dBVEncoding));
            for (String line1; (line1 = reader.readLine()) != null; ) {
                if (countLine++ % maxlines == 0) {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("smallInput" + (countLine / maxlines) + ".txt"), dBVEncoding));
                    countFiles = countLine / maxlines; // Итоговое число файлов равно (countLine + 1)
                }
                writer.write(line1);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Метод деления файла большой длины на число (countFiles + 1) файлов поменьше.
    // по умолчанию файл - "input.txt" , кодировка - "UTF-8"
    public static void divBigFile() {
        int maxlines = 25000;
        BufferedWriter writer = null;
        String dBVName = "input.txt";
        String dBVEncoding = "UTF-8";
        int countLine = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dBVName), dBVEncoding));
            for (String line1; (line1 = reader.readLine()) != null; ) {
                if (countLine++ % maxlines == 0) {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("smallInput" + (countLine / maxlines) + ".txt"), dBVEncoding));
                    countFiles = countLine / maxlines; // Итоговое число файлов равно (countLine + 1)
                }
                writer.write(line1);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Метод создает массивы слов, в нижнем регистре из файла, и количества повторений, и
    // записывает их в рабочий файл
    public static void workFile(String name, String encoding) {
        System.out.println("TestTask.workFile " + new Date().toString());
        String wfName = name != null ? name : "All";
        String wfEncoding = encoding != null ? encoding : "UTF-8";

        workList = new Word();
        BufferedReader reader;
        // Считываем файл name, например "smallInput12.txt"
        System.out.println("Read File = " + wfName);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(wfName), wfEncoding));
            for (String line; (line = reader.readLine()) != null; ) {
            //System.out.println(line);
                StringTokenizer st = new StringTokenizer(line, " \t\r\f\\\'\"():,.&!|/«»@;*{}[]? ");
                while (st.hasMoreTokens()) {
                    workList.alList.add(st.nextToken());
                    //workList.alCount.add(st.countTokens());
                    }
                }
            reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

//        System.out.println("Create:\nworkList = " + workList);
//        for (int i = 0; i < 10; i++) {
//            System.out.print(i + " workList.alList = " + workList.alList.get(i));
//        }
        System.out.println("workList.alList.size() = " + workList.alList.size());
//        System.out.println("workList.alCount.size() = " + workList.alCount.size());
        System.out.println("Translate workList to lower case:" + new Date().toString());

        lowerCase = new Word();
        // Перевод массива в нижний регистр
        for (int i = 0; i < workList.alList.size(); i++) {
            lowerCase.alList.add(workList.alList.get(i).toLowerCase());
        }

        System.out.print("lowerCase = " + lowerCase + new Date().toString());
//        for (int i = 0; i < 10; i++) {
//
//            System.out.println(i + " lowerCase.alList = " + lowerCase.alList.get(i));
//
//        }
        System.out.println("lowerCase.alList.size() = " + lowerCase.alList.size());
        System.out.println("lowerCase.alCount.size() = " + lowerCase.alCount.size());
    }

    // Подсчет количества повторений и удаление повторов слов
    public static void analizeFile() {
        System.out.println("TestTask.analizeFile " + new Date().toString());
        System.out.println("lowerCase = " + lowerCase);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(i + " lowerCase.alList = " + lowerCase.alList.get(i));
//
//        }
        System.out.println("lowerCase.alList.size() = " + lowerCase.alList.size());
        System.out.println("lowerCase.alCount.size() = " + lowerCase.alCount.size());

        int z = lowerCase.alList.size() - 1;
        int c = 0; // счетчик
//        System.out.println(z);
        for (int i = 0; i < z; i++) {

            for (int j = z; (j > i) & (j > 0); j--) {

                if (lowerCase.alList.get(j).equals(lowerCase.alList.get(i))) {
                    c++;

                    lowerCase.alList.remove(j);
                }

            }
            z = z - c;
//            System.out.println(z);
            lowerCase.alCount.add(c + 1);
            c = 0; // счетчик обнулен
            if ((i == z - 1)&!(lowerCase.alList.get(z - 1).equals(lowerCase.alList.get(z)))) lowerCase.alCount.add(1);
        }

        System.out.println("Word countig end:");
        System.out.println("TestTask.analizeFile " + new Date().toString());
        System.out.println("lowerCase = " + lowerCase);
        System.out.println("lowerCase.alCount.size() = " + lowerCase.alCount.size());

        System.out.println("lowerCase.alList.size = " + lowerCase.alList.size());
        analizeList = lowerCase;
        System.out.println("TestTask.analizeFile " + new Date().toString());
        System.out.println("analizeList = " + analizeList);
//        for (int i = 0; i < 10; i++) {
//            System.out.print(i + " analizeList.alList = " + analizeList.alList.get(i));
//            System.out.println(" analizeList.alCount = " + analizeList.alCount.get(i));
//        }

        System.out.println("analizeList.alList.size() = " + analizeList.alList.size());
        System.out.println("analizeList.alCount.size() = " + analizeList.alCount.size());

        workList = null;
        lowerCase = null;

    }

    public static void analizeFile(String nameList) {

        if (nameList == "sortList") {

            System.out.println("TestTask.analizeFile " + new Date().toString());
            System.out.println("sortList = " + sortList);
//            for (int i = 0; i < 10; i++) {
//                System.out.println(i + " lowerCase.alList = " + sortList.alList.get(i));
//
//            }
            System.out.println(".alList.size() = " + sortList.alList.size());
            System.out.println(".alCount.size() = " + sortList.alCount.size());

            int z = sortList.alList.size() - 1;
            int c = 0; // счетчик
//            System.out.println(z);
            for (int i = 0; i < z; i++) {

                for (int j = z; (j > i) & (j > 0); j--) {
                    if (sortList.alList.get(j).equals(sortList.alList.get(i))) {
                        c++;
                        sortList.alCount.set(i, sortList.alCount.get(i) + sortList.alCount.get(j));
                        sortList.alList.remove(j);
                        sortList.alCount.remove(j);
                    }
                }
                z = z - c;
//                System.out.println(z);
                c = 0; // счетчик обнулен
            }

            System.out.println("Word countig end:");
            System.out.println("TestTask.analizeFile " + new Date().toString());
            System.out.println(" = " + sortList);
            System.out.println(".alCount.size() = " + sortList.alCount.size());
            System.out.println(".alList.size = " + sortList.alList.size());
            analizeList = sortList;
            System.out.println("TestTask.analizeFile " + new Date().toString());
            System.out.println("analizeList = " + analizeList);
//            for (int i = 0; i < 10; i++) {
//                System.out.print(i + " analizeList.alList = " + analizeList.alList.get(i));
//                System.out.println(" analizeList.alCount = " + analizeList.alCount.get(i));
//            }

            System.out.println("analizeList.alList.size() = " + analizeList.alList.size());
            System.out.println("analizeList.alCount.size() = " + analizeList.alCount.size());

            workList = null;
            lowerCase = null;
            sortList = null;

        }
    }

    // Сортирует массивы в обратном порядке по количеству повторений
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

//    public static void sortFile(String workFile, String encoding, int sortQuantity) {
//        String sfWorkFile = workFile != null ? workFile : "workFile.txt";
//        String sfEncoding = encoding != null ? encoding : "UTF-8";
//        int sfSortQuantity = sortQuantity;
//        sortList = new Word();
//
//    }

    // Метод сохраняет список во временный файл, повторный вызов допишет новые данные к имеющимся в файле
    public static void  safeFile(String nameFile, Word massiv) {
        String sfNameFile = "OutTemp_" + nameFile + ".txt";
        System.out.println("Start TestTask.safeFile " + new Date().toString());

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

    // Метод загружает список слов из файла name и присваивает переменной massiv
    public static void loadFile(String name, String encoding,String nameList) throws IOException {
        System.out.println("Start TestTask.loadFile " + new Date().toString());

        Word massiv = new Word();


        String lfName = name != null ? name : "OutTemp_analizeList_.txt";
        String lfEncoding = encoding != null ? encoding : "UTF-8";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(lfName), lfEncoding));
        for (String line; (line = reader.readLine()) != null; ) {
//            System.out.println(line);
            StringTokenizer st = new StringTokenizer(line);
            int c = 0;
            while (st.hasMoreTokens()) {
                if (c == 0) massiv.alList.add(st.nextToken());
                else massiv.alCount.add(Integer.parseInt(st.nextToken()));
                c++;
            }
        }

        analizeList = nameList == "analizeList" ? massiv : null;
        workList = nameList == "workList" ? massiv : null;
        lowerCase = nameList == "lowerCase" ? massiv : null;
        sortList = nameList == "sortList" ? massiv : null;
        massiv = null;
        System.out.println("End TestTask.loadFile " + new Date().toString());
    }

    // Вывод итогового массива
    public static void chekIt(Word massiv) {
        for (int i = 0; i < massiv.alList.size(); i++) {
            if (massiv.alCount.size() == massiv.alList.size())
            System.out.println(i +  " - " + massiv.alList.get(i) + "   quantity - " + massiv.alCount.get(i));
            if (massiv.alCount.size() == 0) System.out.println(i +  " - " + massiv.alList.get(i));
        }
    }

    // Вывод отсортированной последовательности
    // метод result() - выводит в консоль (Standart Out) отсортированные в обратном порядке 10 слов
    public static void result() {
        System.out.println("TestTask.result " + new Date().toString());
        for (int i = 0; i < 10; i++) {
            System.out.print(sortList.alList.get(i));
            System.out.println(" " + sortList.alCount.get(i));
        }

    }

    // Метод result(N) - ...
    public static void result(int n) {}

    // Метод result(N, outFile) - ...
    public static  void result(int n, String outFile) {}

    public static void main(String[] args) throws IOException {
        // Решаем задачу на примере сортировки нескольких книг - война и мир
        String encoding = "Cp1251";
        divBigFile(null, encoding);

        for (int i = 0; i <= countFiles; i++) {
            String nameFile = "smallInput" + i + ".txt";
            workFile(nameFile, encoding);
            analizeFile();
            safeFile("analizeList", analizeList);
        }

        String nameFile = "OutTemp_analizeList.txt";
        loadFile(nameFile, "Cp1251", "sortList");
        analizeFile("sortList");
        sortMaxFile(analizeList);
        result();
        safeFile("result",sortList);

    }
}



