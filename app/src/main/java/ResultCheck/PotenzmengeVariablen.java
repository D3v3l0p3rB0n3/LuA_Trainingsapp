package ResultCheck;

public class PotenzmengeVariablen {
    public Boolean[][] potenzmenge;

    public PotenzmengeVariablen(Integer anzahl_variablen) {

        switch (anzahl_variablen) {
            case 2:
                potenzmenge = new Boolean[][]{
                        {false, false}, //00
                        {false, true}, //01
                        {true, false}, //10
                        {true, true}}; //11

            case 3:
                potenzmenge = new Boolean[][]{
                        {false, false, false}, //000
                        {false, false, true}, // 001
                        {false, true, false}, // 010
                        {false, true, true}, //011
                        {true, false, false}, //100
                        {true, false, true}, // 101
                        {true, true, false}, // 110
                        {true, true, true}}; //111
                break;

            case 4:
                potenzmenge = new Boolean[][]{
                        {false, false, false, false}, //0000
                        {false, false, false, true}, //00001
                        {false, false, true, false}, //00010
                        {false, false, true, true}, //00011
                        {false, true, false, false}, //0100
                        {false, true, false, true},//0101
                        {false, true, true, false},//0110
                        {false, true, true, true},//0111
                        {true, false, false, false},//1000
                        {true, false, false, true},//10001
                        {true, false, true, false},//1010
                        {true, false, true, true},//1011
                        {true, true, false, false},//1100
                        {true, true, false, true},//1101
                        {true, true, true, false},//1110
                        {true, true, true, true}};//1111
                break;


        }
    }
}
