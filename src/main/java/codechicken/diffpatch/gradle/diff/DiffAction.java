package codechicken.diffpatch.gradle.diff;

import codechicken.diffpatch.DiffPatch;
import codechicken.diffpatch.gradle.util.ConsumingOutputStream;
import codechicken.diffpatch.gradle.util.Utils;
import codechicken.diffpatch.util.archiver.ArchiveFormat;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Executes a Diff operation with DiffPatch.
 *
 * @see DiffSpec
 * Created by covers1624 on 14/8/20.
 */
public class DiffAction implements DiffSpec {

    private final boolean DEBUG = Boolean.getBoolean("codechicken.diffpatch.gradle.debug");

    private final Project project;

    private Object base;
    private Object modified;
    private Object output;
    private Object outputFormat;
    private boolean autoHeader;
    private int contextLines = -1;
    private boolean verbose = true;
    private boolean printSummary = true;

    public DiffAction(Project project) {
        this.project = project;
    }

    public void execute() {
        if (base == null) {
            throw new IllegalStateException("Base path not specified.");
        }
        if (modified == null) {
            throw new IllegalStateException("Modified path not specified.");
        }
        if (output == null) {
            throw new IllegalStateException("Output path not specified.");
        }

        File base = getBase();
        File modified = getModified();
        File output = getOutput();
        ArchiveFormat outputFormat = getOutputFormat();
        int contextLines = getContextLines();

        List<String> args = new ArrayList<>();
        args.add("-d");
        if (isAutoHeader()) {
            args.add("--auto-header");
        }
        if (contextLines != -1) {
            args.add("--context");
            args.add(String.valueOf(contextLines));
        }
        if (isVerbose()) {
            args.add("--verbose");
        }
        if (isPrintSummary()) {
            args.add("--summary");
        }
        args.add("--output");
        args.add(output.getAbsolutePath());
        if (outputFormat != null) {
            args.add("--archive");
            args.add(outputFormat.name());
        }
        args.add(base.getAbsolutePath());
        args.add(modified.getAbsolutePath());

        if (DEBUG) {
            String line = args.stream().map(e -> "\"" + e + "\"").collect(Collectors.joining(" "));
            project.getLogger().info("Starting DiffPatch with arguments: {}", line);
        }

        PrintStream logger = new PrintStream(new ConsumingOutputStream(line -> project.getLogger().lifecycle(line)));
        int exit = 0;
        try {
            exit = DiffPatch.mainI(args.toArray(new String[0]), logger, null);
        } catch (IOException e) {
            codechicken.diffpatch.util.Utils.throwUnchecked(e);
        }
        if (exit != 0 && exit != 1) {
            throw new RuntimeException("DiffPatch failed with exit code: " + exit);
        }
    }

    //@formatter:off
    @Override public File getBase() { return base != null ? Utils.resolveFile(project, base) : null; }
    @Override public File getModified() { return modified != null ? Utils.resolveFile(project, modified) : null; }
    @Override public File getOutput() { return output != null ? Utils.resolveFile(project, output) : null; }
    @Override public ArchiveFormat getOutputFormat() { return outputFormat != null ? Utils.resolveArchiveFormat(outputFormat) : null; }
    @Override public boolean isAutoHeader() { return autoHeader; }
    @Override public int getContextLines() { return contextLines; }
    @Override public boolean isVerbose() { return verbose; }
    @Override public boolean isPrintSummary() { return printSummary; }
    @Override public void setBase(Object base) { this.base = base; }
    @Override public void setModified(Object modified) { this.modified = modified; }
    @Override public void setOutput(Object patches) { this.output = patches; }
    @Override public void setOutputFormat(Object format) { this.outputFormat = format; }
    @Override public void setAutoHeader(boolean autoHeader) { this.autoHeader = autoHeader; }
    @Override public void setContextLines(int lines) { this.contextLines = lines; }
    @Override public void setVerbose(boolean verbose) { this.verbose = verbose; }
    @Override public void setPrintSummary(boolean printSummary) { this.printSummary = printSummary; }
    //@formatter:on
}
