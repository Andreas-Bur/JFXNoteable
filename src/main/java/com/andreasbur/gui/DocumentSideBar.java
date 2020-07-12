package com.andreasbur.gui;

import com.andreasbur.gui.page.Page;
import com.andreasbur.gui.page.PageLayout;
import com.andreasbur.gui.page.PagePreview;
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

	private final ObservableList<Page> pageList;
	private final ObservableList<PagePreview> pagePreviewList;
	private final DoubleProperty maxPageWidth;
	private final VBox pagePreviewPane;

	public DocumentSideBar(DocumentPane documentPane) {
		super();

		this.pageList = documentPane.getPageList();
		pagePreviewList = FXCollections.observableArrayList();
		maxPageWidth = new SimpleDoubleProperty();

		setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		setFitToWidth(true);
		setFitToHeight(true);
		setMinWidth(100); //TODO: HiDPI

		pagePreviewPane = new VBox();
		pagePreviewPane.setAlignment(Pos.TOP_CENTER);
		pagePreviewPane.setSpacing(25);
		pagePreviewPane.setPadding(new Insets(25));
		Bindings.bindContent(pagePreviewPane.getChildren(), pagePreviewList);

		setContent(pagePreviewPane);

		ChangeListener<? super PageLayout> maxPageWidthListener = (observable, oldValue, newValue) -> updateMaxPageWidth();

		pageList.addListener((ListChangeListener<? super Page>) c -> {
			updateMaxPageWidth();

			while (c.next()) {
				if (c.wasAdded()) {
					for (Page page : c.getAddedSubList()) {
						page.getPagePreview().getImageView().fitWidthProperty().bind(widthProperty()
								.multiply(Bindings.createDoubleBinding(() -> page.getPageLayout().getPageSize().getWidth(), page.pageLayoutProperty()))
								.divide(maxPageWidth)
								.subtract(50));
						page.pageLayoutProperty().addListener(maxPageWidthListener);
					}
				}
			}

			pagePreviewList.setAll(pageList.stream().map(Page::getPagePreview).collect(Collectors.toList()));
			pagePreviewList.forEach(pagePreview -> pagePreview.setOnMouseClicked(event -> documentPane.setSelectedPage(pagePreview.getPage())));
		});
	}

	private void updateMaxPageWidth() {
		maxPageWidth.set(pageList.stream().mapToDouble(value -> value.getPageLayout().getPageSize().getWidth()).max().orElse(100));
	}

	public ObservableList<PagePreview> getPagePreviewList() {
		return pagePreviewList;
	}

	public double getMaxPageWidth() {
		return maxPageWidth.get();
	}

	public DoubleProperty maxPageWidthProperty() {
		return maxPageWidth;
	}

	public VBox getPagePreviewPane() {
		return pagePreviewPane;
	}
}
