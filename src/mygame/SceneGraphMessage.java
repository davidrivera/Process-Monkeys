/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.util.HashMap;

/**
 *
 * @author drivera
 */
@Serializable
public class SceneGraphMessage extends AbstractMessage {
           
      public HashMap<String, SpatialContainer> hash;
    
      public SceneGraphMessage() {
          hash = new HashMap<String, SpatialContainer>();
      }  
     
      
      public void pushSomething(Quaternion q, Vector3f v, String name){
          SpatialContainer a = new SpatialContainer(q, v);
          hash.put(name, a);
      }
      
      public HashMap getHash(){
          return hash;
      }
}