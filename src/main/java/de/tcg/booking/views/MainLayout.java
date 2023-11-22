package de.tcg.booking.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.tcg.booking.views.calendar.CalendarView;
import de.tcg.booking.views.dashboard.DashboardView;
import de.tcg.booking.views.employee.EmployeeView;
import de.tcg.booking.views.reservations.ReservationsView;
import de.tcg.booking.views.services.ServicesView;
import de.tcg.booking.views.settings.SettingsView;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Booking TCG");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Dashboard", DashboardView.class, LineAwesomeIcon.BUROMOBELEXPERTE.create()));
        nav.addItem(new SideNavItem("Calendar", CalendarView.class, LineAwesomeIcon.CALENDAR_ALT_SOLID.create()));
        nav.addItem(new SideNavItem("Reservations", ReservationsView.class, LineAwesomeIcon.LIST_ALT_SOLID.create()));
        nav.addItem(new SideNavItem("Services", ServicesView.class, LineAwesomeIcon.ADDRESS_CARD.create()));
        nav.addItem(new SideNavItem("Employee", EmployeeView.class, LineAwesomeIcon.USERS_COG_SOLID.create()));
        nav.addItem(new SideNavItem("Settings", SettingsView.class, LineAwesomeIcon.EDIT.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
