package codechicken.diffpatch.gradle.diff;

import codechicken.diffpatch.util.archiver.ArchiveFormat;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * Incredibly simple Task implementation of {@link DiffAction}.
 * Does no Input/Output caching, it is recommended to extend this
 * and implement those yourself as desired for your use case.
 * <p>
 * Created by covers1624 on 14/8/20.
 */
public class DiffTask extends DefaultTask implements DiffSpec {

    private final DiffAction action;

    public DiffTask() {
        action = new DiffAction(getProject());
    }

    @TaskAction
    public void doTask() throws Exception {
        action.execute();
    }

    //@formatter:off
    @Override public File getBase() { return action.getBase(); }
    @Override public File getModified() { return action.getModified(); }
    @Override public File getOutput() { return action.getOutput(); }
    @Override public ArchiveFormat getOutputFormat() { return action.getOutputFormat(); }
    @Override public boolean isAutoHeader() { return action.isAutoHeader(); }
    @Override public int getContextLines() { return action.getContextLines(); }
    @Override public boolean isVerbose() { return action.isVerbose(); }
    @Override public boolean isPrintSummary() { return action.isPrintSummary(); }
    @Override public void setBase(Object base) { action.setBase(base); }
    @Override public void setModified(Object modified) { action.setModified(modified); }
    @Override public void setOutput(Object patches) { action.setOutput(patches); }
    @Override public void setOutputFormat(Object format) { action.setOutputFormat(format); }
    @Override public void setAutoHeader(boolean autoHeader) { action.setAutoHeader(autoHeader); }
    @Override public void setContextLines(int lines) { action.setContextLines(lines); }
    @Override public void setVerbose(boolean verbose) { action.setVerbose(verbose); }
    @Override public void setPrintSummary(boolean printSummary) { action.setPrintSummary(printSummary); }
    //@formatter:on
}
