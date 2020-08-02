package com.andreasbur.document;

import com.andreasbur.page.PageModel;
import com.andreasbur.page.PagePane;
import com.andreasbur.tools.ToolEventDistributor;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DocumentPane extends StackPane implements PageSelector {

	private final VBox pageBox;

	private final ObservableList<PagePane> pagePaneList = FXCollections.observableArrayList();
	private final DocumentModel documentModel;

	private PagePane selectedPagePane = null;

	private DocumentController.PageSelectionListener pageSelectionListener;
	private ToolEventDistributor toolEventDistributor;
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
			}
		});

		documentModel.selectedPageIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedPagePane != null) {
				selectedPagePane.setBorder(Border.EMPTY);
				selectedPagePane.getPagePreview().getImageViewPane().setBorder(Border.EMPTY);
			}
			if (newValue.intValue() >= 0) {
				selectedPagePane = pagePaneList.get(newValue.intValue());
				selectedPagePane.setBorder(SELECTED_BORDER);
				selectedPagePane.getPagePreview().getImageViewPane().setBorder(SELECTED_BORDER);
			} else {
				selectedPagePane = null;
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

	public void setPageSelectionListener(DocumentController.PageSelectionListener pageSelectionListener) {
		this.pageSelectionListener = pageSelectionListener;
	}

	public void setToolEventDistributor(ToolEventDistributor toolEventDistributor) {
		this.toolEventDistributor = toolEventDistributor;
		addEventHandler(MouseEvent.ANY, toolEventDistributor);
		pagePaneList.forEach(pagePane -> pagePane.getContentPane().addEventHandler(MouseEvent.ANY, toolEventDistributor));
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
					List<PagePane> newPagePanes = c.getAddedSubList().stream().map(this::createPage).collect(Collectors.toList());
					pagePaneList.addAll(c.getFrom(), newPagePanes);

					if (toolEventDistributor != null) {
						newPagePanes.forEach(pagePane -> pagePane.addEventHandler(MouseEvent.ANY, toolEventDistributor));
					}
				}
			}
		}
	};
}
