/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ClientMessage extends AbstractMessage{
      private String shit;       
  
      public ClientMessage() {}  
  
      public ClientMessage(String s) { shit = s; } 
}
