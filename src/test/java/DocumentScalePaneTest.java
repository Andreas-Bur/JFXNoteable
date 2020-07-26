import com.andreasbur.util.ZoomHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class DocumentScalePaneTest {

	@Test
	void testZoomHandlerInit(){
		ZoomHandler zoomHandler = new ZoomHandler();

		assert zoomHandler.scaleProperty().get() == 100;
		assert zoomHandler.getZoomFactor() == 1;
	}

	@Test
	void testZoomOut() {
		ZoomHandler zoomHandler = new ZoomHandler();
		zoomHandler.zoomOut();

		assert zoomHandler.getZoomFactor() == 0.9;

		zoomHandler.zoomOut();

		assert zoomHandler.getZoomFactor() == 0.8;

		for (int i = 0; i < 6; i++){
			zoomHandler.zoomOut();
		}

		assert zoomHandler.getZoomFactor() == 0.2;
	}

	@Test
	void testZoomOutLimit(){
		ZoomHandler zoomHandler = new ZoomHandler();
		for(int i = 0; i < 8; i++){
			zoomHandler.zoomOut();
		}
		assert zoomHandler.getZoomFactor() == 0.2;

		zoomHandler.zoomOut();

		assert zoomHandler.getZoomFactor() == 0.2;
	}

	@Test
	void testZoomIn() {
		ZoomHandler zoomHandler = new ZoomHandler();
		zoomHandler.zoomIn();

		assert zoomHandler.getZoomFactor() == 1.1;

		zoomHandler.zoomIn();

		assert zoomHandler.getZoomFactor() == 1.2;

		for (int i = 0; i < 28; i++){
			zoomHandler.zoomIn();
		}

		assert zoomHandler.getZoomFactor() == 4;
	}

	@Test
	void testZoomInLimit(){
		ZoomHandler zoomHandler = new ZoomHandler();
		for(int i = 0; i < 30; i++){
			zoomHandler.zoomIn();
		}
		assert zoomHandler.getZoomFactor() == 4;

		zoomHandler.zoomIn();

		assert zoomHandler.getZoomFactor() == 4;
	}

}
