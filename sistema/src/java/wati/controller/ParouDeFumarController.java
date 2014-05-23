/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import org.primefaces.model.StreamedContent;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.primefaces.model.DefaultStreamedContent;





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
import wati.model.ProntoParaParar;
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
	private String recaida;
	private Acompanhamento acompanhamento;
        
       // private StreamedContent lapsoReacaida;
        

	/**
	 * Creates a new instance of ParouDeFumarController
	 */
	public ParouDeFumarController() {
		//super(Acompanhamento.class);
		try {
			this.daoBase = new GenericDAO<Acompanhamento>(Acompanhamento.class);
		} catch (NamingException ex) {
			String message = "Ocorreu um erro inesperado.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
			Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}

	}

	public String recaidaOuLapso() {

		Acompanhamento a = this.getAcompanhamento();
		a.setRecaida(this.recaida.equals("1"));
		try {

			this.getDaoBase().insertOrUpdate(a, this.getEntityManager());

			if (a.isRecaida()) {
				return "parou-de-fumar-acompanhamento-recaidas-identificar-motivos.xhtml";
			} else {
				return "parou-de-fumar-acompanhamento-lapso-identificar-fatores-recaida.xhtml";
			}


		} catch (SQLException ex) {
			Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;

	}

	public String identificarMotivos() {

		Acompanhamento a = this.getAcompanhamento();

		try {

			this.getDaoBase().insertOrUpdate(a, this.getEntityManager());

			return "parou-de-fumar-acompanhamento-lapso-identificar-fatores-recaida.xhtml";

		} catch (SQLException ex) {
			Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;

	}

	public String identificarFatoresRecaida() {

		Acompanhamento a = this.getAcompanhamento();

		try {

			this.getDaoBase().insertOrUpdate(a, this.getEntityManager());

			return "parou-de-fumar-acompanhamento-lapso-plano-evitar-recaida.xhtml";

		} catch (SQLException ex) {
			Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;

	}

	public void enviarEmail() {

		Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");

		if (object == null) {

			Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, "Usuário não logado no sistema requerendo plano.");
			//message to the user
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Você deve estar logado no sistema para solitar o envio do e-mail.", null));

		} else {

			User user = (User) object;
			Acompanhamento a = this.getAcompanhamento();

			try {
				String from = "watiufjf@gmail.com";
				String to = user.getEmail();
				String subject = "Plano para evitar recaídas -- Wati";
				String message = "Prezado " + user.getName() + ",\n\n"
						+ "Segue abaixo seu plano para evitar recaídas:\n\n"
						+ "\nEstratégias para lidar com a recaída:\n";
				if (a.getRecaidaLidar1() != null && !a.getRecaidaLidar1().isEmpty()) {
					message += a.getRecaidaLidar1() + "\n";
				}
				if (a.getRecaidaLidar2() != null && !a.getRecaidaLidar2().isEmpty()) {
					message += a.getRecaidaLidar2() + "\n";
				}
				if (a.getRecaidaLidar3() != null && !a.getRecaidaLidar3().isEmpty()) {
					message += a.getRecaidaLidar3() + "\n";
				}
				message += "\n\nAtenciosamente.\n";

				EMailSSL eMailSSL = new EMailSSL();
				eMailSSL.send(from, to, subject, message);

				Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.INFO, "Plano para evitar recaídas enviado para o e-mail " + user.getEmail() + ".");
				//message to the user
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado com sucesso.", null));

			} catch (Exception ex) {

				Logger.getLogger(ParouDeFumarController.class.getName()).log(Level.SEVERE, "Problemas ao enviar e-mail para " + user.getEmail() + ".");
				//message to the user
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao enviar e-mail. Por favor, tente novamente mais tarde.", null));

			}

		}
	}

	/**
	 * @return the recaida
	 */
	public String getRecaida() {

		Acompanhamento a = this.getAcompanhamento();

		if (a.isRecaida()) {
			return "1";
		} else {
			return "0";
		}

	}

	/**
	 * @param recaida the recaida to set
	 */
	public void setRecaida(String recaida) {
		this.recaida = recaida;
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

			} else {
				Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, "Usuário não logado sendo acompanhado.");
				this.acompanhamento = new Acompanhamento();
			}

		}

		return this.acompanhamento;

	}
        
        
        
       /* 
        public ByteArrayOutputStream gerarPdf2() {
            try{
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                
                Document document = new Document();
                PdfWriter.getInstance(document, os);
                document.open();

                document.addTitle("Recaída ou Lapso");
                document.addAuthor("vivasemtabaco.com.br");
                
                URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/resources/default/images/viva-sem-tabaco-big.png");
                Image img = Image.getInstance(url);
                img.setAlignment(Element.ALIGN_CENTER);
                img.scaleToFit(300, 300);
                document.add(img);
                
                Color color = Color.getHSBColor(214, 81, 46);
                Font f1 = new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE);
                f1.setColor(22, 63, 117);
                Font f2 = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD,  BaseColor.BLUE);
                f2.setColor(22, 63, 117);
                Font f3 = new Font(FontFamily.TIMES_ROMAN, 12);
                    
                Paragraph paragraph = new Paragraph("Recaída ou Lapso",f1);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                paragraph.add(new Paragraph(" "));
                paragraph.add(new Paragraph(" "));

                paragraph = new Paragraph("Data de parada",f2);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getDataPararStr(),f3);
                document.add(paragraph);
                paragraph.add(new Paragraph(" "));
            
                paragraph = new Paragraph("Técnicas para fissura",f2);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getFissuraStr(),f3);
                document.add(paragraph);
                paragraph.add(new Paragraph(" "));
                
                paragraph = new Paragraph("Estratégias para resistir ao cigarro",f2);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getEvitarRecaidaFara1(),f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getEvitarRecaidaFara2(),f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getEvitarRecaidaFara3(),f3);
                document.add(paragraph);
                paragraph.add(new Paragraph(" "));
                               
                paragraph = new Paragraph("O que deu certo da última vez que parei",f2);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getEvitarRecaida1(),f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getEvitarRecaida2(),f3);
                document.add(paragraph);
                paragraph = new Paragraph(this.prontoParaParar.getEvitarRecaida3(),f3);
                document.add(paragraph);
                paragraph.add(new Paragraph(" "));

                document.close();                

                return os;
            }catch(Exception e){
                return null;
            }
        
        
        public StreamedContent getLapsoRecaida(){

            InputStream is;
            try{
                is = new ByteArrayInputStream(gerarPdf().toByteArray());
                return new DefaultStreamedContent(is, "application/pdf", "plano.pdf");
            }catch(Exception e){
              Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, "Erro ao gerar o pdf");
              return null;      
            }
                  

   }
                             
         
            
        }*/
        
        
 }
        
