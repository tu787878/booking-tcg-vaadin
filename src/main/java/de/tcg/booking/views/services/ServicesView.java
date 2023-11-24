package de.tcg.booking.views.services;

import java.util.List;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.tcg.booking.entity.Category;
import de.tcg.booking.entity.ParentCategory;
import de.tcg.booking.entity.Service;
import de.tcg.booking.service.CategoryService;
import de.tcg.booking.service.ParentCategoryService;
import de.tcg.booking.service.ServiceService;
import de.tcg.booking.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Services")
@Route(value = "service", layout = MainLayout.class)
@PermitAll
public class ServicesView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final Tab m_parentCategory;
	private final Tab m_category;
	private final Tab m_service;
	private final VerticalLayout m_content;

	private final ParentCategoryService m_parentCategoryService;
	private final CategoryService m_categoryService;
	private final ServiceService m_serviceService;

	public ServicesView(ParentCategoryService parent, CategoryService category, ServiceService service) {

		m_parentCategoryService = parent;
		m_categoryService = category;
		m_serviceService = service;

		setSpacing(false);

//        Image img = new Image("images/empty-plant.png", "placeholder plant");
		
		List<ParentCategory> parentCategories = m_parentCategoryService.findAll();
		List<Category> categories = m_categoryService.findAll();
		List<Service> services = m_serviceService.findAll();

		m_parentCategory = new Tab(new Span("Parent categories"), createBadge(parentCategories.size()));
		m_category = new Tab(new Span("Categories"), createBadge(categories.size()));
		m_service = new Tab(new Span("Services"), createBadge(services.size()));

		Tabs tabs = new Tabs(m_parentCategory, m_category, m_service);
		tabs.addSelectedChangeListener(event -> setContent(event.getSelectedTab()));

		m_content = new VerticalLayout();
		m_content.setSpacing(false);
		setContent(tabs.getSelectedTab());

		add(tabs, m_content);
		setSizeFull();
	}

	private Span createBadge(int value) {
		Span badge = new Span(String.valueOf(value));
		badge.getElement().getThemeList().add("badge small contrast");
		badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
		return badge;
	}

	private void setContent(Tab tab) {
		m_content.removeAll();

		if (tab.equals(m_parentCategory)) {
			buildParentCategoryContent();
		} else if (tab.equals(m_category)) {
			buildCategoryContent();
		} else {
			buildServiceContent();
		}
	}

	private void buildParentCategoryContent() {
		List<ParentCategory> parentCategories = m_parentCategoryService.findAll();
	}

	private void buildCategoryContent() {
		List<Category> categories = m_categoryService.findAll();
	}

	private void buildServiceContent() {
		List<Service> services = m_serviceService.findAll();
	}
}
