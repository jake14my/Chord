import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.chord.Chord;
import components.chord.Chord1;
import components.map.Map;

/**
 * Test suite for Chord.
 *
 * @author Jake Meyer
 *
 */
public class Chord1Test {

    /**
     * Used to create a chord for tests.
     *
     * @param args
     *            notes to add
     * @return chord with notes {@code args}
     */
    private static Chord createFromArgs(String... args) {
        Chord result = new Chord1();
        for (int i = 0; i < args.length; i++) {
            result.add(args[i]);
        }
        return result;
    }

    /*
     * Testing kernel methods -------------------------------------------------
     */

    @Test
    public void testContainsOneNote() {
        Chord c = createFromArgs("C");

        assertEquals(true, c.contains("C"));
        assertEquals(true, c.contains("C0"));
    }

    @Test
    public void testContainsWrongNote() {
        Chord c = createFromArgs("C");

        assertEquals(false, c.contains("D"));
        assertEquals(false, c.contains("D0"));
    }

    @Test
    public void testContainsWrongOctave() {
        Chord c = createFromArgs("C");

        assertEquals(false, c.contains("C1"));
        assertEquals(false, c.contains("C2"));
        assertEquals(false, c.contains("C3"));
    }

    @Test
    public void testContainsMultipleNotes() {
        Chord c = createFromArgs("C", "D", "A2");

        assertEquals(true, c.contains("C"));
        assertEquals(true, c.contains("C0"));
        assertEquals(true, c.contains("D"));
        assertEquals(true, c.contains("D0"));
        assertEquals(true, c.contains("A2"));
        assertEquals(false, c.contains("A"));
    }

    @Test
    public void testAddWithoutOctave() {
        Chord c = createFromArgs("C");

        assertEquals(true, c.contains("C"));
        assertEquals(false, c.contains("D"));
    }

    @Test
    public void testAddWithOctave() {
        Chord c = createFromArgs("C0");

        assertEquals(true, c.contains("C"));
        assertEquals(true, c.contains("C0"));
        assertEquals(false, c.contains("C1"));
        assertEquals(false, c.contains("C2"));
        assertEquals(false, c.contains("C3"));

        c.add("C2");

        assertEquals(true, c.contains("C"));
        assertEquals(true, c.contains("C0"));
        assertEquals(false, c.contains("C1"));
        assertEquals(true, c.contains("C2"));
        assertEquals(false, c.contains("C3"));
    }

    @Test
    public void testRemoveWithoutOctaveSingle() {
        Chord c = createFromArgs("C");
        c.remove("C");

        assertEquals(false, c.contains("C"));
        assertEquals(false, c.contains("C0"));
        assertEquals(false, c.contains("D"));
    }

    @Test
    public void testRemoveWithOctaveSingle() {
        Chord c = createFromArgs("C0");
        c.remove("C0");

        assertEquals(false, c.contains("C"));
        assertEquals(false, c.contains("C0"));
        assertEquals(false, c.contains("D"));
    }

    @Test
    public void testRemoveMultipleOctaves() {
        Chord c = createFromArgs("C", "C1", "C2", "C3");
        c.remove("C");

        assertEquals(false, c.contains("C"));
        assertEquals(false, c.contains("C0"));
        assertEquals(true, c.contains("C1"));
        assertEquals(true, c.contains("C2"));
        assertEquals(true, c.contains("C3"));
        assertEquals(false, c.contains("D"));
    }

    //Hard to test noteListString and noteListInt with JUnit, did by hand.

    @Test
    public void testCurrentNotes() {
        Chord c = createFromArgs("C0");
        Map<Integer, String> curr = c.currentNotes();

        System.out.print(curr);
        assertEquals(false, curr.hasValue("C")); //mentioned in Javadoc
        assertEquals(true, curr.hasValue("C0"));
        assertEquals(false, curr.hasValue("C1"));
        assertEquals(false, curr.hasValue("D"));
    }

    @Test
    public void testMax() {
        Chord c = createFromArgs("C0");

        assertEquals("C3", c.max());
    }

    @Test
    public void testMaxEmpty() {
        Chord c = createFromArgs();

        assertEquals("C3", c.max());
    }

    @Test
    public void testMin() {
        Chord c = createFromArgs("C0");

        assertEquals("C0", c.min());
    }

    @Test
    public void testMinEmpty() {
        Chord c = createFromArgs();

        assertEquals("C0", c.min());
    }

    @Test
    public void testSize() {
        Chord c = createFromArgs("C0", "G2", "F");

        assertEquals(3, c.size());
    }

    @Test
    public void testSizeEmpty() {
        Chord c = createFromArgs();

        assertEquals(0, c.size());
    }

    /*
     * Testing secondary methods ----------------------------------------------
     */

    @Test
    public void testFlat() {
        Chord c = createFromArgs("D0");

        assertEquals(true, c.contains("D0"));

        c.flat("D");

        assertEquals(true, c.contains("Db"));
        assertEquals(false, c.contains("D0"));
        assertEquals(false, c.contains("D"));

    }

    @Test
    public void testFlatWhenFlat() {
        Chord c = createFromArgs("Eb1");

        assertEquals(true, c.contains("Eb1"));

        c.flat("Eb1");

        assertEquals(true, c.contains("D1"));
        assertEquals(false, c.contains("Eb1"));

    }

    @Test
    public void testFlatWhenSharp() {
        Chord c = createFromArgs("C#1");

        assertEquals(true, c.contains("C#1"));

        c.flat("C#1");

        assertEquals(true, c.contains("C1"));
        assertEquals(false, c.contains("C#1"));

    }

