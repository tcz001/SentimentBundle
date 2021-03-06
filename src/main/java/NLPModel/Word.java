package NLPModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {

    private Map<String, Double> emotions = new HashMap<String, Double>();
	private Map<String, Double> politics = new HashMap<String, Double>();
	
	private String name = "";
	
	public Word(String name){
        String[] basicEmotionList = {"love", "joy", "surprise", "anger",
                "sadness", "fear"};
        String[] basicPoliticsList = {"liberal", "conservative"};
        if (!name.contains("~")){
			this.emotions = new HashMap<String, Double>();
			this.politics = new HashMap<String, Double>();
			for (String s: basicEmotionList){
				this.emotions.put(s, (100d/ basicEmotionList.length) + Math.random());
			}
			for (String s: basicPoliticsList){
				this.politics.put(s, (100d/ basicPoliticsList.length) + Math.random());
			}
			this.name = name;
		}
		else {
			List<String> seperateName = Arrays.asList(name.split("~"));
			if (seperateName.size() > 2){
				this.name = seperateName.get(1);
				String values = seperateName.get(2);
				values = values.replace("{", "");
				values = values.replace("}","");
				values = values.replace(">", "");
				List<String> assignments = Arrays.asList(values.split(", "));
				for (String s: assignments){
					List<String> currentValue = Arrays.asList(s.split("="));
                    List<String> objectEmotionList = Arrays.asList(basicEmotionList);
                    List<String> objectPoliticsList = Arrays.asList(basicPoliticsList);
                    if (objectEmotionList.contains(currentValue.get(0))){
						double d = Float.parseFloat(currentValue.get(1));
						this.emotions.put(currentValue.get(0), d);
					} else if (objectPoliticsList.contains(currentValue.get(0))){
						double d = Float.parseFloat(currentValue.get(1));
						this.politics.put(currentValue.get(0), d);
					}
				}
			}
		}
	}
	
	public Map<String, Double> getFullEmotions(){
		return this.emotions;
	}
	
	public Map<String, Double> getFullPolitics(){
		return this.politics;
	}
	
	public Double getEmotionValue(String emotion){
		return this.emotions.get(emotion);
	}
	
	public Double getPoliticValue(String politic){
		return this.politics.get(politic);
	}
	
	public String getName(){
		return this.name;
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
		return "<~" + this.name + "~" + this.emotions.toString() + ", " + this.politics.toString() + ">\n";
	}
	
	public boolean equals(Word comparator){
        return this.name.equals(comparator.getName());
	}
	
	public int hashCode(){
		return new HashBuilder(this.name).hashCode();
	}
}
