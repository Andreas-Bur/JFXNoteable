import com.andreasbur.gui.ParentPane;
import com.andreasbur.gui.page.Page;
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
		assert parentPane.getDocumentPane().getPageList().isEmpty();
		assert parentPane.getDocumentPane().getSelectedPage() == null;

		assert parentPane.getDocumentSideBar().getPagePreviewList().isEmpty();
		assert parentPane.getDocumentSideBar().getPagePreviewPane().getChildren().isEmpty();
	}

	@Test
	void testFirstPageAdded() throws InterruptedException {
		Page page1 = new Page(Page.A4);
		Platform.runLater(() -> {
			parentPane.getDocumentPane().getPageList().add(page1);
			countDownLatch.countDown();
		});

		countDownLatch.await();

		assert parentPane.getDocumentPane().getPageList().size() == 1;
		assert parentPane.getDocumentPane().getPageList().get(0) == page1;
		assert parentPane.getDocumentSideBar().getPagePreviewList().size() == 1;
		assert parentPane.getDocumentSideBar().getPagePreviewPane().getChildren().size() == 1;

	}

	@Test
	void testMultiplePagesAdded() throws InterruptedException {
		Page page1 = new Page(Page.A3);
		Page page2 = new Page(Page.A4.toLandscapeLayout());
		Page page3 = new Page(Page.A5);

		Platform.runLater(() -> {
			parentPane.getDocumentPane().getPageList().addAll(page1, page2, page3);
			countDownLatch.countDown();
		});

		countDownLatch.await();

		assert parentPane.getDocumentPane().getPageList().size() == 3;
		assert parentPane.getDocumentPane().getPageList().get(0) == page1;
		assert parentPane.getDocumentPane().getPageList().get(1) == page2;
		assert parentPane.getDocumentPane().getPageList().get(2) == page3;
		assert parentPane.getDocumentSideBar().getPagePreviewList().size() == 3;
		assert parentPane.getDocumentSideBar().getPagePreviewPane().getChildren().size() == 3;

	}

}
