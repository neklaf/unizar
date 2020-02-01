// ObjectDB for Java Demo - JDO Directory - A Brief JDO Tutorial
// Copyright (C) 2001-2003, ObjectDB Software. All rights reserved.

package directory.step4;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.jdo.*;
import directory.pc.*;

/**
 * The Manager class represents the main frame window.
 */
public class Manager extends JFrame {
    
    //--------------//
    // Data Members //
    //--------------//

    // Icons for the directory's PersistenceCapable classes:
    private ImageIcon imageCategory =
        new ImageIcon(getClass().getResource("image/category.gif"));
    private ImageIcon imageBook =
        new ImageIcon(getClass().getResource("image/book.gif"));
    private ImageIcon imageItem =
        new ImageIcon(getClass().getResource("image/link.gif"));
    private HashMap classToIcon = new HashMap(); {
        classToIcon.put(Category.class, imageCategory);
        classToIcon.put(Item.class, imageItem);
        classToIcon.put(Book.class, imageBook);
    } // maps Class instances to Icon instances

    /** Long term PersistenceManager instance (application scope) */
    private PersistenceManager pm;

    /** A JTree component for the directory hierarchy */
    private JTree tree = new JTree();
    
    /** The directory tree model (manages the tree data) */
    private DirectoryTreeModel treeModel = new DirectoryTreeModel();

    /** A JTable component for the property table */
    private JTable table = new JTable();

    /** The property table model (manages the table data) */
    private PropertyTableModel tableModel = new PropertyTableModel();

    /** Date format for formatting and parsing dates */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    //----------------//
    // Initialization //
    //----------------//
    
