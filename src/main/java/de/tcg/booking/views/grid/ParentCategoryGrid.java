package de.tcg.booking.views.grid;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import de.tcg.booking.entity.ParentCategory;
import de.tcg.booking.service.ParentCategoryService;
import de.tcg.booking.util.NotificationHelper;
import de.tcg.booking.views.dialog.ParentCategoryDialog;

public class ParentCategoryGrid extends AbstractGrid<ParentCategory, ParentCategoryService, ParentCategoryDialog> {

	public ParentCategoryGrid(ParentCategoryService service) {
		super(service, new Grid<ParentCategory>(ParentCategory.class));
	}

	@Override
	protected void buildPropertyColumns() {
		m_grid.addColumn("name");
		m_grid.addColumn(new ComponentRenderer<>(Image::new, (img, parent) -> {
			img.setSrc("parent-category-icons/" + parent.getIcon());
			img.setWidth("50px");
		})).setHeader("Icon");
		m_grid.addColumn("description");
		m_grid.addColumn("enable").setWidth("9em").setFlexGrow(0);
		m_grid.addColumn("position").setWidth("9em").setFlexGrow(0);
	}

	@Override
	protected ParentCategoryDialog getDialog() {
		ParentCategoryDialog dialog = new ParentCategoryDialog(m_service);
		dialog.addChangeListener(e -> {
			buildGrid();
		});
		return dialog;
	}

	@Override
	protected String getName(ParentCategory entry) {
		return entry.getName();
	}

	@Override
	protected void deleteEntry(ParentCategory entry) {
		try {
			m_service.delete(entry);
			buildGrid();
			NotificationHelper.showSuccess("Deleted!");
		} catch (Exception e) {
			NotificationHelper.showError("Error: the parent category is in use!");
		}
	}

	@Override
	protected void openDialog(ParentCategory entry) {
		m_dialog.openDialog(entry);
	}

	@Override
	public Button getAddButton() {
		Button primaryButton = new Button("New parent category");
		primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		primaryButton.addClickListener(e -> {
			ParentCategory temp = new ParentCategory();
			temp.setEnable(true);
			m_dialog.openDialog(temp);
		});
		return primaryButton;
	}

	@Override
	public List<ParentCategory> getAll() {
		return m_service.findAll();
	}

}
