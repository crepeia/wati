/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import wati.model.Acompanhamento;
import wati.model.User;
import wati.persistence.GenericDAO;
import wati.utility.EMailSSL;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "parouDeFumarController")
@SessionScoped
public class ParouDeFumarController extends BaseController<Acompanhamento> {

    private static final int LIMITE_IGUALDADE_HORAS = 24;
    private String relapse;
    private Acompanhamento acompanhamento;

    private StreamedContent lapsoRecaida;

    /**
     * Creates a new instance of ParouDeFumarController
     */
    public ParouDeFumarController() {
        //super(Acompanhamento.class);
        try {
            this.daoBase = new GenericDAO<>(Acompanhamento.class);
        } catch (NamingException ex) {
            String message = this.getText("message.error");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public String LapseOrRelapse() {

        Acompanhamento a = this.getAcompanhamento();
        //a.setRecaida(this.recaida.equals("1"));
        try {

            this.getDaoBase().insertOrUpdate(a, this.getEntityManager());

            if (a.getRecaida() == 1) {
                return "parou-de-fumar-acompanhamento-recaidas-identificar-motivos.xhtml?faces-redirect=true";
            } else if (a.getRecaida() == 0) {
                return "parou-de-fumar-acompanhamento-lapso.xhtml?faces-redirect=true";
                //"parou-de-fumar-acompanhamento-lapso-identificar-fatores-recaida.xhtml?faces-redirect=true";
            } else if (a.getRecaida() == 2) {
                return "parou-de-fumar-acompanhamento-estou-sem-fumar.xhtml?faces-redirect=true";
            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String identifyReasons() {

        Acompanhamento a = this.getAcompanhamento();

        try {

            this.getDaoBase().insertOrUpdate(a, this.getEntityManager());
            return "parou-de-fumar-acompanhamento-lapso-identificar-fatores-recaida.xhtml?faces-redirect=true";

        } catch (SQLException ex) {
            Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public String identifyRelapseFactors() {

        Acompanhamento a = this.getAcompanhamento();

        try {

            this.getDaoBase().insertOrUpdate(a, this.getEntityManager());
            if (isRelapseDeal11() || isRelapseDeal22() || isRelapseDeal33() || isRelapseSituation11() || isRelapseSituation22() || isRelapseSituation33()) {
                return "parou-de-fumar-acompanhamento-lapso-plano-evitar-recaida.xhtml?faces-redirect=true";
            } else {
                return "parou-de-fumar-acompanhamento-lapso-plano-evitar-recaida-padrao.xhtml?faces-redirect=true";
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public void enviarEmail() {

        Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");

        if (object == null) {

            Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, this.getText("user.not.logged"));
            //message to the user
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.getText("deve.estar.logado"), null));

        } else {

            User user = (User) object;
            Acompanhamento a = this.getAcompanhamento();

            try {
                String from = "contato@vivasemtabaco.com.br";
                String to = user.getEmail();
                String subject = this.getText("plano.wati");
                
                String html;
                String text;
                ByteArrayOutputStream pdf;
                
                if(this.isCompletedPlan()){
                    html = this.defaultEmail(this.getText("plano.personalizado"), this.planoToHTML(user));
                    text = this.planoToText(user);
                    pdf = this.generatePdf2();
                }else{
                    html = this.defaultEmail(this.getText("plano.personalizado"), this.planoPadraoToHTML(user));
                    text = this.planoPadraoToText(user);
                    pdf = this.generateStandardPdf();
                }
                EMailSSL eMailSSL = new EMailSSL();
                
                eMailSSL.send(from, to, subject, text, html, pdf);
                
                String message = "Relapse plan sent to " + user.getEmail();
                Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.INFO, message);
                //message to the user
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.getText("email.enviado"), null));

            } catch (Exception ex) {


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.getText("problemas.enviar.email2"), null));

            }

        }
    }

  
    public void setRelapse(String relapse) {
        this.relapse = relapse;
    }

    public boolean riskSituations() {
        return isRelapseSituation11() || isRelapseSituation22() || isRelapseSituation33();
    }

    public boolean howFace() {
        return isRelapseDeal11() || isRelapseDeal22() || isRelapseDeal33();
    }

    public boolean  isRelapseSituation11() {
        return getAcompanhamento().getRecaidaSituacao1() != null && !getAcompanhamento().getRecaidaSituacao1().trim().equals("");
    }

    public boolean isRelapseSituation22() {
        return acompanhamento.getRecaidaSituacao2() != null && !acompanhamento.getRecaidaSituacao2().trim().equals("");
    }

    public boolean isRelapseSituation33() {
        return acompanhamento.getRecaidaSituacao3() != null && !acompanhamento.getRecaidaSituacao3().trim().equals("");
    }

    public boolean isRelapseDeal11() {
        return acompanhamento.getRecaidaLidar1() != null && !acompanhamento.getRecaidaLidar1().trim().equals("");
    }

    public boolean isRelapseDeal22() {
        return this.getAcompanhamento().getRecaidaLidar2() != null && !acompanhamento.getRecaidaLidar2().trim().equals("");
    }

    public boolean isRelapseDeal33() {
        return acompanhamento.getRecaidaLidar3() != null && !acompanhamento.getRecaidaLidar3().trim().equals("");
    }

    public Acompanhamento getAcompanhamento() {

        if (this.acompanhamento == null) {

            GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
            gc.add(GregorianCalendar.HOUR, ParouDeFumarController.LIMITE_IGUALDADE_HORAS);

            //System.out.println("Dia: " + gc.get( GregorianCalendar.DAY_OF_MONTH ));
            //System.out.println("Hora: " + gc.get( GregorianCalendar.HOUR_OF_DAY ));
            Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");
            if (object != null) {
                try {
                    //System.out.println( "inject: " + this.loginController.getUser().toString() );
                    //List<ProntoParaParar> ppps = this.getDaoBase().list("usuario.id", ((User)object).getId(), this.getEntityManager());
                    List<Acompanhamento> as = this.getDaoBase().list("usuario", object, this.getEntityManager());

                    for (Acompanhamento a : as) {

                        //System.out.println("Verificando acompanhamento: " + a.getId());
                        GregorianCalendar calendar = new GregorianCalendar();
                        calendar.setTime(a.getDataInserido());
                        if (gc.after(calendar)) {
                            this.acompanhamento = a;
                            //System.out.println("Acompanhamento continua.");
                        }

                    }

                    if (this.acompanhamento == null) {

                        this.acompanhamento = new Acompanhamento();
                        this.acompanhamento.setUsuario((User) object);

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } 

        }

        return this.acompanhamento;

    }

    public String planoToText(User user) {
        String text = this.getText("dear") + " " + user.getName() + ",\n"
        + "\n" + this.getText("plano.email") + "\n";
        if (this.riskSituations()) {
            text += "\n" + this.getText("acompanhamento.plano.h2.1") + "\n"
                    + this.getAcompanhamento().getRecaidaSituacao1() + "\n"
                    + this.getAcompanhamento().getRecaidaSituacao2() + "\n"
                    + this.getAcompanhamento().getRecaidaSituacao3() + "\n";
        }
        if (this.howFace()) {
            text += "\n" + this.getText("acompanhamento.plano.h2.1") + "\n"
                    + this.getAcompanhamento().getRecaidaLidar1() + "\n"
                    + this.getAcompanhamento().getRecaidaLidar2() + "\n"
                    + this.getAcompanhamento().getRecaidaLidar3() + "\n";

        }
        text += "\n" + this.getText("att");
        return text;
    }
    
    public String planoToHTML(User user){
        String html = this.getText("dear") + " " + user.getName() + ",<br>"
        + "<br>" + this.getText("plano.email") + "<br>";
        if (this.riskSituations()) {
            html += "<br>" + this.getText("acompanhamento.plano.h2.1") + "<br>"
                    + this.getAcompanhamento().getRecaidaSituacao1() + "<br>"
                    + this.getAcompanhamento().getRecaidaSituacao2() + "<br>"
                    + this.getAcompanhamento().getRecaidaSituacao3() + "<br>";
        }
        if (this.howFace()) {
            html += "<br>" + this.getText("acompanhamento.plano.h2.1") + "<br>"
                    + this.getAcompanhamento().getRecaidaLidar1() + "<br>"
                    + this.getAcompanhamento().getRecaidaLidar2() + "<br>"
                    + this.getAcompanhamento().getRecaidaLidar3() + "<br>";

        }
        html += "<br>" + this.getText("att");
        return html;
    }

    public ByteArrayOutputStream generatePdf2() {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, os);

            document.open();

            document.addTitle(this.getText("meu.plano"));
            document.addAuthor("vivasemtabaco.com.br");

            URL url;
            url = FacesContext.getCurrentInstance().getExternalContext().getResource("/resources/default/images/viva-sem-tabaco-new.png");

            Image img;
            img = Image.getInstance(url);
            img.setAlignment(Element.ALIGN_CENTER);
            img.scaleToFit(75, 75);
            document.add(img);

            Color color = Color.getHSBColor(214, 81, 46);
            Font f1 = new Font(FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);
            f1.setColor(22, 63, 117);
            Font f2 = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE);
            f2.setColor(22, 63, 117);
            Font f3 = new Font(FontFamily.HELVETICA, 11);

            Paragraph paragraph = new Paragraph(this.getText("meu.plano"), f1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            if (this.riskSituations()) {
                paragraph = new Paragraph(this.getText("acompanhamento.plano.h2.1"), f2);
                document.add(paragraph);
                paragraph = new Paragraph(this.getAcompanhamento().getRecaidaSituacao1(), f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.getAcompanhamento().getRecaidaSituacao2(), f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.getAcompanhamento().getRecaidaSituacao3(), f3);
                document.add(paragraph);
                document.add(Chunk.NEWLINE);
            } else {
                paragraph.add(new Paragraph(" "));
            }
            //paragraph.add(new Paragraph(" "));

            if (this.howFace()) {
                paragraph = new Paragraph(this.getText("acompanhamento.plano.h2.1"), f2);
                document.add(paragraph);
                paragraph = new Paragraph(this.getAcompanhamento().getRecaidaLidar1(), f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.getAcompanhamento().getRecaidaLidar2(), f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.getAcompanhamento().getRecaidaLidar3(), f3);
                document.add(paragraph);
            } else {
                paragraph.add(new Paragraph(" "));
            }

            document.close();

            return os;
        } catch (DocumentException ex) {
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }

    }

    public StreamedContent getLapsoRecaida() {

        InputStream is;
        try {
            is = new ByteArrayInputStream(generatePdf2().toByteArray());
            return new DefaultStreamedContent(is, "application/pdf", "plano.pdf");

        } catch (Exception e) {
            Logger.getLogger(ParouDeFumarController.class
                    .getName()).log(Level.SEVERE, this.getText("erro.pdf"));
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return null;
        }

    }

    public String planoPadraoToText(User user) {
        String text =  this.getText("dear") + " " + user.getName() + ",\n" 
                + "\n" + this.getText("plano.email") + "\n"
                + "\n" + this.getText("acompanhamento.plano.padrao.p.1") + "\n"
                + "\n" + this.getText("acompanhamento.plano.padrao.h2.1") + "\n"
                + this.getText("lembre.se2") + "\n"
                + this.getText("lembre.se3") + "\n"
                + "\n" + this.getText("acompanhamento.plano.padrao.h2.2") + "\n"
                + this.getText("recomendamos1") + "\n"
                + this.getText("recomendamos2") + "\n"
                + this.getText("recomendamos3") + "\n"
                + "\n" + this.getText("att");
        return text;
    }

    public String planoPadraoToHTML(User user) {
        String html = this.getText("dear") + " " + user.getName() + ",<br>" 
                + "<br>" + this.getText("plano.email") + "<br>"
                + "<br>" + this.getText("acompanhamento.plano.padrao.p.1") + "<br>"
                + "<br>" + this.getText("acompanhamento.plano.padrao.h2.1") + "<br>"
                + this.getText("lembre.se2") + "<br>"
                + this.getText("lembre.se3") + "<br>"
                + "<br>" + this.getText("acompanhamento.plano.padrao.h2.2") + "<br>"
                + this.getText("recomendamos1") + "<br>"
                + this.getText("recomendamos2") + "<br>"
                + this.getText("recomendamos3") + "<br>"
                + "<br>" + this.getText("att");
        return html;
    }

    public ByteArrayOutputStream generateStandardPdf() {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, os);

            document.open();

            document.addTitle(this.getText("recaida"));
            document.addAuthor("vivasemtabaco.com.br");

            URL url;
            url = FacesContext.getCurrentInstance().getExternalContext().getResource("/resources/default/images/viva-sem-tabaco-new.png");

            Image img;
            img = Image.getInstance(url);
            img.setAlignment(Element.ALIGN_CENTER);
            img.scaleToFit(75, 75);
            document.add(img);

            Color color = Color.getHSBColor(214, 81, 46);
            Font f1 = new Font(FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);
            f1.setColor(22, 63, 117);
            Font f2 = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE);
            f2.setColor(22, 63, 117);
            Font f3 = new Font(FontFamily.HELVETICA, 11);

            Paragraph paragraph = new Paragraph(this.getText("meu.plano"), f1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            paragraph = new Paragraph(this.getText("acompanhamento.plano.padrao.h2.1"), f2);
            document.add(paragraph);
            paragraph = new Paragraph(this.getText("lembre.se2"), f3);
            document.add(paragraph);
            paragraph = new Paragraph(this.getText("lembre.se3"), f3);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);

            paragraph = new Paragraph(this.getText("acompanhamento.plano.padrao.h2.2"), f2);
            document.add(paragraph);
            paragraph = new Paragraph(this.getText("recomendamos1"), f3);
            document.add(paragraph);
            paragraph = new Paragraph(this.getText("recomendamos2"), f3);
            document.add(paragraph);
            paragraph = new Paragraph(this.getText("recomendamos3"), f3);
            document.add(paragraph);

            document.close();

            return os;
        } catch (DocumentException ex) {
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }

    }

    public StreamedContent getRecaidaPadrao() {

        InputStream is;
        try {
            is = new ByteArrayInputStream(generateStandardPdf().toByteArray());
            return new DefaultStreamedContent(is, "application/pdf", "plano.pdf");

        } catch (Exception e) {
            Logger.getLogger(ParouDeFumarController.class
                    .getName()).log(Level.SEVERE, this.getText("erro.pdf"));
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            return null;
        }

    }
    
    public boolean isCompletedPlan(){
        if (isRelapseDeal11() || isRelapseDeal22() || isRelapseDeal33() || isRelapseSituation11() || isRelapseSituation22() || isRelapseSituation33()){
            return true;
        }else{
            return false;
        }
    }
    
    public void saveQuitMessage(){
        Acompanhamento a = this.getAcompanhamento();
        try {
            this.getDaoBase().insertOrUpdate(a, this.getEntityManager());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Thank you!", "Your message has been saved."));
        } catch (SQLException ex) {
            Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
