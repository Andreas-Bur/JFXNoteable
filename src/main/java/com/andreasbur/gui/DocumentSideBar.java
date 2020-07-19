package com.andreasbur.gui;

import com.andreasbur.document.DocumentController;
import com.andreasbur.document.DocumentPane;
import com.andreasbur.page.PagePane;
import com.andreasbur.page.PageLayout;
import com.andreasbur.page.PagePreview;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class DocumentSideBar extends ScrollPane {

	private final ObservableList<PagePane> pagePaneList;
	private final ObservableList<PagePreview> pagePreviewList;
	private final DoubleProperty maxPageWidth;
	private final VBox pagePreviewPane;

	private DocumentController.PageSelectionListener pageSelectionListener = null;

	private static final int PADDING = 25;

	public DocumentSideBar(ObservableList<PagePane> pagePaneList) {
		super();

		this.pagePaneList = pagePaneList;
		pagePreviewList = FXCollections.observableArrayList();
		maxPageWidth = new SimpleDoubleProperty();

		setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		setFitToWidth(true);
		setFitToHeight(true);
		setMinWidth(100); //TODO: HiDPI

		pagePreviewPane = new VBox();
		pagePreviewPane.setAlignment(Pos.TOP_CENTER);
		pagePreviewPane.setSpacing(PADDING);
		pagePreviewPane.setPadding(new Insets(PADDING));
		Bindings.bindContent(pagePreviewPane.getChildren(), pagePreviewList);

		setContent(pagePreviewPane);

		ChangeListener<? super PageLayout> maxPageWidthListener = (observable, oldValue, newValue) -> updateMaxPageWidth();

		pagePaneList.addListener((ListChangeListener<? super PagePane>) c -> {
			updateMaxPageWidth();

			while (c.next()) {
				if (c.wasAdded()) {
					for (PagePane pagePane : c.getAddedSubList()) {
						pagePane.getPagePreview().getImageView().fitWidthProperty().bind(widthProperty().subtract(2 * PADDING)
								.multiply(Bindings.createDoubleBinding(() -> pagePane.getPageModel().getPageLayout().getPageSize().getWidth(), pagePane.getPageModel().pageLayoutProperty()))
								.divide(maxPageWidth));
						pagePane.getPageModel().pageLayoutProperty().addListener(maxPageWidthListener);
					}
				}
			}

			pagePreviewList.setAll(pagePaneList.stream().map(PagePane::getPagePreview).collect(Collectors.toList()));
			pagePreviewList.forEach(pagePreview -> pagePreview.setOnMouseClicked(event -> pageSelectionListener.changed(pagePreviewList.indexOf(pagePreview))));
		});
	}

	private void updateMaxPageWidth() {
		maxPageWidth.set(pagePaneList.stream().mapToDouble(value -> value.getPageModel().getPageLayout().getPageSize().getWidth()).max().orElse(100));
	}

	public ObservableList<PagePreview> getPagePreviewList() {
		return pagePreviewList;
	}

	public VBox getPagePreviewPane() {
		return pagePreviewPane;
	}

	public void setPageSelectionListener(DocumentController.PageSelectionListener pageSelectionListener){
		this.pageSelectionListener = pageSelectionListener;
	}
}
