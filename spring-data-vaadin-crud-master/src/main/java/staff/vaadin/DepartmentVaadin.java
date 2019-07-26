package staff.vaadin;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.grid.MGrid;
import staff.backend.model.Department;
import staff.backend.repositories.DepartmentRepo;

import java.util.ArrayList;
import java.util.List;

public class DepartmentVaadin extends VerticalLayout implements View{
    private static final long serialVersionUID = 1L;
    public static final String NAME = "Main";

    DepartmentRepo repo;
    DepartmentForm personForm;
    EventBus.UIEventBus eventBus;
    private Grid<Department> grid = new Grid<>();
    private MGrid<Department> list;
    private MTextField filterByName = new MTextField()
            .withPlaceholder("Filter by name");
    private Button addNew;
    private Button edit;


    public DepartmentVaadin(){
        initDepartmentVaadin();
    }

    private void initDepartmentVaadin() {
        grid.addColumn(Department::getId).setCaption("ID");
        grid.addColumn(Department::getName).setCaption("Name");

        addNew = new MButton(VaadinIcons.PLUS);
        ((MButton) addNew).addClickListener(new AddListener());

        edit = new MButton(VaadinIcons.PENCIL);
        ((MButton) edit).addClickListener(new EditListener());

        addComponent(filterByName);
        addComponent(addNew);
        addComponent(edit);
        addComponent(grid);


    }

    class AddListener implements MButton.MClickListener {
        @Override
        public void onClick() {
            edit(new Department());
        }
    }

    class EditListener implements MButton.MClickListener{
        @Override
        public void onClick(){
            edit(list.asSingleSelect().getValue());
        }
    }

    protected void edit(final Department phoneBookEntry) {
        personForm.setEntity(phoneBookEntry);
        personForm.openInModalPopup();
    }
}
