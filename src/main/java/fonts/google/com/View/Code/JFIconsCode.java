/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonts.google.com.View.Code;

import fonts.google.com.AbstractModels.ModeloComboString;
import fonts.google.com.Icon;
import fonts.google.com.View.JFIcons;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import org.apache.batik.transcoder.TranscoderException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author OIM
 */
public class JFIconsCode extends JFIcons {

    private GridLayout gridLayout;

    private ItemListener cboCatgoryItemListener;
    private ItemListener cboSubCatgoryItemListener;
//    private KeyAdapter txtFiltroKeyAdapter;
    private ActionListener btnBuscarActionListener;
    private WindowAdapter thisWindowAdapter;
    private ComponentAdapter paSpPaGridComponentAdapter;
    private Class<?> objClass;
    private Field[] lstFields;
    private List<String> lstCategorys;
    private List<String> lstSubCategorys;
    private List<Field> lstFieldFiltrar;
    private final ModeloComboString modCboString;
    private final ModeloComboString modCboString2;
    private final String ICONS = System.getProperty("user.dir") + "\\icons";
    private JDCargaCode dialog;
    private Thread tarea;
    private final String heightDefault = "height=\"20\"";
    private final String widthDefault = "width=\"20\"";
    private final String colorDefault = "fill=\"#000000\"";
    private final List<Integer> lstPixeles = Arrays.asList(new Integer[]{16, 20, 24, 32, 48, 64, 128, 256, 512});

    public JFIconsCode() {
        this.modCboString = new ModeloComboString();
        this.cboCategory.setModel(this.modCboString);
        this.modCboString2 = new ModeloComboString();
        this.cboSubCategory.setModel(this.modCboString2);
        this.gridLayout = new GridLayout(0, 3);
        this.ComponentConstructor();
    }

    private void ComponentConstructor() {
        this.paSpPaGrid.setLayout(this.gridLayout);
        this.setTitle("Material Design Icons Master");
    }

    public void Visualizar() {
        this.CreateEvents();
        this.CargarFields();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.RedimensionarGrid();
        this.AgregarEvents();
    }

