package com.kykov.jo.test;

import java.util.Arrays;
import java.util.Scanner;

public class RomanCalc {

    public static void main(String[] args) throws Exception  {
        char[] operations = {'+', '-', '*', '/'};

        Scanner in = new Scanner(System.in);
        String inputText = in.nextLine();

        for (char o : operations ) {
            int i=inputText.indexOf(o);
            if (i>-1) {
                Digit leftDigit = new Digit (inputText.substring(0,i).trim());
                Digit rightDigit = new Digit (inputText.substring(i+1).trim());
                System.out.println(leftDigit.calculate(o,rightDigit).AsString());
                return;
            }
        }
        throw new Exception("Не удалось найти знак операции во введённом выражении");
    }

// не понимаю, почему static, но IIdea очень настаивала.
    static class Digit {
        String[] romanian = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        String text;
        int value;
        char type;

        Digit (String str) throws Exception {
            text = str.trim();
            recognize();
        }

        void recognize() throws Exception {
            int i=Arrays.asList(romanian).indexOf(text);
            if (i >=0) {
                type = 'r';
                value=i+1;
            }
            else {
                try {
                    type = 'a';
                    value = Integer.parseInt(text);
                }
                catch(Exception ex) { throw new Exception("Не удалось распознать текст '"+text+"'"); }
            }
            if ((value > 10) | (value < 1))
                throw new Exception("Принимаются только числа от 1 до 10 (I до X)");
        }

        String AsString() {
                return text;
        }

        Digit calculate(char operation, Digit second) throws Exception {
            Digit result = new Digit ("1");
            if (this.type != second.type)
                throw new Exception("Операции производятся только над числами, записанными в одной системе счисления.");
            else result.type = this.type;
            switch (operation) {
                case '-':
                    result.value = this.value - second.value;
                    break;
                case '+':
                    result.value = this.value + second.value;
                    break;
                case '*':
                    result.value = this.value * second.value;
                    break;
                case '/':
                    result.value = this.value / second.value;
                    break;
            }
            if ((result.type == 'r') & (result.value < 1))
                throw new Exception("Римские цифры не приспособлены отображать 0 и отрицательные числа.");
            result.rewriteText();
            return result;
        }

        void rewriteText() {
            if (type == 'r')
                text = toRomanString(value);
            else
                text = Integer.toString(value);
        }

        String toRomanString (int i) {
            //Согласен, способ тупой, зато понятный. В конце концов, результат не больше 100.
            String text = "I".repeat(i);
            text = text.replace("IIIII","V");
            text = text.replace("VV","X");
            text = text.replace("XXXXX","L");
            text = text.replace("LL","C");
            text = text.replace("LXXXX", "XC");
            text = text.replace("XXXX", "XL");
            text = text.replace("VIIII", "IX");
            text = text.replace("IIII", "IV");
            return text;
        }
    }

}