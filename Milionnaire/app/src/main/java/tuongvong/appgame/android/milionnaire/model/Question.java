package tuongvong.appgame.android.milionnaire.model;

/**
 * Created by phamtiendat on 11/22/16.
 */

public class Question {
    public String cauhoi;
    public String caseA;
    public String caseB;
    public String caseC;
    public String caseD;
    public int trueCase;
    public int id;

    public Question() {
    }

    public Question(String cauhoi, String caseA, String caseB, String caseC, String caseD, int trueCase, int id) {
        this.cauhoi = cauhoi;
        this.caseA = caseA;
        this.caseB = caseB;
        this.caseC = caseC;
        this.caseD = caseD;
        this.trueCase = trueCase;
        this.id = id;
    }
}
