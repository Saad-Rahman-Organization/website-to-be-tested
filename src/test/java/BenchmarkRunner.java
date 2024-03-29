import com.bittercode.service.impl.BookServiceImplPerfTest;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import perf.*;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {



        Options opt = new OptionsBuilder()
                .include(".*" + BookServiceImplPerfTest.class.getSimpleName() + ".*")
                .warmupIterations(2)
                .measurementIterations(2)
                //.forks(1)
                .build();

        new Runner(opt).run();
        //org.openjdk.jmh.Main.main(args);
    }
}