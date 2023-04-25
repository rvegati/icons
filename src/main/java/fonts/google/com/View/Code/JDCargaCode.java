/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonts.google.com.View.Code;

import fonts.google.com.View.JDCargar;
import java.awt.Frame;
import javax.swing.JLabel;

/**
 *
 * @author 4Rovg5
 */
public class JDCargaCode extends JDCargar {

    private Thread tarea;

    public JDCargaCode(Frame parent, boolean modal) {
        super(parent, modal);
    }

    public void iniciar(Thread tarea, String titulo) {
        this.setLocationRelativeTo(this);
        this.setTitle(titulo);
        Contador.start();
        this.tarea = tarea;
        this.setVisible(true);
    }

    public void finalizar() {
        this.dispose();
    }

    private void incrustarFondo(String texto) {
        this.lblImagenCarga.setText("<html><h1>" + texto + "</h1></html>");
        this.lblImagenCarga.setHorizontalAlignment(JLabel.CENTER);
        this.lblImagenCarga.setVerticalAlignment(JLabel.CENTER);
    }

    private void iniciarTarea() {
        this.tarea.start();
    }

    public void cambiarTitulo(String title) {
        this.setTitle((title != null) ? ((!title.isEmpty()) ? title : this.getTitle()) : this.getTitle());
    }

    Thread Contador = new Thread() {
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            int nroImagen = 0;
            int iniciarTarea = 0;
            for (;;) {
                try {
                    if (nroImagen == 12) {
                        nroImagen = 0;
                    }
                    switch (nroImagen) {
                        case 0:
                            incrustarFondo("<b>CARGANDO...<b>");
                            break;
                        case 1:
                            incrustarFondo("<b>C</b>argando...");
                            break;
                        case 2:
                            incrustarFondo("c<b>A</b>rgando...");
                            break;
                        case 3:
                            incrustarFondo("ca<b>R</b>gando...");
                            break;
                        case 4:
                            incrustarFondo("car<b>G</b>ando...");
                            break;
                        case 5:
                            incrustarFondo("carg<b>A</b>ndo...");
                            break;
                        case 6:
                            incrustarFondo("carga<b>N</b>do...");
                            break;
                        case 7:
                            incrustarFondo("cargan<b>D</b>o...");
                            break;
                        case 8:
                            incrustarFondo("cargand<b>O</b>...");
                            break;
                        case 9:
                            incrustarFondo("cargando<b>.</b>..");
                            break;
                        case 10:
                            incrustarFondo("cargando.<b>.</b>.");
                            break;
                        case 11:
                            incrustarFondo("cargando..<b>.</b>");
                            break;
                    }
                    if (iniciarTarea == 13) {
                        iniciarTarea();
                    }
                    nroImagen += 1;
                    iniciarTarea += 1;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("Error: " + e);
                }
            }

        }

    };

}
