package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class EcoMenuBar {

    private final MenuBar menuBar;

    public EcoMenuBar() {
        menuBar = new MenuBar();
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void addMenu(final String menuName) {
        final Menu menu = new Menu(menuName);
        menuBar.getMenus().add(menu);
    }

    public void addMenuEntry(final String menuName, final String menuEntryName, final EventHandler<ActionEvent> eventHandler) {
        for (final Menu menu : menuBar.getMenus()) {
            if (menu.getText().equals(menuName)) {
                final MenuItem menuItem = new MenuItem(menuEntryName);
                menuItem.setOnAction(eventHandler);
                menu.getItems().add(menuItem);
            }
        }
    }

}
