package assignment3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScrabbleModel {

    HashMap<Character, Integer> letterPoints = new HashMap<Character, Integer>();
    HashMap<Character, Integer> letterCount = new HashMap<Character, Integer>();
    public ArrayList<String> wordHistory = new ArrayList<>();
    public int totalPoints = 0;
    public String error = "";

    public void populateLetterPoints() {
        letterPoints.put('A', 1);
        letterPoints.put('B', 3);
        letterPoints.put('C', 3);
        letterPoints.put('D', 2);
        letterPoints.put('E', 1);
        letterPoints.put('F', 4);
        letterPoints.put('G', 2);
        letterPoints.put('H', 4);
        letterPoints.put('I', 1);
        letterPoints.put('J', 8);
        letterPoints.put('K', 5);
        letterPoints.put('L', 1);
        letterPoints.put('M', 3);
        letterPoints.put('N', 1);
        letterPoints.put('O', 1);
        letterPoints.put('P', 3);
        letterPoints.put('Q', 10);
        letterPoints.put('R', 1);
        letterPoints.put('S', 1);
        letterPoints.put('T', 1);
        letterPoints.put('U', 1);
        letterPoints.put('V', 4);
        letterPoints.put('W', 4);
        letterPoints.put('X', 8);
        letterPoints.put('Y', 4);
        letterPoints.put('Z', 10);
    }

    public void populateLetterCount() {
        letterCount.put('A', 9);
        letterCount.put('B', 2);
        letterCount.put('C', 2);
        letterCount.put('D', 4);
        letterCount.put('E', 12);
        letterCount.put('F', 2);
        letterCount.put('G', 3);
        letterCount.put('H', 2);
        letterCount.put('I', 8);
        letterCount.put('J', 1);
        letterCount.put('K', 1);
        letterCount.put('L', 4);
        letterCount.put('M', 2);
        letterCount.put('N', 6);
        letterCount.put('O', 8);
        letterCount.put('P', 2);
        letterCount.put('Q', 1);
        letterCount.put('R', 6);
        letterCount.put('S', 4);
        letterCount.put('T', 6);
        letterCount.put('U', 4);
        letterCount.put('V', 2);
        letterCount.put('W', 2);
        letterCount.put('X', 1);
        letterCount.put('Y', 2);
        letterCount.put('Z', 1);
    }

    public boolean addWord(String word) {
        String wordCapitalized = word.toUpperCase();
        boolean wordAdded = false;
        if (checkWord(wordCapitalized)) {
            wordHistory.add(wordCapitalized);
            countUpdate(wordCapitalized);
            totalUpdate(wordCapitalized);
            wordAdded = true;
        }
        return wordAdded;
    }

    private boolean checkWord(String wordCapitalized) {
        boolean flag = false;
        if (wordCapitalized.length() >= 2
                && wordCapitalized.length() <= 8
                && checkVowel(wordCapitalized)
                && checkLetterAvailability(wordCapitalized)
                && !wordHistory.contains(wordCapitalized)
        ) {
            flag = true;
        }
        if (wordHistory.contains(wordCapitalized)) {
            error = "Word already spelt";
        }
        if (!checkVowel(wordCapitalized)) {
            error = "Word should contain atleast one vowel";
        }
        if (!checkLetterAvailability(wordCapitalized)) {
            error = "One or more letter not available in bag";
        }
        if (wordCapitalized.length() > 8) {
            error = "Word too long";
        }
        if (wordCapitalized.length() < 2) {
            error = "Word too short";
        }
        if (wordCapitalized.length() == 0) {
            error = "Word is blank";
        }
        return flag;
    }

    private boolean checkVowel(String wordCapitalized) {
        boolean flag = false;
        char[] vowels = {'A', 'E', 'I', 'O', 'U'};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < wordCapitalized.length(); j++) {
                if (!flag && wordCapitalized.charAt(j) == vowels[i]) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private boolean checkLetterAvailability(String wordCapitalized) {
        boolean flag = true;
        HashMap<Character,Integer> temporaryLetterCount = new HashMap<Character,Integer>(letterCount);
        for (int i = 0; i < wordCapitalized.length(); i++) {
            for (Map.Entry entry : temporaryLetterCount.entrySet()) {
                if (flag && wordCapitalized.charAt(i) == (char) entry.getKey()) {
                    Integer count = (int) entry.getValue();
                    if (count == 0) {
                        flag = false;
                    }
                    count -= 1;
                    temporaryLetterCount.replace((char) entry.getKey(), count);
                }
            }
        }
        return flag;
    }

    private void countUpdate(String wordCapitalized) {
        for (int i = 0; i < wordCapitalized.length(); i++) {
            for (Map.Entry entry : letterCount.entrySet()) {
                if (wordCapitalized.charAt(i) == (char) entry.getKey()) {
                    Integer newCount = (int) entry.getValue();
                    newCount -= 1;
                    if (newCount >= 0) {
                        letterCount.replace((char) entry.getKey(), newCount);
                    }
                }
            }
        }
    }

    private void totalUpdate(String wordCapitalized) {
        for (int i = 0; i < wordCapitalized.length(); i++) {
            for (Map.Entry entry : letterPoints.entrySet()) {
                if (wordCapitalized.charAt(i) == (char) entry.getKey()) {
                    Integer points = (int) entry.getValue();
                    totalPoints += points;
                }
            }
        }
    }

    public int updateLabels(char label, int originalCount) {
        Integer letterCounter = originalCount;
        for (Map.Entry entry : letterCount.entrySet()) {
            if (label == (char) entry.getKey()) {
                letterCounter = (int) entry.getValue();
            }
        }
        return letterCounter;
    }

    public boolean checkBagForNoVowels() {
        boolean flag = false;
        int count = 0;
        char[] vowels = {'A', 'E', 'I', 'O', 'U'};
        for (int i = 0; i < 5; i++) {
            for (Map.Entry entry : letterCount.entrySet()) {
                if (vowels[i] == (char) entry.getKey() && (int) entry.getValue() == 0) {
                    count++;
                }
            }
        }
        if (count == 5) {
            flag = true;
        }
        return flag;
    }

    public boolean checkBagForNoWords() {
        boolean flag = false;
        int count = 0;
        for (Map.Entry entry : letterCount.entrySet()) {
            if ((int) entry.getValue() == 0) {
                count++;
            }
        }
        if (count > 25) {
            flag = true;
        }
        return flag;
    }

}
