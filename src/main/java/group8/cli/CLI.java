package group8.cli;

public class CLI {

    private String[] _args;

    public CLI(String[] args) {
        _args = args;
    }

    public void printArgs() {
        for (String arg : _args) {
            System.out.println(arg);
        }
    }

}
