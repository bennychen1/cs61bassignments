public class emptyWeirdList extends WeirdList {
    public emptyWeirdList(){
        super(0, WeirdList.EMPTY);
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public WeirdList map(IntUnaryFunction func) {
        return WeirdList.EMPTY;
    }

}
