package de.tcg.booking.views.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;

import de.tcg.booking.entity.TimeSection;

public class TimeSectionComponent extends HorizontalLayout {
	private static final long serialVersionUID = 1L;
	private TimeSection m_timeSection;

	public TimeSectionComponent(TimeSection time) {
		m_timeSection = time;
		TimePicker from = new TimePicker();
		from.setLabel("From");
		TimePicker to = new TimePicker();
		to.setLabel("To");
		Checkbox checkbox = new Checkbox("Off");
		Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
		closeButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
		closeButton.setAriaLabel("Delete");
		closeButton.setTooltipText("Delete");
		closeButton.addClickListener(e -> {

		});

		add(from, to, checkbox, closeButton);
		setAlignItems(Alignment.END);
	}

	public TimeSection getData() {
		return m_timeSection;
	}
}
