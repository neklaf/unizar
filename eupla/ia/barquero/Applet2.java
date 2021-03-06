/**
    Oomentario:
        Esta clase aporta la interfaz gr�fica para poder representar gr�ficamente el problema
        del granjero que tiene que pasar una oveja, una lechuga y un lobo de una orilla del 
        r�o a otra sin que �stos se coman entre s�.
        
        @author Aitor Acedo
*/

import java.awt.*;
import java.applet.*;

/**
    La clase p�blica Applet2 es derivada de Applet y en ella estar�n las variables que formar�n 
    parte del problema.    
*/

public class Applet2 extends Applet
{   public int i=1;
    String o1="orilla A";
	String o2="orilla B";
    public oveja doly=new oveja(o1);
	public lechuga le=new lechuga(o1);
	public lobo l=new lobo(o1);	
	public orilla a=new orilla(o1,doly,l,le);
	public orilla b=new orilla(o2,null,null,null);
		
	/**
	    El mensaje inc() incrementa un contador que tiene la clase Applet2 para poder
	    realizar las movimientos de los animales paso a paso.
	*/
	void inc(){
	    i++;   
	}
	
	/**
	    El m�todo init es el que inicializar� los componentes que se representar�n en la
	    superficie del applet.
	    Aqu� podremos encontrar los textField's y los botones que m�s tarde se dibujar�n 
	    en el applet.
	*/
	public void init()
	{
		// Take out this line if you don't use symantec.itools.net.RelativeURL or symantec.itools.awt.util.StatusScroller
		symantec.itools.lang.Context.setApplet(this);
	
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setSize(426,266);
		button1.setLabel("Solucion Paso a Paso");
		add(button1);
		button1.setBackground(java.awt.Color.lightGray);
		button1.setBounds(96,204,144,40);
		button2.setLabel("Inicializa");
		add(button2);
		button2.setBackground(java.awt.Color.lightGray);
		button2.setBounds(264,204,72,40);
		add(textField1);
		textField1.setBounds(72,48,100,40);
		add(textField2);
		textField2.setBounds(168,48,100,40);
		add(textField3);
		textField3.setBounds(264,48,100,40);
		add(textField4);
		textField4.setBounds(72,144,100,40);
		add(textField5);
		textField5.setBounds(168,144,100,40);
		add(textField6);
		textField6.setBounds(264,144,100,40);
		label1.setText("Orilla 1");
		label1.setAlignment(java.awt.Label.CENTER);
		add(label1);
		label1.setBounds(180,0,100,40);
		label2.setText("Orilla2");
		label2.setAlignment(java.awt.Label.CENTER);
		add(label2);
		label2.setBounds(168,108,100,40);
		//}}
	
		//{{REGISTER_LISTENERS
		/**
		    Tambi�n dentro del init() se definen los ActionListeners que ser�n los encargados 
		    de recibir los eventos realizados por los componentes del applet.
		*/
		SymAction lSymAction = new SymAction();
		button1.addActionListener(lSymAction);
		button2.addActionListener(lSymAction);
		//}}
	}
	
	//{{DECLARE_CONTROLS
	java.awt.Button button1 = new java.awt.Button();
	java.awt.Button button2 = new java.awt.Button();
	java.awt.TextField textField1 = new java.awt.TextField();
	java.awt.TextField textField2 = new java.awt.TextField();
	java.awt.TextField textField3 = new java.awt.TextField();
	java.awt.TextField textField4 = new java.awt.TextField();
	java.awt.TextField textField5 = new java.awt.TextField();
	java.awt.TextField textField6 = new java.awt.TextField();
	java.awt.Label label1 = new java.awt.Label();
	java.awt.Label label2 = new java.awt.Label();
	//}}
	
	/**
	    El m�todo inicializa() da el valor inicial a cada una de las variables que forman
	    parte del problema como por ejemplo las orillas y adem�s inicializa los controles.
	*/
    void inicializa(){
        a=new orilla(o1,doly,l,le);
        b=new orilla(o2,null,null,null);
        i=1;//importante!!
        textField1.setText("oveja");         
        textField2.setText("lobo");         
        textField3.setText("lechuga"); 
        textField4.setText("");         
        textField5.setText("");         
        textField6.setText("");         
    }
    
        
    //Se podr�a arreglar si agruparamos los textFields en un array
    /**
        Representa el estado de las orillas en la pantalla del applet.
        Tiene que verificar previamente si en la orilla los objetos de tipo ser, tienen una 
        instancia creada o por el contrario su valor es null.
    */
    void MostrarOrillas(){
           
                if (a.huespedes[0]!=null){
                    textField1.setText(a.huespedes[0].tipo);         
                }else{
                    textField1.setText("");         
                }
                if (a.huespedes[1]!=null){
                    textField2.setText(a.huespedes[1].tipo);         
                }else{
                    textField2.setText("");         
                }
                if (a.huespedes[2]!=null){
                    textField3.setText(a.huespedes[2].tipo);         
                }else{
                    textField3.setText("");         
                }
                if (b.huespedes[0]!=null){
                    textField4.setText(b.huespedes[0].tipo);         
                }else{
                    textField4.setText("");         
                }
                if (b.huespedes[1]!=null){
                    textField5.setText(b.huespedes[1].tipo);         
                }else{
                    textField5.setText("");         
                }
                if (b.huespedes[2]!=null){
                    textField6.setText(b.huespedes[2].tipo);         
                }else{
                    textField6.setText("");         
                }                           
    }
    
    /**
        Este m�todo es el que invocar� el bot�n de realizar la soluci�n paso a paso
        y que ejecutar� ordenadamente las reglas necesarias para que los animales puedan
        llegar a la orilla contraria satisfactoriamente.
        
        @param El par�metro "i" es un par�metro pasado por valor, se utiliza para indicarle
        al m�todo cual es el n�mero de la regla que tiene que lanzarse.
    */
    void HacerSolucion(int i){
          
           switch(i){
                case 1: a.quitaSer(doly);
		                b.colocaEnOrilla(doly);		                
                        break;
                        
               case 2:  a.quitaSer(l);
		                b.colocaEnOrilla(l);		                
                        break;
                        
                case 3: b.quitaSer(doly);
		                a.colocaEnOrilla(doly);		                
                        break;
                        
                case 4: a.quitaSer(le);
		                b.colocaEnOrilla(le);
		                break;
                        
                case 5: a.quitaSer(doly);
		                b.colocaEnOrilla(doly);		                
                        break;
                
           }
    }
    
    /**
        La clase SymAction ser� la encargada de implentar el c�digo necesario para capturar 
        los eventos lanzados por los componentes que forman el applet.
        
        @see button1_ActionPerformed
        
        @see button2_ActionPerformed
    */
	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == button1)
				button1_ActionPerformed(event);
			else if (object == button2)
				button2_ActionPerformed(event);
		}
	}
    /**
        Este m�todo codifica la acci�n que realizar� el primer bot�n al ser pulsado.
        
        @param event ser� el evento recogido por Listener, dicho evento al ser capturado 
        generar� el c�digo que el programador haya asignado para el evento en cuesti�n.
    */
	void button1_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
		if(i<6){
		    HacerSolucion(i);
		    inc();
		    MostrarOrillas();
		}
		//a.muestraOrilla();
	}
    /**
        Y el m�todo siguiente tiene la misma finalidad que el anterior pero para el 
        segundo bot�n.
    */
	void button2_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
		inicializa();	 
	}
}
