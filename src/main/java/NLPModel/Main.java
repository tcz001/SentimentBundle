package NLPModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tcz
 * Date: 13-5-6
 * Time: 下午9:13
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Training dummy = new Training();
        dummy.initializeDictionary();
        //String conservativeCorpus = "resources/ConservativeTweets.txt";
        String liberalCorpus = "resources/conservativeTest.txt";

        //File conservative = new File(conservativeCorpus);
        File liberal = new File(liberalCorpus);
        //BufferedReader conservativeReader = new BufferedReader(new FileReader(conservative.getAbsoluteFile()));
        BufferedReader liberalReader = new BufferedReader(new FileReader(liberal.getAbsoluteFile()));

        WordList totalDictionary = dummy.parseFileToData("resources/LanguageDump.txt");
        String current = "Placeholder";
        double Total = 0d;
        double Hit = 0d;
        while (current != null){
            Total++;
            current = liberalReader.readLine();
            List<List<String>> chunkedCorpus = dummy.cleanCorpus(current);
            for (List<String> s: chunkedCorpus){
                Map<String, Double> politics = dummy.politicsScore(totalDictionary, s);
                if (politics.get("liberal") > politics.get("conservative")){ Hit++; }
            }
        }
        liberalReader.close();
        System.out.println(Hit/Total);
/*
        while (liberalReader.readLine() != null){
            current = liberalReader.readLine();
            dummy.trainWordList(totalDictionary, "liberal", current);
        }
        liberalReader.close();
        totalDictionary.fileDump("resources/LanguageDump.txt");
*/

    }
}
