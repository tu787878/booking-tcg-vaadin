package de.tcg.booking.views.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Booking")
@Route(value = "booking")
@RouteAlias(value = "")
@AnonymousAllowed
public class FrontEndView extends VerticalLayout{

}
