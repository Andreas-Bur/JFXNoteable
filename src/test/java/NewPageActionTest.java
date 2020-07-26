import com.andreasbur.actions.ActionHandler;
import com.andreasbur.actions.NewPageAction;
import com.andreasbur.document.DocumentController;
import com.andreasbur.document.DocumentModel;
import com.andreasbur.document.DocumentPane;
import com.andreasbur.gui.ParentPane;
import com.andreasbur.page.PageLayout;
import com.andreasbur.page.PageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class NewPageActionTest {

	private DocumentModel documentModel;
	private DocumentPane documentPane;
	private DocumentController documentController;
	private ActionHandler actionHandler;

	@BeforeEach
	void start() {
		documentModel = new DocumentModel();
		documentPane = new DocumentPane(documentModel);
		documentController = new DocumentController(documentModel, documentPane);
		actionHandler = new ActionHandler();
	}

	@Test
	void testNewPageActionAsFirstPage() {
		NewPageAction newPageActionTest = new NewPageAction(documentController);
		actionHandler.execute(newPageActionTest);

		assert documentModel.getPageModels().size() == 1;
		assert documentModel.getPageModels().get(0).getPageLayout() == PageLayout.DEFAULT;
	}

	@Test
	void testUndoNewPageActionAsFirstPage() {
		NewPageAction newPageActionTest = new NewPageAction(documentController);
		actionHandler.execute(newPageActionTest);
		actionHandler.undo();

		assert documentModel.getPageModels().size() == 0;
	}


	@Test
	void testPageSelectionAfterFirstPageAdded() {
		NewPageAction newPageActionTest = new NewPageAction(documentController);
		actionHandler.execute(newPageActionTest);

		assert documentModel.getSelectedPageIndex() == 0;
		assert documentModel.getSelectedPageModel() == documentModel.getPageModels().get(0);
	}

}
