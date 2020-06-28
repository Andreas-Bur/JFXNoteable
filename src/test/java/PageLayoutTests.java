import com.andreasbur.gui.page.Page;
import com.andreasbur.gui.page.PageLayout;
import javafx.geometry.Dimension2D;
import org.junit.jupiter.api.Test;

public class PageLayoutTests {

	@Test
	void testPageLayoutInit() {
		int one = 1;
		int two = 2;
		PageLayout pageLayout1 = new PageLayout(new Dimension2D(one, two), Page.Orientation.PORTRAIT);

		assert (pageLayout1.getOrientation() == Page.Orientation.PORTRAIT);
		assert (pageLayout1.getPageSize().getWidth() == one && pageLayout1.getPageSize().getHeight() == two);

		PageLayout pageLayout2 = new PageLayout(new Dimension2D(one, one), Page.Orientation.PORTRAIT);

		assert (pageLayout2.getOrientation() == Page.Orientation.PORTRAIT);

		PageLayout pageLayout3 = new PageLayout(new Dimension2D(one, one), Page.Orientation.LANDSCAPE);

		assert (pageLayout3.getOrientation() == Page.Orientation.LANDSCAPE);
	}

	@Test
	void testPageLayoutChange() {
		int one = 1;
		int two = 2;
		PageLayout pageLayout = new PageLayout(new Dimension2D(one, two), Page.Orientation.PORTRAIT);

		pageLayout.setOrientation(Page.Orientation.LANDSCAPE);

		assert (pageLayout.getOrientation() == Page.Orientation.LANDSCAPE);
		assert (pageLayout.getPageSize().getWidth() == two && pageLayout.getPageSize().getHeight() == one);

		pageLayout.setPageSize(new Dimension2D(one, two));

		assert (pageLayout.getOrientation() == Page.Orientation.PORTRAIT);
		assert (pageLayout.getPageSize().getWidth() == one && pageLayout.getPageSize().getHeight() == two);
	}

	@Test
	void testPageLayoutNoChange() {
		int one = 1;
		int two = 2;
		int three = 2;
		PageLayout pageLayout1 = new PageLayout(new Dimension2D(one, two), Page.Orientation.PORTRAIT);
		pageLayout1.setPageSize(new Dimension2D(one, three));

		assert (pageLayout1.getOrientation() == Page.Orientation.PORTRAIT);

		PageLayout pageLayout2 = new PageLayout(new Dimension2D(one, one), Page.Orientation.PORTRAIT);

		assert (pageLayout2.getOrientation() == Page.Orientation.PORTRAIT);

		pageLayout2.setOrientation(Page.Orientation.LANDSCAPE);

		assert (pageLayout2.getOrientation() == Page.Orientation.LANDSCAPE);
	}
}
