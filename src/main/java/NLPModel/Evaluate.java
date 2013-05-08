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
public class Evaluate {
    public static void main(String[] args) throws IOException {
        Training dummy = new Training();
//        dummy.initializeDictionary();
        String conservativeCorpus = "resources/neg.txt";
//        String liberalCorpus = "resources/postest.txt";

        File conservative = new File(conservativeCorpus);
//        File liberal = new File(liberalCorpus);
        BufferedReader conservativeReader = new BufferedReader(new FileReader(conservative.getAbsoluteFile()));
//        BufferedReader liberalReader = new BufferedReader(new FileReader(liberal.getAbsoluteFile()));

        WordList totalDictionary = dummy.parseFileToData("resources/LanguageDump.txt");
        String current = "Placeholder";




        double Total = 0d;
        double Hit = 0d;
        while (current != null){
            current = conservativeReader.readLine();
            List<List<String>> chunkedCorpus = dummy.cleanCorpus(current);
            for (List<String> s: chunkedCorpus){
                Map<String, Double> politics = dummy.politicsScore(totalDictionary, s);
                if (politics.get("liberal") <= politics.get("conservative")){
                    Hit++;
                }
            Total++;
            }
        }
        conservativeReader.close();
        System.out.println(Hit/(Total));


        for (int i=0;i<20;i++){
            conservativeReader = new BufferedReader(new FileReader(conservative.getAbsoluteFile()));
            while (conservativeReader.readLine() != null){
                current = conservativeReader.readLine();
                dummy.trainWordList(totalDictionary, "conservative", current);
            }
            conservativeReader.close();
            totalDictionary.fileDump("resources/LanguageDump.txt");
        }

        Total = 0d;
        Hit = 0d;
        while (current != null){
            current = conservativeReader.readLine();
            List<List<String>> chunkedCorpus = dummy.cleanCorpus(current);
            for (List<String> s: chunkedCorpus){
                Map<String, Double> politics = dummy.politicsScore(totalDictionary, s);
                if (politics.get("liberal") <= politics.get("conservative")){
                    Hit++;
                }
                Total++;
            }
        }
        conservativeReader.close();
        System.out.println(Hit/(Total));
    }
}
