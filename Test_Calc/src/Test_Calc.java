import java.util.Arrays;
import java.util.Scanner;

public class Test_Calc {

    // Метод перевода числа int в римские цифры String
    public static String intToRoman(int num) {
        String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] vals = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        String ret = "";
        int ind = 0;

        while (ind < keys.length) {
            while (num >= vals[ind]) {
                int d = num / vals[ind];
                num = num % vals[ind];
                for (int i = 0; i < d; i++)
                    ret = ret + keys[ind];
            }
            ind++;
        }
        return ret;                                                 //если =="" - преобразование не удалось
    }

    //Метод перевода (String) с римскими числами из диапазона [1-10] в арабские (int)
    public static int romanToInt(String roman) {
        String[] keys = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (int i = 0; i < 10; i++) if (keys[i].equals(roman.toUpperCase())) return i + 1;
        return -1;                                                 //-1 - если не нашлось совпадение
    }

    // Метод парсинга входной строки (String) в массив строк (String[])
    // Output: [0] - первое число, [1] - второе число, [2] - знак

    public static String[] parseSeq(String in) {
        in = in.replaceAll("\\s", "");             // Минус все пробелы
        char[] inArray = in.toCharArray();                          // В массив символов, чтобы потом получить знак
        String[] split = in.split("\\+|-|\\*|/");             // Минус все разрешенные знаки операций
        int len = split[0].length();                                // Длина 1 числа, чтобы оторвать знак после

        // Если не 2 числа - выражение точно некорректно
        if (split.length != 2)
            throw new RuntimeException("Invalid input: can't recognize correct operation sign");

        String sign = String.valueOf(inArray[len]);                 // Знак равен следующему char за 1 числом
        return new String[]{split[0], split[1], sign};
    }

    public static String calc(String input) {
        String[] Data = parseSeq(input);                //получаем 1 и 2 число и знак
        String DataString = Arrays.toString(Data).replaceAll("[\\[\\],]", "");
                                                        //убираем скобки и запятые из Arrays.toString, пробелы остаются
        Scanner s = new Scanner(DataString);
        boolean isInt1 = s.hasNextInt();                //если int -> ставим флаг
        if (isInt1) s.nextInt();                        //читаем дальше
        boolean isInt2 = s.hasNextInt();                //второй флаг
        int roman1 = romanToInt(Data[0]);               //попытка прочитать в римском формате. -1 -> fail
        int roman2 = romanToInt(Data[1]);               //второе число

        int num1, num2, res = 0;
        boolean isArabic = true;                        // флаг arabic/roman

        // если isInt1 и isInt2 == true --> это однозначно два арабских числа.
        // если roman1 и roman2 != -1   --> это однозначно два римских числа.

        if (!((isInt1 && isInt2) || (roman1 != -1 && roman2 != -1)))
            throw new RuntimeException("Invalid input: mixed roman & arabic digits, or unrecognized characters");

        if (roman1 != -1 && roman2 != -1) {
            isArabic = false;
            num1 = roman1;
            num2 = roman2;
        } else {
            num1 = Integer.parseInt(Data[0]);
            num2 = Integer.parseInt(Data[1]);
        }
        String sign = Data[2];

        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10))
            throw new RuntimeException("Invalid input: out of range [1..10]");

        switch (sign) {
            case ("+"):
                res = num1 + num2; break;
            case ("-"):
                res = num1 - num2; break;
            case ("*"):
                res = num1 * num2; break;
            case ("/"):
                res = num1 / num2; break;
        }

        if (!isArabic) {
            if (res > 0)
                return intToRoman(res);
            else throw new RuntimeException("Out of result range: roman value cant be lower than 1");
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        System.out.println("Enter math expression for arabic/roman number in range [1-10], for example 5+4 or X*III");
        System.out.println("Valid math operators: +, - , *, /\n");

        Scanner console = new Scanner(System.in);
        String inputData = console.nextLine();

        System.out.println(calc(inputData));
    }
}