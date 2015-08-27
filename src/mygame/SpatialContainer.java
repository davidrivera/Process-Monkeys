/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author drivera
 */
public class SpatialContainer {
    private Quaternion mQuaternion;
    private Vector3f mVector3f;
    public SpatialContainer(Quaternion q, Vector3f v){
        this.mQuaternion = q;
        this.mVector3f = v;
    }
}
