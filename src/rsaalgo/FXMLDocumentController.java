/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaalgo;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author ujjwal
 */
public class FXMLDocumentController implements Initializable {
    EncryptionUtil enalgo;
    
    @FXML
    private Label label;
    
    @FXML
    public TextField dtext;

    @FXML
    public TextField etext;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
          try
        {
            enalgo= new EncryptionUtil();

            // Check if the pair of keys are present else generate those.
            if (!EncryptionUtil.areKeysPresent())
            {
                // Method generates a pair of keys using the RSA algorithm and stores it
                // in their respective files
                EncryptionUtil.generateKey();
            }
             ObjectInputStream inputStream ;
             String text=etext.getText();
             byte[] message = text.getBytes("UTF8");
              inputStream = new ObjectInputStream(new FileInputStream("C:/keys/public"));
            final PublicKey publicKey = (PublicKey) inputStream.readObject();
            final byte[] cipherText = EncryptionUtil.encrypt(message, publicKey);
            
             dtext.setText( DatatypeConverter.printBase64Binary(cipherText));

            System.out.println("encrypted text"+cipherText);
        }catch(Exception e){
         e.printStackTrace();
        }
 
    }
     /**
     *
     * @param event
     */
      @FXML
    private void decryptbutton(ActionEvent event) {
        ObjectInputStream inputStream1 = null;
        try{
          String encrypted=dtext.getText();
          byte[] fin =DatatypeConverter.parseBase64Binary(encrypted);
         inputStream1 = new ObjectInputStream(new FileInputStream("C:/keys/private"));
            final PrivateKey privateKey = (PrivateKey) inputStream1.readObject();
            final String plainText = EncryptionUtil.decrypt(fin, privateKey);
            dtext.setText(plainText);
        }catch(Exception e)
        {
         e.printStackTrace();   
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
