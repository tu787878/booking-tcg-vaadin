package de.tcg.booking.views.employee;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.tcg.booking.entity.Employee;
import de.tcg.booking.service.EmployeeService;
import de.tcg.booking.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Employee")
@Route(value = "employee", layout = MainLayout.class)
@PermitAll
public class EmployeeView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private Tabs m_tabs = new Tabs();

	private HorizontalLayout m_content = new HorizontalLayout();

	private final EmployeeService m_employeeService;
	
	List<Employee> m_employees;

	public EmployeeView(EmployeeService employeeService) {
		m_employeeService = employeeService;
		m_tabs.setOrientation(Tabs.Orientation.VERTICAL);
		m_tabs.setHeightFull();
		m_tabs.setWidth("240px");
		m_tabs.addSelectedChangeListener(e -> buildLayout(m_tabs.getSelectedIndex()));
		setSpacing(false);
		setSizeFull();
		Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
		plusButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY);
		plusButton.setAriaLabel("Add new");
		add(plusButton, m_content);
		m_content.setSizeFull();
		m_employees =  m_employeeService.findAll();
		for (Employee employee : m_employees) {
			Tab e = new Tab();
			e.setLabel(employee.getName());
			m_tabs.add(e);
		}
		buildLayout(0);
	}

	private void buildLayout(int index) {
		m_content.removeAll();
		if(m_employees.isEmpty()) return;
		m_content.add(m_tabs, new EmployeeDetails(m_employeeService, m_employees.get(index)));
	}
	
}
