package components.chord;

import components.map.Map;
import components.standard.Standard;

/**
 * Chord kernel component with primary methods. (Note: by package-wide
 * convention, all references are non-null.)
 *
 * @author Jake Meyer
 */
public interface ChordKernel extends Standard<Chord> {

    /**
     * Adds {@code note} to the chord (this).
     *
     * @param note
     *            the note to be added
     * @requires {@code note} is not already in this and {@code note} is within
     *           noteList
     * @ensures this = #this + note
     */
    void add(String note);

    /**
     * Removes {@code note} from the chord (this).
     *
     * @param note
     *            the name of the note to be removed
     * @requires {@code note} is in this and {@code note} is within
     *           noteListString
     * @ensures this = #this - note and remove = note
     * @return note name that was removed
     */
    String remove(String note);

    /**
     * Checks whether {@code note} is in the chord (this).
     *
     * @param note
     *            the name of the note to check for
     * @requires {@code note} is within noteListString
     * @ensures contains = {true if note is in this, false otherwise}
     * @return true if note is in this, false otherwise
     */
    boolean contains(String note);

    /**
     * Returns an array of all possible note names to guide the user.
     *
     * @ensures noteList = {array of all possible note names}
     * @return an array of all possible note names
     */
    String[] noteList();

    /**
     * Returns a map of possible note names and their respective ints. Intended
     * so user can use methods with syntax such as
     * add(noteListString.value("A"). Note: Just typing "A" will function as
     * "A1". Other octaves can be specified, but only values within the map will
     * function as expected.
     *
     * @ensures noteListString = {map of note names and their respective ints}
     * @return a map of notes and their int values, searchable by name
     */
    Map<String, Integer> noteListString();

    /**
     * Returns a map that is essentially noteListString but flipped. Used to
     * assist remove and add. If a note is flat/sharp, it will default to
     * showing the corresponding flat. Will also include the octave number.
     *
     * @ensures noteListInt = {inverted version of noteListString}
     * @return a map of notes and their int values, searchable by int
     */
    Map<Integer, String> noteListInt();

    /**
     * Returns a map of all notes currently inside this. Client should only
     * expect notes with a number after. Ex: if C was added, expect C0, not C.
     *
     * @ensures currentNotes = {representation of the chord in map form}
     * @return a map of all notes currently inside this
     */
    Map<Integer, String> currentNotes();

    /**
     * Returns the highest note allowed by the component in Letter * Octave
     * form.
     *
     * @ensures max = {highest note allowed by the component}
     * @return highest note allowed by the component
     */
    String max();

    /**
     * Returns the lowest note allowed by the component in Letter * Octave form.
     *
     * @ensures min = {lowest note allowed by the component}
     * @return lowest note allowed by the component
     */
    String min();

    /**
     * Returns the size (number of notes inside) of the chord.
     *
     * @ensures size = {number of notes inside this}
     * @return number of notes inside this
     */
    int size();
}
