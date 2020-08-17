package codechicken.diffpatch.gradle.patch;

import codechicken.diffpatch.util.PatchMode;
import codechicken.diffpatch.util.archiver.ArchiveFormat;

import java.io.File;

/**
 * Created by covers1624 on 14/8/20.
 */
public interface PatchSpec {

    /**
     * Gets the base path to be patched.
     *
     * @return The base files.
     */
    File getBase();

    /**
     * Gets the patches to apply.
     *
     * @return The patch files.
     */
    File getPatches();

    /**
     * Gets the output path.
     *
     * @return The output location.
     */
    File getOutput();

    /**
     * Gets the path to save patch rejects.
     *
     * @return The reject location.
     */
    File getRejects();

    /**
     * Gets the {@link ArchiveFormat} to save the Output in.
     * Can be a {@link ArchiveFormat} or a string
     * representing one of its values.
     *
     * @return The format.
     */
    ArchiveFormat getOutputFormat();

    /**
     * Gets the {@link ArchiveFormat} to save rejects in.
     * Can be a {@link ArchiveFormat} or a string
     * representing one of its values.
     *
     * @return The format.
     */
    ArchiveFormat getRejectsFormat();

    /**
     * The minimum quality of a fuzzy match for DiffPatch to consider
     * successful.
     *
     * @return The quality.
     */
    float getMinFuzzQuality();

    /**
     * The maximum number of lines DiffPatch is allowed to wander
     * from the hunk target for a fuzzy patch.
     *
     * @return The number of lines.
     */
    int getMaxFuzzOffset();

    /**
     * The mode for this patching operation.
     *
     * @return The mode.
     */
    PatchMode getPatchMode();

    /**
     * A prefix DiffPatch will apply to the patches input.
     * Useful for reading patches out of a sub-directory of a zip.
     *
     * @return The offset.
     */
    String getPatchesPrefix();

    /**
     * @return If verbose output is enabled.
     */
    boolean isVerbose();

    /**
     * @return If summary output is enabled.
     */
    boolean isPrintSummary();

    /**
     * If the task should fail when patches fail to apply.
     * In some use cases it might be okay for patches to fail,
     * and let the rest of the build process continue.
     * Only patch rejects or missing files are considered failures.
     *
     * @return If the task should fail patches failing.
     */
    boolean isFailOnError();

    /**
     * Sets the base path to patch.
     * Can be a File, Supplier, Closure, String.
     * A Supplier or Closure will be evaluated at task execution.
     *
     * @param base The base path.
     */
    void setBase(Object base);

    /**
     * Sets the location to patch from.
     * Can be a File, Supplier, Closure, String.
     * A Supplier or Closure will be evaluated at task execution.
     *
     * @param patches The patches path.
     */
    void setPatches(Object patches);

    /**
     * Sets the location to output patched files.
     * Can be a File, Supplier, Closure, String.
     * A Supplier or Closure will be evaluated at task execution.
     *
     * @param output The output path.
     */
    void setOutput(Object output);

    /**
     * Sets the location to output reject files.
     * Can be a File, Supplier, Closure, String.
     * A Supplier or Closure will be evaluated at task execution.
     *
     * @param rejects The rejects path.
     */
    void setRejects(Object rejects);

    /**
     * Sets the format for the patched output.
     * Can be a {@link ArchiveFormat} or a string
     * representing one of its values.
     *
     * @param outputFormat The format.
     */
    void setOutputFormat(Object outputFormat);

    /**
     * Sets the format for the rejects output.
     * Can be a {@link ArchiveFormat} or a string
     * representing one of its values.
     *
     * @param rejectsFormat The format.
     */
    void setRejectsFormat(Object rejectsFormat);

    /**
     * The minimum quality of a fuzzy match for DiffPatch to consider
     * successful.
     *
     * @param fuzz The quality, [0-1]
     */
    void setMinFuzzQuality(float fuzz);

    /**
     * Sets the maximum number of lines DiffPatch is allowed to wander
     * from the hunk target for a fuzzy patch.
     *
     * @param maxFuzzOffset The number of lines.
     */
    void setMaxFuzzOffset(int maxFuzzOffset);

    /**
     * Sets the PatchMode to apply in.
     * May be a {@link PatchMode} or a string
     * representing one of its values.
     *
     * @param patchMode The mode.
     */
    void setPatchMode(Object patchMode);

    /**
     * Sets the prefix DiffPatch will apply to the patches input.
     * Useful for reading patches out of a sub-directory of a zip.
     *
     * @param prefix The prefix.
     */
    void setPatchesPrefix(String prefix);

    /**
     * If verbose output is enabled.
     *
     * @param verbose The value.
     */
    void setVerbose(boolean verbose);

    /**
     * If summary output is enabled.
     *
     * @param printSummary The value.
     */
    void setPrintSummary(boolean printSummary);

    /**
     * Sets if the task should fail when patches fail to apply.
     * In some use cases it might be okay for patches to fail,
     * and let the rest of the build process continue.
     * Only patch rejects or missing files are considered failures.
     *
     * @param failOnError The value.
     */
    void setFailOnError(boolean failOnError);

    //Groovy magic overloads.
    //@formatter:off
    default void base(Object base) { setBase(base); }
    default void patches(Object patches) { setPatches(patches); }
    default void output(Object output) { setOutput(output); }
    default void rejects(Object rejects) { setRejects(rejects); }
    default void outputFormat(Object outputFormat) { setOutputFormat(outputFormat); }
    default void rejectsFormat(Object rejectsFormat) { setRejectsFormat(rejectsFormat); }
    default void minFuzzQuality(float fuzzQuality) { setMinFuzzQuality(fuzzQuality); }
    default void maxFuzzOffset(int maxOffset) { setMaxFuzzOffset(maxOffset); }
    default void patchMode(Object patchMode) { setPatchMode(patchMode); }
    default void patchesPrefix(String prefix) { setPatchesPrefix(prefix); }
    default void verbose(boolean verbose) { setVerbose(verbose); }
    default void printSummary(boolean printSummary) { setPrintSummary(printSummary); }
    default void failOnError(boolean failOnError) { setFailOnError(failOnError); }
    //@formatter:on
}
