package de.tcg.booking.views.employee;

import java.util.List;

import org.vaadin.addons.tatu.ColorPicker;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.tcg.booking.entity.Employee;
import de.tcg.booking.service.EmployeeService;
import de.tcg.booking.service.ServiceService;
import de.tcg.booking.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Employee")
@Route(value = "admin/employee", layout = MainLayout.class)
@PermitAll
public class EmployeeView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private Tabs m_tabs = new Tabs();

	private HorizontalLayout m_content = new HorizontalLayout();

	private final EmployeeService m_employeeService;
	
	private final ServiceService m_serviceService;
	
	private List<Employee> m_employees;

	private Dialog m_dialog = new Dialog();

	private Binder<Employee> m_binder = new Binder<>(Employee.class);

	public EmployeeView(EmployeeService employeeService, ServiceService serviceService) {
		m_employeeService = employeeService;
		m_serviceService = serviceService;
		m_tabs.setOrientation(Tabs.Orientation.VERTICAL);
		m_tabs.setHeightFull();
		m_tabs.setWidth("240px");
		m_tabs.addSelectedChangeListener(e -> buildLayout(m_tabs.getSelectedIndex()));
		setSpacing(false);
		setSizeFull();
		Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
		plusButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY);
		plusButton.setAriaLabel("Add new");
		plusButton.addClickListener(e -> m_dialog.open());
		add(plusButton, m_content);
		m_content.setSizeFull();
		loadTabs();

		m_dialog.setHeaderTitle("New employee");
		VerticalLayout dialogLayout = createDialogLayout();
		m_dialog.add(dialogLayout);

		Button saveButton = createSaveButton(m_dialog);
		Button cancelButton = new Button("Cancel", e -> m_dialog.close());
		m_dialog.getFooter().add(cancelButton);
		m_dialog.getFooter().add(saveButton);

		buildLayout(0);
	}
	
	private void loadTabs(){
		m_tabs.removeAll();
		m_employees = m_employeeService.findAll();
		for (Employee employee : m_employees) {
			Tab e = new Tab();
			e.setLabel(employee.getName());
			m_tabs.add(e);
		}
	}

	private VerticalLayout createDialogLayout() {

		TextField nameField = new TextField("Full name");
		TextField ageField = new TextField("Age");
		TextField emailField = new TextField("Email");
		Checkbox checkbox = new Checkbox("Enable");
		checkbox.setValue(true);

		ColorPicker colorPicker = new ColorPicker();
		colorPicker.setLabel("Color");
		ColorPicker textPicker = new ColorPicker();
		colorPicker.setLabel("Text Color");

		try {
			m_binder.forField(nameField).bind(Employee::getName, Employee::setName);
			m_binder.forField(ageField).withConverter(new StringToIntegerConverter("Not an integer value!"))
					.bind(Employee::getAge, Employee::setAge);
			m_binder.forField(emailField).bind(Employee::getEmail, Employee::setEmail);
			m_binder.forField(checkbox).bind(Employee::isEnable, Employee::setEnable);
			m_binder.forField(colorPicker).bind(Employee::getColorHex, Employee::setColorHex);
			m_binder.forField(textPicker).bind(Employee::getTextColorHex, Employee::setTextColorHex);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		VerticalLayout dialogLayout = new VerticalLayout(nameField, ageField, emailField, checkbox, colorPicker,
				textPicker);
		dialogLayout.setPadding(false);
		dialogLayout.setSpacing(false);
		dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

		return dialogLayout;
	}

	private Button createSaveButton(Dialog dialog) {
		Button saveButton = new Button("Add", e -> dialog.close());
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener(e -> {
			Employee newEmployee = new Employee();
			try {
				m_binder.writeBean(newEmployee);
				m_employeeService.save(newEmployee);
				UI.getCurrent().getPage().reload();
			} catch (ValidationException e1) {
				System.err.println(e1.getMessage());
			}
		});
		return saveButton;
	}

	private void buildLayout(int index) {
		if(index < 0 || index > m_employees.size()) return;
		m_content.removeAll();
		if (m_employees.isEmpty())
			return;
		EmployeeDetails tmp = new EmployeeDetails(m_employeeService, m_employees.get(index), m_serviceService);
		tmp.addChangeListener(e -> {loadTabs();});
		m_content.add(m_tabs, tmp);
	}

}
