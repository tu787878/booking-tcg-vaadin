package de.tcg.booking.views.dialog;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public abstract class AbstractDialog<T, S> extends Dialog {

	private static final long serialVersionUID = 1L;
	protected T m_data;
	protected S m_service;

	public class ChangeEvent extends ComponentEvent<Dialog> {
		private static final long serialVersionUID = 1L;

		public ChangeEvent(Dialog source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public Registration addChangeListener(ComponentEventListener<AbstractDialog.ChangeEvent> listener) {
		return super.addListener(AbstractDialog.ChangeEvent.class, listener);
	}

	public AbstractDialog(S service) {
		super();
		m_service = service;
		Button cancelButton = new Button("Cancel", e -> close());
		Button saveButton = new Button("Save", e -> {
			if (saveData()) {
				close();
			}

		});
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		getFooter().add(cancelButton);
		getFooter().add(saveButton);
	}

	public void openDialog(T data) {
		m_data = data;
		removeAll();
		VerticalLayout dialogLayout = createDialogLayout();
		add(dialogLayout);
		open();
	}

	protected abstract VerticalLayout createDialogLayout();

	protected abstract boolean saveData();

}
