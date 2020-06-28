import com.andreasbur.gui.ParentPane;
import org.junit.jupiter.api.Test;

public class ParentPaneTest {

	@Test
	void testParentPaneInit() {
		ParentPane parentPane = new ParentPane();

		assert parentPane.getDocumentPane() != null;
		assert parentPane.getDocumentScalePane() != null;
		assert parentPane.getDocumentSideBar() != null;
		assert parentPane.getStatusBar() != null;
		assert parentPane.getToolbarPane() != null;
		assert parentPane.getActionHandler() != null;
	}
}
