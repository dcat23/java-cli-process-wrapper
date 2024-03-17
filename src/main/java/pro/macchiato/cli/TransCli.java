package pro.macchiato.cli;

import pro.macchiato.cli.exceptions.CliException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
public class TransCli extends Cli{
    public TransCli() {
        super("transmission-cli");
        addOption("-w", this.directory + "/trans");
    }



    @Override
    public void execute() throws CliException {
        super.execute(new Callback());
    }

    public static class Callback implements ProgressCallback {

        private static final String GROUP_PERCENT = "percent";
        private static final String GROUP_ELAPSED = "elapsed";

        private float progress = -1;
        private float elapsed = -1;
        private final Pattern p = Pattern.compile(
                        "Progress:\\s+(?<percent>\\d+\\.\\d+)%.*");
//                        "Progress:\\s+(?<percent>\\d+\\.\\d+)%.*\\[(?<elapsed>\\d+\\.\\d+)]");
        @Override
        public void processLine(String line) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                float progress = Float.parseFloat(m.group(GROUP_PERCENT));
//                float elapsed = Float.parseFloat(m.group(GROUP_ELAPSED));
                float elapsed = 0;
                if (progress != this.progress || elapsed != this.elapsed)
                {
                    System.out.println(line);
                    this.progress = progress;
                    this.elapsed = elapsed;
                }
            }
        }

        @Override
        public long getElapsed() {
            return 0;
        }
    }
}
