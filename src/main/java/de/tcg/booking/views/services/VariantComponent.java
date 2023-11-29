package de.tcg.booking.views.services;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

import de.tcg.booking.entity.ServiceVariant;
import de.tcg.booking.util.NotificationHelper;

public class VariantComponent extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	private ServiceVariant m_variant;

	private Binder<ServiceVariant> m_binder = new Binder<>(ServiceVariant.class);

	public class DeletedEvent extends ComponentEvent<VariantComponent> {
		private static final long serialVersionUID = 1L;

		public DeletedEvent(VariantComponent source, boolean fromClient) {
			super(source, fromClient);
		}
	}
	
	public Registration addChangeListener(
	         ComponentEventListener<DeletedEvent> listener) {
	        return addListener(DeletedEvent.class, listener);
	    }

	public VariantComponent(ServiceVariant variant) {
		super();
		m_variant = variant;
		setAlignItems(Alignment.END);
		getThemeList().add("spacing-s");
		createLayout();
	}

	private void createLayout() {
		removeAll();
		TextField nameField = new TextField("Name");
		Checkbox checkbox = new Checkbox();
		checkbox.setLabel("Enable");
		TextField priceField = new TextField();
		priceField.setLabel("Price");
		TextField timeField = new TextField();
		timeField.setLabel("Time");
		IntegerField integerField = new IntegerField();
		integerField.setLabel("Position");
		integerField.setMin(0);
		integerField.setStepButtonsVisible(true);
		Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
		closeButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
		closeButton.setAriaLabel("Delete");
		closeButton.setTooltipText("Delete");
		closeButton.addClickListener(e -> {
			fireEvent(new DeletedEvent(this, false));
		});
		add(nameField, priceField, timeField, integerField, checkbox, closeButton);

		m_binder.forField(nameField).bind(ServiceVariant::getName, ServiceVariant::setName);
		m_binder.forField(checkbox).bind(ServiceVariant::isEnable, ServiceVariant::setEnable);
		m_binder.forField(integerField).bind(ServiceVariant::getPosition, ServiceVariant::setPosition);
		m_binder.forField(priceField).withConverter(new StringToFloatConverter("Not a float value!"))
				.bind(ServiceVariant::getPrice, ServiceVariant::setPrice);
		m_binder.forField(timeField).withConverter(new StringToIntegerConverter("Not a integer value!"))
				.bind(ServiceVariant::getTime, ServiceVariant::setTime);
		m_binder.readBean(m_variant);
	}


	public ServiceVariant getVariant() {
		try {
			m_binder.writeBean(m_variant);
		} catch (ValidationException e) {
			NotificationHelper.showError("Error: " + e.getMessage());
		}
		return m_variant;
	}

}
