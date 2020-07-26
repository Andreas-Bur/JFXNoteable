import com.andreasbur.actions.ActionHandler;
import com.andreasbur.actions.RotatePageAction;
import com.andreasbur.document.DocumentController;
import com.andreasbur.document.DocumentModel;
import com.andreasbur.document.DocumentPane;
import com.andreasbur.page.PageLayout;
import com.andreasbur.page.PageModel;
import com.andreasbur.page.PagePane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RotatePageActionTest {
	private DocumentModel documentModel;
	private DocumentController documentController;
	private ActionHandler actionHandler;

	@BeforeEach
	void start() {
		documentModel = new DocumentModel();
		documentController = new DocumentController(documentModel, null);
		actionHandler = new ActionHandler();
	}

	@Test
	void testRotatePage() {

		PageModel pageModel = new PageModel(PageLayout.A4.toPortrait());
		documentController.addPage(0, pageModel, true);

		assert pageModel.getPageLayout().getOrientation() == PagePane.Orientation.PORTRAIT;

		RotatePageAction rotatePageAction = new RotatePageAction(documentController);
		actionHandler.execute(rotatePageAction);

		assert pageModel.getPageLayout().getOrientation() == PagePane.Orientation.LANDSCAPE;

	}

	@Test
	void testUndoRotatePage() {

		PageModel pageModel = new PageModel(PageLayout.A4.toPortrait());
		documentController.addPage(0, pageModel, true);

		assert pageModel.getPageLayout().getOrientation() == PagePane.Orientation.PORTRAIT;

		RotatePageAction rotatePageAction = new RotatePageAction(documentController);
		actionHandler.execute(rotatePageAction);
		actionHandler.undo();

		assert pageModel.getPageLayout().getOrientation() == PagePane.Orientation.PORTRAIT;

	}
}
