package de.tcg.booking.views.component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.shared.Registration;

import de.tcg.booking.entity.DayType;
import de.tcg.booking.entity.TimeSection;

public class TimeSectionComponent extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private TimeSection m_timeSection;

	private TimePicker m_from;

	private TimePicker m_to;

	private Checkbox m_checkbox;
	
	private Checkbox m_allDayCheckbox;
	
	private DatePicker m_dateFrom;
	
	private DatePicker m_dateTo;

	public class ChangeTimeSectionEvent extends ComponentEvent<TimeSectionComponent> {
		private static final long serialVersionUID = 1L;

		private TimeSection m_timeSection;

		public TimeSection getTimeSection() {
			return m_timeSection;
		}

		public ChangeTimeSectionEvent(TimeSectionComponent source, boolean fromClient, TimeSection time) {
			super(source, fromClient);
			m_timeSection = time;
		}
	}

	public Registration addChangeListener(ComponentEventListener<ChangeTimeSectionEvent> listener) {
		return super.addListener(ChangeTimeSectionEvent.class, listener);
	}

	public TimeSectionComponent(TimeSection time) {
		HorizontalLayout row1 = new HorizontalLayout();
		HorizontalLayout row2 = new HorizontalLayout();
		m_timeSection = time;
		m_from = new TimePicker();
		m_from.setLabel("From");
		m_to = new TimePicker();
		m_to.setLabel("To");
		m_checkbox = new Checkbox("Off");
		Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
		closeButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
		closeButton.setAriaLabel("Delete");
		closeButton.setTooltipText("Delete");
		closeButton.addClickListener(e -> {
			fireEvent(new ChangeTimeSectionEvent(this, false, m_timeSection));
		});

		if(m_timeSection.getType() == DayType.SPECIFIED) {
			m_dateFrom = new DatePicker("Date from");
			m_dateTo = new DatePicker("Date to");
			row1.add(m_dateFrom, m_dateTo);
			
			if (m_timeSection.getId() != null) {
				m_dateFrom.setValue(m_timeSection.getDateFrom());
				m_dateTo.setValue(m_timeSection.getDateTo());
			}
		}
		
		m_allDayCheckbox = new Checkbox("All day");
		m_allDayCheckbox.addValueChangeListener(e -> {
			if(m_allDayCheckbox.getValue()) {
				m_from.setVisible(false);
				m_to.setVisible(false);
			}else {
				m_from.setVisible(true);
				m_to.setVisible(true);
			}
		});
		
		if (m_timeSection.getId() != null) {
			m_from.setValue(m_timeSection.getTimeFrom());
			m_to.setValue(m_timeSection.getTimeTo());
			m_checkbox.setValue(m_timeSection.isOff());
			m_allDayCheckbox.setValue(m_timeSection.isAllDay());
		}
		row2.add(m_from, m_to, m_checkbox, m_allDayCheckbox, closeButton);
		row2.setAlignItems(Alignment.END);
		row2.setPadding(false);
		
		add(row1, row2);
		setPadding(false);
		addClassName("time-section");
	}

	public TimeSection getData() {
		LocalTime from = m_from.getValue();
		LocalTime to = m_to.getValue();
		if (!m_allDayCheckbox.getValue() && (from == null || to == null))
			return null;
		
		m_timeSection.setTimeFrom(m_from.getValue());
		m_timeSection.setTimeTo(m_to.getValue());
		
		m_timeSection.setOff(m_checkbox.getValue());
		m_timeSection.setAllDay(m_allDayCheckbox.getValue());
		
		if(m_timeSection.getType() == DayType.SPECIFIED) {
			if(m_dateFrom.getValue() == null || m_dateTo.getValue() == null) return null;
			m_timeSection.setDateFrom(m_dateFrom.getValue());
			m_timeSection.setDateTo(m_dateTo.getValue());
		}

		return m_timeSection;
	}

	public Date convertToDateViaInstant(LocalTime timeToConvert) {
		Instant timeOnEpochDayInDefaultTimeZone = LocalDate.EPOCH.atTime(timeToConvert).atZone(ZoneId.systemDefault())
				.toInstant();
		return Date.from(timeOnEpochDayInDefaultTimeZone);
	}

	public LocalTime fromDate(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
	}
}
