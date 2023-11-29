package de.tcg.booking.views.grid;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;

import de.tcg.booking.entity.Category;
import de.tcg.booking.service.CategoryService;
import de.tcg.booking.util.NotificationHelper;
import de.tcg.booking.views.dialog.CategoryDialog;

public class CategoryGrid extends AbstractGrid<Category, CategoryService, CategoryDialog> {

	public CategoryGrid(CategoryService service) {
		super(service, new Grid<Category>(Category.class));
	}

	@Override
	protected void buildPropertyColumns() {
		m_grid.addColumn("name");
		m_grid.addColumn(cat -> {
			return cat.getParentCategory().getName();
		}).setHeader("Parent category");
		m_grid.addColumn("description");
		m_grid.addColumn("enable").setWidth("9em").setFlexGrow(0);
		m_grid.addColumn("position").setWidth("9em").setFlexGrow(0);
	}

	@Override
	protected CategoryDialog getDialog() {
		CategoryDialog dialog = new CategoryDialog(m_service);
		dialog.addChangeListener(e -> {
			buildGrid();
		});
		return dialog;
	}

	@Override
	protected String getName(Category entry) {
		return entry.getName();
	}

	@Override
	protected void deleteEntry(Category entry) {
		try {
			m_service.delete(entry);
			buildGrid();
			NotificationHelper.showSuccess("Deleted!");
		} catch (Exception e) {
			NotificationHelper.showError("Error: the category is in use!");
		}
	}

	@Override
	protected void openDialog(Category entry) {
		m_dialog.openDialog(entry);
	}

	@Override
	public Button getAddButton() {
		Button primaryButton = new Button("New category");
		primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		primaryButton.addClickListener(e -> {
			Category cat = new Category();
			cat.setEnable(true);
			m_dialog.openDialog(cat);
		});
		return primaryButton;
	}

	@Override
	public List<Category> getAll() {
		return m_service.findAll();
	}

}
