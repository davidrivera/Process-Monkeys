package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
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
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends SimpleApplication {
    
    private static final int PORT = 6666;
    
    private Geometry mGeometry;
    
    private BitmapText myText;
    
    private Client mClient;
    
    private Server mServer;
    
    private String message;

    public static void main(String[] args) {
        Main app = new Main();
        
        app.start();
    }

    @Override
    public void simpleInitApp() {

        generateBoxShit();
        
        generateHUDText();
         
        startServer();
        
        startClient();

        rootNode.attachChild(mGeometry);
    }
    
    public void setMessage(String m){
        message = m;
    }
    
    private void generateBoxShit(){
                Box b = new Box(1, 1, 1);
        mGeometry = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Brown);
        mGeometry.setMaterial(mat);
    }
    
    private void startClient()
    {
        try {
            mClient = Network.connectToServer("localhost", PORT);
            mClient.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Timer t = new Timer();
        final int count = 0;
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if(!mServer.isRunning()){
                    return;
                }
                System.out.println("I'm gonna send a message");
                ClientMessage m = new ClientMessage("HELLO FROM CLIENT"+String.valueOf(count));
                mClient.send(m);
                
            }
        }, 10l, 10l);
    }
    
    private void startServer() {
        final Main thing = this;
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

                public void connectionAdded(Server server, HostedConnection conn) {
                    System.out.println("something happened");
                }

                public void connectionRemoved(Server server, HostedConnection conn) {
                    System.out.println("something happened");
                }
            });
            mServer.start();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        mGeometry.rotate(0, 2*tpf, 0); 
        myText.setText(message);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
}
