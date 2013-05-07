package NLPModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordPair {
	
	private String wordOne = "";
	private String wordTwo = "";
	
	private String[] basicEmotionList = {"love", "joy", "surprise", "anger",
            "sadness", "fear"};
	private String[] basicPoliticsList = {"liberal", "conservative"};

    private Map<String, Double> emotions = new HashMap<String, Double>();
	private Map<String, Double> politics = new HashMap<String, Double>();
	
	public WordPair(String first, String second){
		this.wordOne = first;
		this.wordTwo = second;
		
		this.emotions = new HashMap<String, Double>();
		this.politics = new HashMap<String, Double>();
		
		for (String s: basicEmotionList){
			this.emotions.put(s, (100d/basicEmotionList.length) + Math.random());
		}
		for (String s: basicPoliticsList){
			this.politics.put(s, (100d/basicPoliticsList.length) + Math.random());
		}
	}
	
	public WordPair(String fileFormat){
		List<String> splitFileFormat = Arrays.asList(fileFormat.split("#"));
		if (splitFileFormat.size() == 4){
			this.wordOne = splitFileFormat.get(1);
			this.wordTwo = splitFileFormat.get(2);
			String values = splitFileFormat.get(3);
			values = values.replace("{", "");
			values = values.replace("}","");
			values = values.replace(">", "");
			List<String> assignments = Arrays.asList(values.split(", "));
			for (String s: assignments){
				List<String> currentValue = Arrays.asList(s.split("="));
                List<String> objectEmotionList = Arrays.asList(basicEmotionList);
                List<String> objectPoliticsList = Arrays.asList(basicPoliticsList);
                if (objectEmotionList.contains(currentValue.get(0))){
					this.emotions.put(currentValue.get(0), Double.parseDouble(currentValue.get(1)));
				} else if (objectPoliticsList.contains(currentValue.get(0))){
					this.politics.put(currentValue.get(0), Double.parseDouble(currentValue.get(1)));
				}
			}
		}
	}
	
	public WordPair(Word first, Word second){
		this.wordOne = first.getName();
		this.wordTwo = second.getName();
		this.emotions = new HashMap<String, Double>();
		this.politics = new HashMap<String, Double>();
		
		for (String s: basicEmotionList){
			this.emotions.put(s, 100d/basicEmotionList.length + Math.random());
		}
		for (String s: basicPoliticsList){
			this.politics.put(s, 100d/basicPoliticsList.length + Math.random());
		}
	}
	
	public Map<String, Double> getFullEmotions(){
		return this.emotions;
	}
	
	public Map<String, Double> getFullPolitics(){
		return this.politics;
	}
	
	public String getWordOne(){
		return this.wordOne;
	}
	
	public String getWordTwo(){
		return this.wordTwo;
	}
	
	public void changeEmotionValue(String emotion, Double newValue){
		this.emotions.put(emotion, newValue);
	}
	
	public void changePoliticValue(String politic, Double newValue){
		this.politics.put(politic, newValue);
	}
	
	public void incrementEmotionValue(String emotion, Double increase){
		this.emotions.put(emotion, this.emotions.get(emotion)*increase);
	}
	
	public void incrementPoliticValue(String politic, Double increase){
		this.politics.put(politic, this.politics.get(politic)*increase);
	}
	
	public String toString(){
		return ("<#" + this.wordOne + "#" + this.wordTwo +
				"#" + this.emotions.toString() + ", " + this.politics.toString() + ">\n");
	}
	
	public boolean equals(WordPair wp){
        return this.wordOne.equals(wp.getWordOne()) && this.wordTwo.equals(wp.getWordTwo());
	}
}
