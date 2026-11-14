package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Menu extends JMenuBar {

    public void addJMenu(String name) {
        add(new JMenu(name));
    }

    public void addJMenuItem(String parentName, String itemName) {
        JComponent component = getComponentByName(parentName);

        if (component instanceof JMenu) {
            JMenu parent = (JMenu) component;
            parent.add(new JMenuItem(itemName));
        }
    }

    public void addJMenuItem(String parentName, String itemName,
            ActionListener al) {
        JComponent component = getComponentByName(parentName);

        if (component instanceof JMenu) {
            JMenuItem item = new JMenuItem(itemName);
            item.addActionListener(al);

            JMenu parent = (JMenu) component;
            parent.add(item);
        }
    }

    public void addJMenuItem(String parentName, String itemName,
            ActionListener al, KeyStroke keyStroke) {
        JComponent component = getComponentByName(parentName);

        if (component instanceof JMenu) {
            JMenuItem item = new JMenuItem(itemName);
            item.addActionListener(al);
            item.setAccelerator(keyStroke);

            JMenu parent = (JMenu) component;
            parent.add(item);
        }
    }

    public void addSubJMenu(String parentName, String subMenuName) {
        JComponent component = getComponentByName(parentName);

        if (component instanceof JMenu) {
            JMenu parent = (JMenu) component;
            parent.add(new JMenu(subMenuName));
        }
    }

    public JMenu getJMenu(int index) {
        return getMenu(index);
    }

    private JComponent getComponentByName(String name) {

        for (Component component : getComponents()) {

            JMenu menu = (JMenu) component;

            if (menu.getText().equals(name)) {
                return menu;
            }

            for (Component child : menu.getMenuComponents()) {

                if (child instanceof JMenuItem) {
                    JMenuItem menuItem = (JMenuItem) child;

                    if (menuItem.getText().equals(name)) {
                        return menuItem;
                    }
                }
            }
        }

        return null;
    }
}
