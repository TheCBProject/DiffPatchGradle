package codechicken.diffpatch.gradle.diff;

import codechicken.diffpatch.util.archiver.ArchiveFormat;

import java.io.File;

/**
 * Created by covers1624 on 14/8/20.
 */
public interface DiffSpec {

    /**
     * Gets the base file path for the diff operation.
     *
     * @return The path.
     */
    File getBase();

    /**
     * Gets the modified file path for the diff operation.
     *
     * @return The path.
     */
    File getModified();

    /**
     * Gets the patch output path.
     *
     * @return The path.
     */
    File getOutput();

    /**
     * Gets the ArchiveFormat to write patches into.l
     *
     * @return The format.
     */
    ArchiveFormat getOutputFormat();

    /**
     * If the Differ will generate patches including an automatic header,
     * Substituting <code>start2</code> in the hunk header with <code>_</code>
     *
     * @return If AutoHeader is enabled.
     */
    boolean isAutoHeader();

    /**
     * The number of context lines to generate in a patch file.
     *
     * @return The number of lines.
     */
    int getContextLines();

    /**
     * @return If verbose information will be printed or not.
     */
    boolean isVerbose();

    boolean isPrintSummary();

    /**
     * Set the base file path for the diff operation.
     * Can be a File, Supplier, Closure, String.
     * A Supplier or Closure will be evaluated at task execution.
     *
     * @param base The path.
     */
    void setBase(Object base);

    /**
     * Set the modified file path for the diff operation.
     * Can be a File, Supplier, Closure, String.
     * A Supplier or Closure will be evaluated at task execution.
     *
     * @param modified The path.
     */
    void setModified(Object modified);

    /**
     * Set the patches output path.
     * Can be a File, Supplier, Closure, String.
     * A Supplier or Closure will be evaluated at task execution.
     *
     * @param patches The path.
     */
    void setOutput(Object patches);

    /**
     * Sets the format for the patches output.
     * Can be a {@link ArchiveFormat} or a string
     * representing one of its values.
     *
     * @param format The format.
     */
    void setOutputFormat(Object format);

    /**
     * Sets whether AutoHeaders are enabled or not.
     *
     * @param autoHeader The state.
     */
    void setAutoHeader(boolean autoHeader);

    /**
     * Sets the number of context lines to generate in patches.
     *
     * @param lines The number of lines.
     */
    void setContextLines(int lines);

    /**
     * Sets whether the Diff operation will print verbose
     * information or not.
     *
     * @param verbose The state.
     */
    void setVerbose(boolean verbose);

    void setPrintSummary(boolean printSummary);

    //Groovy magic overloads.
    //@formatter:off
    default void base(Object base) { setBase(base); }
    default void modified(Object modified) { setModified(modified); }
    default void output(Object output) { setOutput(output); }
    default void outputFormat(Object format) { setOutputFormat(format); }
    default void autoHeader(boolean autoHeader) { setAutoHeader(autoHeader); }
    default void contextLines(int lines) { setContextLines(lines); }
    default void verbose(boolean quiet) { setVerbose(quiet); }
    default void printSummary(boolean quiet) { setPrintSummary(quiet); }
    //@formatter:on
}
