package components.chord;

/**
 * {@code ChordKernel} enhanced with secondary methods.
 *
 * @author Jake Meyer
 */
public interface Chord extends ChordKernel {

    /**
     * Lowers {@code note} by one half-step.
     *
     * @param note
     *            the note to become flat
     * @requires {@code note} is in this and {@code note} is not min and
     *           {@code note} is within noteList
     * @ensures this = (#this - note) + {note one half-step lower}
     */
    void flat(String note);

    /**
     * Raises {@code note} by one half-step.
     *
     * @param note
     *            the note to become sharp
     * @requires {@code note} is in this and {@code note} is not max and
     *           {@code note} is within noteList
     * @ensures this = (#this - note) + {node one half-step higher}
     */
    void sharp(String note);

    /**
     * Removes any sharps or flats from {@code note}. Note that Fb and Cb are
     * recognized as E and B respectively, so natural would return E and B.
     *
     * @param note
     *            the note to become natural
     * @requires {@code note} is in this and {@code note} is within noteList
     * @ensures this = (#this - note) + {note with no flats or sharps}
     */
    void natural(String note);

    /**
     * Raises {@code note} by one octave.
     *
     * @param note
     *            the note to be raised
     * @requires {@code note} is in this and raised {@code note} is not in this
     *           and {@code note} + one octave is not >= max and {@code note} is
     *           within noteList
     * @ensures this = (#this - note) + {note one octave higher}
     */
    void octaveUp(String note);

    /**
     * Lowers {@code note} by one octave.
     *
     * @param note
     *            the note to be lowered
     * @requires {@code note} is in this and lowered {@code note} is not in this
     *           and {@code note} - one octave is not >= max and {@code note} is
     *           within noteList
     * @ensures this = (#this - note) + {note one octave lower}
     */
    void octaveDown(String note);

    /**
     * Removes and returns the lowest note from the chord (this).
     *
     * @ensures this = #this - removeLowest
     * @return {lowest note in chord}
     */
    String removeLowest();

}
