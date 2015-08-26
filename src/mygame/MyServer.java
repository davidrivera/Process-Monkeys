package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.network.Client;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.JmeContext;
import com.jme3.scene.Spatial;
import com.jme3.light.DirectionalLight;
import com.jme3.light.AmbientLight;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.asset.plugins.HttpZipLocator;
import com.jme3.math.Vector3f;
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServer extends SimpleApplication {
    
    private static final int PORT = 6666;
    
    private Server mServer;
    
    private BitmapText myText;
    
    private Geometry mGeometry;
    
    // store state of scenegraph on server
    // have client messages update state of scenegraph
    // on update loop:
    //      // broadcast updates to the scenegraph
    //      every second, broadcast full scenegraph data
    //
    // scenegraph serialization will be basic:
    //      object name
    //      object position Vector3f
    //      object orientation Vector3f
    //  no scaling will be performed
    //  no new objects will be added to the scenegraph
    
    public static void main(String[] args) {
        MyServer app = new MyServer();
        
        app.start();
    }

    @Override
    public void simpleInitApp() {  
        mGeometry = generateBoxShit();
        mGeometry.setLocalTranslation(new Vector3f(1.0f, 1.0f, 0.0f));
        rootNode.attachChild(mGeometry);
        
        generateHUDText();
    }
    
    public void setMessage(String m){
        message = m;
    }

    private void startServer() {
        final MyServer thing = this;
              try {
            mServer = Network.createServer(PORT);
            Serializer.registerClass(ClientMessage.class);
            mServer.addMessageListener(new MessageListener() {

                public void messageReceived(Object source, Message m) {
                    System.out.println("I got a message!");
                    System.out.println(m.toString());
                    thing.setMessage(m.toString());
                }
            });
            mServer.addConnectionListener(new ConnectionListener() {

                public void connectionAdded(MyServer server, HostedConnection conn) {
                    System.out.println("something happened");
                }

                public void connectionRemoved(MyServer server, HostedConnection conn) {
                    System.out.println("something happened");
                }
            });
            mServer.start();
            
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Geometry generateBoxShit(){
        Box b = new Box(1, 1, 1);
        Geometry mGeometry = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        mGeometry.setMaterial(mat);
        
//        mGeometry.setLocalTranslation(new Vector3f(5.0f, 5.0f, 0.0f));
        return mGeometry;
    }
    
    private void generateHUDText()
    {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        myText = new BitmapText(guiFont, false);
        myText.setSize(guiFont.getCharSet().getRenderedSize());
        myText.setText("This is some random bullshit");
        myText.setLocalTranslation(300, myText.getLineHeight(), 0);
        guiNode.attachChild(myText);
    }
   


    @Override
    public void simpleUpdate(float tpf) {
//        mGeometry.rotate(0, 2*tpf, 0); 
//        mGeometry.scale(1.001f);
//        scene.scale(1.001f);
//        scene.scale(0.999f);
//        myText.setText(message);
        
        mGeometry.rotate(0, 2*tpf, 0); 
        List<Spatial> children = rootNode.getChildren();
//        myText.setText(children.get(0).toString());
//        myText.setText(children.get(1).toString());
        
        for(Spatial child : children) {
            Quaternion rot = child.getLocalRotation();
            Vector3f pos = child.getLocalTranslation();
            String name = child.getName();
            myText.setText(rot.toString());
            // add to list of things that are in the scene graph, include name
        }
        // 

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
}