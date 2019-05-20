import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CorpusBuilder
{
    private File corpusFile;
    private HashMap<String, Integer> dictionaryHash;

    public CorpusBuilder()
    {
        this.dictionaryHash = new HashMap<String, Integer>(1300000);
    }

    public HashMap<String, Integer> getDictionaryHash()
    {
        return dictionaryHash;
    }

    public void setDictionaryHash(HashMap<String, Integer> dictionaryHash)
    {
        this.dictionaryHash = dictionaryHash;
    }

    public boolean isCorpusEmpty(String corpusFile)
    {
        try(BufferedReader br = Files.newBufferedReader(Paths.get(corpusFile)))
        {
            if(br.readLine() == null)
            {
                //File empty
                return true;
            }
        }
        catch(IOException e)
        {
            System.err.println("Corpus file is empty!");
            return false;
        }
        return false;
    }

    public File getCorpusFile()
    {
        return corpusFile;
    }

    public void setCorpusFile(String corpusFile)
    {
        this.corpusFile = new File(corpusFile);
    }

    public void readCorpus()
    {
        int count = 0;
        try
        {
            var patter = Pattern.compile("[A-Za-z]+");

            BufferedReader read = new BufferedReader(new FileReader(corpusFile));
            String line;
            while((line = read.readLine()) != null)
            {
                var matcher = patter.matcher(line);
                while(matcher.find())
                {
                    var word = matcher.group();

                    Integer frequency = dictionaryHash.get(word);
                    if(frequency != null)
                    {
                        //word exists
                        dictionaryHash.put(word, frequency + 1);
                    }
                    else
                    {
                        //word doesn't exist
                        frequency = 1;
                        dictionaryHash.put(word, frequency);
                    }
                    count++;
                }
            }

        }
        catch(IOException e)
        {
            System.err.println("File doesn't exist.");
        }

        System.out.print("Word count: ");
        System.out.println(count);
    }

    public void clearHashBuffer()
    {
        this.dictionaryHash.clear();
    }
}