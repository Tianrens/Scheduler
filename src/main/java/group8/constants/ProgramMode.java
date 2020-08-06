package group8.constants;

public enum ProgramMode {
    VISUALISE("v"),
    PARALLELISE("p"),
    OUTPUT("o");


    private final String _code;

    ProgramMode(String code){
        this._code=code;
    }

    public String getCode() {
        return this._code;
    }
}
