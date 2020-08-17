package codechicken.diffpatch.gradle.patch;

import codechicken.diffpatch.DiffPatch;
import codechicken.diffpatch.gradle.util.ConsumingOutputStream;
import codechicken.diffpatch.gradle.util.Utils;
import codechicken.diffpatch.util.PatchMode;
import codechicken.diffpatch.util.archiver.ArchiveFormat;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Executes a Patch operation with DiffPatch.
 *
 * @see PatchSpec
 * Created by covers1624 on 14/8/20.
 */
public class PatchAction implements PatchSpec {

    private final boolean DEBUG = Boolean.getBoolean("codechicken.diffpatch.gradle.debug");

    private final Project project;

    private Object base;
    private Object patches;
    private Object output;
    private Object rejects;
    private Object outputFormat;
    private Object rejectsFormat;
    private float minFuzzQuality = -1;
    private int maxFuzzOffset = -1;
    private Object patchMode = PatchMode.EXACT;
    private String patchesPrefix;
    private boolean verbose = false;
    private boolean printSummary = true;
    private boolean failOnError = true;

    public PatchAction(Project project) {
        this.project = project;
    }

    public int execute() {
        if (base == null) {
            throw new IllegalStateException("Base path not specified.");
        }
        if (patches == null) {
            throw new IllegalStateException("Patches path not specified.");
        }
        if (output == null) {
            throw new IllegalStateException("Output path not specified.");
        }

        File base = getBase();
        File patches = getPatches();
        File output = getOutput();
        File rejects = getRejects();
        ArchiveFormat outputFormat = getOutputFormat();
        ArchiveFormat rejectsFormat = getRejectsFormat();
        float minFuzz = getMinFuzzQuality();
        int maxOffset = getMaxFuzzOffset();
        PatchMode mode = getPatchMode();

        List<String> args = new ArrayList<>();
        args.add("-p");
        if (minFuzz != -1) {
            args.add("--fuzz");
            args.add(String.valueOf(minFuzz));
        }
        if (maxOffset != -1) {
            args.add("--offset");
            args.add(String.valueOf(maxOffset));
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
        if (mode != null) {
            args.add("--mode");
            args.add(mode.name());
        }
        if (patchesPrefix != null) {
            args.add("--prefix");
            args.add(patchesPrefix);
        }
        if (rejects != null) {
            args.add("--reject");
            args.add(rejects.getAbsolutePath());
            if (rejectsFormat != null) {
                args.add("--archive-rejects");
                args.add(rejectsFormat.name());
            }
        }
        args.add(base.getAbsolutePath());
        args.add(patches.getAbsolutePath());

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
        if (exit != 0 && isFailOnError()) {
            throw new RuntimeException("Patches failed to apply.");
        }
        return exit;
    }

    //@formatter:off
    @Override public File getBase() { return base != null ? Utils.resolveFile(project, base) : null; }
    @Override public File getPatches() { return patches != null ? Utils.resolveFile(project, patches) : null; }
    @Override public File getOutput() { return output != null ? Utils.resolveFile(project, output) : null; }
    @Override public File getRejects() { return rejects != null ? Utils.resolveFile(project, rejects) : null; }
    @Override public ArchiveFormat getOutputFormat() { return outputFormat != null ? Utils.resolveArchiveFormat(outputFormat) : null; }
    @Override public ArchiveFormat getRejectsFormat() { return rejectsFormat != null ? Utils.resolveArchiveFormat(rejectsFormat) : null; }
    @Override public float getMinFuzzQuality() { return minFuzzQuality; }
    @Override public int getMaxFuzzOffset() { return maxFuzzOffset; }
    @Override public PatchMode getPatchMode() { return Utils.resolvePatchMode(patchMode); }
    @Override public String getPatchesPrefix() { return patchesPrefix; }
    @Override public boolean isVerbose() { return verbose; }
    @Override public boolean isPrintSummary() { return printSummary; }
    @Override public boolean isFailOnError() { return failOnError; }
    @Override public void setBase(Object base) { this.base = base; }
    @Override public void setPatches(Object patches) { this.patches = patches; }
    @Override public void setOutput(Object output) { this.output = output; }
    @Override public void setRejects(Object rejects) { this.rejects = rejects; }
    @Override public void setOutputFormat(Object outputFormat) { this.outputFormat = outputFormat; }
    @Override public void setRejectsFormat(Object rejectsFormat) { this.rejectsFormat = rejectsFormat; }
    @Override public void setMinFuzzQuality(float minFuzzQuality) { this.minFuzzQuality = minFuzzQuality; }
    @Override public void setMaxFuzzOffset(int maxFuzzOffset) { this.maxFuzzOffset = maxFuzzOffset; }
    @Override public void setPatchMode(Object patchMode) { this.patchMode = patchMode; }
    @Override public void setPatchesPrefix(String patchesPrefix) { this.patchesPrefix = patchesPrefix; }
    @Override public void setVerbose(boolean verbose) { this.verbose = verbose; }
    @Override public void setPrintSummary(boolean printSummary) { this.printSummary = printSummary; }
    @Override public void setFailOnError(boolean failOnError) { this.failOnError = failOnError; }
    //@formatter:on
}