    @Test
    public void testFlatF() {
        Chord c = createFromArgs("Fb2");

        assertEquals(true, c.contains("Fb2"));

        c.flat("Fb2");

        assertEquals(true, c.contains("Eb2"));
        assertEquals(false, c.contains("E1"));

    }

    @Test
    public void testFlatC() {
        Chord c = createFromArgs("Cb2");

        assertEquals(true, c.contains("Cb2"));

        c.flat("Cb2");

        assertEquals(true, c.contains("Bb2"));
        assertEquals(false, c.contains("Cb2"));

    }

    @Test
    public void testSharp() {
        Chord c = createFromArgs("D1");

        assertEquals(true, c.contains("D1"));

        c.sharp("D1");

        assertEquals(true, c.contains("Eb1"));
        assertEquals(false, c.contains("D1"));

    }

    @Test
    public void testSharpWhenFlat() {
        Chord c = createFromArgs("Gb2");

        assertEquals(true, c.contains("Gb2"));

        c.sharp("Gb2");

        assertEquals(true, c.contains("G2"));
        assertEquals(false, c.contains("Gb2"));

    }

    @Test
    public void testSharpWhenSharp() {
        Chord c = createFromArgs("F#2");

        assertEquals(true, c.contains("F#2"));

        c.sharp("F#2");

        assertEquals(true, c.contains("G2"));
        assertEquals(false, c.contains("F#2"));

    }

    @Test
    public void testSharpE() {
        Chord c = createFromArgs("E");

        assertEquals(true, c.contains("E0"));

        c.sharp("E");

        assertEquals(true, c.contains("F"));
        assertEquals(false, c.contains("E"));

    }

    @Test
    public void testSharpB() {
        Chord c = createFromArgs("B1");

        assertEquals(true, c.contains("B1"));

        c.sharp("B1");

        assertEquals(true, c.contains("C2"));
        assertEquals(false, c.contains("B1"));

    }

    @Test
    public void testNaturalFlat() {
        Chord c = createFromArgs("Db1");

        assertEquals(true, c.contains("Db1"));

        c.natural("Db1");

        assertEquals(true, c.contains("D1"));
        assertEquals(false, c.contains("Db1"));

    }

    @Test
    public void testNaturalSharp() {
        Chord c = createFromArgs("G#2");

        assertEquals(true, c.contains("G#2"));

        c.natural("G#2");

        assertEquals(true, c.contains("G2"));
        assertEquals(false, c.contains("G#2"));

    }

    @Test
    public void testNaturalWhenNatural() {
        Chord c = createFromArgs("C2");

        assertEquals(true, c.contains("C2"));

        c.natural("C2");

        assertEquals(true, c.contains("C2"));
        assertEquals(true, c.size() == 1);

    }

    @Test
    public void testNaturalFb() {
        Chord c = createFromArgs("Fb");

        assertEquals(true, c.contains("Fb0"));

        c.natural("Fb");

        assertEquals(true, c.contains("F"));
        assertEquals(false, c.contains("E"));

    }

    @Test
    public void testNaturalCb() {
        Chord c = createFromArgs("Cb2");

        assertEquals(true, c.contains("Cb2"));

        c.natural("Cb2");

        assertEquals(true, c.contains("C3"));
        assertEquals(false, c.contains("B2"));

    }

    @Test
    public void testOctaveUp0() {
        Chord c = createFromArgs("F");

        assertEquals(true, c.contains("F0"));

        c.octaveUp("F");

        assertEquals(true, c.contains("F1"));
        assertEquals(false, c.contains("F"));
        assertEquals(false, c.contains("F0"));

    }

    @Test
    public void testOctaveUp1() {
        Chord c = createFromArgs("G1");

        assertEquals(true, c.contains("G1"));

        c.octaveUp("G1");

        assertEquals(true, c.contains("G2"));
        assertEquals(false, c.contains("G1"));

    }

    @Test
    public void testOctaveUp2() {
        Chord c = createFromArgs("C2");

        assertEquals(true, c.contains("C2"));

        c.octaveUp("C2");

        assertEquals(true, c.contains("C3"));
        assertEquals(false, c.contains("C2"));

    }

    @Test
    public void testOctaveDown0() {
        Chord c = createFromArgs("C3");

        assertEquals(true, c.contains("C3"));

        c.octaveDown("C3");

        assertEquals(true, c.contains("C2"));
        assertEquals(false, c.contains("C3"));

    }

    @Test
    public void testOctaveDown1() {
        Chord c = createFromArgs("A2");

        assertEquals(true, c.contains("A2"));

        c.octaveDown("A2");

        assertEquals(true, c.contains("A1"));
        assertEquals(false, c.contains("A2"));

    }

    @Test
    public void testOctaveDown2() {
        Chord c = createFromArgs("C1");

        assertEquals(true, c.contains("C1"));

        c.octaveDown("C1");

        assertEquals(true, c.contains("C0"));
        assertEquals(true, c.contains("C"));
        assertEquals(false, c.contains("C1"));

    }

    @Test
    public void testRemoveLowest() {
        Chord c = createFromArgs("C0", "C2", "C1", "C3");

        String removed = c.removeLowest();

        assertEquals(false, c.contains("C0"));
        assertEquals(removed, "C0");
        assertEquals(true, c.contains("C1"));
        assertEquals(true, c.contains("C2"));
        assertEquals(true, c.contains("C3"));

    }
}
