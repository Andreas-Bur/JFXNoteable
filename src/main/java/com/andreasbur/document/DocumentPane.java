package com.andreasbur.document;

import com.andreasbur.page.PageModel;
import com.andreasbur.page.PagePane;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DocumentPane extends StackPane {

	private final VBox pageBox;

	private final ObservableList<PagePane> pagePaneList = FXCollections.observableArrayList();
	private final DocumentModel documentModel;

	private DocumentController.PageSelectionListener pageSelectionListener;
	private static final Border SELECTED_BORDER = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2)));

	public DocumentPane(DocumentModel documentModel) {

		this.documentModel = documentModel;

		pageBox = new VBox();
		pageBox.setPadding(new Insets(25, 25, 25, 25));
		pageBox.setSpacing(25);
		pageBox.setFillWidth(false);
		pageBox.setAlignment(Pos.CENTER);

		Group pageGroup = new Group(pageBox);
		getChildren().add(pageGroup);

		Bindings.bindContent(pageBox.getChildren(), pagePaneList);

		documentModel.getPageModels().addListener(pageModelsListener);

		pagePaneList.addListener((ListChangeListener<? super PagePane>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (PagePane pagePane : c.getAddedSubList()) {
						pagePane.updatePreviewImage();
						pagePane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> pageSelectionListener.changed(pagePaneList.indexOf(pagePane)));
					}
				}
				if (documentModel.isPageSelected().get() && c.getRemoved().contains(pagePaneList.get(documentModel.getSelectedPageIndex()))) {
					pageSelectionListener.changed(-1);
				}
			}
		});

		documentModel.selectedPageIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() >= 0) {
				pagePaneList.get(oldValue.intValue()).setBorder(Border.EMPTY);
				pagePaneList.get(oldValue.intValue()).getPagePreview().getImageViewPane().setBorder(Border.EMPTY);
			}
			if (newValue.intValue() >= 0) {
				pagePaneList.get(newValue.intValue()).setBorder(SELECTED_BORDER);
				pagePaneList.get(newValue.intValue()).getPagePreview().getImageViewPane().setBorder(SELECTED_BORDER);
			}
		});
	}

	private PagePane createPage(PageModel pageModel) {
		if (pageModel == null) {
			throw new IllegalArgumentException("pageModel must not be null");
		}

		return new PagePane(pageModel);
	}

	public VBox getPageBox() {
		return pageBox;
	}

	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	public ObservableList<PagePane> getPagePaneList() {
		return pagePaneList;
	}

	public void setPageSelectionListener(DocumentController.PageSelectionListener pageSelectionListener){
		this.pageSelectionListener = pageSelectionListener;
	}

	private ListChangeListener<? super PageModel> pageModelsListener = c -> {
		while (c.next()) {
			if (c.wasPermutated()) {
				ArrayList<PagePane> oldPagePaneList = new ArrayList<>(pagePaneList);
				TreeMap<Integer, Integer> pageIndexMap = new TreeMap<>();
				for (int i = 0; i < c.getList().size(); i++) {
					pageIndexMap.put(c.getPermutation(i), i);
				}
				for (Map.Entry<Integer, Integer> entry : pageIndexMap.entrySet()) {
					pagePaneList.set(entry.getKey(), oldPagePaneList.get(entry.getValue()));
				}
			} else {
				if (c.wasRemoved()) {
					c.getRemoved().forEach(pageModel -> pagePaneList.removeIf(page -> page.getPageModel() == pageModel));
				}
				if (c.wasAdded()) {
					pagePaneList.addAll(c.getFrom(), c.getAddedSubList().stream().map(this::createPage).collect(Collectors.toList()));
				}
			}
		}
	};
}
