package co.lex.domain.model.dataloader;

import co.lex.domain.model.DataLoader.ArchiveReader;
import co.lex.domain.model.lexicon.analysis.Analyzer;
import co.lex.domain.model.lexicon.analysis.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Danie on 13/11/2016.
 */
@RunWith(Parameterized.class)
public class ArchiveReaderTest {

    static ArchiveReader a;

    List<String> expected;
    String suppliedString;

    public ArchiveReaderTest(List<String> expected, String reading) {
        this.expected = expected;
        this.suppliedString = reading;
    }

    @Before
    public void Before(){
        a = new ArchiveReader();
    }

    @Test
    public void fileReader() throws Exception {
        Analyzer analyzer = new Analyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) analyzer.parse(this.suppliedString);

        
    }



    @Parameterized.Parameters
    public static Iterable<Object[]> Dates() throws IOException {

        List<Object[]> parameters = new ArrayList<>();

        List<String> expect = new ArrayList<>();

        List<String> reading = a.FileReader("C:\\Users\\Danie\\Desktop\\prueba - copia.txt");

        Object[] objectsSet1 = null;
        for (int i = 0; i< 10; i++){
            String string = reading.get(i);
            objectsSet1 = new Object[]{expect, string}; //EL EXPECTECT LO PONES TU
        }


        parameters.add(objectsSet1);

        return parameters;
    }




}