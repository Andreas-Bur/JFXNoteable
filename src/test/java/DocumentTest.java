import com.andreasbur.document.DocumentController;
import com.andreasbur.document.DocumentModel;
import com.andreasbur.document.DocumentPane;
import com.andreasbur.gui.DocumentSideBar;
import com.andreasbur.page.PageModel;
import com.andreasbur.gui.ParentPane;
import com.andreasbur.page.PagePane;
import com.andreasbur.page.PageLayout;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import javax.print.Doc;
import java.util.concurrent.CountDownLatch;

@ExtendWith(ApplicationExtension.class)
public class DocumentTest {

	private DocumentModel documentModel;
	private DocumentPane documentPane;
	private DocumentController documentController;

	@BeforeEach
	void start() {
		documentModel = new DocumentModel();
		documentPane = new DocumentPane(documentModel);
		documentController = new DocumentController(documentModel, documentPane);
	}

	@Test
	void testDocumentInit() {

		assert documentModel.getPageModels().isEmpty();
		assert documentPane.getPagePaneList().isEmpty();

		assert documentModel.getSelectedPageModel() == null;
		assert documentModel.getSelectedPageIndex() == -1;
	}

	@Test
	void testFirstPageAdded() {

		PageModel pageModel = new PageModel(PageLayout.A4);
		documentController.addPage(0, pageModel);

		assert documentModel.getPageModels().size() == 1;
		assert documentModel.getPageModels().get(0) == pageModel;

		assert documentPane.getPagePaneList().size() == 1;
		assert documentPane.getPagePaneList().get(0).getPageModel() == documentModel.getPageModels().get(0);

	}

	@Test
	void testOrderOfMultiplePagesAdded() {

		PageModel pageModel1 = new PageModel(PageLayout.A5);
		PageModel pageModel2 = new PageModel(PageLayout.A4.toLandscape());
		PageModel pageModel3 = new PageModel(PageLayout.A3);

		documentController.addPage(0, pageModel1);
		documentController.addPage(1, pageModel3);
		documentController.addPage(1, pageModel2);

		assert documentModel.getPageModels().size() == 3;
		assert documentModel.getPageModels().get(0) == pageModel1;
		assert documentModel.getPageModels().get(1) == pageModel2;
		assert documentModel.getPageModels().get(2) == pageModel3;

	}

}
