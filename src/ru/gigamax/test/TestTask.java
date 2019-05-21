package ru.gigamax.test;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс TestTask находит N (N=10) самых упоминаемых слов в большом текстовом файле
 * и вывод результата в консоль (standart out).
 * Результат  - отсортированный в обратном порядке,
 * список слов в нижнем регистре с количеством упоминаний каждого слова в тексте!!!
 *
 * @autor Максим Кишинский, kmaxk2006@mail.ru, т.+7(910)73-88-00-9, https://github.com/GigaMax007
 *
 * @version 2.2
 */
public class TestTask {
    private static final String ENCODING = "UTF-8";
    public static int countFiles;
    public static List<String> loadList;
    public static Map<String, Long> tempMap;
    public static Map<String, Long> resultMap = new HashMap<>(80_000);

    public static void main(String[] args) {
        divBigFile("input.txt", 250_000.0);
        for (int i = 1; i <= countFiles; i++) {
            String fileName = "mini" + i + ".tmp";
            createMap(fileName);
            resultMap = mergeTempMaps(tempMap);
        }
        resultMap = mergeTempMaps(tempMap);
        System.out.println("resultMap.size() = " + resultMap.size());
        printSortedMap(resultMap, 10);
    }
    // Деление большого файла на маленькие mini1...6.tmp
    public static void divBigFile(String inputFile, double nol) {
        System.out.println("Start divBigFile " + new Date().toString());
        try {
            // Чтение файла и подсчет количества строк и определение количества файлов, которые будут сгенерированы.
            Scanner scanner = new Scanner(new File(inputFile));
            int count = 0;
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
            System.out.println("Lines in the file: " + count);     // Выводит количество строк в исходном файле.
            double temp = (count / nol);
            int temp1 = (int) temp;
            countFiles = 0;
            if (temp1 == temp) countFiles = temp1;
            else countFiles = temp1 + 1;
            System.out.println("No. of files to be generated :" + countFiles); // Выводит количество файлов, которые будут сгенерированы.
            // Разбиение файла на более мелкие
            DataInputStream in = new DataInputStream(new FileInputStream(inputFile));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            for (int j = 1; j <= countFiles; j++) {
                // Файлы, которые будут созданы
                BufferedWriter out = new BufferedWriter(new FileWriter("mini" + j + ".tmp"));
                for (int i = 1; i <= nol; i++) {
                    strLine = br.readLine();
                    if (strLine != null) {
                        out.write(strLine);
                        if (i != nol) {
                            out.newLine();
                        }
                    }
                }
                out.close();
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        // Вывод размера одного из разделенных файлов - mini1.tmp, если размер больше 85Мб
        float fileSize = (new File("mini1.tmp")).length() / (1024.0f * 1024.0f);
        if (fileSize > 85.0f) {
            System.out.println("mini1.tmp = " + fileSize + " Mb");
            System.out.println("Line size is about " + 1024 * 1024 * fileSize / nol + " Bytes");
            System.out.println("Please, reduce the number of the lines (nol - parameter of divBigFile method) ");
        }
    }
    // Создание списка loadList и словаря tempMap
    public static void createMap(String mFileName) {
        BufferedReader reader;
        loadList = new ArrayList<>(3_000_000);
        System.out.println("\nStart load List loadList " + new Date().toString());

        // Считываем файл name, например "mini0.tmp" и создаем список loadList
        System.out.println("Read File = " + mFileName);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mFileName), ENCODING));
            for (String line; (line = reader.readLine()) != null; ) {
                StringTokenizer st = new StringTokenizer(line, " \t\r\f\\\'\"():,.&!|/«=»@;*{}[]?\n");
                while (st.hasMoreTokens()) {
                    loadList.add(st.nextToken().toLowerCase());
                }
            }
            reader.close();
        } catch (OutOfMemoryError e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("loadList.size() = " + loadList.size());
        System.out.println("\nStart Map tempMap " + new Date().toString());
        // Создание временного словаря tempMap используя поток...
        tempMap = loadList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("tempMap.size() = " + tempMap.size());
        // Удаление временного файла по завершении работы
        new File(mFileName).deleteOnExit();
    }
    // Вывод отсортированного словаря в консоль
    public static void printSortedMap(Map<String, Long> map, int n){
        // Начинаем сортировку
        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(n).forEach(System.out::println);
    }
    // Слияние временных словарей ArrayList<Map<String, Long>> allTempMap
    public static Map<String, Long> mergeTempMaps(Map<String, Long> allTempMap) {
        // Используем метод merge, example: m2.forEach((k, v) -> m.merge(k, v, (v1, v2) -> v1 + v2));
        allTempMap.forEach((k, v) -> resultMap.merge(k, v, (v1, v2) -> v1 + v2));
        allTempMap.clear();
        return resultMap;
    }
}