package it.polimi.ingsw.exceptions;

public class ParamsConvertionException extends RuntimeException {
    public ParamsConvertionException(){ super(); }
    public ParamsConvertionException(String msg){ super(msg); }
}
