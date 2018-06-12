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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
            if (!enalgo.areKeysPresent())
            {
                // Method generates a pair of keys using the RSA algorithm and stores it
                // in their respective files
                enalgo.generateKey();
            }
             ObjectInputStream inputStream = null;
             String text=etext.getText();
              inputStream = new ObjectInputStream(new FileInputStream("C:/keys/public"));
            final PublicKey publicKey = (PublicKey) inputStream.readObject();
            final byte[] cipherText = enalgo.encrypt(text, publicKey);
            String en=cipherText.toString();
            dtext.setText(en);

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
          byte[] ebyte = encrypted.getBytes();
         inputStream1 = new ObjectInputStream(new FileInputStream("C:/keys/private"));
            final PrivateKey privateKey = (PrivateKey) inputStream1.readObject();
            final String plainText = enalgo.decrypt(ebyte, privateKey);
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
