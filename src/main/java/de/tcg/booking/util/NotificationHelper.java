package de.tcg.booking.util;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationHelper {
	
	public static void showSuccess(String message) {
		Notification notification = new Notification(message);
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.setPosition(Position.BOTTOM_CENTER);
		notification.setDuration(3000);
		notification.open();
	}
	
	public static void showError(String message) {
		Notification notification = new Notification(message);
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		notification.setPosition(Position.BOTTOM_CENTER);
		notification.setDuration(3000);
		notification.open();
	}

}
