package components.chord;

import components.map.Map;
import components.map.Map2;

/**
 * {@code Chord} represented as a {@code Map} with implementations of primary
 * methods.
 *
 * @author Jake Meyer
 *
 */
public class Chord1 extends ChordSecondary {

    /**
     * Representation of {@code this}.
     */
    private Map<Integer, String> rep;

    /**
     * Number of possible notes, for noteList.
     */
    private final int n = 85;

    /**
     * Sequence of note name options for the user.
     */
    private String[] noteList = new String[this.n];

    /**
     * Map of notes organized by name.
     */
    private Map<String, Integer> noteListString;

    /**
     * Map of notes organized by their int value.
     */
    private Map<Integer, String> noteListInt;

    /**
     * Creator of original representation.
     */
    private void createNewRep() {
        this.rep = new Map2<Integer, String>();
        this.noteListString = new Map2<String, Integer>();
        this.noteListInt = new Map2<Integer, String>();

        /*
         * Creating noteListString (done in here so it's only done once).
         */

        //Some out of order to allow exceptions like E# to work normally.
        final String[] notes = new String[] { "C", "C#", "Db", "D", "D#", "Eb",
                "E", "Fb", "E#", "F", "F#", "Gb", "G", "G#", "Ab", "A", "A#",
                "Bb", "B", "Cb", "B#" };
        int value = 0;
        for (int i = 0; i < notes.length; i++) {
            String curr = notes[i];
            if (!this.noteStatus(curr).equals("Flat") && !curr.equals("F")
                    && !curr.equals("C")) {
                value++;
            }
            this.noteListString.add(curr, value);
        }

        //Octave-specific adding
        value = 0;
        final int numOctaves = 3;
        for (int o = 0; o < numOctaves; o++) {
            for (int i = 0; i < notes.length; i++) {
                String curr = notes[i];
                if (!this.noteStatus(curr).equals("Flat") && !curr.equals("F")
                        && !curr.equals("C")) {
                    value++;
                }
                this.noteListString.add(curr + o, value);
            }
        }
        this.noteListString.add("C3", value);

        /*
         * Creating noteListInt.
         */
        final String[] notes2 = new String[] { "C", "Db", "D", "Eb", "E", "F",
                "Gb", "G", "Ab", "A", "Bb", "B" };
        final int notesInOctave = 12;
        int key = 0;
        for (int o = 0; o < numOctaves; o++) {
            int i = 0;
            while (key < notes2.length + (notesInOctave * o)) {
                this.noteListInt.add(key, notes2[i] + o);
                key++;
                i++;
            }
        }
        this.noteListInt.add(key, "C3");

        /*
         * Creating noteList.
         */
        int i = 0;
        for (Map.Pair<String, Integer> curr : this.noteListString) {
            this.noteList[i] = curr.key();
            i++;
        }
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Chord1() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final Chord newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Chord source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";

        Chord1 localSource = (Chord1) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Helper methods ---------------------------------------------------------
     */

    /**
     * Small method to scan a note string and return if it has a flat or sharp.
     *
     * @param note
     *            note to be scanned
     * @return {"Flat" if note is flat, "Sharp" if note is sharp, "" if note is
     *         natural}
     */
    private String noteStatus(String note) {
        int i = 0;
        String result = "";
        while (i < note.length() && result.equals("")) {
            if (note.charAt(i) == 'b') {
                result = "Flat";
            } else if (note.charAt(i) == '#') {
                result = "Sharp";
            }
            i++;
        }
        return result;
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(String note) {
        int address = this.noteListString.value(note);
        this.rep.add(address, note);
    }

    @Override
    public final String remove(String note) {
        int address = this.noteListString.value(note);
        this.rep.remove(address);
        return note;
    }

    @Override
    public final boolean contains(String note) {
        int address = this.noteListString.value(note);
        return this.rep.hasKey(address);
    }

    @Override
    public final String[] noteList() {
        return this.noteList;
    }

    @Override
    public final Map<String, Integer> noteListString() {
        return this.noteListString;
    }

    @Override
    public final Map<Integer, String> noteListInt() {
        return this.noteListInt;
    }

    @Override
    public final Map<Integer, String> currentNotes() {
        return this.rep;
    }

    @Override
    public final String max() {
        return "C3";
    }

    @Override
    public final String min() {
        return "C0";
    }

    @Override
    public final int size() {
        return this.rep.size();
    }
}
