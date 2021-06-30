package it.polimi.ingsw.utils;

public class Pair<X, Y> {

    private final X x;
    private final Y y;

    /**
     * Initialize the pair
     * @param x first element of the pair
     * @param y second element of the pair
     */
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the first element of the pair
     * @return the first element of the pair
     */
    public X getFirst() {
        return x;
    }

    /**
     * Retrieves the second element of the pair
     * @return the second element of the pair
     */
    public Y getSecond() {
        return y;
    }

    /**
     * Check if the elements of the pair are the same
     * @param object another pair oblect
     * @return true if the elements of the pair are the same, false otherwise
     */
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
