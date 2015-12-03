package konto;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;


public class MainApp extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout view = new VerticalLayout();
        view.addComponent(new Label("Hello Vaadin!"));
        setContent(view);
    }
}

