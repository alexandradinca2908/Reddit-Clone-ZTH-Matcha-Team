package org.example.textprocessors;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class ProfanityFilter {
    private static ProfanityFilter instance;
    private static final File file = new File("D:\\Cognyte\\Matcha\\Backend\\cli-backend\\src\\main\\java\\org\\example\\textprocessors\\enbanned.txt");
    private static  String remplacementChar;
    private static TreeSet<String> bannedWords;

    public ProfanityFilter() {
        bannedWords = new TreeSet<>();
        remplacementChar = "*";

    }

    public static ProfanityFilter getInstance() {
        if (instance == null) {
            instance = new ProfanityFilter();
        }
        return instance;
    }

    static void initBannedWords() throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            bannedWords.add(line);
        }
    }

    public String filter(String text) throws FileNotFoundException {
        initBannedWords();
        String[] wordList = text.split(" ");
        StringBuilder cleanText = new StringBuilder();
        for (String word : wordList) {
            if(bannedWords.contains(word)){
                int wordLength = word.length();
                cleanText.append(remplacementChar.repeat(wordLength));
            } else {
                cleanText.append(word);
            }
            cleanText.append(" ");
        }
        return cleanText.toString();
    }



}
