/*****************
 * @author william
 * @date 26-Mar-2012
 *****************/

package service;

import game.Game;
import game.MorpionRules;
import game.Rules;
import java.util.HashMap;
import java.util.Map;


public class GameManager 
{
    /* SINGLETON */
    public static GameManager instance = null;
    public static synchronized GameManager getInstance()
    {
        if(instance == null)
            instance = new GameManager();
        return instance;
    }
    
    /* CLASS NAMESPACE ATTRIBUTES */
    private static int next_id = 0;
    
    /* ATTRIBUTES */
    
    private Rules rules;
    private Map<Integer, Game> games;
    
    /* METHODS */
    
    // singleton
    private GameManager()
    {
        games = new HashMap<Integer, Game>();
        rules = MorpionRules.getInstance();
    }
    
    // query
    public synchronized Game getGame(int id)
    {
        return games.get(id);
    }
    
    // modification
    public synchronized void setRules(Rules _rules)
    {
        rules = _rules;
    }
    
    public synchronized Game newGame()
    {
        // create and save the new game
        Game game = new Game(next_id, rules);
        games.put(next_id, game);
        
        // remember to increment the id generator!
        next_id++;
        
        // return the game we created
        return game;
    }
    
    
}
