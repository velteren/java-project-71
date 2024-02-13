package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.HashMap;
import java.util.concurrent.Callable;

@Command(name = "gendiff", version = "1.0", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable {
    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filepath1;
    @Parameters(paramLabel = "filepath2", description = "path to first file")
    private String filepath2;
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        var map1 = new HashMap<String, Object>();
        var map2 = new HashMap<String, Object>();
        try {
            map1 = Differ.getMapFromJSON(filepath1);
            map2 = Differ.getMapFromJSON(filepath2);
        } catch (Exception e) {

        }
        System.out.println(map1);
        System.out.println(map2);
        System.out.println(Differ.generate(map1, map2));
        return 0;
    }
}
