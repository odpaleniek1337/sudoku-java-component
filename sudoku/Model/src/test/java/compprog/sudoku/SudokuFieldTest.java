package compprog.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    public SudokuFieldTest() {
    }

    @Test
    public void testOneFieldEqualThisColumn() {
        SudokuField field = new SudokuField();
        assertTrue(field.equals(field));
    }

    @Test
    public void testFieldNotEqualDifferentObject() {
        SudokuField field = new SudokuField();
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        assertFalse(field.equals(board));
    }

    @Test
    public void testTwoSameHashCodesAreEqual() {
        SudokuField field = new SudokuField();
        assertEquals(field.hashCode(),field.hashCode());
    }

	@Test
    public void testNullObjectOnEqualsMethod() {
        SudokuField field = new SudokuField();
        SudokuField field1 = null;
        assertFalse(field.equals(field1));
    }
	
    @Test
    public void testTwoHashCodesAreNotEqual() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();

        field1.setFieldValue(1);
        field2.setFieldValue(2);
        assertNotEquals(field1.hashCode(),field2.hashCode());
    }

    @Test
    public void testToStringMethodSudokuBoard() {
        SudokuField field = new SudokuField();
        field.setFieldValue(1);
        String toString = field.toString();
        assertTrue(toString.contains("value"));
        assertTrue(toString.contains("observers"));
        assertTrue(toString.contains("1"));
    }

    @Test
    public void testFieldCloning() throws CloneNotSupportedException {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(5);
        SudokuField field2 = field1.clone();
        assertEquals(field1.getFieldValue(), field2.getFieldValue());
        assertEquals(field1.observers, field2.observers);
    }
}