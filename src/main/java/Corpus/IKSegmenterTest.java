package Corpus;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: tcz
 * Date: 13-4-16
 * Time: 下午3:03
 */
public class IKSegmenterTest {
    private static File[] files;
    private static File dump_file;
    IKSegmenter ikSegmenter;

    public void setUp() throws IOException {
//        String s = "由于分词器没有处理歧义分词的能力,才使用了IKQueryParser来解决搜索时的歧义冲突问题";
//        Reader reader = new StringReader(s);
        files = new File("/home/tcz/Github/TrainingFrame/resources/rar/NB_del_4000/pos").listFiles();
        dump_file = new File("/home/tcz/Github/TrainingFrame/resources/rar/NB_del_4000/pos/pos.txt");
        if(!dump_file.exists())dump_file.createNewFile();
        assert files != null;
    }

    public static void main(String args[]) throws IOException {
        IKSegmenterTest ikSegmenterTest = new IKSegmenterTest();
        ikSegmenterTest.setUp();

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dump_file));
        for (File fl : files) {
            if (fl.isDirectory())
                System.out.println(fl.toString());
            else
                System.out.println(fl.getName());
            try {
                bufferedReader = new BufferedReader(new FileReader(fl.getAbsoluteFile()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            assert bufferedReader != null;
            String s = bufferedReader.readLine();
            while (s!=null){
                if(!s.isEmpty()){
                    StringReader sr = new StringReader(s);
                    System.out.println(s);
                    ikSegmenterTest.ikSegmenter = new IKSegmenter(sr, false);
                    Lexeme lexeme = ikSegmenterTest.ikSegmenter.next();
                    while (lexeme != null) {
                        System.out.print(lexeme.getLexemeText() + " ");
                        bufferedWriter.write(lexeme.getLexemeText()+" ");
                        lexeme = ikSegmenterTest.ikSegmenter.next();
                    }
                    System.out.println();
                    bufferedWriter.write("\n");
                }
                s = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        bufferedWriter.close();
    }
}
