package com.andreasbur.gui;

import com.andreasbur.gui.page.Page;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DocumentPane extends VBox {

	private final ObservableList<Page> pageList = FXCollections.observableArrayList();
	private final SimpleObjectProperty<Page> selectedPage = new SimpleObjectProperty<>();

	private static final Border selectedBorder = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2)));

	public DocumentPane(ParentPane parentPane) {

		setPadding(new Insets(25, 25, 25, 25));
		setSpacing(25);
		setFillWidth(false);
		setAlignment(Pos.CENTER);

		Bindings.bindContent(getChildren(), pageList);

		pageList.addListener((ListChangeListener<? super Page>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (Page page : c.getAddedSubList()) {
						page.updatePreviewImage();
						page.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> selectedPage.set(page));
					}
				}
				if (c.getRemoved().contains(selectedPage.get())) {
					selectedPage.set(null);
				}
			}
			for (int i = 0; i < pageList.size(); i++) {
				pageList.get(i).setPageIndex(i + 1);
			}
		});

		selectedPage.addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				oldValue.setBorder(Border.EMPTY);
				oldValue.getPagePreview().getImageViewPane().setBorder(Border.EMPTY);
			}
			if (newValue != null) {
				newValue.setBorder(selectedBorder);
				newValue.getPagePreview().getImageViewPane().setBorder(selectedBorder);
			}
			parentPane.getDocumentScalePane().scrollToNode(newValue);
		});
	}

	public ObservableList<Page> getPageList() {
		return pageList;
	}

	public Page getSelectedPage() {
		return selectedPage.get();
	}

	public SimpleObjectProperty<Page> selectedPageProperty() {
		return selectedPage;
	}

	public void setSelectedPage(Page selectedPage) {
		this.selectedPage.set(selectedPage);
	}
}
