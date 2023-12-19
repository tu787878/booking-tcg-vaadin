package de.tcg.booking.views.dialog;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import de.tcg.booking.entity.Category;
import de.tcg.booking.entity.Service;
import de.tcg.booking.entity.ServiceVariant;
import de.tcg.booking.service.ServiceService;
import de.tcg.booking.util.NotificationHelper;
import de.tcg.booking.views.services.VariantComponent;

public class ServiceDialog extends AbstractDialog<Service, ServiceService> {

	private static final long serialVersionUID = 1L;

	private Binder<Service> m_binder = new Binder<>(Service.class);

	private List<VariantComponent> m_variantComponents = new ArrayList<>();

	public ServiceDialog(ServiceService service) {
		super(service);
		setHeaderTitle("Service");
	}

	@Override
	protected VerticalLayout createDialogLayout() {

		TextField nameField = new TextField("Name");
		TextField descriptionField = new TextField("Description");
		Checkbox checkbox = new Checkbox();
		checkbox.setLabel("Enable");
		TextField priceField = new TextField();
		priceField.setLabel("Price");
		TextField timeField = new TextField();
		timeField.setLabel("Time");

		ComboBox<Category> iconBox = new ComboBox<>("Category");
		iconBox.setItems(m_service.findAllCategories());
		iconBox.setItemLabelGenerator(Category::getName);

		IntegerField integerField = new IntegerField();
		integerField.setLabel("Position");
		integerField.setMin(0);
		integerField.setStepButtonsVisible(true);

		m_binder.forField(nameField).bind(Service::getName, Service::setName);
		m_binder.forField(descriptionField).bind(Service::getDescription, Service::setDescription);
		m_binder.forField(checkbox).bind(Service::isEnable, Service::setEnable);
		m_binder.forField(integerField).bind(Service::getPosition, Service::setPosition);
		m_binder.forField(iconBox).bind(Service::getCategory, Service::setCategory);
		m_binder.forField(priceField).withConverter(new StringToFloatConverter("Not a float value!"))
				.bind(Service::getPrice, Service::setPrice);
		m_binder.forField(timeField).withConverter(new StringToIntegerConverter("Not a integer value!"))
				.bind(Service::getTime, Service::setTime);

		m_binder.readBean(m_data);

		HorizontalLayout variantsAction = new HorizontalLayout();
		Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
		plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
		plusButton.setAriaLabel("Add variant");

		Text variantText = new Text("Variants");
		variantsAction.add(variantText, plusButton);
		variantsAction.setAlignItems(Alignment.CENTER);

		VerticalLayout dialogLayout = new VerticalLayout(nameField, iconBox, descriptionField, priceField, timeField,
				checkbox, integerField, variantsAction);
		dialogLayout.setPadding(false);
		dialogLayout.setSpacing(false);
		dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		dialogLayout.setWidth("1000px");

		plusButton.addClickListener(e -> {
			ServiceVariant vari = new ServiceVariant();
			vari.setService(m_data);
			vari.setEnable(true);
			VariantComponent tmp = new VariantComponent(vari);
			dialogLayout.add(tmp);
			m_variantComponents.add(tmp);
			tmp.addChangeListener(a -> {
				tmp.setVisible(false);
				m_variantComponents.remove(tmp);
			});
		});

		m_variantComponents.clear();

		for (ServiceVariant variant : m_data.getVariants()) {
			VariantComponent tmp = new VariantComponent(variant);
			dialogLayout.add(tmp);
			m_variantComponents.add(tmp);
			tmp.addChangeListener(e -> {
				tmp.setVisible(false);
				m_variantComponents.remove(tmp);
			});
			
		}

		if (m_data.getId() != null) {
			nameField.setValue(m_data.getName());
		}

		return dialogLayout;
	}

	@Override
	protected boolean saveData() {
		try {
			m_binder.writeBean(m_data);

			List<ServiceVariant> variants = new ArrayList<>();
			for (VariantComponent com : m_variantComponents) {
				variants.add(com.getVariant());
			}
			m_data.setVariants(variants);

			m_service.save(m_data);
		} catch (Exception e) {
			NotificationHelper.showError("Error: " + e.getMessage());
			return false;
		}
		fireEvent(new ChangeEvent(this, false));
		NotificationHelper.showSuccess("The service was saved successfully!");
		return true;
	}

}
