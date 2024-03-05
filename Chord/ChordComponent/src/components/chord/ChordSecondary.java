package components.chord;

/**
 * Layered implementations of secondary methods for {@code Chord}.
 *
 * @author Jake Meyer
 */
public abstract class ChordSecondary implements Chord {

    /**
     * Used for incrementing/decrementing octaves.
     */
    private static final int NOTES_IN_OCTAVE = 12;

    /*
     * Common methods (from Object) -------------------------------------------
     */

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Chord)) {
            return false;
        }
        Chord c = (Chord) obj;
        if (this.size() != c.size()) {
            return false;
        }
        Chord thisTemp = this.newInstance();
        Chord cTemp = this.newInstance();
        while (this.size() > 0) {
            String x1 = this.removeLowest();
            Object x2 = c.removeLowest();
            if (!x2.equals(x1)) {
                return false;
            }
            thisTemp.add(x1);
            cTemp.add((String) x2);
        }
        this.transferFrom(thisTemp);
        c.transferFrom(cTemp);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        Chord temp = this.newInstance();
        while (this.size() > 0) {
            result.append(this.removeLowest());
            if (this.size() > 0) {
                result.append(",");
            }
        }
        result.append(">");
        this.transferFrom(temp);
        return result.toString();
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
     * Other non-kernel methods -----------------------------------------------
     */

    @Override
    public void flat(String note) {
        this.remove(note);
        int address = this.noteListString().value(note);
        this.add(this.noteListInt().value(address - 1));
    }

    @Override
    public void sharp(String note) {
        this.remove(note);
        int address = this.noteListString().value(note);
        this.add(this.noteListInt().value(address + 1));
    }

    @Override
    public void natural(String note) {
        int address = this.noteListString().value(note);
        this.remove(note);
        String status = this.noteStatus(note);
        if (status.equals("Flat")) {
            address++;
        } else if (status.equals("Sharp")) {
            address--;
        }
        this.add(this.noteListInt().value(address));

    }

    @Override
    public void octaveUp(String note) {
        this.remove(note);
        int address = this.noteListString().value(note);
        this.add(this.noteListInt().value(address + NOTES_IN_OCTAVE));
    }

    @Override
    public void octaveDown(String note) {
        this.remove(note);
        int address = this.noteListString().value(note);
        this.add(this.noteListInt().value(address - NOTES_IN_OCTAVE));
    }

    @Override
    public String removeLowest() {
        int i = 0;
        while (!this.contains(this.noteList()[i])) {
            i++;
        }

        return this.remove(this.noteListInt().value(i));
    }

}
