package de.tcg.booking.views.employee;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.addons.tatu.ColorPicker;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import de.tcg.booking.entity.DayType;
import de.tcg.booking.entity.Employee;
import de.tcg.booking.entity.TimeSection;
import de.tcg.booking.service.EmployeeService;
import de.tcg.booking.util.NotificationHelper;
import de.tcg.booking.views.component.TimeSectionComponent;

public class EmployeeDetails extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private Employee m_employee;

	private Binder<Employee> m_binder = new Binder<>(Employee.class);

	private final EmployeeService m_employeeService;

	private Map<DayType, List<TimeSection>> m_mapTimes = new HashMap<>();

	public EmployeeDetails(EmployeeService employeeService, Employee employee) {
		super();
		m_employeeService = employeeService;
		m_employee = employee;
		setSizeFull();
		buildLayout();
	}

	private void buildLayout() {
		removeAll();
		Details general = new Details("General information", buildGeneral());
		general.setOpened(false);

		Details time = new Details("Working time", buildTime());
		time.setOpened(true);

		Details service = new Details("Service", buildGeneral());
		service.setOpened(false);

		add(general, time, service);
	}

	private VerticalLayout buildGeneral() {
		VerticalLayout layout = new VerticalLayout();

		TextField nameField = new TextField("Name");
		TextField ageField = new TextField("Age");
		TextField emailField = new TextField("Email");
		Checkbox checkbox = new Checkbox("Enable");

		ColorPicker colorPicker = new ColorPicker();
		colorPicker.setLabel("Color");
		ColorPicker textPicker = new ColorPicker();
		colorPicker.setLabel("Text Color");

		nameField.setWidth("300px");
		ageField.setWidth("300px");
		emailField.setWidth("300px");
		checkbox.setWidth("300px");

		m_binder.forField(nameField).bind(Employee::getName, Employee::setName);
		m_binder.forField(ageField).withConverter(new StringToIntegerConverter("Not an integer value!"))
				.bind(Employee::getAge, Employee::setAge);
		m_binder.forField(emailField).bind(Employee::getEmail, Employee::setEmail);
		m_binder.forField(checkbox).bind(Employee::isEnable, Employee::setEnable);
		m_binder.forField(colorPicker).bind(Employee::getColorHex, Employee::setColorHex);
		m_binder.forField(textPicker).bind(Employee::getTextColorHex, Employee::setTextColorHex);

		m_binder.readBean(m_employee);

		Button primaryButton = new Button("Save");
		primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		primaryButton.addClickListener(e -> {
			try {
				m_binder.writeBean(m_employee);
				// vcl
				m_employee.setName(nameField.getValue());
				m_employee.setAge(Integer.valueOf(ageField.getValue()));
				m_employee.setEmail(emailField.getValue());
				m_employee.setEnable(checkbox.getValue());
				m_employee.setColorHex(colorPicker.getValue());
				m_employee.setTextColorHex(textPicker.getValue());

				m_employee = m_employeeService.save(m_employee);
				NotificationHelper.showSuccess("General information was saved successfully!");
				buildLayout();
			} catch (Exception a) {
				NotificationHelper.showError("Error: " + a.getMessage());
			}
		});

		layout.add(nameField, ageField, emailField, colorPicker, textPicker, checkbox, primaryButton);
		return layout;
	}

	private VerticalLayout buildTime() {
		VerticalLayout layout = new VerticalLayout();

		for (DayType type : DayType.values()) {
			layout.add(buildDay(type));
		}

		layout.setWidthFull();
		return layout;
	}

	private VerticalLayout buildDay(DayType type) {
		VerticalLayout layout = new VerticalLayout();
		List<TimeSection> times = getTimeBy(type);
		m_mapTimes.put(type, times);
		HorizontalLayout row = new HorizontalLayout();
		H4 title = new H4(type.toString());
		Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
		plusButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
		plusButton.setAriaLabel("Add new");
		plusButton.addClickListener(e -> {
			System.out.println("nene");
			TimeSection temp = new TimeSection();
			if (m_mapTimes.get(type) == null || m_mapTimes.get(type).isEmpty()) {
				m_mapTimes.put(type, Arrays.asList(temp));
			} else {
				m_mapTimes.get(type).add(temp);
			}

			layout.add(new TimeSectionComponent(temp));
		});
		row.add(title, plusButton);
		row.setAlignItems(Alignment.CENTER);
		layout.add(row);

		for (TimeSection time : times) {
			layout.add(new TimeSectionComponent(time));
		}
		return layout;
	}

	private List<TimeSection> getTimeBy(DayType type) {
		List<TimeSection> temp = m_employee.getWorkingDays();
		temp = temp.stream().filter(e -> e.getType().equals(type)).toList();
		return temp;
	}

}
