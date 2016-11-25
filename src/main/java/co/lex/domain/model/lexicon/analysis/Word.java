package co.lex.domain.model.lexicon.analysis;

/**
 * Created by jotauribe on 24/11/16.
 */
public class Word {

    private String data;

    private int start;

    private int end;

    private void setStart(int start){
        if(start < 0)
            throw new IllegalArgumentException("Illegal word start");
        this.start = start;
    }

    private void setEnd(int end){
        if(start > end)
            throw new IllegalArgumentException("Illegal word ending");
        this.end = end;
    }

    public Word(String data, int start, int end){
        this.data = data;
        setStart(start);
        setEnd(end);
    }

    public int start(){
        return start;
    }

    public int end(){
        return end;
    }

    @Override
    public String toString() {
        return data;
    }
}
