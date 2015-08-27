/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author drivera
 */
@Serializable
public class SpatialContainer  {
    private Quaternion mQuaternion;
    private Vector3f mVector3f;
    public SpatialContainer(){}
    public SpatialContainer(Quaternion q, Vector3f v){
        this.mQuaternion = q;
        this.mVector3f = v;
    }
    
    public Quaternion getRot() {
        return mQuaternion;
    }
    
    public Vector3f getPos() {
        return mVector3f;
    }
}
