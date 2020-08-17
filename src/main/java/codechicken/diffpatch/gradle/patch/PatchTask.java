package codechicken.diffpatch.gradle.patch;

import codechicken.diffpatch.util.PatchMode;
import codechicken.diffpatch.util.archiver.ArchiveFormat;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * Incredibly simple Task implementation of PatchAction.
 * Does no Input/Output caching, it is recommended to extend this
 * and implement those yourself as desired.
 * <p>
 * Created by covers1624 on 14/8/20.
 */
public class PatchTask extends DefaultTask implements PatchSpec {

    private final PatchAction action;

    public PatchTask() {
        this.action = new PatchAction(getProject());
    }

    @TaskAction
    public void doTask() throws Exception {
        action.execute();
    }

    //@formatter:off
    @Override public File getBase() { return action.getBase(); }
    @Override public File getPatches() { return action.getPatches(); }
    @Override public File getOutput() { return action.getOutput(); }
    @Override public File getRejects() { return action.getRejects(); }
    @Override public ArchiveFormat getOutputFormat() { return action.getOutputFormat(); }
    @Override public ArchiveFormat getRejectsFormat() { return action.getRejectsFormat(); }
    @Override public float getMinFuzzQuality() { return action.getMinFuzzQuality(); }
    @Override public int getMaxFuzzOffset() { return action.getMaxFuzzOffset(); }
    @Override public PatchMode getPatchMode() { return action.getPatchMode(); }
    @Override public String getPatchesPrefix() { return action.getPatchesPrefix(); }
    @Override public boolean isVerbose() { return action.isVerbose(); }
    @Override public boolean isPrintSummary() { return action.isPrintSummary(); }
    @Override public boolean isFailOnError() { return action.isFailOnError(); }
    @Override public void setBase(Object base) { action.setBase(base); }
    @Override public void setPatches(Object patches) { action.setPatches(patches); }
    @Override public void setOutput(Object output) { action.setOutput(output); }
    @Override public void setRejects(Object rejects) { action.setRejects(rejects); }
    @Override public void setOutputFormat(Object outputFormat) { action.setOutputFormat(outputFormat); }
    @Override public void setRejectsFormat(Object rejectsFormat) { action.setRejectsFormat(rejectsFormat); }
    @Override public void setMinFuzzQuality(float fuzz) { action.setMinFuzzQuality(fuzz); }
    @Override public void setMaxFuzzOffset(int maxFuzzOffset) { action.setMaxFuzzOffset(maxFuzzOffset); }
    @Override public void setPatchMode(Object patchMode) { action.setPatchMode(patchMode); }
    @Override public void setPatchesPrefix(String prefix) { action.setPatchesPrefix(prefix); }
    @Override public void setVerbose(boolean verbose) { action.setVerbose(verbose); }
    @Override public void setPrintSummary(boolean printSummary) { action.setPrintSummary(printSummary); }
    @Override public void setFailOnError(boolean failOnError) { action.setFailOnError(failOnError); }
    //@formatter:on
}
