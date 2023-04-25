/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonts.google.com;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Renzo Oscar Vega Gonzales | 74831526 | Perú
 */
public class Icon {

    private final String heightDefault = "height=\"20\"";
    private final String widthDefault = "width=\"20\"";
    private final String colorDefault = "fill=\"#000000\"";
    private final String extension = ".png";

    private String ruta;
    private String color;
    private String icono;
    private Dimension pixel;

    public Icon(String ruta, String icono, Dimension pixel) {
        this.ruta = ruta;
        this.icono = icono;
        this.pixel = pixel;
    }

    public Icon(String ruta, String icono, Dimension pixel, String color) {
        this.ruta = ruta;
        this.icono = icono;
        this.pixel = pixel;
        this.color = color;
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

    public File getIcon() throws FileNotFoundException, UnsupportedEncodingException, NullPointerException, IOException, TranscoderException, JSONException {
        File icon;
        OutputStream png_ostream;

        File directory = new File(this.ruta);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        JSONObject jsonIcon = new JSONObject(this.icono);
        String name = jsonIcon.getString("name");
        String svg = jsonIcon.getString("body");
        
        svg = this.reemplazarHeight(svg, this.pixel.height);
        svg = this.reemplazarWidth(svg, this.pixel.width);
        if(this.color!=null){
            svg = this.reemplazarColor(svg, this.color);
        }
        
        String pixelString = (this.pixel.width == this.pixel.height) ? this.pixel.width + "px" : this.pixel.width + "x" + this.pixel.height + "px";
        String nameIcon = name + "_" + (this.color != null ? this.color.replaceAll("#", "") : "000000") + "_" + pixelString;
        icon = new File(ruta + File.separator + nameIcon + extension);
        if (!icon.exists()) {
            String rutaSvg = ruta + File.separator + nameIcon + ".svg";
            try (PrintWriter write = new PrintWriter(rutaSvg, "UTF-8")) {
                write.print(svg);
            }

            //Step -1: We read the input SVG document into Transcoder Input
            //We use Java NIO for this purpose
            String svg_URI_input = Paths.get(rutaSvg).toUri().toURL().toString();
            TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);
            //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
            png_ostream = new FileOutputStream(icon.getAbsolutePath());
            TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
            // Step-3: Create PNGTranscoder and define hints if required
            PNGTranscoder my_converter = new PNGTranscoder();
            // Step-4: Convert and Write output
            my_converter.transcode(input_svg_image, output_png_image);
            // Step 5- close / flush Output Stream
            png_ostream.flush();
            png_ostream.close();
            (new File(rutaSvg)).delete();
        }
        return icon;
    }
}
