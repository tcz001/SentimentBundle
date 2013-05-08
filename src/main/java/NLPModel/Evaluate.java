package NLPModel;

import java.io.*;
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

//        File liberal = new File(liberalCorpus);
//        BufferedReader liberalReader = new BufferedReader(new FileReader(liberal.getAbsoluteFile()));



        for (int i=0;i<10;i++){
            WordList totalDictionary = dummy.parseFileToData("resources/LanguageDump.txt");
            evaluate(conservativeCorpus,dummy,totalDictionary);
            dummy.trainOnCorpus(conservativeCorpus,"conservative",totalDictionary);
        }
    }

    public static void evaluate(String filePath,Training dummy,WordList totalDictionary) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        String current = "Placeholder";
        double Total = 0d;
        double Hit = 0d;
        while (current != null){
            current = reader.readLine();
            List<List<String>> chunkedCorpus = dummy.cleanCorpus(current);
            for (List<String> s: chunkedCorpus){
                Map<String, Double> politics = dummy.politicsScore(totalDictionary, s);
                if (politics.get("liberal") <= politics.get("conservative")){
                    Hit++;
                }
            Total++;
            }
        }
        reader.close();
        System.out.println(Hit/(Total));
    }
}
