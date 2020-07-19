import com.andreasbur.page.PageModel;
import com.andreasbur.gui.ParentPane;
import com.andreasbur.page.PagePane;
import com.andreasbur.page.PageLayout;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.concurrent.CountDownLatch;

@ExtendWith(ApplicationExtension.class)
public class DocumentTest {

	ParentPane parentPane;
	CountDownLatch countDownLatch;

	@BeforeEach
	void start() {
		parentPane = new ParentPane();
		countDownLatch = new CountDownLatch(1);
	}

	@Test
	void testDocumentInit() {
		assert parentPane.getDocumentPane().getPagePaneList().isEmpty();
		assert parentPane.getDocumentPane().getDocumentModel().getSelectedPageIndex() == -1;

		assert parentPane.getDocumentSideBar().getPagePreviewList().isEmpty();
		assert parentPane.getDocumentSideBar().getPagePreviewPane().getChildren().isEmpty();
	}

	@Test
	void testFirstPageAdded() throws InterruptedException {
		PagePane pagePane1 = new PagePane(new PageModel(PageLayout.A4));
		Platform.runLater(() -> {
			parentPane.getDocumentPane().getPagePaneList().add(pagePane1);
			countDownLatch.countDown();
		});

		countDownLatch.await();

		assert parentPane.getDocumentPane().getPagePaneList().size() == 1;
		assert parentPane.getDocumentPane().getPagePaneList().get(0) == pagePane1;
		assert parentPane.getDocumentSideBar().getPagePreviewList().size() == 1;
		assert parentPane.getDocumentSideBar().getPagePreviewPane().getChildren().size() == 1;

	}

	@Test
	void testMultiplePagesAdded() throws InterruptedException {
		PagePane pagePane1 = new PagePane(new PageModel(PageLayout.A3));
		PagePane pagePane2 = new PagePane(new PageModel(PageLayout.A4.toLandscape()));
		PagePane pagePane3 = new PagePane(new PageModel(PageLayout.A5));

		Platform.runLater(() -> {
			parentPane.getDocumentPane().getPagePaneList().addAll(pagePane1, pagePane2, pagePane3);
			countDownLatch.countDown();
		});

		countDownLatch.await();

		assert parentPane.getDocumentPane().getPagePaneList().size() == 3;
		assert parentPane.getDocumentPane().getPagePaneList().get(0) == pagePane1;
		assert parentPane.getDocumentPane().getPagePaneList().get(1) == pagePane2;
		assert parentPane.getDocumentPane().getPagePaneList().get(2) == pagePane3;
		assert parentPane.getDocumentSideBar().getPagePreviewList().size() == 3;
		assert parentPane.getDocumentSideBar().getPagePreviewPane().getChildren().size() == 3;

	}

}
