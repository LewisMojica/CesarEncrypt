import javax.swing.*;
import java.awt.event.*;

public class Encryp extends JFrame implements ActionListener, ItemListener{
  boolean cryp = true; //esta variable determina si se va a encritar o a Desencriptar
  String alpha = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyzáéíóú\n.,:;¿? "; //alfabeto de referencia
  String outText; // texto encriptado
  long pass = 0;  //clave de encriptado
  private JTextArea inField; //entrada de datos a encriptar
  private JScrollPane scroll,scroll_2; //contenedor de datos encriptados
  private JTextArea outField; //salida de datos encriptados
  private JButton doEncryp; //este boton acciona el evento para encriptar
  private JPasswordField passField; //ccomtenedor de la clave
  private JLabel textPass, textNoEncryp, textEncryp; //textos que indican la funcion de cada contenedor
  private JComboBox combo_select_mode; //lista desplegable para selectionar encriptar o esencriptar
  public Encryp(){
    setLayout(null);

    combo_select_mode = new JComboBox(); //combo para seleccionar modo de trajajo: encriptar o desencriptar
    combo_select_mode.setBounds(450,110,100,30);
    add(combo_select_mode);
    combo_select_mode.addItem("Encryp"); //opcion a seleccionar para modo encritar
    combo_select_mode.addItem("Desencryp");//opcion a seleccionar para modo desencriptar
    combo_select_mode.addItemListener(this);

    inField = new JTextArea();
    /*inField.setBounds(50,90,200,30);
    add(inField);*/

    outField = new JTextArea();
    outField.setEditable(false);

    scroll = new JScrollPane(outField);
    scroll.setBounds(330,200,220,200);
    add(scroll);

    textEncryp = new JLabel("OUTPUT");
    textEncryp.setBounds(410,180,60,10);
    add(textEncryp);

    scroll_2 = new JScrollPane(inField);
    scroll_2.setBounds(50,200,220,200);
    add(scroll_2);

    textNoEncryp = new JLabel("INPUT");
    textNoEncryp.setBounds(130,180,60,10);
    add(textNoEncryp);

    doEncryp = new JButton("Make");
    doEncryp.setBounds(200,110,100,30);
    add(doEncryp);
    doEncryp.addActionListener(this);

    passField = new JPasswordField();
    passField.setBounds(50,110,100,30);
    add(passField);

    textPass = new JLabel("password");
    textPass.setBounds(65,70,70,30);
    add(textPass);

  }

  public void itemStateChanged(ItemEvent e){
    if (e.getSource() == combo_select_mode){
      if(combo_select_mode.getSelectedItem().toString().equals("Encryp")){
        cryp = true;
      }
      else{
        cryp = false;
      }
    }
  }

  public void actionPerformed(ActionEvent e){
    String err_state = noErr(inField.getText());
    if (isNumeric(err_state)){
      pass = Long.parseLong(passField.getText());
      if ((e.getSource() == doEncryp)){
        outField.setText(makeEncryp(inField.getText(), pass, cryp)); //se encripata el texto y se imprime en pantalla
        selectOutText();
      }
    } else{
      outField.setText(err_state);
    }

  }
  public String makeEncryp(String in, long pass, boolean _cryp){
    String out = "";
    // in = in.trim().toUpperCase();
    long temp_pass;
    if (_cryp){
      temp_pass = pass;
    } else {
      if (pass > alpha.length()){
        temp_pass = alpha.length() - (pass % alpha.length());
      } else{
      temp_pass = alpha.length() - pass;
      }
    }

    for (int i = 0; i < in.length(); i++ ) {  //encriptacion
      long coded = (alpha.indexOf(in.charAt(i))) + temp_pass;
      if (coded > alpha.length() - 1){
        coded = ((coded + 1) % alpha.length()) - 1;
      }
      out += alpha.charAt((int)coded);
    }
    return out;
  }

  public String noErr(String in){ //comprobar si hay caracteres que no están contenidos en el alfabeto
    String errors = "Errores:\n";
    int err = 0;

    ///////verifica si el texto de entrada esta vacio
    if (in.isEmpty()){
      errors += "*Texto de entrada vacio\n";
    }
    //////verifica si el valor de la clave es un factor del largo del alfabeto
    if(!passField.getText().isEmpty() && isNumeric(passField.getText()) && (Integer.parseInt(passField.getText()) % alpha.length()) == 0){
        errors += "*Clave insegura, intente con otro número que no sea factor de " + alpha.length() + "\n";

    }
    //////verifica si la clave es numerica
    if (!passField.getText().isEmpty() && !(isNumeric(passField.getText()))){
      errors += "*La clave debe estar conformada por números exculsivamente\n";
    }
    // verifica si la clave contiene caracteres
    if (passField.getText().isEmpty()){
      errors += "*La clave está vacia\n";
    }
    // verifica si la clave es menor o igual a cero
    if(!passField.getText().isEmpty() && isNumeric(passField.getText()) && Integer.parseInt(passField.getText()) <= 0){
        errors += "*La clave debe ser mayor que cero\n";

    }
    //verifica si los caracteres del texto de entras estan contenidos en el alfabeto
    for (int i = 0; i < in.length(); i++){
      for (int e = 0; e < alpha.length(); e++){
        if (alpha.charAt(e) == in.charAt(i)){
          err++;
        }
      }
    }
    if (!(err == in.length())){
      errors += "*Uno o varios caracteres no estan dentro del alfabeto\n";;
    }
    if (errors.equals("Errores:\n")){
      errors = "0";
    }
    return errors;
  }

  public boolean isNumeric(String test){
    int checked = 0;
    String numbers = "0123456789";
    for (int i = 0; i< test.length(); i++){
      for (int e = 0; e < numbers.length(); e++){
        if (test.charAt(i) == numbers.charAt(e)){
          checked++;
        }
      }
    }
    if (checked != test.length()) {
      return false;
    }
    else{
      return true;
    }
  }
  //sobrea el texto de salida para que el usuario pueda copiarlo facilmente
  public void selectOutText(){
    outField.requestFocus();
    outField.selectAll();
  }


  public static void main(String[] args) {
    Encryp interfac = new Encryp();
    interfac.setBounds(0,0,600,500);
    interfac.setVisible(true);
    interfac.setResizable(false);
    interfac.setLocationRelativeTo(null);

  }
}
