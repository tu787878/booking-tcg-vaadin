package de.tcg.booking.views.services;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.tcg.booking.service.CategoryService;
import de.tcg.booking.service.ParentCategoryService;
import de.tcg.booking.service.ServiceService;
import de.tcg.booking.views.MainLayout;
import de.tcg.booking.views.grid.CategoryGrid;
import de.tcg.booking.views.grid.ParentCategoryGrid;
import de.tcg.booking.views.grid.ServiceGrid;
import jakarta.annotation.security.PermitAll;

@PageTitle("Services")
@Route(value = "service", layout = MainLayout.class)
@PermitAll
public class ServicesView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private Tab m_parentCategory;
	private Tab m_category;
	private Tab m_service;
	private VerticalLayout m_content;
	private Span m_parentCount;
	private Span m_categoryCount;
	private Span m_serviceCount;
	private Tabs m_tabs;

	private final ParentCategoryService m_parentCategoryService;
	private final CategoryService m_categoryService;
	private final ServiceService m_serviceService;

	public ServicesView(ParentCategoryService parent, CategoryService category, ServiceService service) {

		m_parentCategoryService = parent;
		m_categoryService = category;
		m_serviceService = service;

		setSpacing(false);

//        Image img = new Image("images/empty-plant.png", "placeholder plant");

		m_parentCount = createBadge(0);
		m_categoryCount = createBadge(0);
		m_serviceCount = createBadge(0);

		m_parentCategory = new Tab(new Span("Parent categories"), m_parentCount);
		m_category = new Tab(new Span("Categories"), m_categoryCount);
		m_service = new Tab(new Span("Services"), m_serviceCount);

		m_tabs = new Tabs(m_parentCategory, m_category, m_service);
		m_tabs.addSelectedChangeListener(event -> setContent(event.getSelectedTab()));

		m_content = new VerticalLayout();
		m_content.setSpacing(false);
		m_content.setSizeFull();
		setContent(m_tabs.getSelectedTab());

		add(m_tabs, m_content);
		setSizeFull();
	}

	private Span createBadge(int value) {
		Span badge = new Span(String.valueOf(value));
		badge.getElement().getThemeList().add("badge small contrast");
		badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
		return badge;
	}

	private void setContent(Tab tab) {
		m_parentCount.setText(String.valueOf(m_parentCategoryService.count()));
		m_categoryCount.setText(String.valueOf(m_categoryService.count()));
		m_serviceCount.setText(String.valueOf(m_serviceService.count()));

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
		ParentCategoryGrid grid = new ParentCategoryGrid(m_parentCategoryService);
		m_content.add(grid.getAddButton(), grid.getGrid());
	}

	private void buildCategoryContent() {
		CategoryGrid grid = new CategoryGrid(m_categoryService);
		m_content.add(grid.getAddButton(), grid.getGrid());
	}

	private void buildServiceContent() {
		ServiceGrid grid = new ServiceGrid(m_serviceService);
		m_content.add(grid.getAddButton(), grid.getGrid());
	}
}
