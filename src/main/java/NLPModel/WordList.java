package NLPModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordList {

    private List<Word> allWords;
    private List<WordPair> allWordPairs;

    public WordList() {
        this.allWordPairs = new ArrayList<WordPair>();
        this.allWords = new ArrayList<Word>();
    }

    public Integer numberOfWords() {
        return this.allWords.size();
    }

    public Integer numberOfWordPairs() {
        return this.allWordPairs.size();
    }

    public List<Word> getAllWords() {
        return this.allWords;
    }

    public List<WordPair> getAllWordPairs() {
        return this.allWordPairs;
    }

    public Word getWord(String s) {
        for (Word w : this.allWords) {
            if (w.getName().equals(s)) {
                return w;
            }
        }
        return null;
    }

    public WordPair getWordPair(String first, String second) {
        for (WordPair wp : this.allWordPairs) {
            if (wp.getWordOne().equals(first)) {
                if (wp.getWordTwo().equals(second)) {
                    return wp;
                }
            }
        }
        return null;
    }

    public void addWord(Word newWord) {
        if (!this.containsWord(newWord)) {
            this.allWords.add(newWord);
        }
    }

    public void addWordPair(WordPair newWordPair) {
        if (!this.containsWordPair(newWordPair)) {
            this.allWordPairs.add(newWordPair);
        }
    }

    public void fileDump(String filePath) throws IOException {
        File output = new File(filePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(output.getAbsoluteFile()));
        for (Word w : allWords) {
            writer.write(w.toString());
        }

        for (WordPair wp : allWordPairs) {
            writer.write(wp.toString());
        }

        writer.close();
    }

    public void dumpWords(String wordFilePath) throws IOException {
        File output = new File(wordFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(output.getAbsoluteFile()));
        for (Word w : allWords) {
            writer.write(w.toString());
        }
    }

    public void dumpWordPairs(String wordPairFilePath) throws IOException {
        File output = new File(wordPairFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(output.getAbsoluteFile()));
        for (WordPair wp : allWordPairs) {
            writer.write(wp.toString());
        }
    }

    public boolean containsWord(Word word) {
        for (Word w : this.allWords) {
            if (w.getName().equals(word.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean containsWordPair(WordPair test) {
        if (this.allWordPairs.size() > 1) {
            for (WordPair wp : this.allWordPairs) {
                if (wp.getWordOne().equals(test.getWordOne())) {
                    if (wp.getWordTwo().equals(test.getWordTwo())) {
                        return true;
                    }
                }
            }
        } else {
            return false;
        }
        return test == null;
    }

    public void changeWord(Word word) {
        for (Word w : this.allWords) {
            if (w.getName().equals(word.getName())) {
                w = word;
            }
        }
    }

    public void changeWordPair(WordPair wordPair) {
        if (this.allWordPairs.size() > 1) {
            for (WordPair wp : this.allWordPairs) {
                if (wp.getWordOne().equals(wordPair.getWordOne())) {
                    if (wp.getWordTwo().equals(wordPair.getWordTwo())) {
                        wp = wordPair;
                    }
                }
            }
        }
    }
}
