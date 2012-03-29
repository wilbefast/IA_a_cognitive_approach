/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix.Position;
import game.Game.Player;
import java.util.List;


public class Frontier 
{ 
    /* CONSTANTS */
    protected static final String DEFAULT_S_SERVER_URL = 
                                "http://localhost:8084/game_service/ws";
    
  
    /* ATTRIBUTES */
   
    private Actuator actuator;
    private Sensor sensor;
    private int game_id;
    private Player player;
    
    
    
    /* METHODS */
    
    // creation
    public Frontier()
    {
        // boot up the interface
        BootstrapClient bootstrap = new BootstrapClient(DEFAULT_S_SERVER_URL);
        game_id = bootstrap.getId();
        sensor = bootstrap.getSensor();
        actuator = bootstrap.getActuator();
    }
    
    // query
    
    public boolean tryAction(Position move)
    {
        // to send information via the external interface
        return actuator.tryMove(game_id, player, move);
    }
    
    public Percept getPercept()
    {
        // to receive information via the external interface
        return sensor.getPercept(game_id, player);
    }

}
