package de.tcg.booking.views.dialog;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import de.tcg.booking.entity.ParentCategory;
import de.tcg.booking.service.ParentCategoryService;
import de.tcg.booking.util.NotificationHelper;

public class ParentCategoryDialog extends AbstractDialog<ParentCategory, ParentCategoryService> {

	private static final long serialVersionUID = 1L;

	private Binder<ParentCategory> m_binder = new Binder<>(ParentCategory.class);

	public ParentCategoryDialog(ParentCategoryService service) {
		super(service);
		setHeaderTitle("Parent category");
	}

	@Override
	protected VerticalLayout createDialogLayout() {

		TextField nameField = new TextField("Name");
		TextField descriptionField = new TextField("Description");
		Checkbox checkbox = new Checkbox();
		checkbox.setLabel("Enable");

		Image preview = new Image();
		preview.setSrc("parent-category-icons/" + m_data.getIcon());
		System.out.println("parent-category-icons/" + m_data.getIcon());
		preview.setWidth("50px");

		ComboBox<String> iconBox = new ComboBox<>("Icon");
		iconBox.setItems(getAllIconFromLocal());
		iconBox.setRenderer(new ComponentRenderer<>(Image::new, (img, path) -> {
			img.setSrc("parent-category-icons/" + path);
			img.setWidth("50px");
		}));
		iconBox.addValueChangeListener(e -> {
			preview.setSrc("parent-category-icons/" + e.getValue());
		});

		IntegerField integerField = new IntegerField();
		integerField.setLabel("Position");
		integerField.setMin(0);
		integerField.setStepButtonsVisible(true);

		m_binder.forField(nameField).bind(ParentCategory::getName, ParentCategory::setName);
		m_binder.forField(descriptionField).bind(ParentCategory::getDescription, ParentCategory::setDescription);
		m_binder.forField(checkbox).bind(ParentCategory::isEnable, ParentCategory::setEnable);
		m_binder.forField(integerField).bind(ParentCategory::getPosition, ParentCategory::setPosition);
		m_binder.forField(iconBox).bind(ParentCategory::getIcon, ParentCategory::setIcon);

		m_binder.readBean(m_data);

		VerticalLayout dialogLayout = new VerticalLayout(nameField, preview, iconBox, descriptionField, checkbox,
				integerField);
		dialogLayout.setPadding(false);
		dialogLayout.setSpacing(false);
		dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		dialogLayout.setWidth("500px");

		if (m_data.getId() != null) {
			nameField.setValue(m_data.getName());
		}

		return dialogLayout;
	}

	private List<String> getAllIconFromLocal() {
		List<String> list = new ArrayList<>();
		URL res = getClass().getClassLoader().getResource("META-INF/resources/parent-category-icons");

		try {
			File folder = Paths.get(res.toURI()).toFile();
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					list.add(listOfFiles[i].getName());
				}
			}
		} catch (URISyntaxException e) {
			NotificationHelper.showError("Error: Can' get the icons");
		}

		return list;
	}

	@Override
	protected boolean saveData() {
		try {
			m_binder.writeBean(m_data);
			m_service.save(m_data);
		} catch (Exception e) {
			NotificationHelper.showError("Error: " + e.getMessage());
			return false;
		}
		fireEvent(new ChangeEvent(this, false));
		NotificationHelper.showSuccess("The parent category was saved successfully!");
		return true;
	}

}
