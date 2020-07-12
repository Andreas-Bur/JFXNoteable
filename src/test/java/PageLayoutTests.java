import com.andreasbur.gui.page.Page;
import com.andreasbur.gui.page.PageLayout;
import javafx.geometry.Dimension2D;
import org.junit.jupiter.api.Test;

public class PageLayoutTests {

	@Test
	void testPageLayoutInit() {
		int one = 1;
		int two = 2;
		PageLayout pageLayout1 = new PageLayout(new Dimension2D(one, two));

		assert (pageLayout1.getOrientation() == Page.Orientation.PORTRAIT);
		assert (pageLayout1.getPageSize().getWidth() == one && pageLayout1.getPageSize().getHeight() == two);

		PageLayout pageLayout2 = new PageLayout(new Dimension2D(one, one));

		assert (pageLayout2.getOrientation() == Page.Orientation.PORTRAIT);
	}

	@Test
	void testPageLayoutChange() {
		int one = 1;
		int two = 2;
		PageLayout pageLayout = new PageLayout(new Dimension2D(one, two), Page.Orientation.PORTRAIT);

		PageLayout pageLayout2 = pageLayout.toLandscapeLayout();
		PageLayout pageLayout3 = pageLayout.toRotatedLayout();

		assert pageLayout2.equals(pageLayout3);

		assert (pageLayout2.getOrientation() == Page.Orientation.LANDSCAPE);
		assert (pageLayout2.getPageSize().getWidth() == two && pageLayout2.getPageSize().getHeight() == one);
	}
}
