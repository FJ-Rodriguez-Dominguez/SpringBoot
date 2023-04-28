package com.cebem.rickandmorty.untils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Untils {
    public static boolean esPalindromo(String word) {
        String inverse = new StringBuilder(word).reverse().toString();
        return inverse.equalsIgnoreCase(word);
    }

    public static void writeOnDisk(String fileName, String info) throws IOException {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, true);
            fw.write(info);
        } finally {
            if (fw != null)
                fw.close();
        }
    }

    public static boolean deleteFromDisk(String filename) {
        File f = new File(filename);
        return f.delete();
    }

    public static String readFromDisk(String filename) throws FileNotFoundException, IOException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("datos.txt");
            br = new BufferedReader(fr);
            String line;
            String content = "<ul>";
            while ((line = br.readLine()) != null) {
                content += "<li>" + line + "<li>";
            }
            content += "</ul>";
            return content;
        } finally {

            if (fr != null)
                fr.close();
            if (br != null)
                br.close();

        }
    }

    public static void clearFile(String fileName) throws IOException {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, false);
            fw.write("");
        } finally {
            if (fw != null)
                fw.close();
        }
    }

    public static String maxNum(float... numeros) throws NumberFormatException {
        if (numeros == null || numeros.length == 0) {
            throw new NumberFormatException();

        }
        float elMayor = numeros[0];
        for (int i = 1; i < numeros.length; i++) {
            if (numeros[i] > elMayor)
                elMayor = numeros[i];
        }
        return elMayor + "";
    }

    public static String capitalizeText(String text) {
        String[] words = text.split(" ");
        for (String word : words) {
            char letra = Character.toUpperCase(word.charAt(0));
            String resto = word.substring(1).toLowerCase();
            word = letra + resto;
        }
        return Arrays.toString(words);
    }

    public static int getRandomValue(int max) {
        return (int) Math.floor(Math.random() * max);
    }
}
