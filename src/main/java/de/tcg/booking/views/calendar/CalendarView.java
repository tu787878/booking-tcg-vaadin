package de.tcg.booking.views.calendar;

import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.tcg.booking.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Calendar")
@Route(value = "admin/calendar", layout = MainLayout.class)
@PermitAll
public class CalendarView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

	public CalendarView() {
        setSpacing(false);

//        Image img = new Image("images/empty-plant.png", "placeholder plant");
//        img.setWidth("200px");
//        add(img);
        
        FullCalendar calendar = FullCalendarBuilder.create().build();
        calendar.setSizeFull();
        add(calendar);
        
        setSizeFull();
    }

}
