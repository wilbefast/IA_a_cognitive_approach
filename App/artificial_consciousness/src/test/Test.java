/*****************
 * @author william
 * @date 31-Mar-2012
 *****************/


package test;

import agent.Agent;


public class Test 
{
    /* MAIN */
    
    public static void main(String[] args)
    {
        // create agent(s)
        Agent agent = new MTurkAgent();
        
        // main loop
        boolean stop = false;
        while(!stop)
        {
            // update the agent(s)
            agent.act();
            
            // stop if the agent breaks down or requests euthanasia 
            if(agent.getState() != Agent.State.NORMAL)
                stop = true;
        }
    }
}