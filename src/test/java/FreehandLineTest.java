import com.andreasbur.shapes.FreehandLine;
import javafx.scene.shape.Polyline;
import org.junit.jupiter.api.Test;

public class FreehandLineTest {

	@Test
	void testIsIntersectingTrue() {
		FreehandLine freehandLine = new FreehandLine(0, 0, 100, 100);
		Polyline eraserLine = new Polyline(100, 0, 0, 100);

		assert freehandLine.isIntersecting(eraserLine);
	}

	@Test
	void testIsIntersectingFalse() {
		FreehandLine freehandLine = new FreehandLine(0, 0, 100, 100);
		Polyline eraserLine = new Polyline(10, 0, 110, 100);

		assert freehandLine.isIntersecting(eraserLine) == false;
	}
}
