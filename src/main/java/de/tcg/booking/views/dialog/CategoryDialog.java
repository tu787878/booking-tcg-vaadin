package de.tcg.booking.views.dialog;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import de.tcg.booking.entity.Category;
import de.tcg.booking.entity.ParentCategory;
import de.tcg.booking.service.CategoryService;
import de.tcg.booking.util.NotificationHelper;

public class CategoryDialog extends AbstractDialog<Category, CategoryService> {

	private static final long serialVersionUID = 1L;

	private Binder<Category> m_binder = new Binder<>(Category.class);

	public CategoryDialog(CategoryService service) {
		super(service);
		setHeaderTitle("Category");
	}

	@Override
	protected VerticalLayout createDialogLayout() {

		TextField nameField = new TextField("Name");
		TextField descriptionField = new TextField("Description");
		Checkbox checkbox = new Checkbox();
		checkbox.setLabel("Enable");

		ComboBox<ParentCategory> parentCombo = new ComboBox<>("Parent");
		parentCombo.setItems(m_service.findAllParents());
		parentCombo.setItemLabelGenerator(ParentCategory::getName);
		parentCombo.setVisible(true);

		IntegerField integerField = new IntegerField();
		integerField.setLabel("Position");
		integerField.setMin(0);
		integerField.setStepButtonsVisible(true);

		m_binder.forField(nameField).bind(Category::getName, Category::setName);
		m_binder.forField(descriptionField).bind(Category::getDescription, Category::setDescription);
		m_binder.forField(checkbox).bind(Category::isEnable, Category::setEnable);
		m_binder.forField(integerField).bind(Category::getPosition, Category::setPosition);
		m_binder.forField(parentCombo).bind(Category::getParentCategory, Category::setParentCategory);

		m_binder.readBean(m_data);

		VerticalLayout dialogLayout = new VerticalLayout(nameField, parentCombo, descriptionField, checkbox, integerField);
		dialogLayout.setPadding(false);
		dialogLayout.setSpacing(false);
		dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		dialogLayout.setWidth("500px");

		return dialogLayout;
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
		NotificationHelper.showSuccess("The category was saved successfully!");
		return true;
	}

}
