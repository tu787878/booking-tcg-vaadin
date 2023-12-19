package de.tcg.booking.views.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.addons.tatu.ColorPicker;
import org.vaadin.addons.tatu.ColorPicker.InputMode;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
import com.vaadin.flow.shared.Registration;

import de.tcg.booking.entity.Category;
import de.tcg.booking.entity.DayType;
import de.tcg.booking.entity.Employee;
import de.tcg.booking.entity.Service;
import de.tcg.booking.entity.TimeSection;
import de.tcg.booking.service.EmployeeService;
import de.tcg.booking.service.ServiceService;
import de.tcg.booking.util.NotificationHelper;
import de.tcg.booking.views.component.TimeSectionComponent;

public class EmployeeDetails extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private Employee m_employee;

	private Binder<Employee> m_binder = new Binder<>(Employee.class);

	private final EmployeeService m_employeeService;
	
	private final ServiceService m_serviceService;

	private List<TimeSectionComponent> m_timeComponents = new ArrayList<>();
	
	public class ChangeGeneralEvent extends ComponentEvent<EmployeeDetails> {
		private static final long serialVersionUID = 1L;
		
		public ChangeGeneralEvent(EmployeeDetails source, boolean fromClient) {
			super(source, fromClient);
		}
	}
	
	public Registration addChangeListener(ComponentEventListener<ChangeGeneralEvent> listener) {
		return super.addListener(ChangeGeneralEvent.class, listener);
	}

	public EmployeeDetails(EmployeeService employeeService, Employee employee, ServiceService serviceService) {
		super();
		m_employeeService = employeeService;
		m_serviceService = serviceService;
		m_employee = employee;
		setSizeFull();
		buildLayout();
	}

	private void buildLayout() {
		removeAll();
		Details general = new Details("General information", buildGeneral());
		general.setOpened(true);

		Details time = new Details("Working time", buildTime());
		time.setOpened(false);

		Details service = new Details("Service", buildServices());
		service.setOpened(false);

		add(general, time, service);
	}
	
	private VerticalLayout buildServices() {
		VerticalLayout layout = new VerticalLayout();
		Map<Category, List<Service>> catServices = m_serviceService.getCatServices();
		List<Service> employeeServices = m_employee.getDoServices();
		Map<Checkbox, Service> checkboxes = new HashMap<>();
		for(Category cat : catServices.keySet()) {
			VerticalLayout layout2 = new VerticalLayout();
			for(Service service : catServices.get(cat)){
				Checkbox serviceCheckBox = new Checkbox(service.getName());
				checkboxes.put(serviceCheckBox, service);
				if(employeeServices.stream().anyMatch(e -> e.getId() == service.getId())) {
					serviceCheckBox.setValue(true);
				}
				layout2.add(serviceCheckBox);
			}
			Details catDetails = new Details(cat.getName(), layout2);
			catDetails.setOpened(true);
			layout.add(catDetails); 
		}
		Button primaryButton = new Button("Save");
		primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		primaryButton.addClickListener(e -> {
			List<Service> finals = new ArrayList<>();
			for(Checkbox key : checkboxes.keySet()) {
				if(key.getValue()) {
					finals.add(checkboxes.get(key));
				}
			}
			m_employee.setDoServices(finals);
			m_employeeService.save(m_employee);
			NotificationHelper.showSuccess("Services was saved successfully!");
		});
		layout.add(primaryButton);
		return layout;
	}

	private VerticalLayout buildGeneral() {
		VerticalLayout layout = new VerticalLayout();

		TextField nameField = new TextField("Name");
		TextField ageField = new TextField("Age");
		TextField emailField = new TextField("Email");
		Checkbox checkbox = new Checkbox("Enable");

		ColorPicker colorPicker = new ColorPicker();
		colorPicker.setLabel("Color");
		colorPicker.setInputMode(InputMode.NOCSSINPUT);
		ColorPicker textPicker = new ColorPicker();
		colorPicker.setLabel("Text Color");
		textPicker.setInputMode(InputMode.NOCSSINPUT);

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
//				buildLayout();
			} catch (Exception a) {
				NotificationHelper.showError("Error: " + a.getMessage());
				return;
			}
			fireEvent(new ChangeGeneralEvent(this, false));
		});

		layout.add(nameField, ageField, emailField, colorPicker, textPicker, checkbox, primaryButton);
		return layout;
	}

	private VerticalLayout buildTime() {
		VerticalLayout layout = new VerticalLayout();

		for (DayType type : DayType.values()) {
			layout.add(buildDay(type));
		}
		
		Button primaryButton = new Button("Save");
		primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		primaryButton.addClickListener(e -> {
			List<TimeSection> times = new ArrayList<>();
			for(TimeSectionComponent component : m_timeComponents) {
				TimeSection timeSection = component.getData();
				if(timeSection != null)
					times.add(timeSection);
			}
			
			m_employee.setWorkingDays(times);
			m_employeeService.save(m_employee);
			NotificationHelper.showSuccess("Working times was saved successfully!");
		});

		layout.add(primaryButton);
		layout.setWidthFull();
		return layout;
	}

	private VerticalLayout buildDay(DayType type) {
		VerticalLayout layout = new VerticalLayout();
		List<TimeSection> times = getTimeBy(type);
		HorizontalLayout row = new HorizontalLayout();
		H4 title = new H4(type.toString());
		Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
		plusButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
		plusButton.setAriaLabel("Add new");
		plusButton.addClickListener(e -> {
			TimeSection temp = new TimeSection();
			temp.setType(type);
			TimeSectionComponent time = new TimeSectionComponent(temp);
			m_timeComponents.add(time);
			time.addChangeListener(a -> {
				layout.remove(a.getSource());
				m_timeComponents.remove(a.getSource());
			});
			layout.add(time);
		});
		row.add(title, plusButton);
		row.setAlignItems(Alignment.CENTER);
		layout.add(row);

		for (TimeSection time : times) {
			TimeSectionComponent tmp = new TimeSectionComponent(time);
			m_timeComponents.add(tmp);
			tmp.addChangeListener(a -> {
				layout.remove(a.getSource());
				m_timeComponents.remove(a.getSource());
			});
			layout.add(tmp);
		}
		return layout;
	}

	private List<TimeSection> getTimeBy(DayType type) {
		List<TimeSection> temp = m_employee.getWorkingDays();
		temp = temp.stream().filter(e -> e.getType().equals(type)).toList();
		return temp;
	}

}
