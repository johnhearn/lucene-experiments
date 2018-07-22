package johnhearn;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import static johnhearn.IndexAndSearchText.*;

public class Benchmarks {

    @State(Scope.Benchmark)
    public static class MyState {
        private Index index;

        @Setup
        public void init() throws Exception {
            BufferedReader reader = new BufferedReader(new FileReader("moby dick.txt"));
            index = new NonIndex();
            index.indexText(reader, 20_000);
        }
    }

    @Benchmark @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Measurement(iterations = 10) @Warmup(iterations = 2) @Fork(1)
    public void testMethod(MyState state) throws Exception {
        state.index.findInText("hyena");
    }

}
