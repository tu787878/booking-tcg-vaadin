package de.tcg.booking.views.grid;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;

import de.tcg.booking.entity.Service;
import de.tcg.booking.service.ServiceService;
import de.tcg.booking.util.NotificationHelper;
import de.tcg.booking.views.dialog.ServiceDialog;

public class ServiceGrid extends AbstractGrid<Service, ServiceService, ServiceDialog> {

	public ServiceGrid(ServiceService service) {
		super(service, new Grid<Service>(Service.class));
	}

	@Override
	protected void buildPropertyColumns() {
		m_grid.addColumn("name");
		m_grid.addColumn(cat -> {
			return cat.getCategory().getName();
		}).setHeader("Category");
		m_grid.addColumn("description");
		m_grid.addColumn("time");
		m_grid.addColumn("price");
		m_grid.addColumn("enable").setWidth("9em").setFlexGrow(0);
		m_grid.addColumn("position").setWidth("9em").setFlexGrow(0);
	}

	@Override
	protected ServiceDialog getDialog() {
		ServiceDialog dialog = new ServiceDialog(m_service);
		dialog.addChangeListener(e -> {
			buildGrid();
		});
		return dialog;
	}

	@Override
	protected String getName(Service entry) {
		return entry.getName();
	}

	@Override
	protected void deleteEntry(Service entry) {
		try {
			m_service.delete(entry);
			buildGrid();
			NotificationHelper.showSuccess("Deleted!");
		} catch (Exception e) {
			NotificationHelper.showError("Error: the service is in use!");
		}
	}

	@Override
	protected void openDialog(Service entry) {
		m_dialog.openDialog(entry);
	}

	@Override
	public Button getAddButton() {
		Button primaryButton = new Button("New service");
		primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		primaryButton.addClickListener(e -> {
			Service tmp = new Service();
			tmp.setEnable(true);
			tmp.setVariants(new ArrayList<>());
			m_dialog.openDialog(tmp);
		});
		return primaryButton;
	}

	@Override
	public List<Service> getAll() {
		return m_service.findAll();
	}

}
