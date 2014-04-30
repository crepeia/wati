/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wati.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Thiago
 */
@Entity
@Table(name="tb_acompanhamento_email")
public class AcompanhamentoEmail {
    
    @Transient
    final String MENSAGEM_DATA_DIFERENTE = "Olá! Não deixe de começar seu plano na data escolhida. Enviaremos e-mails de acompanhamento durante este período. Caso você queira mudar seu plano, entre no nosso site. \n Cordialmente, \n" +
"Equipe Viva sem Tabaco";
    @Transient
    final String MENSAGEM_PRIMEIRA_SEMANA = "Faz uma semana que você entrou no nosso programa - Viva sem Tabaco. \n Se você já conseguiu parar de fumar, você pode estar sentindo algumas mudanças positivas* no seu corpo:\n" +
"* Melhora do ritmo cardíaco, \n" +
"* Redução do nível de monoxído de carbono no sangue - o mesmo que sai dos escapamentos de carros e ônibus,\n" +
"* Redução do risco úlceras no estômago. Se você ainda, não conseguiu parar. \n" +
"Sabemos que o processo de parar de fumar é difícil. O importante é continuar tentando. Convidamos a voltar em nosso site e tentar um novo plano.\n Cordialmente, \n" +
"Equipe Viva sem Tabaco";  
    @Transient
    final String MENSAGEM_SEGUNDA_SEMANA = "Parabéns! Fazem duas semanas que você decidiu parar. Nesta semana, gostaríamos de falar brevemente sobre dois temas: desejo intenso de fumar (fissura) e o medo do ganho de peso.\n" +
"\n" +
"A fissura, ou desejo intenso de fumar, costuma demorar apenas alguns minutos. Veja abaixo algumas dicas para vencer a fissura:\n" +
"* Comer frutas, beber água podem ajudá-lo a vencer estes momentos. \n" +
"* Separamos para você, um exercício de relaxamento preparado por especialistas em tabagismo. Você pode acessá-lo ou fazer o download no site. \n" +
"\n" +
"Para evitar o ganho de peso, tente fazer exercícios físicos (como caminhada) e comer mais frutas e vegetais.\n Não conseguiu parar ainda? Teve uma recaída?\n" +
"Sabemos que o processo de parar de fumar é difícil. Temos algumas informações e exercícios que podem ajudá-lo em nosso site.\n Cordialmente, \n" +
"Equipe Viva sem Tabaco";  
    @Transient
    final String MENSAGEM_TERCEIRA_SEMANA = "Parabéns! Você está superando um dos maiores desafios de sua vida. E para comemorar, que tal comprar alguma coisa ou presentear alguém com o dinheiro economizado com o cigarro?\n" +
"\n" +
"Você já deve ter percebido melhoras no seu organismo como seu fôlego, auto-estima. Além disso a partir desta semana, o risco de ter um ataque cardíaco começa a reduzir. Seus pulmões começam a funcionar melhor*.\n" +
"\n" +
"Não conseguiu parar ainda? Teve uma recaída?\n" +
"Sabemos que o processo de parar de fumar é difícil. Temos algumas informações e exercícios que podem ajudá-lo em nosso site.\n Cordialmente, \n" +
"Equipe Viva sem Tabaco";  
    @Transient
    final String MENSAGEM_MENSAL = "Hora do acompanhamento mensal! Caso tenha alguma dúvida sobre tabagismo, não deixe de entrar em nosso programa \n" +
"\n" +
"Nós temos uma página no facebook e conta do twitter. Deixe um recado para gente ou para outras as pessoas que também estão tentando parar.\n" +
"\n" +
"Não conseguiu parar ainda? Teve uma recaída?\n" +
"Sabemos que o processo de parar de fumar é difícil. Temos algumas informações e exercícios que podem ajudá-lo em nosso site. \n Cordialmente, \n" +
"Equipe Viva sem Tabaco"; 
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "dataDiferente")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDiferente;
    @Column(name = "primeiraSemana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date primeiraSemana;
    @Column(name = "segundaSemana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date segundaSemana;
    @Column(name = "terceiraSemana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date terceiraSemana;
    @Column(name = "mensal")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date mensal;
    
    @OneToOne
    private User usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataDiferente() {
        return dataDiferente;
    }

    public void setDataDiferente(Date dataDiferente) {
        this.dataDiferente = dataDiferente;
    }

    

    public Date getPrimeiraSemana() {
        return primeiraSemana;
    }

    public void setPrimeiraSemana(Date primeiraSemana) {
        this.primeiraSemana = primeiraSemana;
    }

    public Date getSegundaSemana() {
        return segundaSemana;
    }

    public void setSegundaSemana(Date segundaSemana) {
        this.segundaSemana = segundaSemana;
    }

    public Date getTereiraSemana() {
        return terceiraSemana;
    }

    public void setTereiraSemana(Date tereiraSemana) {
        this.terceiraSemana = tereiraSemana;
    }

    public Date getMensal() {
        return mensal;
    }

    public void setMensal(Date mensal) {
        this.mensal = mensal;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
    
    
    
}
