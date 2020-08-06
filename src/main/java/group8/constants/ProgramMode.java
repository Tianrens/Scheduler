package group8.constants;

public enum ProgramMode {
    Visualise("v"),
    Parallelise("p"),
    Output("o");


    private final String _code;

    ProgramMode(String code){
        this._code=code;
    }

    public String getCode() {
        return this._code;
    }
}