    private void CreateEvents() {
        this.cboCatgoryItemListener = (ItemEvent e) -> {
//            FiltrarIconos();
            Esperar();
            CargarTarea();
        };

        this.cboSubCatgoryItemListener = (ItemEvent e) -> {
//            FiltrarIconos();
            Esperar();
            CargarTarea();
        };

//        this.txtFiltroKeyAdapter = new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
////                FiltrarIconos();
//                CargarTarea();
//            }
//        };
        this.btnBuscarActionListener = (ActionEvent e) -> {
            Esperar();
            CargarTarea();
        };

        this.paSpPaGridComponentAdapter = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                RedimensionarGrid();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                RedimensionarGrid();
            }

        };

        this.thisWindowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EliminarEvents();
                System.exit(0);
            }

            @Override
            public void windowOpened(WindowEvent e) {
//                CargarFields();
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                RedimensionarGrid();
            }

            @Override
            public void windowIconified(WindowEvent e) {
                RedimensionarGrid();
            }
        };
    }

    private void AgregarEvents() {
        this.cboCategory.addItemListener(this.cboCatgoryItemListener);
        this.cboSubCategory.addItemListener(this.cboSubCatgoryItemListener);
//        this.txtFiltro.addKeyListener(this.txtFiltroKeyAdapter);
        this.btnBuscar.addActionListener(this.btnBuscarActionListener);
        this.addComponentListener(this.paSpPaGridComponentAdapter);
        this.addWindowListener(this.thisWindowAdapter);
    }

    private void EliminarEvents() {
        this.cboCategory.removeItemListener(this.cboCatgoryItemListener);
        this.cboSubCategory.removeItemListener(this.cboSubCatgoryItemListener);
//        this.txtFiltro.removeKeyListener(this.txtFiltroKeyAdapter);
        this.btnBuscar.removeActionListener(this.btnBuscarActionListener);
        this.removeComponentListener(this.paSpPaGridComponentAdapter);
        this.removeWindowListener(this.thisWindowAdapter);
    }

    private void RedimensionarGrid() {
        int anchoFrame = this.getSize().width;
        int altoFrame = this.getSize().height;
        int anchoPanel = anchoFrame - 40;
        int altoPanel = altoFrame - 101;
//        int columns = Integer.valueOf(String.valueOf(((anchoFrame - 40) / 240.0)).split("\\.")[0]);
        double columnsDecimal = Double.valueOf(String.valueOf(anchoPanel)) / 330.0;
        int columnTrunc = Integer.valueOf(String.valueOf(columnsDecimal).split("\\.")[0]);
//        int columns = (columnsDecimal > columnTrunc) ? (columnTrunc - 1) : columnTrunc;
        int columns = columnTrunc;

        int items = this.lstFieldFiltrar != null ? this.lstFieldFiltrar.size() : 0;
        double itemXcolumnsDecimal = items / columns;
        int itemXcolumnsTrunc = Integer.valueOf(String.valueOf(itemXcolumnsDecimal).split("\\.")[0]);
        int rows = (itemXcolumnsDecimal > itemXcolumnsTrunc) ? (itemXcolumnsTrunc + 1) : itemXcolumnsTrunc;
        int alturaPanel = rows * 100;

//        System.out.println(" Altura MAX" + alturaPanel + " Ancho MAX" + anchoPanel + " Filas " + rows + " Columnas " + columns + " Items " + items);
        this.gridLayout.setColumns(columns);
        this.gridLayout.setHgap(0);
        this.gridLayout.setVgap(0);
//        this.paSpPaGrid.setPreferredSize(new Dimension(anchoPanel, alturaPanel));
        this.paSpPaGrid.setMaximumSize(new Dimension(anchoPanel, alturaPanel));
        this.spPaGrid.setMaximumSize(new Dimension(anchoPanel, altoPanel));
        this.gridLayout.layoutContainer(this.paSpPaGrid);
        this.paSpPaGrid.revalidate();
        this.paSpPaGrid.repaint();
        this.spPaGrid.revalidate();
        this.spPaGrid.repaint();
        //System.out.println("Dimension" + this.paSpPaGrid.size().toString());
//        this.CorreccionSizePanel(anchoPanel, alturaPanel, rows);
    }

    /*private void CorreccionSizePanel(int ancho, int alturaOld, int rows) {
        Component[] component = this.paSpPaGrid.getComponents();
        double alturaButton = 0.0;
        int alturaNew = 0;
        for (Component objComp : component) {
            if (objComp instanceof JButton) {
                if (((JButton) objComp).getSize().getHeight() > alturaButton) {
                    alturaButton = ((JButton) objComp).getSize().getHeight();
                }
            }
        }
        alturaNew = Integer.valueOf(String.valueOf(alturaButton * rows).split("\\.")[0]);
        if (alturaOld > alturaNew && alturaNew != 0) {
            //System.out.println("EJCUTADO Altura actual: " + alturaOld + " Altura nueva: " + (alturaButton * rows) + " Filas: " + rows);
//            this.paSpPaGrid.setPreferredSize(new Dimension(ancho, alturaNew + 30));
            this.paSpPaGrid.setMaximumSize(new Dimension(ancho, alturaNew + 10));
            this.gridLayout.layoutContainer(this.paSpPaGrid);
            this.paSpPaGrid.revalidate();
            this.paSpPaGrid.repaint();
            this.paGrid.revalidate();
            this.paGrid.repaint();
        }
        //System.out.println("Altura actual: " + alturaOld + " Altura nueva: " + (alturaButton * rows) + " Filas: " + rows);
        //System.out.println("======================================");
    }*/
    private void AddPanelIcons(String icon, int pos) {
        JSONObject json = new JSONObject(icon);
        String name = json.getString("name").trim().split("__")[1].toUpperCase().replaceAll("_", " ");
        String body = json.getString("body").trim();

        JButton btnIcon = new JButton();
        btnIcon.setText("<html>" + name + "<h5>N°" + pos + "</h5></html>");
        btnIcon.setIcon(new ImageIcon(this.getIcon(icon, new Dimension(80, 80), null).getAbsolutePath()));
        btnIcon.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIcon.setContentAreaFilled(false);

        btnIcon.addActionListener((ActionEvent e) -> {
            MouseEvent eventMouse = new MouseEvent(btnIcon, 0, e.getWhen(), KeyEvent.VK_UNDEFINED, 0, 0, 1, true);
            JPopupMenu menuOpciones = new JPopupMenu();
            JMenuItem mnuCopyName = new JMenuItem("Copy Name");
            mnuCopyName.setToolTipText("Copy Name");
            mnuCopyName.addActionListener((ActionEvent ex) -> {
//                System.out.println(name);
                copiarTexto(json.getString("name").trim());
            });
            menuOpciones.add(mnuCopyName);

            JMenu mnuCopyBody = new JMenu("Copy SVG");
            lstPixeles.forEach(pixel -> {
                JMenuItem mnuCopyBodypx = new JMenuItem(pixel + " px");
                mnuCopyBodypx.setToolTipText("Copy SVG " + pixel + " px");
                mnuCopyBodypx.addActionListener((ActionEvent ex) -> {
                    String bodyNew = reemplazarWidth(reemplazarHeight(body, pixel), pixel);
                    copiarTexto(bodyNew);
                });
                mnuCopyBody.add(mnuCopyBodypx);
            });

            menuOpciones.add(mnuCopyBody);
            menuOpciones.show(eventMouse.getComponent(), eventMouse.getX(), eventMouse.getY());
        });

        this.paSpPaGrid.add(btnIcon);
        this.paSpPaGrid.revalidate();
        this.paSpPaGrid.repaint();
        this.paGrid.revalidate();
        this.paGrid.repaint();
    }

    private void FiltrarIconos() {
        try {
            this.RedimensionarGrid();
            lstFieldFiltrar = Arrays.asList(this.lstFields);
            String filtro = this.txtFiltro.getText().trim();
            String category = this.modCboString.getSeleccionado();
            String subCategory = this.modCboString2.getSeleccionado();
            lstFieldFiltrar = lstFieldFiltrar.stream()
                    .filter(field -> category.trim().equalsIgnoreCase("--TODOS--") ? true
                    //                            : field.getName().trim().contains(category))
                    : like(field.getName().trim(), category.concat("%")))
                    //                    .filter(field -> field.getName().trim().contains(subCategory))
                    .filter(field -> like(field.getName().trim(), "%".concat(subCategory)))
                    //                    .filter(field -> field.getName().trim().toUpperCase().contains(filtro.trim().toUpperCase()))
                    .filter(field -> like( reurnSplitValue(field.getName().trim(),"__",1).toUpperCase(), "%".concat(filtro.trim().toUpperCase().replaceAll(" ", "%")).concat("%")))
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList());
            this.paSpPaGrid.removeAll();
            for (int i = 0; i < lstFieldFiltrar.size(); i++) {
//                String name = lstFieldFiltrar.get(i).getName().trim().split("__")[1].toUpperCase();
                String body = (String) lstFieldFiltrar.get(i).get(this.objClass);
                this.AddPanelIcons(body, lstFieldFiltrar.size() - i);
            }
            this.RedimensionarGrid();
        } catch (IllegalAccessException | IllegalArgumentException | JSONException e) {
            System.err.println("Error: " + e);
        }
    }

    private void CargarFields() {
        try {
            String ritaIcon = "fonts.google.com.icon.Icons";
            objClass = Class.forName(ritaIcon);
            this.lstFields = objClass.getDeclaredFields();
            this.ExtraerCategorys();
            this.CargarCategory();
            this.CargarSubCategory();
//            this.FiltrarIconos();
            this.CargarTarea();
        } catch (ClassNotFoundException | SecurityException e) {
            System.err.println("Error: " + e);
        }
    }

    private void ExtraerCategorys() {
        if (this.lstFields != null) {
            if (this.lstFields.length > 0) {
                this.lstCategorys = new ArrayList<>();
                this.lstSubCategorys = new ArrayList<>();
                this.lstCategorys.add("--TODOS--");
                for (Field lstField : this.lstFields) {
                    String[] nameFPart = lstField.getName().trim().split("__");
                    String nameF = nameFPart[0];
                    String nameSubF = nameFPart[nameFPart.length - 1];
                    if (this.lstCategorys.isEmpty()) {
                        this.lstCategorys.add(nameF);
                    } else if (this.lstCategorys.stream().filter(name -> name.trim().equalsIgnoreCase(nameF)).collect(Collectors.toList()).isEmpty()) {
                        this.lstCategorys.add(nameF);
                    }
                    if (this.lstSubCategorys.isEmpty()) {
                        this.lstSubCategorys.add(nameSubF);
                    } else if (this.lstSubCategorys.stream().filter(name -> name.trim().equalsIgnoreCase(nameSubF)).collect(Collectors.toList()).isEmpty()) {
                        this.lstSubCategorys.add(nameSubF);
                    }
                }
            }
        }
    }

    private void CargarCategory() {
        if (this.lstCategorys != null) {
            if (!this.lstCategorys.isEmpty()) {
                this.modCboString.setListaTipoProceso(this.lstCategorys);
                this.cboCategory.setSelectedIndex(0);
            }
        }
    }

    private void CargarSubCategory() {
        if (this.lstSubCategorys != null) {
            if (!this.lstSubCategorys.isEmpty()) {
                this.modCboString2.setListaTipoProceso(this.lstSubCategorys);
                this.cboSubCategory.setSelectedIndex(0);
            }
        }
    }

    private File getIcon(String icono, Dimension pixel, String color) {
        File icon = null;
        try {
            icon = new Icon(this.ICONS, icono, pixel, color).getIcon();
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Error: " + ex);
        } catch (NullPointerException | TranscoderException | JSONException | IOException ex) {
            System.err.println("Error: " + ex);
        }
//        if (icon == null) {
//            if ((new File(Global.iconsManual)).exists()) {
//                (new File(Global.iconsManual)).mkdirs();
//            }
//            icon = new File(Global.iconsManual + File.separator + iconoManual);
//        }
        return icon;
    }

    private void CargarTarea() {
        this.TareaGenerarIcons();
        this.dialog = new JDCargaCode(this, true);
        this.dialog.iniciar(this.tarea, "Cargando Icons...");
    }

    private void TareaGenerarIcons() {
        this.tarea = null;
        this.tarea = new Thread(() -> {
            this.EjecutarTarea();
        });
    }

    private void EjecutarTarea() {
        this.FiltrarIconos();
        this.dialog.finalizar();
        this.tarea.stop();
    }

    private void Esperar() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Error: " + e);
            }
        }).start();
    }

    private void copiarTexto(String myString) {
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }

    private String reemplazarHeight(String contenido, int newHeight) {
        return contenido.replaceFirst(heightDefault, "height=\"" + newHeight + "\"");
    }

    private String reemplazarWidth(String contenido, int newWidth) {
        return contenido.replaceFirst(widthDefault, "width=\"" + newWidth + "\"");
    }

    private String reemplazarColor(String contenido, String newColor) {
        return contenido.replaceFirst(colorDefault, "fill=\"" + newColor + "\"");
    }

    private boolean like(String valor, String busqueda) {
        Optional<String> busquedaOpc = Optional.ofNullable(busqueda);
        String busquedaAux = busquedaOpc.isPresent() ? busquedaOpc.get() : "";
        return Pattern.matches("^".concat(busquedaAux.replaceAll("%", ".*")).concat("$"), valor);
    }

    private String reurnSplitValue(String valor, String split, int pos) {
        Optional<String> valorOpc = Optional.ofNullable(valor);
        Optional<String> splitOpc = Optional.ofNullable(split);
        String[] valores = (valorOpc.isPresent() ? valorOpc.get() : "").split(splitOpc.isPresent() ? splitOpc.get() : "");
        return valores.length > pos ? valores[pos] : "";
    }
}
