package NLPModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Training {

	private String[] STOP_WORDS = {"a","an","by","than","the","to", " ", "", " "};
	private List<String> stopWords = Arrays.asList(STOP_WORDS);
	
	private String[] basicEmotionList = {"love", "joy", "surprise", "anger",
            "sadness", "fear"};
	private String[] basicPoliticsList = {"liberal", "conservative"};
	
	private List<String> objectEmotionList = Arrays.asList(basicEmotionList);
	private List<String> objectPoliticsList = Arrays.asList(basicPoliticsList);
	
	public List<List<String>> cleanCorpus(String corpus){
		List<List<String>> sparklingCorpus = new ArrayList<List<String>>();
		try{
			String rinsedCorpus = corpus.replaceAll("[,'\"]", "");
			List<String> scrubbedCorpus= Arrays.asList(rinsedCorpus.split("\\.")); 
			
			for (String s: scrubbedCorpus){
				sparklingCorpus.add(Arrays.asList(s.split(" ")));
			}
			return sparklingCorpus;
		}
		catch(NullPointerException ex){
			return new ArrayList<List<String>>();
		}
	}
	
	public List<Word> parseToWords(List<String> brokenSentence){
		List<Word> words = new ArrayList<Word>();
		for (String s: brokenSentence){
			s = s.toLowerCase();
			if (!stopWords.contains(s)){
				words.add(new Word(s));
			}
		}
		return words;
	}
	
	public List<Word> removeWordDuplicates(Collection<? extends Word> allWords){
		Set<Word> singleWords = new HashSet<Word>(allWords);
		return new ArrayList<Word>(singleWords);
	}
	
	public List<WordPair> removeWordPairDuplicates(Collection<? extends WordPair> allWordPairs){
		Set<WordPair> singleWordPairs = new HashSet<WordPair>(allWordPairs);
		return new ArrayList<WordPair>(singleWordPairs);
	}
	
	public List<WordPair> parseToWordPairs(List<Word> words){
		List<WordPair> wordPairs = new ArrayList<WordPair>();
		Word currentWord = null;
		Word lastWord = null;
		for (Word w: words){
			currentWord = w;
			if (lastWord != null){
				wordPairs.add(new WordPair(lastWord, currentWord));
			}
			
			lastWord = currentWord;
		}
		return wordPairs;
	}
	
	public WordList parseFileToData(String filePath) throws IOException{
		File input = new File(filePath);
		
		List<WordPair> wordPairs = new ArrayList<WordPair>();
		List<Word> words = new ArrayList<Word>();
		
		BufferedReader reader = new BufferedReader(new FileReader(input.getAbsoluteFile()));
		String current = "PlaceHolder";
		while(current != null){
			current = reader.readLine();
			if (current != null){
				if (current.contains("#")){
					wordPairs.add(new WordPair(current));
				} else {
					words.add(new Word(current));
				}
			}
		}
		reader.close();
		WordList answer = new WordList();
		for (Word w: words){
			answer.addWord(w);
		}
		
		for (WordPair wp: wordPairs){
			answer.addWordPair(wp);
		}
		return answer;
	}
	
	public WordList parseFileToWordList(String fileName) throws IOException{
		File input = new File(fileName);
		
		List<WordPair> wordPairs = new ArrayList<WordPair>();
		List<Word> words = new ArrayList<Word>();
		
		BufferedReader reader = new BufferedReader(new FileReader(input.getAbsoluteFile()));
		String current;
		List<Word> allWords = new ArrayList<Word>();
		List<WordPair> allWordPairs = new ArrayList<WordPair>();
		while(reader.readLine() != null){
			current = reader.readLine();
			List<List<String>> brokenSentences = cleanCorpus(current);
			if (brokenSentences.size() > 1){
				for (List<String> list: brokenSentences){
					List<Word> currentSentence = parseToWords(list);
					List<WordPair> currentWordPairs = parseToWordPairs(currentSentence);
					allWords.addAll(currentSentence);
					allWordPairs.addAll(currentWordPairs);
				}
			}
		}
		WordList wordizzles = new WordList();
		for (Word w: allWords){
			wordizzles.addWord(w);
		}
		for (WordPair wp: allWordPairs){
			wordizzles.addWordPair(wp);
		}
		
		return wordizzles;
	}
	public Map<String, Double> emotionScore(WordList master, List<String> corpus){
		Map<String, Double> sums = new HashMap<String, Double>();
		for (String s: basicEmotionList){
			sums.put(s, 0d);
		}
		List<Word> words = parseToWords(corpus);
		List<WordPair> wordPairs = parseToWordPairs(words);
		for (Word w: words){
			Word usable = master.getWord(w.getName());
			for (String s: usable.getFullEmotions().keySet()){
				sums.put(s, sums.get(s)+usable.getEmotionValue(s));
			}
		}
		
		for (WordPair wp: wordPairs){
			WordPair usable = master.getWordPair(wp.getWordOne(), wp.getWordTwo());
			for (String s: usable.getFullEmotions().keySet()){
				sums.put(s,sums.get(s)+usable.getFullEmotions().get(s));
			}
		}
		
		return sums;
	}
	
	public Map<String, Double> politicsScore(WordList master, List<String> corpus){
		Map<String, Double> sums = new HashMap<String, Double>();
		for (String s: basicPoliticsList){
			sums.put(s, 0d);
		}
		List<Word> words = parseToWords(corpus);
		List<WordPair> wordPairs = parseToWordPairs(words);
		for (Word w: words){
			Word usable = master.getWord(w.getName());
			if (usable != null){
				for (String s: usable.getFullPolitics().keySet()){
					sums.put(s, sums.get(s)+usable.getPoliticValue(s));
				}
			}
		}
		
		for (WordPair wp: wordPairs){
			WordPair usable = master.getWordPair(wp.getWordOne(), wp.getWordTwo());
			if (usable != null){
				for (String s: usable.getFullPolitics().keySet()){
					sums.put(s,sums.get(s)+usable.getFullPolitics().get(s));
				}
			}
		}
		
		return sums;
	}
	
	public void massIncrementEmotion(WordList master, String feature, List<String> brokenSentence, double value){
		List<Word> words = parseToWords(brokenSentence);
		List<WordPair> wordPairs = parseToWordPairs(words);
		int totalSize = words.size() + wordPairs.size();
		double largeIncrementSize = (value/totalSize)/2d;
		double smallIncrementSize = largeIncrementSize/objectEmotionList.size()-1;
		for (Word w: words){
			for (String s: objectEmotionList){
				if (s.equals(feature)){
					w.changeEmotionValue(s, largeIncrementSize + w.getEmotionValue(s));
				} else {
					w.changeEmotionValue(s, w.getEmotionValue(s) - smallIncrementSize);
				}
			}
		}
		
		for (WordPair wp: wordPairs){
			for (String s: objectEmotionList){
				if (s.equals(feature)){
					wp.changeEmotionValue(s, wp.getFullEmotions().get(s) + largeIncrementSize);
				} else {
					wp.changeEmotionValue(s, wp.getFullEmotions().get(s) - smallIncrementSize);
				}
			}
		}
		
	}
	
	public void massIncrementPolitic(WordList master, String feature, List<String> brokenSentence, double value){
		List<Word> words = parseToWords(brokenSentence);
		List<WordPair> wordPairs = parseToWordPairs(words);
		int totalSize = words.size() + wordPairs.size();
		double largeIncrementSize = (value/totalSize)/2d;
		double smallIncrementSize = largeIncrementSize/objectPoliticsList.size()-1;
		for (Word w: words){
			for (String s: objectPoliticsList){
				if (s.equals(feature)){
					w.changePoliticValue(s, largeIncrementSize + w.getPoliticValue(s));
				} else {
					w.changePoliticValue(s, w.getPoliticValue(s) - smallIncrementSize);
				}
			}
		}
		
		for (WordPair wp: wordPairs){
			for (String s: objectPoliticsList){
				if (s.equals(feature)){
					wp.changePoliticValue(s, wp.getFullPolitics().get(s) + largeIncrementSize);
				} else {
					wp.changePoliticValue(s, wp.getFullPolitics().get(s) - smallIncrementSize);
				}
			}
		}
		
	}
	
	public void trainWordList(WordList master, String feature, String miniCorpus){
		List<List<String>> cleanedCorpus = cleanCorpus(miniCorpus);
		if (objectEmotionList.contains(feature)){
			for(List<String> list: cleanedCorpus){
				Map<String, Double> values = emotionScore(master, list);
				double maxValue = Collections.max(values.values());
				double featureValue = values.get(feature);
				if (maxValue == featureValue){
					continue;
				} else{
					Double difference = maxValue - featureValue;
					massIncrementEmotion(master, feature, list, difference);
				}
			}
		} else if (objectPoliticsList.contains(feature)){
			for (List<String> list: cleanedCorpus){
				Map<String, Double> values = politicsScore(master, list);
				double maxValue = Collections.max(values.values());
				double featureValue = values.get(feature);
				if (maxValue == featureValue){
					continue;
				} else{
					Double difference = maxValue - featureValue;
					massIncrementPolitic(master, feature, list, difference);
				}
			}
			
		}
	}
	
	public WordList initializeDictionary() throws IOException{
		String conservativeCorpus = "resources/ConservativeTweets.txt";
		String liberalCorpus = "resources/LiberalTweets.txt";
		
		WordList conservativeWords = parseFileToWordList(conservativeCorpus);
		WordList liberalWords = parseFileToWordList(liberalCorpus);
		
		for (Word w: liberalWords.getAllWords()){
			conservativeWords.addWord(w);
		}
		
		for (WordPair wp: liberalWords.getAllWordPairs()){
			conservativeWords.addWordPair(wp);
		}
		
		conservativeWords.fileDump("resources/LanguageDump.txt");
		return conservativeWords;
	}
	
	public void trainOnCorpus(String filePath, String feature, WordList master) throws IOException{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
		String current;
		while (reader.readLine() != null){
			current = reader.readLine();
			trainWordList(master, "conservative", current);
		}
		reader.close();
	}
}
