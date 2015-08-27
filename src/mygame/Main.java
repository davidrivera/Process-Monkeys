package mygame;


import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Spatial;
import com.jme3.light.DirectionalLight;
import com.jme3.light.AmbientLight;
import com.jme3.math.Vector3f;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends SimpleApplication {
    
    private static final int PORT = 6666;
    
    private Geometry mGeometry;
    
    private BitmapText myText;
    
    private Client mClient;
    
    private Main mServer;
    
    private String message;
    
    private Spatial scene;
    private Spatial crap;
    
    private Random rand;
    
    private SceneGraphMessage recentState;

    public static void main(String[] args) {
        Main app = new Main();
        Serializer.registerClass(ClientMessage.class);
        Serializer.registerClass(SceneGraphMessage.class);
        Serializer.registerClass(SpatialContainer.class);
        app.start();
    }

    @Override
    public void simpleInitApp() {

        rand = new Random();
        
        mGeometry = generateBoxShit();
        mGeometry.setLocalTranslation(new Vector3f(1.0f, 1.0f, 0.0f));
        rootNode.attachChild(mGeometry);
        
        Spatial colbert = putThisFuckinModelOntoTheScene("colbertrex.obj");
        
        Spatial demon = putThisFuckinModelOntoTheScene("demon-ball.obj");

        addDirectionalLight(1.0f, 0.0f, 0.0f);
        addDirectionalLight(-1.0f, 0.0f, 0.0f);
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(ambient);
        
        
        generateHUDText();
         
//        startServer();
//        
        startClient();

//        rootNode.attachChild(mGeometry);
    }
    
    private Spatial putThisFuckinModelOntoTheScene(String name) {
        Spatial crap = assetManager.loadModel("Models/" + name);
        crap.setLocalTranslation(new Vector3f(rand.nextFloat()*10.0f, 0.1f, rand.nextFloat()*10.0f));
        rootNode.attachChild(crap);
        
        return crap;
    }
    
    public void setMessage(String m){
        message = m;
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
    
    private void startClient()
    {
        try {
            mClient = Network.connectToServer("192.168.1.128", PORT);
            mClient.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        final Main self = this;
        
        mClient.addMessageListener(new MessageListener() {

            public void messageReceived(Object source, Message m) {
                if (m instanceof SceneGraphMessage) {
                    self.recentState = (SceneGraphMessage) m;
                }
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
//        Timer t = new Timer();
//        final int count = 0;
//        t.scheduleAtFixedRate(new TimerTask() {
//
//            @Override
//            public void run() {
////                if(!mServer.isRunning()){
////                    return;
////                }
//                System.out.println("I'm gonna send a message");
//                ClientMessage m = new ClientMessage("HELLO FROM CLIENT"+String.valueOf(count));
//                mClient.send(m);
//                
//            }
//        }, 10l, 10l);
    }
    
//    private void startServer() {
//        final Main thing = this;
//              try {
//            mServer = Network.createServer(PORT);
//            Serializer.registerClass(ClientMessage.class);
//            mServer.addMessageListener(new MessageListener() {
//
//                public void messageReceived(Object source, Message m) {
//                    System.out.println("I got a message!");
//                    System.out.println(m.toString());
//                    thing.setMessage(m.toString());
//                }
//            });
//            mServer.addConnectionListener(new ConnectionListener() {
//
//                public void connectionAdded(Server server, HostedConnection conn) {
//                    System.out.println("something happened");
//                }
//
//                public void connectionRemoved(Server server, HostedConnection conn) {
//                    System.out.println("something happened");
//                }
//            });
//            mServer.start();
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
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
        HashMap<String, SpatialContainer> h = recentState.getHash();
        for(String name: h.keySet()){
//                        System.out.println(name);
            SpatialContainer c = h.get(name);
            Spatial thing = rootNode.getChild(name);
            thing.setLocalRotation(c.getRot());
            thing.setLocalTranslation(c.getPos());
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void addDirectionalLight(float x, float y, float z) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(x,y,z));
        rootNode.addLight(sun);
    }
    
}
