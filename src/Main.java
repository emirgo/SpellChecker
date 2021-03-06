public class Main
{
    public static void main(String[] args)
    {
        var db = new DictionaryBuilder();
        var cb = new CorpusBuilder();
        var spellChecker = new SpellChecker(db.getDictionaryFile());


        db.doesDictionaryExist();
        db.isDictionaryEmpty();

        db.wipePreviousData();
        do {
            cb.askCorpusFile();
        }
        while(!cb.doesExist());
        cb.readCorpus();
        //here takes a bit long but this gets dictionary, sorts according to frequency and then writes to file.
        spellChecker.setDictionaryMap(db.fillDictionary(cb.getDictionaryHash()));

        UserFile uf = new UserFile();

        do {
            uf.setUserFile(uf.askUserFile());
        }
        while(!uf.doesExist());

        uf.setUserFileContent(uf.readUserFile());

        CorrectedFile correctedFile = new CorrectedFile(spellChecker.spellCheck(uf.getUserFileContent()));
        correctedFile.makeFile();

    }
}
