package de.tcg.booking.views.grid;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;

public abstract class AbstractGrid<T, S, D>{

	protected List<T> m_dataList;
	protected S m_service;
	protected D m_dialog;
	protected Grid<T> m_grid;

	public AbstractGrid(S service, Grid<T> grid) {
		m_grid = grid;
		m_service = service;
		m_grid.setDropMode(GridDropMode.BETWEEN);
		m_grid.setRowsDraggable(true);
		m_grid.setSizeFull();
		buildGrid();
	}

	public void buildGrid() {
		m_dialog = getDialog();
		m_grid.removeAllColumns();
		m_dataList = getAll();
		m_grid.setItems(m_dataList);
		buildPropertyColumns();
		buildActionColumns();
	}

	protected abstract void buildPropertyColumns();

	private void buildActionColumns() {

		m_grid.addColumn(new ComponentRenderer<>(Button::new, (button, entry) -> {
			button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
			button.addClickListener(e -> {
				openDialog(entry);
			});
			button.setIcon(new Icon(VaadinIcon.EDIT));
		})).setHeader("Edit").setWidth("5em").setFlexGrow(0);
		m_grid.addColumn(new ComponentRenderer<>(Button::new, (button, entry) -> {
			button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
			button.addClickListener(e -> {
				ConfirmDialog confirmDlg = new ConfirmDialog();
				confirmDlg.setHeader("Delete \"" + getName(entry) + "\"?");
				confirmDlg.setText("Are you sure you want to permanently delete this item?");

				confirmDlg.setCancelable(true);
				confirmDlg.setConfirmText("Delete");
				confirmDlg.setConfirmButtonTheme("error primary");
				confirmDlg.addConfirmListener(event -> {
					deleteEntry(entry);
				});
				confirmDlg.open();
			});
			button.setIcon(new Icon(VaadinIcon.TRASH));
		})).setHeader("Delete").setWidth("5em").setFlexGrow(0);
	}

	protected abstract D getDialog();

	protected abstract String getName(T entry);

	protected abstract void deleteEntry(T entry);

	protected abstract void openDialog(T entry);
	
	public abstract Button getAddButton();
	
	protected abstract List<T> getAll();
	
	public Grid<T> getGrid(){
		return m_grid;
	}
	
	

}
