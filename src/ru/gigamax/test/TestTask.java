package ru.gigamax.test;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ����� TestTask ������� N (N=10) ����� ����������� ���� � ������� ��������� ����� � ����� ���������� � ������� (standart out).
 * ���������  - ��������������� � �������� �������,
 * ������ ���� � ������ �������� � ����������� ���������� ������� ����� � ������!!!
 *
 * @autor ������ ���������, kmaxk2006@mail.ru, �.+7(910)73-88-00-9, https://github.com/GigaMax007
 *
 * @version 2.0
 */
public class TestTask {
    private static final String ENCODING = "Cp1251";
    public static int countFiles;
    public static List<String> loadList;
    public static Map<String, Long> tempMap;
    public static Map<String, Long> resultMap = new HashMap<>(80_000);

    public static void main(String[] args) {
        divBigFile(25);
        for (int i = 0; i <= countFiles; i++) {
            String fileName = "mini" + i + ".tmp";
            createMap(fileName);
            resultMap = mergeTempMaps(tempMap);
        }
        System.out.println("resultMap.size() = " + resultMap.size());
        printSortedMap(resultMap, 10);
    }
    // ������� �������� ����� �� ��������� mini0...6.tmp
    public static void divBigFile(int sizeMb) {
        final String NAME = "input.txt";
        BufferedWriter writer = null;
        int count1Mb = 0;
        long l = 0; // C������ ���� �����
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(NAME), ENCODING));
            for (String line1; (line1 = reader.readLine()) != null; ) {

                if (l >= 1048576) {
                    l = 0;
                    count1Mb++;
                }
                if ((count1Mb % sizeMb == 0)&(l == 0)) {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("mini" + (count1Mb / sizeMb) + ".tmp"), ENCODING));
                    countFiles = count1Mb / sizeMb; // �������� ����� ������ ����� (countFiles + 1)
                }
                writer.write(line1);
                writer.newLine();
                l += (long)line1.length();
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    // �������� ������ loadList � ������� tempMap
    public static void createMap(String mFileName) {
        BufferedReader reader;
        loadList = new ArrayList<>(1_000_000);
        System.out.println("\nStart load List loadList " + new Date().toString());

        // ��������� ���� name, �������� "mini0.tmp" � ������� ������ loadList
        System.out.println("Read File = " + mFileName);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mFileName), ENCODING));
            for (String line; (line = reader.readLine()) != null; ) {
                StringTokenizer st = new StringTokenizer(line, " \t\r\f\\\'\"():,.&!|/��@;*{}[]?\n");
                while (st.hasMoreTokens()) {
                    loadList.add(st.nextToken().toLowerCase());
                }
            }
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("loadList.size() = " + loadList.size());
        System.out.println("\nStart Map tempMap " + new Date().toString());
        // �������� ���������� ������� tempMap ��������� �����...
        tempMap = loadList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("tempMap.size() = " + tempMap.size());
        // �������� ���������� ����� �� ���������� ������
        new File(mFileName).deleteOnExit();
    }
    //����� ���������������� ������� � �������
    public static void printSortedMap(Map<String, Long> map, int n){
        // �������� ����������
        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(n).forEach(System.out::println);
    }
    // ������� ��������� �������� ArrayList<Map<String, Long>> allTempMap
    public static Map<String, Long> mergeTempMaps(Map<String, Long> allTempMap) {
        /*     ���������� ����� merge, example:
                    m2.forEach((k, v) -> m.merge(k, v, (v1, v2) -> v1 + v2));  */
        allTempMap.forEach((k, v) -> resultMap.merge(k, v, (v1, v2) -> v1 + v2));
        allTempMap.clear();
        return resultMap;
    }
}