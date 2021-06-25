package it.polimi.ingsw.utils;

public class Pair<X, Y> {

    private final X x;
    private final Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getFirst() {
        return x;
    }

    public Y getSecond() {
        return y;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean isEqual = false;

        if (object != null && object instanceof Pair)
        {
            Pair<X,Y> secondPair = (Pair<X,Y>) object;
            isEqual = this.x.equals(secondPair.x) && this.y.equals(secondPair.y);
        }

        return isEqual;
    }
}