    /**
     * Constructs a Manager instance.
     */
    public Manager() {

        // Open the database connection:
        pm = directory.Utilities.getPersistenceManager(false);
        if (getChildElements(null).isEmpty())
            directory.step1.InitDB.insertObjects(pm);
                // Use Step 1 to fill the database when necessary

        // Set the title and the icon of the main frame window:
        setTitle("Directory Manager");
        setIconImage(new ImageIcon(
            getClass().getResource("image/manager.gif")).getImage());

        // Add the split pane with the tree and the table:
        JSplitPane jSplitPane = new JSplitPane();
        jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane.setDividerLocation(220);
        jSplitPane.setTopComponent(new JScrollPane(tree));
        jSplitPane.setBottomComponent(new JScrollPane(table));
        getContentPane().add(jSplitPane, BorderLayout.CENTER);

        // Add the button panel:
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        
        // Add the New buttons:
        JButton addCtgButton = new JButton("New Category", imageCategory);
        addCtgButton.addActionListener(new NewActionListener(Category.class));
        buttonPanel.add(addCtgButton);
        JButton addItemButton = new JButton("New Item", imageItem);
        addItemButton.addActionListener(new NewActionListener(Item.class));
        buttonPanel.add(addItemButton);
        JButton addBookButton = new JButton("New Book", imageBook);
        addBookButton.addActionListener(new NewActionListener(Book.class));
        buttonPanel.add(addBookButton);

        // Add the Delete button:
        JButton deleteButton = new JButton("Delete", 
            new ImageIcon(getClass().getResource("image/delete.gif")));
        deleteButton.addActionListener(new DeleteActionListener());
        buttonPanel.add(deleteButton);
        
        // Prepare the directory tree:
        tree.setModel(treeModel);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(
            TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.expandRow(0);
        tree.setCellRenderer( // just for adding icons to the tree
            new DefaultTreeCellRenderer() {
                public Component getTreeCellRendererComponent(JTree tree,
                        Object value, boolean sel, boolean expanded,
                        boolean leaf, int row, boolean hasFocus) {
                    JLabel label = (JLabel)super.getTreeCellRendererComponent(
                        tree, value, sel, expanded, leaf, row, hasFocus);
                    label.setIcon((Icon)classToIcon.get(value.getClass()));
                    return label;
                }
            }
        ); // end of renderer

        // Handler for DELETE key in the tree:
        tree.registerKeyboardAction(new DeleteActionListener(),
            KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        // Handler for selection change in the tree:
        tree.getSelectionModel().addTreeSelectionListener(
            new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    table.tableChanged(null);
                    initTableColumns();
                }
            }
        );
        
        // Prepare the property table:
        table.setModel(tableModel);
        table.getParent().setBackground(Color.white);
        initTableColumns();

        // Handler for closing the main frame window:
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                if (pm != null && !pm.isClosed())
                    pm.close(); // close the database connection
                System.exit(0);
            }
        });

        // Show the main frame window:
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((ss.width - 680) / 2,(ss.height - 440) / 2, 680, 440);
        show();
        tree.requestFocus();
    }

    /**
     * Initializes the width of the table columns.
     */
    private void initTableColumns() {
        TableColumn tableColumn = table.getColumnModel().getColumn(0);
        tableColumn.setMinWidth(120);
        tableColumn.setPreferredWidth(180);
        tableColumn.setMaxWidth(240);
    }

    //----------------//
    // Data Retrieval //
    //----------------//

    /**
     * Retrieves the children of a specified parent element.
     */
    private java.util.List getChildElements(Object parent) {
        // Handle parent elements:
        if (parent instanceof Category)
            return ((Category)parent).getElements();
        if (parent instanceof Item)
            return Collections.EMPTY_LIST;
        
        // Handle root retrival:
        Query query = pm.newQuery(Category.class, "parent == null");
        try {
            return new ArrayList((Collection)query.execute());
        }
        finally {
            query.closeAll();
        }
    }
    
    //-------------------------//
    // Button Action Listeners //
    //-------------------------//

    /**
     * Handler for the [New Category] [New Item] [New Book] buttons.
     */
    private class NewActionListener implements ActionListener {
        private Class cls; // one of the directory's persistence capable classes
        NewActionListener(Class cls) {
            this.cls = cls;
        }
        public void actionPerformed(ActionEvent evt) {
            try {
                // Get the selected (parent) path:
                TreePath path = tree.getSelectionPath();
                if (path == null)
                    path = tree.getPathForRow(0);
                Object selectedNode = path.getLastPathComponent();
                if (selectedNode instanceof Item) {
                    selectedNode = ((Item)selectedNode).getParent();
                    path = path.getParentPath();
                }
                else if (!(selectedNode instanceof Category))
                    if (cls != Category.class)
                        throw new RuntimeException(
                            "No selected parent category");

                // Update the database:
                pm.currentTransaction().begin();
                Element newElement = (Element)cls.newInstance();
                if (selectedNode instanceof Category)
                    newElement.setParent((Category)selectedNode);
                pm.makePersistent(newElement);
                pm.currentTransaction().commit();

                // Refresh the display:
                refreshTree(path);
                tree.setSelectionPath(path.pathByAddingChild(newElement));
            }
            catch (Exception x) {
                JOptionPane.showMessageDialog(Manager.this, x.getMessage(),
                    "New Element Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handler for the [Delete] button and the [Delete] key.
     */
    private class DeleteActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                // Get the selected element (for deletion):
                TreePath path = tree.getSelectionPath();
                if (path == null)
                    throw new RuntimeException("No selected object for delete");
                Element element = (Element)path.getLastPathComponent();
                if (element.getParent() == null)
                    throw new RuntimeException("Cannot delete a root category");
                int rowIx = tree.getRowForPath(path);

                // Update the database:
                pm.currentTransaction().begin();
                pm.deletePersistent(element);
                pm.currentTransaction().commit();

                // Refresh the display:
                TreePath parentPath = path.getParentPath();
                refreshTree(parentPath);
                tree.setSelectionRow(Math.min(rowIx, tree.getRowCount() - 1));
            }

            // Handle errors:
            catch (Exception x) {
                JOptionPane.showMessageDialog(Manager.this, x.getMessage(),
                    "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Updates the tree display after an Add / Delete operation.
     */
    private void refreshTree(TreePath path) {
        treeModel.fireTreeChanged(path);
        tree.expandPath(path);
        tree.setSelectionPath(path);
        tree.requestFocus();
    }

    //----------------------//
    // Directory Tree Model //
    //----------------------//

    /**
     * The DirectoryTreeModel inner class manages the directory tree data.
     */
    private class DirectoryTreeModel extends DefaultTreeModel {
        DirectoryTreeModel() {
            super(new DefaultMutableTreeNode("Directory"));
        }
        public int getChildCount(Object parent) {
            return getChildElements(parent).size();
        }
        public Object getChild(Object parent, int index) {
            return getChildElements(parent).get(index);
        }
        public boolean isLeaf(Object node) {
            return node instanceof Item;
        }
        public int getIndexOfChild(Object parent, Object child) {
            return getChildElements(parent).indexOf(child);
        }
        void fireTreeChanged(TreePath path) {
            fireTreeStructureChanged(this,new Object[] { path }, null, null);
        }
    }

    //----------------------//
    // Property Table Model //
    //----------------------//

    /**
     * The PropertyTableModel inner class manages the property table data.
     */
    private class PropertyTableModel extends AbstractTableModel {
        public int getRowCount() {
            return getFields().length;
        }
        public int getColumnCount() {
            return 2;
        }
        public String getColumnName(int columnIndex) {
            return (columnIndex == 0) ? "Field" : "Value";
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                Field field = getFields()[rowIndex];
                if (columnIndex == 0)
                    return field.getName();
                field.setAccessible(true);
                Object o = field.get(getSelectedElement());
                return (o instanceof Date) ? dateFormat.format((Date)o) : o;
            }
            catch (Exception x) {
                JOptionPane.showMessageDialog(Manager.this, x.getMessage(),
                    "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                return x.getMessage();
            }
        }
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                Field field = getFields()[rowIndex];
                try {
                    pm.currentTransaction().begin();
                    field.setAccessible(true);
                    Element element = getSelectedElement();
                    JDOHelper.makeDirty(element, field.getName());
                        // Direct reflection modification is not tracked
                        // automatically by the enhanced code...
                    Parser parser = (Parser)parsers.get(field.getType());
                    field.set(element, parser.parse(aValue.toString()));
                    pm.currentTransaction().commit();
                }
                catch (Exception x) {
                    JOptionPane.showMessageDialog(Manager.this, x.getMessage(),
                        "Edit Error", JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    if (pm.currentTransaction().isActive())
                        pm.currentTransaction().rollback();
                    refreshTree(tree.getSelectionPath());
                }
            }
        }
        
        /**
         * Gets the selected element in the tree.
         */
        private Element getSelectedElement() {
            TreePath path = tree.getSelectionPath();
            if (path != null)
                return (Element)path.getLastPathComponent();
            return null;
        }

        /**
         * Uses reflection to get all the fields of the selected element.
         */
        private Field[] getFields() {
            Element e = getSelectedElement();
            if (e == null)
                return new Field[0];
            ArrayList allFields = new ArrayList();
            Class cls = e.getClass();
            while (cls != Object.class) {
                Field[] fields = cls.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (!field.getName().startsWith("jdo"))
                        if (parsers.get(field.getType()) != null)
                            allFields.add(fields[i]);
                }
                cls = cls.getSuperclass();
            }
            Collections.sort(allFields, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Field)o1).getName().compareTo(
                        ((Field)o2).getName());
                }
            });
            return (Field[])allFields.toArray(new Field[allFields.size()]);
        }
    }
    
    // Parsers for the supported types in the property table:
    interface Parser {
        Object parse(String str) throws ParseException;
    }
    private HashMap parsers = new HashMap();
    {
        parsers.put(String.class, new Parser() {
            public Object parse(String str) throws ParseException {
                return str;
            }
        });
        parsers.put(Date.class, new Parser() {
            public Object parse(String str) throws ParseException {
                return dateFormat.parse(str);
            }
        });
        parsers.put(int.class, new Parser() {
            public Object parse(String str) throws ParseException {
                return new Integer(str);
            }
        });
    }
}
