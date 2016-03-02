/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author thiago
 */
@ManagedBean(name = "templateController")
@ApplicationScoped
public class TemplateController extends BaseController {

    private String contactTemplate;    
    
    public TemplateController() {
       
    }


    public String readTemplate(String path) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String absolutePath = servletContext.getRealPath(path);
        byte[] encoded = Files.readAllBytes(Paths.get(absolutePath));
        String template = new String(encoded, StandardCharsets.UTF_8);
        return template;
    }

    public String fillContactTemplate(String title, String subtitle, String content) {
        try {
            if(contactTemplate == null)
            contactTemplate = readTemplate("/resources/default/templates/contact-template.html");
        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String template = contactTemplate;
        if (title != null) {
            template = template.replace("#title#", title);
        }
        if (subtitle != null) {
            template = template.replace("#subtitle#", subtitle);
        }
        if (content != null) {
            template = template.replace("#content#", content);
        }
        return template;

    }


}

